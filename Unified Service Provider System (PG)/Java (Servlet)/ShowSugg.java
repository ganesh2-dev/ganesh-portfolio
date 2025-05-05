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
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@WebServlet("/ShowSugg")
public class ShowSugg extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        out.println("<html>");
        out.println("<head>");
        out.println("<style>");
        out.println(".card {");
        out.println("    border: 1px solid #ddd;");
        out.println("    padding: 20px;");
        out.println("    margin: 20px;");
        out.println("    width: 300px;");
        out.println("    box-shadow: 2px 2px 10px rgba(0, 0, 0, 0.1);");
        out.println("    display: inline-block;");
        out.println("    text-align: center;");
        out.println("}");
        out.println(".card img {");
        out.println("    border-radius: 50%;");
        out.println("    margin-bottom: 10px;");
        out.println("}");
        out.println(".card p {");
        out.println("    font-family: Arial, sans-serif;");
        out.println("    color: #333;");
        out.println("}");
        out.println(".card a {");
        out.println("    text-decoration: none;");
        out.println("    color: mediumorchid;");
        out.println("    font-weight: bold;");
        out.println("}");
        out.println(".card a:hover {");
        out.println("    text-decoration: underline;");
        out.println("}");
        out.println("</style>");
        out.println("</head>");
        out.println("<body>");

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            // Establish database connection
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sample", "root", "");
            
            String sql = "SELECT * FROM suggestion";
            pstmt = con.prepareStatement(sql);
            
            // Execute the query
            rs = pstmt.executeQuery();
            
            // Display results
            while (rs.next()) {
                String service = rs.getString("service_name");
                String custid = rs.getString("userid");
                String description = rs.getString("description");
                
                out.println("<div class='card'>");
                out.println("<p><strong>CustId:</strong> " + custid + "</p>");
                out.println("<p><strong>Service:</strong> " + service + "</p>");
                out.println("<p><strong>Description:</strong> " + description + "</p>");
              
                out.println("</div>");
            }
        } catch (Exception e) {
            out.println("<p>Error: " + e.getMessage() + "</p>");
            e.printStackTrace();  // Optionally print stack trace to server logs for debugging
        } finally {
            // Close resources in finally block
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (con != null) con.close();
            } catch (Exception e) {
                out.println("<p>Error closing resources: " + e.getMessage() + "</p>");
            }
        }

        out.println("</body></html>");
    }
}