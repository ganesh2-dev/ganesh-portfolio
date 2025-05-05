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

@WebServlet("/DeleteOrder")
public class DeleteOrder extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        // Get the provider ID from the session
        HttpSession session1 = request.getSession();
        String provid = (String) session1.getAttribute("user");
        System.out.println(provid + " welcome");
        
        // Get customer ID and action (accept/reject) from the request parameters
        String custid = request.getParameter("custid");
        String orderid=request.getParameter("orderid");
        String accept = request.getParameter("accept");
        String reject = request.getParameter("reject");
        
       
        
        // Database connection and query execution
        try {
            // Load database driver and establish connection
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sample", "root", "");

         
                String query1 = "UPDATE orders SET order_status = 'Rejected' WHERE orderid= ?";
                PreparedStatement pstmt1 = con.prepareStatement(query1);
            
                pstmt1.setString(1, orderid);
                int result = pstmt1.executeUpdate();
                
                // If the update was successful, redirect to the provider's order page
               
                    response.sendRedirect("ProvHome.html");
                
            

        } catch (Exception e) {
            // Print stack trace for debugging
            e.printStackTrace();
            response.getWriter().println("Error occurred: " + e.getMessage());
        }
    }
}