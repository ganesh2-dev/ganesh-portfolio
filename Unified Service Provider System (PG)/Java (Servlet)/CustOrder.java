package provider;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.*;
import jakarta.servlet.annotation.WebServlet;

@WebServlet("/CustOrder")
public class CustOrder extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        try {

            out.println("<html><body style='font-family: Arial,sans-serif;margin:20px;background-color:#f7f7f7;'><center>");

            String user= (String) getServletContext().getAttribute("user");
            if (user == null) {
                out.println("Session expired. Please log in again.");
                return;
            }

            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sample", "root", "");

            String order_status = "Rejected";

            PreparedStatement pstmt = con.prepareStatement("SELECT * FROM orders WHERE custid = ? AND order_status!=?");
            pstmt.setString(1, user);
            pstmt.setString(2, order_status);

            ResultSet rs = pstmt.executeQuery();
            out.println("<form method='post'  style='padding: 20px;'>");
            out.println("<br><br><table  style='width:100%;border-collapse:collapse;margin-bottom: 20px;'>");
            out.println("<tr><th style='padding:12px;text-align:center;border:1px solid #ddd;background-color:#1976d2;color:white;'>Order Id</th>" +
                        "<th style='padding:12px;text-align:center;border:1px solid #ddd;background-color:#1976d2;color:white;'>Date</th>"+
                        "<th style='padding:12px;text-align:center;border:1px solid #ddd;background-color:#1976d2;color:white;'>Time</th>"+
                        "<th style='padding:12px;text-align:center;border:1px solid #ddd;background-color:#1976d2;color:white;'>Starting Time</th>"+
                        "<th style='padding:12px;text-align:center;border:1px solid #ddd;background-color:#1976d2;color:white;'>Ending Time</th>"+ 
                        "<th style='padding:12px;text-align:center;border:1px solid #ddd;background-color:#1976d2;color:white;'>Provider Id</th>"+
                        "<th style='padding:12px;text-align:center;border:1px solid #ddd;background-color:#1976d2;color:white;'>Provider Name</th>"+
                        "<th style='padding:12px;text-align:center;border:1px solid #ddd;background-color:#1976d2;color:white;'>Provider Address</th>"+
                        "<th style='padding:12px;text-align:center;border:1px solid #ddd;background-color:#1976d2;color:white;'>Service</th>"+
                        "<th style='padding:12px;text-align:center;border:1px solid #ddd;background-color:#1976d2;color:white;'>Amount/hr</th>"+
                        "<th style='padding:12px;text-align:center;border:1px solid #ddd;background-color:#1976d2;color:white;'>Provider Phnno</th>"+
                        "<th style='padding:12px;text-align:center;border:1px solid #ddd;background-color:#1976d2;color:white;'>Order Status</th>"+
                        "<th style='padding:12px;text-align:center;border:1px solid #ddd;background-color:#1976d2;color:white;'>Payment</th>");
            boolean found=false;
            while (rs.next()) {
                int orderid=rs.getInt("orderid");
                String date = rs.getString("date");
                String time = rs.getString("time");
                String start=rs.getString("starting_time");
                String end=rs.getString("ending_time");
                String provid = rs.getString("provid");
                String provname = rs.getString("provname");

                Long provphnno = rs.getLong("provphnno");
                String provaddr = rs.getString("provaddr");
                String provservice=rs.getString("provservice");
                int provamount=rs.getInt("provamount");
                String order_status1 = rs.getString("order_status");
                String payment_status=rs.getString("payment_status");

                // Checking if start and end time are not empty
                if(!start.isEmpty() && !end.isEmpty()){
                    // Adding JavaScript function to show alert and redirection
                    out.println("<script type=\"text/javascript\">");
                    out.println("function showAlertAndRedirect() {");
                    out.println("    alert('Service is finished to pay click Payment!');");
                    out.println("    document.getElementById('payment-link').style.display = 'block';"); // Show payment link after alert
                    out.println("}");
                    out.println("</script>");
                }

                if(!payment_status.equals("received")){
                    out.println("<tr><td style='background-color:#f9f9f9;padding:12px;text-align:center;border:1px solid #ddd;'><center>" + orderid + "</td>" +
                                "<td style='background-color:#f9f9f9;padding:12px;text-align:center;border:1px solid #ddd;'><center>" + date + "</td>" +
                                "<td style='background-color:#f9f9f9;padding:12px;text-align:center;border:1px solid #ddd;'><center>" + time + "</td>" +
                                "<td style='background-color:#f9f9f9;padding:12px;text-align:center;border:1px solid #ddd;'><center>" + start + "</td>" +
                                "<td style='background-color:#f9f9f9;padding:12px;text-align:center;border:1px solid #ddd;'><center>" + end + "</td>" +
                                "<td style='background-color:#f9f9f9;padding:12px;text-align:center;border:1px solid #ddd;'><center>" + provid + "</td>" +
                                "<td style='background-color:#f9f9f9;padding:12px;text-align:center;border:1px solid #ddd;'><center>" + provname + "</td>" +
                                "<td style='background-color:#f9f9f9;padding:12px;text-align:center;border:1px solid #ddd;'><center>" + provaddr + "</td>" +
                                "<td style='background-color:#f9f9f9;padding:12px;text-align:center;border:1px solid #ddd;'><center>" + provservice + "</td>" +
                                "<td style='background-color:#f9f9f9;padding:12px;text-align:center;border:1px solid #ddd;'><center>" + provamount + "</td>" +
                                 "<td style='background-color:#f9f9f9;padding:12px;text-align:center;border:1px solid #ddd;'><center>" + provphnno + "</td>" +
                                "<td style='background-color:#f9f9f9;padding:12px;text-align:center;border:1px solid #ddd;'><center>" + order_status1 + "</td>" +
                               "<td style='background-color:#f9f9f9;padding:12px;text-align:center;border:1px solid #ddd;'><center><a href=Payment?orderid="+orderid+">Proceed</button></td>");
                    found = true;
                }
            }
            if(!found) {
                out.println("<tr><td colspan='13' style='padding:12px;text-align:center;border:1px solid #ddd;'>No present orders found.</td></tr>");
            }

            out.println("</table></form>");
            out.println("</center></body></html>");

        } catch (Exception e) {
            out.println(e);
        }
    }
}