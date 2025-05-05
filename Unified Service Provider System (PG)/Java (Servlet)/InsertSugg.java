package provider;

import java.sql.*;     
import java.io.IOException;
import java.io.InputStream;
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
import jakarta.servlet.http.Part;

@WebServlet("/InsertSugg")
public class InsertSugg extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			
			response.setContentType("text/html");
			PrintWriter pw=response.getWriter();

		String user= (String) getServletContext().getAttribute("user");
        String service=request.getParameter("service");
        String suggestion=request.getParameter("suggestion");
                       
                        try {	
                            Class.forName("com.mysql.cj.jdbc.Driver");
			    Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/sample","root","");
                            PreparedStatement pstmt = con.prepareStatement("insert into suggestion(userid,service_name,description)values(?,?,?)");
                                    pstmt.setString(1,user);          
                                    pstmt.setString(2, service);
                                    pstmt.setString(3,suggestion);
                                    int rowsAffected = pstmt.executeUpdate();
                                       pw.println("<script type=\"text/javascript\">");
                                    pw.println("alert('Suggestion send successfully');");
                                    pw.println("location='http://localhost:7750/Project/CustHome.html';");
                                    pw.println("</script>");
                        
                        
            
                        }
			catch(Exception e) {
                            pw.println(e);
                        }

	}

}