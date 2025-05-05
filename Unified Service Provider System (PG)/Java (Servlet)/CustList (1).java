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

@WebServlet("/CustList")
@MultipartConfig
public class CustList extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html><body style='font-family: Arial,sans-serif;margin:20px;background-color:#f7f7f7;'><center>");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sample", "root", "");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from custdetails");
            
            out.println("<form method='post'  style='padding: 20px;'>");
            out.println("<br><br><table  style='width:100%;border-collapse:collapse;margin-bottom: 20px;'>");
            out.println("<tr><th style='padding:12px;text-align:center;border:1px solid #ddd;background-color:#1976d2;color:white;'>ID</th>"
                    + "<th style='padding:12px;text-align:center;border:1px solid #ddd;background-color:#1976d2;color:white;'>Name</th>"
                    + "<th style='padding:12px;text-align:center;border:1px solid #ddd;background-color:#1976d2;color:white;'>Address</th>"
                    + "<th style='padding:12px;text-align:center;border:1px solid #ddd;background-color:#1976d2;color:white;'>Area</th>"
                    + "<th style='padding:12px;text-align:center;border:1px solid #ddd;background-color:#1976d2;color:white;'>City</th>"
                    + "<th style='padding:12px;text-align:center;border:1px solid #ddd;background-color:#1976d2;color:white;'>Email</th>"
                    + "<th style='padding:12px;text-align:center;border:1px solid #ddd;background-color:#1976d2;color:white;'>Phone Number</th>");

            boolean found = false;
            while (rs.next()) {
                String cust_id = rs.getString("custid");
                String name = rs.getString("name");
               String address=rs.getString("address");
                String area = rs.getString("area");
                String city = rs.getString("city");
               
                String email = rs.getString("email");
                Long phnno = rs.getLong("phnno");

        
                    out.println("<tr><td style='background-color:#f9f9f9;padding:12px;text-align:center;border:1px solid #ddd;'><center>" + cust_id + "</td>" +
                            "<td style='background-color:#f9f9f9;padding:12px;text-align:center;border:1px solid #ddd;'><center>" + name + "</td>" +
                                                       "<td style='background-color:#f9f9f9;padding:12px;text-align:center;border:1px solid #ddd;'><center>" + address + "</td>" +
                            "<td style='background-color:#f9f9f9;padding:12px;text-align:center;border:1px solid #ddd;'><center>" + area + "</td>" +
                            "<td style='background-color:#f9f9f9;padding:12px;text-align:center;border:1px solid #ddd;'><center>" + city + "</td>" +
                            "<td style='background-color:#f9f9f9;padding:12px;text-align:center;border:1px solid #ddd;'><center>" + email + "</td>" +
                            "<td style='background-color:#f9f9f9;padding:12px;text-align:center;border:1px solid #ddd;'><center>" + phnno + "</td>");
  found = true; 
              
                   
            }   if (!found) {
                out.println("<tr><td colspan='9' style='padding:12px;text-align:center;border:1px solid #ddd;'>No customers found.</td></tr>");
            }           
            out.println("</table></form></body></html>");
        } catch (Exception e) {
            out.println(e);
        }
    }}

   