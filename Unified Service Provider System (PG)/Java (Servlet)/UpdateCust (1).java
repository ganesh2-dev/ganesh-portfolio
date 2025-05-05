package provider;

import java.sql.*;     
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

@WebServlet("/UpdateCust")
@MultipartConfig
public class UpdateCust extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			
			response.setContentType("text/html");
			PrintWriter pw=response.getWriter();

			//String user=request.getParameter("user");
                        //user=user.toUpperCase();
                        String custid=request.getParameter("id");
                       System.out.println(custid);
                        try {	
                            Class.forName("com.mysql.cj.jdbc.Driver");
			    Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/sample","root","");
                                
                                    String name=request.getParameter("name");
                                    String address=request.getParameter("address");
                                    String area=request.getParameter("area");
                                    String city=request.getParameter("city");
                                    long pincode= Long.parseLong(request.getParameter("pincode")); 
                                    String email=request.getParameter("email");
                                    long phnno = Long.parseLong(request.getParameter("phnno"));
                        
                                    PreparedStatement pstmt1 = con.prepareStatement("UPDATE custdetails SET name=?, address=?, area=?, city=?, pincode=?, email=?, phnno=? WHERE custid=?");
                                    
    
                                    pstmt1.setString(1,name);
                                    pstmt1.setString(2,address);
                                    pstmt1.setString(3,area);
                                    pstmt1.setString(4,city);
                                    pstmt1.setLong(5,pincode);
                                    pstmt1.setString(6,email);
                                    pstmt1.setLong(7,phnno);
                                    pstmt1.setString(8,custid);
                                    int rowsAffected1 = pstmt1.executeUpdate();
                                    pw.println("<script type=\"text/javascript\">");
                                    pw.println("alert('Updated Successfully');");
                                    pw.println("location='http://localhost:7750/Project/CustHome.html';");
                                    pw.println("</script>");
                          
                        }
			catch(Exception e) {
			System.out.println(e);
                        }

	}

}