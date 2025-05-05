package provider;

import java.sql.*;     
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.nio.file.Files;import jakarta.servlet.annotation.MultipartConfig;
import java.nio.file.Paths;import jakarta.servlet.annotation.WebServlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

@WebServlet("/DataInsert")
@MultipartConfig
public class DataInsert extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			
			response.setContentType("text/html");
			PrintWriter pw=response.getWriter();

			String user=request.getParameter("user");
                        String letters = user.replaceAll("[^a-zA-Z]", "");
                        System.out.println(letters);
                        String pass=request.getParameter("pass");
                       
                        try {	
                            Class.forName("com.mysql.cj.jdbc.Driver");
			    Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/sample","root","");
                            PreparedStatement pstmt = con.prepareStatement("insert into login(userid,password)values(?,?)");
                                if(letters.equals("cust")){
                                    pstmt.setString(1,user);          
                                    pstmt.setString(2, pass);
                                    int rowsAffected = pstmt.executeUpdate();
                                    
                                    String name=request.getParameter("name");
                                    String address=request.getParameter("address");
                                    String area=request.getParameter("area");
                                    String city=request.getParameter("city");
                                    long pincode= Long.parseLong(request.getParameter("pincode")); 
                                    String email=request.getParameter("email");
                                    long phnno = Long.parseLong(request.getParameter("phnno"));
                                 
                        
                                    PreparedStatement pstmt1 = con.prepareStatement("insert into custdetails(custid,name,address,area,city,pincode,email,phnno)values(?,?,?,?,?,?,?,?)");
                                    pstmt1.setString(1, user);
                                 
                                    pstmt1.setString(2,name);
                                    pstmt1.setString(3,address);
                                    pstmt1.setString(4,area);
                                    pstmt1.setString(5,city);
                                    pstmt1.setLong(6,pincode);
                                    pstmt1.setString(7,email);
                                    pstmt1.setLong(8,phnno);
                                    int rowsAffected1 = pstmt1.executeUpdate();
                                    pw.println("<script type=\"text/javascript\">");
                                    pw.println("alert('Signup successfully');");
                                    pw.println("location='http://localhost:7750/Project/First.html';");
                                    pw.println("</script>");
                                }
                                else{
                                    pstmt.setString(1,user);          
                                    pstmt.setString(2, pass);
                                    int rowsAffected = pstmt.executeUpdate();
                                    
                                    String name=request.getParameter("name");
                                    String address=request.getParameter("address");
                                    String area=request.getParameter("area");
                                    String city=request.getParameter("city");
                                    long pincode= Long.parseLong(request.getParameter("pincode")); 
                                    String email=request.getParameter("email");
                                    long phnno = Long.parseLong(request.getParameter("phnno"));
                                    
                                       Part filePart = request.getPart("photo");
                                       Part filePart1=request.getPart("proof");
                        String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
                        String fileName1 = Paths.get(filePart1.getSubmittedFileName()).getFileName().toString();
        // Convert the uploaded image into a byte array
        InputStream inputStream = filePart.getInputStream();
        byte[] imageBytes = new byte[inputStream.available()];
        inputStream.read(imageBytes);
                InputStream inputStream1 = filePart1.getInputStream();
        byte[] imageBytes1 = new byte[inputStream1.available()];
        inputStream1.read(imageBytes1);
                               
                                    String service=request.getParameter("service");
                                    String worktime=request.getParameter("wrktime");
                                    String amount=request.getParameter("amount");
                                    String status="null";
                                    

                                    
                                    PreparedStatement pstmt1 = con.prepareStatement("insert into provdetails(provid,name,address,"
                                            + "area,city,pincode,email,phnno,filename,filedata,filename1,filedata1,service,wrk_time,amount,status)values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
                                    pstmt1.setString(1,user);
                                    pstmt1.setString(2,name);
                                    pstmt1.setString(3,address);
                                    pstmt1.setString(4,area);
                                    pstmt1.setString(5,city);
                                    pstmt1.setLong(6,pincode);
                                    pstmt1.setString(7,email);
                                    pstmt1.setLong(8,phnno);
                                     pstmt1.setString(9, fileName);
            pstmt1.setBytes(10, imageBytes);
             pstmt1.setString(11, fileName1);
            pstmt1.setBytes(12, imageBytes1);
                                    pstmt1.setString(13, service);
                                    pstmt1.setString(14, worktime);
                                    pstmt1.setString(15, amount);
                                    pstmt1.setString(16, status);
                                    int rowsAffected1 = pstmt1.executeUpdate();
                                    
                                        pw.println("<script type=\"text/javascript\">");
                                        pw.println("alert('Signup successfully');");
                                        pw.println("location='http://localhost:7750/Project/First.html';");
                                        pw.println("</script>");
                                     
                                       
                                 
                                }
                        }
			catch(Exception e) {
                            pw.println(e);
                        }

	}

}