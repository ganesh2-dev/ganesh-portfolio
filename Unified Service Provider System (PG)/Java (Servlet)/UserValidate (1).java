package provider;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;



@WebServlet("/UserValidate")
public class UserValidate extends HttpServlet {

	
   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
 
        String user=request.getParameter("user");
         String letters = user.replaceAll("[^a-zA-Z]", "");
        
        String pass=request.getParameter("pass");
        
        getServletContext().setAttribute("user", user);
        
        System.out.println("hello"+user);
     
        PrintWriter pw=response.getWriter();
     

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn=DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/sample","root","");
			PreparedStatement ps=conn.prepareStatement("select * from login where userid=? and password=?");
			
            // Set parameters to prevent SQL injection
            ps.setString(1, user);
            ps.setString(2, pass);// In a real app, use hashed passwords!

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                if (letters.equals("cust")) {
                    pw.println("<script type=\"text/javascript\">");
                    pw.println("alert('Login successfully');");
                    pw.println("location='http://localhost:7750/Project/CustHome.html';");
                    pw.println("</script>");
                } else if (user.equals("admin")) {
                    
                    pw.println("<script type=\"text/javascript\">");
                    pw.println("alert('Login successfully');");
                    pw.println("location='http://localhost:7750/Project/AdminHome.html';");
                    pw.println("</script>");
                    
                }
                else if(letters.equals("prov")){
                   
                   
            PreparedStatement ps1 = conn.prepareStatement("select status from provdetails where provid=?");
            ps1.setString(1, user);
            ResultSet rs1 = ps1.executeQuery();
                   
                    if (rs1.next()) {
                        String status = rs1.getString("status");
                        System.out.println(status);
                        if("Approved".equals(status)){
                        pw.println("<script type=\"text/javascript\">");
                        pw.println("alert('Login successfully');");
                        pw.println("location='http://localhost:7750/Project/ProvHome.html';");
                        pw.println("</script>");// Cleaner redirect
                        } else {
                        pw.println("<script type=\"text/javascript\">");
                        pw.println("alert('You are not get approved to login');");
                        pw.println("location='http://localhost:7750/Project/First.html';");
                        pw.println("</script>");
                        }
                    }}
                
         } else {
                // Invalid username or password
                pw.println("<script type=\"text/javascript\">");
                pw.println("alert('Invalid username or password');");
                pw.println("location='http://localhost:7750/Project/First.html';");
                pw.println("</script>");
            }
        }
	catch(Exception e) {
            e.printStackTrace();
	}
		
	}

}