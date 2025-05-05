package provider;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.PreparedStatement;

@WebServlet("/Book")
public class Book extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       
        response.setContentType("text/html;charset=UTF-8");
        String provid = request.getParameter("provid");

        PrintWriter out = response.getWriter();


             String custid= (String) getServletContext().getAttribute("user");

        try {
            // Database connection
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sample", "root", "");
            
            // Fetch provider details
            PreparedStatement pstmt = con.prepareStatement("SELECT provid, name,address,area,city,amount,service,phnno FROM provdetails WHERE provid=?");
            pstmt.setString(1, provid);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String provname = rs.getString("name");
                String provaddr=rs.getString("address");
                String provarea=rs.getString("area");
                String provcity=rs.getString("city");
                int provamount = rs.getInt("amount");
                String provservice=rs.getString("service");
                long provphnno = rs.getLong("phnno");

                // Fetch customer details
                PreparedStatement pstmt1 = con.prepareStatement("SELECT custid, name,address,area,city,email, phnno FROM custdetails WHERE custid=?");
                pstmt1.setString(1, custid);
                ResultSet rs1 = pstmt1.executeQuery();

                if (rs1.next()) {
                    String custname = rs1.getString("name");
                    String custaddr=rs.getString("address");
                    String custarea=rs.getString("area");
                    String custcity=rs.getString("city");
                    long custphnno = rs1.getLong("phnno");
                    
                    String status="Pending";
                    String payment_status="Null";
                    String starting="00:00:00";
                    String ending="00:00:00";
                    
                    PreparedStatement pstmt2 = con.prepareStatement(
                        "INSERT INTO orders (starting_time,ending_time,custid,custname,custaddr,custarea,custcity,custphnno,provid,provname,provaddr,provarea,provcity,provservice,provamount,provphnno,order_status,payment_status) " +
                        "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
                    pstmt2.setString(1,starting);
                    pstmt2.setString(2, ending);
                    pstmt2.setString(3, custid);
                    pstmt2.setString(4, custname);
                    pstmt2.setString(5, custaddr);
                    pstmt2.setString(6,custcity);
                    pstmt2.setString(7,custarea);
                    pstmt2.setLong(8, custphnno);
                    pstmt2.setString(9, provid);
                    pstmt2.setString(10, provname);
                    pstmt2.setString(11, provaddr);
                    pstmt2.setString(12,provarea);
                    pstmt2.setString(13,provcity);
                    pstmt2.setString(14, provservice);
                    pstmt2.setInt(15,provamount);
                    pstmt2.setLong(16,provphnno);
                    pstmt2.setString(17,status);
                    pstmt2.setString(18,payment_status);

                    // Execute the insert
                    int rowsAffected = pstmt2.executeUpdate();

                    if (rowsAffected > 0) {
                        // Redirect with success message
                        out.println("<script type=\"text/javascript\">");
                        out.println("alert('Order placed successfully! Click your order link.');");
                        out.println("location='http://localhost:7750/Project/CustHome.html';");
                        out.println("</script>");
                    }
                }
            }
        } catch (Exception e) {
            out.println(e);
        }
    }
}