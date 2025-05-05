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
@WebServlet("/ProvOrder")
public class ProvOrder extends HttpServlet {
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
            String status = "Rejected";
            pstmt = con.prepareStatement("SELECT * FROM orders WHERE provid = ? AND order_status != ?");
            pstmt.setString(1, user);
            pstmt.setString(2, status);
            
            rs = pstmt.executeQuery();
            
            out.println("<form method='post' style='padding: 20px;'>");
            out.println("<br><br><table style='width:100%; border-collapse: collapse; margin-bottom: 20px;'>");
            out.println("<tr><th style='padding:12px; text-align:center; border:1px solid #ddd; background-color: #1976d2; color: white;'>Order Id</th><th style='padding:12px; text-align:center; border:1px solid #ddd; background-color: #1976d2; color: white;'>Date</th>"+
                    "<th style='padding:12px; text-align:center; border:1px solid #ddd; background-color: #1976d2; color: white;'>Time</th>"+
                    "<th style='padding:12px; text-align:center; border:1px solid #ddd; background-color: #1976d2; color: white;'>Starting Time</th>"+
                    "<th style='padding:12px; text-align:center; border:1px solid #ddd; background-color: #1976d2; color: white;'>Ending Time</th>"+
                    "<th style='padding:12px; text-align:center; border:1px solid #ddd; background-color: #1976d2; color: white;'>Customer Id</th>"
                    + "<th style='padding:12px; text-align:center; border:1px solid #ddd; background-color: #1976d2; color: white;'>Customer Name</th>"
                    + "<th style='padding:12px; text-align:center; border:1px solid #ddd; background-color: #1976d2; color: white;'>Customer Address</th>"
                    + "<th style='padding:12px; text-align:center; border:1px solid #ddd; background-color: #1976d2; color: white;'>Customer Area</th>"
                    + "<th style='padding:12px; text-align:center; border:1px solid #ddd; background-color: #1976d2; color: white;'>Customer City</th>"
                    + "<th style='padding:12px; text-align:center; border:1px solid #ddd; background-color: #1976d2; color: white;'>Customer Phnno</th>"
                    + "<th style='padding:12px; text-align:center; border:1px solid #ddd; background-color: #1976d2; color: white;'>Status</th>");
            
