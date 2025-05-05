package provider;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

@WebServlet("/Payment")
public class Payment extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

            
            int orderid = Integer.parseInt(request.getParameter("orderid"));
            System.out.println(orderid);

 

            try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sample", "root", "");
                 PreparedStatement ps = con.prepareStatement("SELECT provid, orderid, starting_time, provamount, ending_time, order_status, payment_status FROM orders WHERE orderid=?");

                ps.setInt(1, orderid);
                ResultSet rs = ps.executeQuery();

                // Start of HTML output
                out.println("<html>");
                out.println("<head>");
                out.println("<style>");
                out.println("body { font-family: Arial, sans-serif; }");
                out.println("h2 { color: #4CAF50; }");
                out.println("form { margin-top: 20px; }");
                out.println("label { font-weight: bold; margin-top: 10px; }");
                out.println("input[readonly] { background-color: #f0f0f0; border: 1px solid #ccc; padding: 10px; width: 250px; font-size: 16px; font-family: Arial, sans-serif; color: #333; }");
                out.println("button { background-color: #2196f3; color: white; border: none; padding: 10px 20px; font-size: 16px; cursor: pointer; margin-top: 20px; display: block; width: 250px; }");
                out.println("button:hover { background-color: #45a049; }");
                out.println("</style>");
                out.println("</head>");
                out.println("<body>");

                while (rs.next()) {
                    String provid = rs.getString("provid");
                    String start = rs.getString("starting_time");
                    String end = rs.getString("ending_time");
                    String status = rs.getString("order_status");
                    System.out.println(status);
                    int provamount = rs.getInt("provamount");

                    // Parse time and calculate duration
                   

                    if ("Completed".equalsIgnoreCase(status)) {
                         LocalTime t1 = LocalTime.parse(start);
                    LocalTime t2 = LocalTime.parse(end);

                    long secondsDifference = ChronoUnit.SECONDS.between(t1, t2);
                    long hours = secondsDifference / 3600;
                    long minutes = (secondsDifference % 3600) / 60;
                    long seconds = secondsDifference % 60;

                    long totalHours = hours;
                    int totalCost = (int) (totalHours * provamount);

                    // Add tax calculation (10% tax)
                    double taxPercentage = 0.10;
                    double taxAmount = totalCost * taxPercentage;
                    double finalAmount = totalCost + taxAmount;
                        out.println("<h2>Payment Details</h2>");
                        out.println("<form><table style='width:100%; border-collapse: collapse;'>");
                        out.println("<tr><td style='padding:12px;'><label>Order ID:</label></td><td><input type='text' value='" + orderid + "' readonly/></td></tr>");
                        out.println("<tr><td style='padding:12px;'><label>Provider ID:</label></td><td><input type='text' value='" + provid + "' readonly/></td></tr>");
                        out.println("<tr><td style='padding:12px;'><label>Amount/Hr:</label></td><td><input type='text' value='" + provamount + "' readonly/></td></tr>");
                        out.println("<tr><td style='padding:12px;'><label>Start Time:</label></td><td><input type='text' value='" + start + "' readonly/></td></tr>");
                        out.println("<tr><td style='padding:12px;'><label>End Time:</label></td><td><input type='text' value='" + end + "' readonly/></td></tr>");
                        out.println("<tr><td style='padding:12px;'><label>Time Difference:</label></td><td><input type='text' value='" + hours + " hours, " + minutes + " minutes, " + seconds + " seconds' readonly/></td></tr>");
                        out.println("<tr><td style='padding:12px;'><label>Total Cost:</label></td><td><input type='text' value='Rs." + totalCost + "' readonly/></td></tr>");
                        out.println("<tr><td style='padding:12px;'><label>Tax (10%):</label></td><td><input type='text' value='Rs." + taxAmount + "' readonly/></td></tr>");
                        out.println("<tr><td style='padding:12px;'><label>Final Amount (Including Tax):</label></td><td><input type='text' value='Rs." + finalAmount + "' readonly/></td></tr>");
                        out.println("</table>");
                        out.println("<button type='button' onclick='redirectToHome()'>Confirm Payment</button>");
                        out.println("</form>");

                        // Update payment status to 'received' for this order
                        String query = "UPDATE orders SET payment_status='received' WHERE orderid=?";
                        PreparedStatement pstmt = con.prepareStatement(query);
                        pstmt.setInt(1, orderid);
                        int result = pstmt.executeUpdate();

                        if (result > 0) {
                            out.println("<script type=\"text/javascript\">");
                            out.println("function redirectToHome() {");
                            out.println("    alert('Payment Processed Successfully!');");
                            out.println("    window.location.href = 'http://localhost:7750/Project/CustHome.html';");
                            out.println("}");  
                            out.println("</script>");
                        } 
                    
                } else {
                   out.println("<script type=\"text/javascript\">");
                       
                        out.println("    alert('Process can be made after work status is completed');");
                        out.println("    window.location.href = 'http://localhost:7750/Project/CustHome.html';");
                         out.println("</script>");
                }

                out.println("</body>");
                out.println("</html>");
            }
        } catch (Exception e) {
            e.printStackTrace(); // For debugging
            out.println("<p>Error: " + e.getMessage() + "</p>");
        }
    }
}