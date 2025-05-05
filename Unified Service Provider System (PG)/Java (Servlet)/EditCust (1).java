package provider;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/EditCust")
public class EditCust extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        PrintWriter out = response.getWriter();
      String user= (String) getServletContext().getAttribute("user");

        // Output the start of the HTML document
        out.println("<html>");
        out.println("<head>");
        
        // Inline CSS Styling
        out.println("<style>");
        out.println("body {");
        out.println("    font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;");
        out.println("    margin: 0;");
        out.println("    padding: 0;");
        out.println("    background: #f1f1f1; /* Light background */");
        out.println("    color: #333; /* Dark text color for contrast */");
        out.println("}");
 
        
        out.println("table {");
        out.println("    width: 100%;");
        out.println("    border-collapse: collapse;");
        out.println("    margin-top: 20px;");
        out.println("}");

        out.println("td, th {");
        out.println("    padding: 12px;");
        out.println("    text-align: left;");
        out.println("    border: 1px solid #ddd;");
        out.println("}");

        out.println("label {");
        out.println("    color: #1976d2;");
        out.println("    font-weight: bold;");
        out.println("}");

        out.println("td input, td textarea {");
        out.println("    width: 98%;");
        out.println("    padding: 10px;");
        out.println("    border: 1px solid #ccc;");
        out.println("    border-radius: 5px;");
        out.println("    font-size: 14px;");
        out.println("    margin-top: 5px;");
        out.println("}");

        out.println("button {");
        out.println("    background-color: #2196f3;");
        out.println("    color: white;");
        out.println("    padding: 14px 20px;");
        out.println("    border: none;");
        out.println("    border-radius: 5px;");
        out.println("    cursor: pointer;");
        out.println("    font-size: 16px;");
        out.println("    width: 100%;");
        out.println("    margin-top: 20px;");
        out.println("}");

        out.println("button:hover {");
        out.println("    background-color: #1976d2;");
        out.println("}");

        out.println("</style>");
        
        out.println("</head>");
        out.println("<body>");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sample", "root", "");
            String sql = "SELECT * FROM custdetails WHERE custid=?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, user);
            ResultSet rs = pstmt.executeQuery();

            out.println("<div class='container'>");
            out.println("<form action='UpdateCust' method='POST'>");
            out.println("<table>");

            if (rs.next()) {
                String cust_id = rs.getString("custid");
                String name = rs.getString("name");
                String address = rs.getString("address");
                String area = rs.getString("area");
                String city = rs.getString("city");
                long pincode = rs.getLong("pincode");
                String email = rs.getString("email");
                long phnno = rs.getLong("phnno");

                out.println("<tr><td><label for='id'>ID</label>"
                        + "<input type='text' name='id' value='" + cust_id + "' readonly/></td></tr>");

                out.println("<tr><td><label for='name'>Name</label>"
                        + "<input type='text' name='name' value='" + name + "' /></td></tr>");

                out.println("<tr><td><label for='address'>Address</label>"
                        + "<textarea name='address'>" + address + "</textarea></td></tr>");

                out.println("<tr><td><label for='area'>Area</label>"
                        + "<input type='text' name='area' value='" + area + "' /></td></tr>");

                out.println("<tr><td><label for='city'>City</label>"
                        + "<input type='text' name='city' value='" + city + "' /></td></tr>");

                out.println("<tr><td><label for='pincode'>Pincode</label>"
                        + "<input type='number' name='pincode' value='" + pincode + "' /></td></tr>");

                out.println("<tr><td><label for='email'>Email</label>"
                        + "<input type='email' name='email' value='" + email + "' /></td></tr>");

                out.println("<tr><td><label for='phnno'>Phone Number</label>"
                        + "<input type='number' name='phnno' value='" + phnno + "' /></td></tr>");

                out.println("<tr><td><center><button type='submit'>Update</button></center></td></tr>");
            }

            out.println("</table>");
            out.println("</form>");
            out.println("</div>");
        } catch (Exception e) {
            e.printStackTrace();
        }

        out.println("</body>");
        out.println("</html>");
    }
}