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
import java.sql.ResultSet;
import java.sql.Statement;


@WebServlet("/ElecRegis")
public class ElecRegis extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sample", "root", "");
            Statement stmt = con.createStatement();
            String sql = "SELECT provid FROM provdetails ORDER BY provid DESC LIMIT 1";
            ResultSet rs = stmt.executeQuery(sql);

            String newProvid = "prov1";

            if (rs.next()) {
                String lastProvid = rs.getString("provid");
                int lastNumber = Integer.parseInt(lastProvid.replaceAll("[^0-9]", ""));
                newProvid = "prov" + (lastNumber + 1);
            }

            out.println("<html><head><style>");
            out.println("body { margin: 0; padding: 20px; background-color: #f4f4f4; font-family: Arial, sans-serif; }");
            out.println(".order-form { max-width: 800px; margin: 0 auto; background-color: #fff; padding: 20px; border-radius: 8px; box-shadow: 0px 4px 6px rgba(0, 0, 0, 0.1); }");
            out.println("h1 { color: #2196f3; text-align: center; margin-bottom: 20px; }");
            out.println("label { color: #2196f3; font-size: 16px; font-weight: bold; display: block; margin-bottom: 10px; }");
            out.println("input, textarea { width: 100%; padding: 10px; border-radius: 5px; border: 1px solid #ccc; margin-bottom: 15px; font-size: 14px; }");
            out.println("input[type='submit'] { background-color: #2196f3; color: white; border: none; border-radius: 5px; font-size: 16px; padding: 12px; cursor: pointer; width: 100%; }");
            out.println("input[type='submit']:hover { background-color: #1976d2; }");
            out.println(".form-row { display: flex; flex-wrap: wrap; gap: 20px; margin-bottom: 20px; }");
            out.println(".form-row div { flex: 1; min-width: 250px; }");
            out.println(".form-row div input, .form-row div textarea { width: 100%; }");
            out.println(".form-row div:last-child { margin-right: 0; }");
            out.println("</style></head><body>");
            out.println("<form method='post' action='DataInsert' enctype='multipart/form-data'>");
            out.println("<div class='order-form'>");
     
            
            // Username and Password
            out.println("<div class='form-row'>");
             out.println("<div><label for='service'>Service</label><input type='text' name='service' value='Electrician' readonly required /></div>");
            
            out.println("<div><label for='user'>User Id</label><input type='text' name='user' value='" + newProvid + "' readonly required /></div>");
                out.println("</div>");
            
            // Name and Address
            out.println("<div class='form-row'>");            
            out.println("<div><label for='password'>Password</label><input type='password' name='pass' required /></div>");
            
            out.println("<div><label for='name'>Name</label><input type='text' name='name' required /></div>");
         
            out.println("</div>");
            
            // Area and City
            out.println("<div class='form-row'>");   out.println("<div><label for='address'>Address</label><textarea name='address' required></textarea></div>");
            out.println("<div><label for='area'>Area</label><input type='text' name='area' required /></div>");
        
            out.println("</div>");
            
            // Pincode and Email
            out.println("<div class='form-row'>");    out.println("<div><label for='city'>City</label><input type='text' name='city' required /></div>");
            out.println("<div><label for='pincode'>Pincode</label><input type='number' name='pincode' required /></div>");
         
            out.println("</div>");
            
            // Phone Number and Photo
            out.println("<div class='form-row'>");   out.println("<div><label for='email'>Email</label><input type='email' name='email' required /></div>");
            out.println("<div><label for='phnno'>Phone Number</label><input type='number' name='phnno' required /></div>");
            out.println("</div>");
            
            // ID Proof and Service
            out.println("<div class='form-row'>"); out.println("<div><label for='photo'>Photo</label><input type='file' name='photo' title='PDF format' accept='application/pdf' required /></div>");
           
            out.println("<div><label for='idproof'>ID Proof</label><input type='file' name='proof' title='like license' required /></div>");
            out.println("</div>");
            
            // Working Time and Amount
            out.println("<div class='form-row'>");
            out.println("<div><label for='wrktime'>Working time</label><input type='text' name='wrktime' required /></div>");
            out.println("<div><label for='amount'>Amount/hr</label><input type='number' name='amount' required /></div>");
            out.println("</div>");
            
            // Submit Button
            out.println("<div><input type='submit' value='Submit' /></div>");
            out.println("</div>");
            out.println("</form></body></html>");
        } catch (Exception e) {
            out.println(e);
        }
    }
}