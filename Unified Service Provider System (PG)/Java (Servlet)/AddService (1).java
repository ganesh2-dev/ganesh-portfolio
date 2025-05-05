package provider;

import java.io.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.sql.*;
import java.nio.file.Files;import jakarta.servlet.annotation.MultipartConfig;
import java.nio.file.Paths;import jakarta.servlet.annotation.WebServlet;

@WebServlet("/AddService")
@MultipartConfig
public class AddService extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Set response content type
        response.setContentType("text/html");

        // Get the image file from the form input
        String service=request.getParameter("service");
        Part filePart = request.getPart("file");
        String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
        
        // Convert the uploaded image into a byte array
        InputStream inputStream = filePart.getInputStream();
        byte[] imageBytes = new byte[inputStream.available()];
        inputStream.read(imageBytes);

        // Database connection details
        String dbURL = "jdbc:mysql://localhost:3306/sample";
        String dbUsername = "root";
        String dbPassword = "";
        
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            // Establish database connection
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(dbURL, dbUsername, dbPassword);

            // SQL query to insert image into the database
            String sql = "INSERT INTO services (service_name,image_name, image_data) VALUES (?,?,?)";
            pstmt = conn.prepareStatement(sql);

            // Set the image name and image byte array to the prepared statement
            pstmt.setString(1,service);
            pstmt.setString(2, fileName);
            pstmt.setBytes(3, imageBytes);
            
            

            // Execute the query
            int row = pstmt.executeUpdate();
            if (row > 0) {
                response.getWriter().println("Image uploaded successfully!");
                response.sendRedirect("AdminHome.html");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Error uploading image: " + e.getMessage());
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }
}