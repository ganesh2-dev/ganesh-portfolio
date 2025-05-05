package provider;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.*;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import java.util.Base64;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

@WebServlet("/ProvList")
@MultipartConfig
public class ProvList extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html><body style='font-family: Arial,sans-serif;margin-bottom:10px;background-color:#f7f7f7;'><center>");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sample", "root", "");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from provdetails");
            
            out.println("<form method='post'  style='padding: 50px;'>");
            out.println("<br><br><table  style='width:100%;border-collapse:collapse;margin-bottom: 40px;padding: 200px;'>");
            out.println("<tr><th style='padding:12px;text-align:center;border:1px solid #ddd;background-color:#1976d2;color:white;'>ID</th>"
                    + "<th style='padding:12px;text-align:center;border:1px solid #ddd;background-color:#1976d2;color:white;'>Name</th>"
                    + "<th style='padding:12px;text-align:center;border:1px solid #ddd;background-color:#1976d2;color:white;'>Area</th>"
                    + "<th style='padding:12px;text-align:center;border:1px solid #ddd;background-color:#1976d2;color:white;'>City</th>"
                    + "<th style='padding:12px;text-align:center;border:1px solid #ddd;background-color:#1976d2;color:white;'>Email</th>"
                    + "<th style='padding:12px;text-align:center;border:1px solid #ddd;background-color:#1976d2;color:white;'>Phone Number</th>"
                    + "<th style='padding:12px;text-align:center;border:1px solid #ddd;background-color:#1976d2;color:white;'>Photo</th>"
                    + "<th style='padding:12px;text-align:center;border:1px solid #ddd;background-color:#1976d2;color:white;'>ID Proof</th>"
                    + "<th style='padding:12px;text-align:center;border:1px solid #ddd;background-color:#1976d2;color:white;'>Service</th>"
                    + "<th style='padding:12px;text-align:center;border:1px solid #ddd;background-color:#1976d2;color:white;'>Working Time</th>"
                    + "<th style='padding:12px;text-align:center;border:1px solid #ddd;background-color:#1976d2;color:white;'>Amount/hr</th>"
                    + "<th style='padding:12px;text-align:center;border:1px solid #ddd;background-color:#1976d2;color:white;'>Status</th></tr>");
            boolean found = false;
            while (rs.next()) {
                String prov_id = rs.getString("provid");
                String name = rs.getString("name");
               
                String area = rs.getString("area");
                String city = rs.getString("city");
               
                String email = rs.getString("email");
                Long phnno = rs.getLong("phnno");

                // Get file names and image data
                String file_name = rs.getString("filename");
                String file_name1 = rs.getString("filename1");
                InputStream imageData1 = rs.getBinaryStream("filedata1");
                InputStream imageData2 = rs.getBinaryStream("filedata1");

                // Convert image data to base64 strings
                String base64Image1 = encodeImageToBase64(imageData1);
                String base64Image2 = encodeImageToBase64(imageData2);

                String service = rs.getString("service");
                String work_time = rs.getString("wrk_time");
                int amount = rs.getInt("amount");
                String status = rs.getString("status");

                if (status != null && status.equals("Approved")) {
                    out.println("<tr><td style='background-color:#f9f9f9;padding:12px;text-align:center;border:1px solid #ddd;'><center>" + prov_id + "</td>" +
                            "<td style='background-color:#f9f9f9;padding:12px;text-align:center;border:1px solid #ddd;'><center>" + name + "</td>" +
                            "<td style='background-color:#f9f9f9;padding:12px;text-align:center;border:1px solid #ddd;'><center>" + area + "</td>" +
                            "<td style='background-color:#f9f9f9;padding:12px;text-align:center;border:1px solid #ddd;'><center>" + city + "</td>" +
                            "<td style='background-color:#f9f9f9;padding:12px;text-align:center;border:1px solid #ddd;'><center>" + email + "</td>" +
                            "<td style='background-color:#f9f9f9;padding:12px;text-align:center;border:1px solid #ddd;'><center>" + phnno + "</td>" +
                            "<td style='background-color:#f9f9f9;padding:12px;text-align:center;border:1px solid #ddd;'><center><a href='data:image/jpeg;base64," + base64Image1+ "' download='image_" + file_name + ".jpg'>Download </a></td>" +
                            "<td style='background-color:#f9f9f9;padding:12px;text-align:center;border:1px solid #ddd;'><center><a href='data:image/jpeg;base64," + base64Image2+ "' download='image_" + file_name1 + ".jpg'>Download</td>" +
                            "<td style='background-color:#f9f9f9;padding:12px;text-align:center;border:1px solid #ddd;'><center>" + service + "</td>" +
                            "<td style='background-color:#f9f9f9;padding:12px;text-align:center;border:1px solid #ddd;'><center>" + work_time + "</td>" +
                            "<td style='background-color:#f9f9f9;padding:12px;text-align:center;border:1px solid #ddd;'><center>" + amount + "</td>" +
                            "<td style='color:green;background-color:#f9f9f9;padding:12px;text-align:center;border:1px solid #ddd;'><center>" + status + "</td></tr>");
                found = true; } else {
                      out.println("<tr><td style='background-color:#f9f9f9;padding:12px;text-align:center;border:1px solid #ddd;'><center>" + prov_id + "</td>" +
                            "<td style='background-color:#f9f9f9;padding:12px;text-align:center;border:1px solid #ddd;'><center>" + name + "</td>" +
                            "<td style='background-color:#f9f9f9;padding:12px;text-align:center;border:1px solid #ddd;'><center>" + area + "</td>" +
                            "<td style='background-color:#f9f9f9;padding:12px;text-align:center;border:1px solid #ddd;'><center>" + city + "</td>" +
                            "<td style='background-color:#f9f9f9;padding:12px;text-align:center;border:1px solid #ddd;'><center>" + email + "</td>" +
                            "<td style='background-color:#f9f9f9;padding:12px;text-align:center;border:1px solid #ddd;'><center>" + phnno + "</td>" +
                            "<td style='background-color:#f9f9f9;padding:12px;text-align:center;border:1px solid #ddd;'><center><a href='data:image/jpeg;base64," + base64Image1+ "' download='image_" + file_name + ".jpg'>Download </a></td>" +
                            "<td style='background-color:#f9f9f9;padding:12px;text-align:center;border:1px solid #ddd;'><center><a href='data:image/jpeg;base64," + base64Image2+ "' download='image_" + file_name1 + ".jpg'>Download</td>" +
                            "<td style='background-color:#f9f9f9;padding:12px;text-align:center;border:1px solid #ddd;'><center>" + service + "</td>" +
                            "<td style='background-color:#f9f9f9;padding:12px;text-align:center;border:1px solid #ddd;'><center>" + work_time + "</td>" +
                            "<td style='background-color:#f9f9f9;padding:12px;text-align:center;border:1px solid #ddd;'><center>" + amount + "</td>" +
                           
                "<td style='background-color:#f9f9f9;padding:12px;text-align:center;border:1px solid #ddd;'><a style='color:red;' href='UpdateStatus?provid=" + prov_id + "'><center>Approve</a></td></tr>");
               found = true;  }
            }
            if (!found) {
                out.println("<tr><td colspan='12' style='padding:12px;text-align:center;border:1px solid #ddd;'>No providers found.</td></tr>");
            }
            out.println("</table></form></body></html>");
        } catch (Exception e) {
            out.println(e);
        }
    }

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