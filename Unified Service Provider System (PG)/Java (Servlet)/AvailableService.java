package provider;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/AvailableService")
public class AvailableService extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            // Database connection
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sample", "root", "");

            // Prepare SQL query to fetch data from the database
            pstmt = con.prepareStatement("SELECT service_name FROM services");
            rs = pstmt.executeQuery();

            out.println("<html><body>");
            out.println("<style>");
            out.println("body {font-family: Arial, sans-serif; text-align: center; padding: 20px;}");  // Add padding to body
            out.println("select {width: 300px;height: 45px;font-size: 18px; padding: 10px; border-radius: 5px; border: 1px solid #2196f3;}");  // Style the select box
            out.println(".dynamic-content { width: 100%; margin-top: 20px; padding: 20px; border-radius: 5px; display: none; transition: opacity 0.5s ease-in-out; opacity: 0;}");  // Style dynamic content div with transition
            out.println(".dynamic-content.show { display: block; opacity: 1;}");  // Show content smoothly with opacity transition
            out.println("</style>");

            // Create the combobox
            out.println("<div class='select-container'>");
            out.println("<h3 style='color: #2196f3;'>Select Your Service:</h3>");
            out.println("<select name='service' id='serviceSelect' onchange='showPage()'>");

            // Default "Select a service" option
            out.println("<option value='' disabled selected>--- --- ---</option>");

            // Loop through the result set and create options for the combobox
            while (rs.next()) {
                String serviceName = rs.getString("service_name");
                out.println("<option value='" + serviceName + "'>" + serviceName + "</option>");
            }

            out.println("</select>");
            out.println("</div>");

            // Dynamic content div to show when service is selected
            out.println("<div id='dynamicContent' class='dynamic-content'></div>");

            // JavaScript to handle the page display based on the selected service
            out.println("<script>");
            out.println("function showPage() {");
            out.println("    var selectedService = document.getElementById('serviceSelect').value;");
            out.println("    var dynamicContent = document.getElementById('dynamicContent');");

            // Hide the dynamic content div initially
            out.println("    dynamicContent.style.display = 'none';");
            out.println("    dynamicContent.classList.remove('show');");  // Remove 'show' class to hide content

            // Fetch the webpage content dynamically based on the selected service
            out.println("    if (selectedService === 'Cooking') {");
            out.println("        fetch('CookRegis')");
            out.println("            .then(response => response.text())");
            out.println("            .then(html => {");
            out.println("                dynamicContent.innerHTML = html;");  // Inject the fetched HTML content");
            out.println("                dynamicContent.style.display = 'block';");
            out.println("                dynamicContent.classList.add('show');");  // Add 'show' class to display content smoothly");
            out.println("            })");
            out.println("            .catch(error => console.error('Error loading Cooking service page:', error));");
            out.println("    } else if (selectedService === 'Driver') {");
            out.println("        fetch('DriverRegis')");
            out.println("            .then(response => response.text())");
            out.println("            .then(html => {");
            out.println("                dynamicContent.innerHTML = html;");
            out.println("                dynamicContent.style.display = 'block';");
            out.println("                dynamicContent.classList.add('show');");
            out.println("            })");
            out.println("            .catch(error => console.error('Error loading Driver service page:', error));");
             out.println("    } else if (selectedService === 'Plumber') {");
            out.println("        fetch('PlumberRegis')");
            out.println("            .then(response => response.text())");
            out.println("            .then(html => {");
            out.println("                dynamicContent.innerHTML = html;");
            out.println("                dynamicContent.style.display = 'block';");
            out.println("                dynamicContent.classList.add('show');");
            out.println("            })");
            out.println("            .catch(error => console.error('Error loading Driver service page:', error));");
             out.println("    } else if (selectedService === 'Baby Care') {");
            out.println("        fetch('BabyCareRegis')");
            out.println("            .then(response => response.text())");
            out.println("            .then(html => {");
            out.println("                dynamicContent.innerHTML = html;");
            out.println("                dynamicContent.style.display = 'block';");
            out.println("                dynamicContent.classList.add('show');");
            out.println("            })");
            out.println("            .catch(error => console.error('Error loading Driver service page:', error));");
             out.println("    } else if (selectedService === 'Electrician') {");
            out.println("        fetch('ElecRegis')");
            out.println("            .then(response => response.text())");
            out.println("            .then(html => {");
            out.println("                dynamicContent.innerHTML = html;");
            out.println("                dynamicContent.style.display = 'block';");
            out.println("                dynamicContent.classList.add('show');");
            out.println("            })");
            out.println("            .catch(error => console.error('Error loading Driver service page:', error));");
             out.println("    } else if (selectedService === 'Pet Care') {");
            out.println("        fetch('PetCareRegis')");
            out.println("            .then(response => response.text())");
            out.println("            .then(html => {");
            out.println("                dynamicContent.innerHTML = html;");
            out.println("                dynamicContent.style.display = 'block';");
            out.println("                dynamicContent.classList.add('show');");
            out.println("            })");
            out.println("            .catch(error => console.error('Error loading Driver service page:', error));");
               out.println("    } else if (selectedService === 'Plumber') {");
            out.println("        fetch('PlumberRegis')");
            out.println("            .then(response => response.text())");
            out.println("            .then(html => {");
            out.println("                dynamicContent.innerHTML = html;");
            out.println("                dynamicContent.style.display = 'block';");
            out.println("                dynamicContent.classList.add('show');");
            out.println("            })");
            out.println("            .catch(error => console.error('Error loading Driver service page:', error));");
            out.println("    } else {");
            out.println("        dynamicContent.style.display = 'none';");
            out.println("        dynamicContent.classList.remove('show');");
            out.println("    }");
            out.println("}");
            out.println("</script>");

            out.println("</body></html>");

        } catch (Exception e) {
            e.printStackTrace();
            out.println("<html><body><h2>Error: " + e.getMessage() + "</h2></body></html>");
        } finally {
            // Close database resources
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (con != null) con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}