            boolean found = false;
            while (rs.next()) {
                String orderid = rs.getString("orderid");
                String date = rs.getString("date");
                String time = rs.getString("time");
                 String starting_time = rs.getString("starting_time");
                   String ending_time = rs.getString("ending_time");
                String custid = rs.getString("custid");
                String custname = rs.getString("custname");
                String custaddr = rs.getString("custaddr");
                String custarea = rs.getString("custarea");
                String custcity = rs.getString("custcity");
                long custphnno = rs.getLong("custphnno");
                String order_status = rs.getString("order_status");
                String payment_status=rs.getString("payment_status");

                // Handle various order statuses
                if ("accepted".equalsIgnoreCase(order_status) || "rejected".equalsIgnoreCase(order_status)) {
                    out.println("<tr><td style='background-color:#f9f9f9;padding:12px;text-align:center;border:1px solid #ddd;'>" + orderid + "</td>" +
                             "<td style='background-color:#f9f9f9;padding:12px;text-align:center;border:1px solid #ddd;'>" + date + "</td>" +
                             "<td style='background-color:#f9f9f9;padding:12px;text-align:center;border:1px solid #ddd;'>" + time + "</td>" +
                             "<td style='background-color:#f9f9f9;padding:12px;text-align:center;border:1px solid #ddd;'>" + starting_time + "</td>" + 
                             "<td style='background-color:#f9f9f9;padding:12px;text-align:center;border:1px solid #ddd;'>" + ending_time + "</td>" +
                             "<td style='background-color:#f9f9f9;padding:12px;text-align:center;border:1px solid #ddd;'>" + custid + "</td>" +
                             "<td style='background-color:#f9f9f9;padding:12px;text-align:center;border:1px solid #ddd;'>" + custname + "</td>" +
                             "<td style='background-color:#f9f9f9;padding:12px;text-align:center;border:1px solid #ddd;'>" + custaddr + "</td>" +
                             "<td style='background-color:#f9f9f9;padding:12px;text-align:center;border:1px solid #ddd;'>" + custarea + "</td>" +
                             "<td style='background-color:#f9f9f9;padding:12px;text-align:center;border:1px solid #ddd;'>" + custcity + "</td>" +
                             "<td style='background-color:#f9f9f9;padding:12px;text-align:center;border:1px solid #ddd;'>" + custphnno + "</td>" +
                             "<td style='background-color:#f9f9f9;padding:12px;text-align:center;border:1px solid #ddd;'>" +
                             "<pre><a href='UpdateOrder?reached=reached&date=" + date + "&time=" + time + "&custid=" + custid + "'>Reached</td></tr>");
                    found = true;
                }else if ("Reached".equalsIgnoreCase(order_status)) {
    String updateQuery = "UPDATE orders SET starting_time = CURRENT_TIMESTAMP WHERE orderid = ?";
    PreparedStatement pstmt2 = con.prepareStatement(updateQuery);
    pstmt2.setString(1, orderid);
    int rowsAffected = pstmt2.executeUpdate();
    
    if (rowsAffected > 0) {
        String fetchQuery = "SELECT starting_time FROM orders WHERE orderid = ?";
        PreparedStatement pstmt3 = con.prepareStatement(fetchQuery);
        pstmt3.setString(1, orderid);
        ResultSet rs1 = pstmt3.executeQuery();
        
        if (rs1.next()) {
            starting_time = rs1.getString("starting_time");
            out.println("<tr><td style='background-color:#f9f9f9;padding:12px;text-align:center;border:1px solid #ddd;'>" + orderid + "</td>" +
                        "<td style='background-color:#f9f9f9;padding:12px;text-align:center;border:1px solid #ddd;'>" + date + "</td>" +
                        "<td style='background-color:#f9f9f9;padding:12px;text-align:center;border:1px solid #ddd;'>" + time + "</td>" +
                        "<td style='background-color:#f9f9f9;padding:12px;text-align:center;border:1px solid #ddd;'>" + starting_time + "</td>" +
                        "<td style='background-color:#f9f9f9;padding:12px;text-align:center;border:1px solid #ddd;'>" + ending_time + "</td>" +
                        "<td style='background-color:#f9f9f9;padding:12px;text-align:center;border:1px solid #ddd;'>" + custid + "</td>" +
                        "<td style='background-color:#f9f9f9;padding:12px;text-align:center;border:1px solid #ddd;'>" + custname + "</td>" +
                        "<td style='background-color:#f9f9f9;padding:12px;text-align:center;border:1px solid #ddd;'>" + custaddr + "</td>" +
                        "<td style='background-color:#f9f9f9;padding:12px;text-align:center;border:1px solid #ddd;'>" + custarea + "</td>" +
                        "<td style='background-color:#f9f9f9;padding:12px;text-align:center;border:1px solid #ddd;'>" + custcity + "</td>" +
                        "<td style='background-color:#f9f9f9;padding:12px;text-align:center;border:1px solid #ddd;'>" + custphnno + "</td>" +
                        "<td style='background-color:#f9f9f9;padding:12px;text-align:center;border:1px solid #ddd;'><a href='UpdateOrder?completed=completed&date=" + date + "&time=" + time + "&custid=" + custid + "'>Completed</td></tr>");
        }
    }
    found = true;


                    } else if ("Completed".equalsIgnoreCase(order_status)) {
                        if("received".equalsIgnoreCase(payment_status))
                        { out.println("<tr><td colspan='12' style='text-align:center;'></td></tr>");}else{
                   String updateQuery1 = "UPDATE orders SET ending_time = CURRENT_TIMESTAMP WHERE orderid = ?";
    PreparedStatement pstmt4 = con.prepareStatement(updateQuery1);
    pstmt4.setString(1, orderid);
    int rowsAffected = pstmt4.executeUpdate();
    
    if (rowsAffected > 0) {
        String fetchQuery = "SELECT ending_time FROM orders WHERE orderid = ?";
        PreparedStatement pstmt5 = con.prepareStatement(fetchQuery);
        pstmt5.setString(1, orderid);
        ResultSet rs2 = pstmt5.executeQuery();
        
        if (rs2.next()) {
            ending_time = rs2.getString("ending_time");
            out.println("<tr><td style='background-color:#f9f9f9;padding:12px;text-align:center;border:1px solid #ddd;'>" + orderid + "</td>" +
                        "<td style='background-color:#f9f9f9;padding:12px;text-align:center;border:1px solid #ddd;'>" + date + "</td>" +
                        "<td style='background-color:#f9f9f9;padding:12px;text-align:center;border:1px solid #ddd;'>" + time + "</td>" +
                        "<td style='background-color:#f9f9f9;padding:12px;text-align:center;border:1px solid #ddd;'>" + starting_time + "</td>" +
                        "<td style='background-color:#f9f9f9;padding:12px;text-align:center;border:1px solid #ddd;'>" + ending_time + "</td>" +
                        "<td style='background-color:#f9f9f9;padding:12px;text-align:center;border:1px solid #ddd;'>" + custid + "</td>" +
                        "<td style='background-color:#f9f9f9;padding:12px;text-align:center;border:1px solid #ddd;'>" + custname + "</td>" +
                        "<td style='background-color:#f9f9f9;padding:12px;text-align:center;border:1px solid #ddd;'>" + custaddr + "</td>" +
                        "<td style='background-color:#f9f9f9;padding:12px;text-align:center;border:1px solid #ddd;'>" + custarea + "</td>" +
                        "<td style='background-color:#f9f9f9;padding:12px;text-align:center;border:1px solid #ddd;'>" + custcity + "</td>" +
                        "<td style='background-color:#f9f9f9;padding:12px;text-align:center;border:1px solid #ddd;'>" + custphnno + "</td>" +
                        "<td style='background-color:#f9f9f9;padding:12px;text-align:center;border:1px solid #ddd;'>Completed</td></tr>");
        }
    }
    found = true;}

                } else {
                    out.println("<tr><td style='background-color:#f9f9f9;padding:12px;text-align:center;border:1px solid #ddd;'>" + orderid + "</td>" +
                            "<td style='background-color:#f9f9f9;padding:12px;text-align:center;border:1px solid #ddd;'>" + date + "</td>" +
                            "<td style='background-color:#f9f9f9;padding:12px;text-align:center;border:1px solid #ddd;'>" + time + "</td>" +
                            "<td style='background-color:#f9f9f9;padding:12px;text-align:center;border:1px solid #ddd;'>" + starting_time + "</td>" + 
                            "<td style='background-color:#f9f9f9;padding:12px;text-align:center;border:1px solid #ddd;'>" + ending_time + "</td>" +
                            "<td style='background-color:#f9f9f9;padding:12px;text-align:center;border:1px solid #ddd;'>" + custid + "</td>" +
                            "<td style='background-color:#f9f9f9;padding:12px;text-align:center;border:1px solid #ddd;'>" + custname + "</td>" +
                            "<td style='background-color:#f9f9f9;padding:12px;text-align:center;border:1px solid #ddd;'>" + custaddr + "</td>" +
                            "<td style='background-color:#f9f9f9;padding:12px;text-align:center;border:1px solid #ddd;'>" + custarea + "</td>" +
                            "<td style='background-color:#f9f9f9;padding:12px;text-align:center;border:1px solid #ddd;'>" + custcity + "</td>" +
                            "<td style='background-color:#f9f9f9;padding:12px;text-align:center;border:1px solid #ddd;'>" + custphnno + "</td>" +
                             "<td style='background-color:#f9f9f9;padding:12px;text-align:center;border:1px solid #ddd;'>" +
                            "<pre><a href='UpdateOrder?accept=accept&date="+date+"&time="+time+"&custid=" + custid + "' class='button'>Accept</a>" +
                            "  <a href='DeleteOrder?reject=reject&date="+date+"&orderid="+orderid+"&custid=" + custid + "' class='reject'>Reject</a></pre>" +
                            "</td></tr>");
                    found = true;
                }
            }

            if (!found) {
                out.println("<tr><td colspan='12' style='text-align:center;'>No present orders found</td></tr>");
            }

            out.println("</table>");
            out.println("</form>");
            out.println("</center></body></html>");
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
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