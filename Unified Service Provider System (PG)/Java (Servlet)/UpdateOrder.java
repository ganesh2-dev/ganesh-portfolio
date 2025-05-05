package provider;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/UpdateOrder")
public class UpdateOrder extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
         String provid= (String) getServletContext().getAttribute("user");
        
        // Get customer ID and action (accept/reject) from the request parameters
                String date= request.getParameter("date");
        String time= request.getParameter("time");
        String custid = request.getParameter("custid");
        String accept = request.getParameter("accept");
        String reached=request.getParameter("reached");
        String completed=request.getParameter("completed");
        
       
        
        // Database connection and query execution
        try {
            // Load database driver and establish connection
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sample", "root", "");

            // Process 'Accept' action
            if (accept != null && accept.equals("accept")) {
                String query = "UPDATE orders SET order_status = 'Accepted' WHERE date=? AND time=? AND custid = ? AND provid = ? ";
                PreparedStatement pstmt = con.prepareStatement(query);
                pstmt.setString(1,date);
                pstmt.setString(2,time);
                pstmt.setString(3, custid);
                pstmt.setString(4, provid);
                int result = pstmt.executeUpdate();
                
                // If the update was successful, redirect to the provider's order page
                if (result > 0) {
                  response.sendRedirect("ProvHome.html");
                } else {
                    response.getWriter().println("Failed to update order status.");
                }
            }else if(completed != null && completed.equals("completed")){
                  String query1 = "UPDATE orders SET order_status = 'Completed' WHERE date=? AND time=? AND custid = ? AND provid = ?";
                PreparedStatement pstmt2 = con.prepareStatement(query1);
                pstmt2.setString(1, date);
                pstmt2.setString(2, time);
                pstmt2.setString(3, custid);
                pstmt2.setString(4, provid);
                int result = pstmt2.executeUpdate();
                
                    response.sendRedirect("ProvHome.html");
                
            }
            else {
                String query2 = "UPDATE orders SET order_status = 'Reached' WHERE date=? AND time=? AND custid = ? AND provid = ?";
                PreparedStatement pstmt1 = con.prepareStatement(query2);
                pstmt1.setString(1, date);
                pstmt1.setString(2, time);
                pstmt1.setString(3, custid);
                pstmt1.setString(4, provid);
                int result = pstmt1.executeUpdate();
                
                    response.sendRedirect("ProvHome.html");
                
            } 

        } catch (Exception e) {
            // Print stack trace for debugging
            e.printStackTrace();
            response.getWriter().println("Error occurred: " + e.getMessage());
        }
    }
}