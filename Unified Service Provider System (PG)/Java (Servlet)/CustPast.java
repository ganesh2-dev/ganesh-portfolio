package provider;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/CustPast")
public class CustPast extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            // Load database driver and establish connection
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sample", "root", "");
            
            // HTML and CSS for the page
            out.println("<html><head><style>");
            out.println("pre{font-family: Arial, sans-serif;}");
            out.println("a.button {");
            out.println("    background-color: green;");
            out.println("    padding: 10px 20px;");
            out.println("    text-decoration: none;");
            out.println("    color: white;");
            out.println("    border-radius: 5px;");
            out.println("    font-size: 16px;");
            out.println("    transition: background-color 0.3s ease;");
            out.println("}");
            out.println("a.button:hover {");
            out.println("    background-color: darkgreen;");
            out.println("    color: white;");
            out.println("}");
            // Reject button styles
            out.println("a.reject {");
            out.println("    background-color: red;");
            out.println("    padding: 10px 20px;");
            out.println("    text-decoration: none;");
            out.println("    color: white;");
            out.println("    border-radius: 5px;");
            out.println("    font-size: 16px;");
            out.println("    transition: background-color 0.3s ease;");
            out.println("}");
            out.println("a.reject:hover {");
            out.println("    background-color: darkred;");
            out.println("    color: white;");
            out.println("}");
            out.println("</style></head>");
            out.println("<body style='font-family: Arial, sans-serif; margin: 20px; background-color: #f7f7f7;'><center>");
            
         String user= (String) getServletContext().getAttribute("user");

            if (user == null) {
                out.println("Session expired. Please log in again.");
                return;
            
            }
            
            String rejected = "Rejected"; // Status to check for rejected orders
            String payment="received";
            // Prepare SQL query to fetch orders for the provider
            pstmt = con.prepareStatement("SELECT orderid,date, time, provid, provname, provaddr, provarea, provcity, provphnno, order_status,payment_status FROM orders WHERE custid = ? AND (order_status=? OR payment_status=?)");
            pstmt.setString(1, user);
            pstmt.setString(2,rejected);
            pstmt.setString(3, payment); // Excluding rejected orders
            rs = pstmt.executeQuery();
            


            // Output the table headers and structure
            out.println("<form method='post' style='padding: 20px;'>");
            out.println("<br><br><table style='width:100%; border-collapse: collapse; margin-bottom: 20px;'>");
            out.println("<tr><th style='padding:12px; text-align:center; border:1px solid #ddd; background-color: #1976d2; color: white;'>Order Id</th>"+
"                   <th style='padding:12px; text-align:center; border:1px solid #ddd; background-color: #1976d2; color: white;'>Date</th>"+
                    "<th style='padding:12px; text-align:center; border:1px solid #ddd; background-color: #1976d2; color: white;'>Time</th>"+
                    "<th style='padding:12px; text-align:center; border:1px solid #ddd; background-color: #1976d2; color: white;'>Provider Id</th>"+
                    "<th style='padding:12px; text-align:center; border:1px solid #ddd; background-color: #1976d2; color: white;'>Provider Name</th>"+
                    "<th style='padding:12px; text-align:center; border:1px solid #ddd; background-color: #1976d2; color: white;'>Provider Address</th>"+
                    "<th style='padding:12px; text-align:center; border:1px solid #ddd; background-color: #1976d2; color: white;'>Provider Area</th>"+
                    "<th style='padding:12px; text-align:center; border:1px solid #ddd; background-color: #1976d2; color: white;'>Provider City</th>"+
                    "<th style='padding:12px; text-align:center; border:1px solid #ddd; background-color: #1976d2; color: white;'>Provider Phnno</th>"+
                    "<th style='padding:12px; text-align:center; border:1px solid #ddd; background-color: #1976d2; color: white;'>Status</th>");
            
            // If no orders are found, display a message
            boolean found = false;
            
            // Iterate through the results and display them in the table
            while (rs.next()) {
                String orderid=rs.getString("orderid");
                String date = rs.getString("date");
                String time = rs.getString("time");
                String provid = rs.getString("provid");
                String provname = rs.getString("provname");
                String provaddr = rs.getString("provaddr");
                String provarea = rs.getString("provarea");
                String provcity = rs.getString("provcity");
                long provphnno= rs.getLong("provphnno");
                String order_status = rs.getString("order_status");
                String payment_status=rs.getString("payment_status");

                // Display the data for accepted orders
                if(order_status.equals("Rejected")|| payment_status.equals("received")){
                out.println("<tr><td style='background-color:#f9f9f9;padding:12px;text-align:center;border:1px solid #ddd'>"+ orderid +"</td>" +
                        "<td style='background-color:#f9f9f9;padding:12px;text-align:center;border:1px solid #ddd;'>" + date + "</td>" +
                        "<td style='background-color:#f9f9f9;padding:12px;text-align:center;border:1px solid #ddd;'>" + time + "</td>" +
                        "<td style='background-color:#f9f9f9;padding:12px;text-align:center;border:1px solid #ddd;'>" + provid + "</td>" +
                        "<td style='background-color:#f9f9f9;padding:12px;text-align:center;border:1px solid #ddd;'>" + provname + "</td>" +
                        "<td style='background-color:#f9f9f9;padding:12px;text-align:center;border:1px solid #ddd;'>" + provaddr + "</td>" +
                        "<td style='background-color:#f9f9f9;padding:12px;text-align:center;border:1px solid #ddd;'>" + provarea + "</td>" +
                        "<td style='background-color:#f9f9f9;padding:12px;text-align:center;border:1px solid #ddd;'>" + provcity + "</td>" +
                        "<td style='background-color:#f9f9f9;padding:12px;text-align:center;border:1px solid #ddd;'>" + provphnno + "</td>" +
                        "<td style='background-color:#f9f9f9;padding:12px;text-align:center;border:1px solid #ddd;'>" +order_status+
                       "</td></tr>");
                
                found = true;} // If we found any records
            }

            // If no records found, show a message
            if (!found) {
                out.println("<tr><td colspan='10' style='padding:12px;text-align:center;border:1px solid #ddd;'>No past orders found.</td></tr>");
            }
            
            out.println("</table></form>");
            out.println("</center></body></html>");
        } catch (Exception e) {
            out.println("<p>Error: " + e.getMessage() + "</p>");
        } finally {
            // Close resources to avoid connection leaks
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}