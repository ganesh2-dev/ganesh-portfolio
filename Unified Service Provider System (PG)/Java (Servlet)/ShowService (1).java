package provider;

import java.io.*;
import java.sql.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import java.util.Base64;

@WebServlet("/ShowService")
@MultipartConfig
public class ShowService extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Set content type for HTML output
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection( "jdbc:mysql://localhost:3306/sample", "root", "");

            String sql = "SELECT service_name,image_name ,image_data FROM services";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            
            out.println("<html><body>");
            out.println("<table style='border-collapse: collapse;'><tr>"); 
            int count = 0;
            while (rs.next()) {
                String imagename = rs.getString("service_name");
                InputStream imageData = rs.getBinaryStream("image_data");
                
                String base64Image = encodeImageToBase64(imageData);

                out.println("<td style='padding: 10px; text-align: center;'>");
                out.println("<a  href='FindProvider?servicename=" + imagename + "'><img src='data:image/jpeg;base64," + base64Image + "' alt='Image' width='300' height='200' /></a> <br>");
                out.println("<span>" + imagename + "</span>");
                out.println("</td>");

                count++;
                
                if (count % 4== 0) {
                    out.println("</tr><tr>");
                }
            }
           
            
            out.println("</table></body></html>");

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("<h3>Error fetching images: " + e.getMessage() + "</h3>");
        } 
    }

    // Helper method to convert InputStream to Base64 encoded string for embedding in HTML
    private String encodeImageToBase64(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[4096];
        int bytesRead;
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            byteArrayOutputStream.write(buffer, 0, bytesRead);
        }
        byte[] imageBytes = byteArrayOutputStream.toByteArray();
        return Base64.getEncoder().encodeToString(imageBytes); // Convert image bytes to Base64
    }
}