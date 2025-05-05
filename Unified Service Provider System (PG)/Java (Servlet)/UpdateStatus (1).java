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

@WebServlet("/UpdateStatus")

public class UpdateStatus extends HttpServlet {
  
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            
        
        PrintWriter out=response.getWriter();
        String provid=request.getParameter("provid");
        System.out.println(provid);
          
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/sample","root","");
            String query="update provdetails set status='Approved' where provid=?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, provid);
            int result=pstmt.executeUpdate();
            response.sendRedirect("AdminHome.html");
        }
        catch(Exception e) {
                e.printStackTrace();
        }
    }
}