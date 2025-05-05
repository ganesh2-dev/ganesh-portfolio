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

@WebServlet("/AdminOrder")
public class AdminOrder extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            // Database connection
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sample", "root", "");
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Get session details
     String user= (String) getServletContext().getAttribute("user");

            // Session Check
            if (user == null) {
                out.println("<html><body><h2>Session expired. Please log in again.</h2></body></html>");
                return;
            }

            // Query to fetch orders
            pstmt = con.prepareStatement("SELECT * FROM orders");
            rs = pstmt.executeQuery();

             out.println("<html><body>");
out.println("<style>.status-accepted{color:green}" +
            ".status-rejected{color:red}" +
            "table {width:100%;border-collapse:collapse;margin-bottom: 20px;}" + // Added margin and other improvements
            "th, td {padding: 12px; text-align: center; border: 1px solid #ddd;}" +
            "th {background-color: #1976d2; color: white;}" +
            ".status-completed {color: green;}" +
            ".status-rejected {color: red;}" +
            "</style>");

out.println("<form method='post' style='padding: 20px;'>");
out.println("<br><br><table>");
out.println("<tr><th>Date</th><th>Time</th><th>Customer ID</th><th>Customer Name</th>"
            + "<th>Customer Area</th><th>Customer City</th><th>Customer Phnno</th>"
            + "<th>Provider ID</th><th>Provider Name</th><th>Provider Area</th>"
            + "<th>Provider City</th><th>Provider Phnno</th><th>Status</th></tr>");    

boolean found=false;
            while (rs.next()) {
                String date=rs.getString("date");
                String time=rs.getString("time");
                String custid = rs.getString("custid");
                String custname = rs.getString("custname");
             
                String custarea=rs.getString("custarea");
                String custcity= rs.getString("custcity");
                long custphnno = rs.getLong("custphnno");
                String provid = rs.getString("provid");
                String provname = rs.getString("provname");
      
                String provarea= rs.getString("provarea");
                String provcity= rs.getString("provcity");
                long provphnno = rs.getLong("provphnno");
                String status = rs.getString("order_status");

            
                String statusClass = "status-pending"; // Default class
                if ("Completed".equals(status)) {
                    statusClass = "status-completed";
                } else if ("Rejected".equals(status)) {
                    statusClass = "status-rejected";
                }else{
                    statusClass="status-pending";
                }

                out.println("<tr><td>"+date+"</td><td>"+time+"</td><td>" + custid + "</td>"
                        + "<td>" + custname + "</td>"
                        
                        + "<td>" + custarea + "</td>"
                        + "<td>" + custcity + "</td>"
                        + "<td>" + custphnno + "</td>"
                        + "<td>" + provid + "</td>"
                        + "<td>" + provname + "</td>"
                       
                        + "<td>" + provarea + "</td>"
                                + "<td>" + provcity+ "</td>"
                        + "<td>" + provphnno + "</td>"
                        + "<td class='" + statusClass + "'>" + status + "</td></tr>");
                found=true;
            }
            if(!found){
                  out.println("<tr><td colspan='13' style='padding:12px;text-align:center;border:1px solid #ddd;'>No orders found.</td></tr>");
            
            }
            

            out.println("</table></form></center>");
            out.println("</body></html>");
        } catch (Exception e) {
            out.println("<html><body><h2>An error occurred: " + e.getMessage() + "</h2></body></html>");
            e.printStackTrace();
        } finally {
            // Clean up database resources
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