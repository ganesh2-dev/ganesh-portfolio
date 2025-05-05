package provider;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/FindProvider")
public class FindProvider extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");

        String name = request.getParameter("servicename");
        PrintWriter out = response.getWriter();

        try {
            out.println("<html><head><style>");
            out.println("input {width: 20%; padding: 10px; border-radius: 5px; border: 1px solid #ccc;}");
            out.println("label {display: block; font-weight: bold; font-size: 20px;}");
            out.println("button {width: 9%; padding: 10px; background-color: orchid; color: white; border: none; border-radius: 5px; font-size: 16px;}");
            out.println("</style></head><body>");
            out.println("<form id='findProviderForm'><center>");
            out.println("<label>Service Name:</label><input type='text' name='servicename' value='" + name + "' readonly><br>");
            out.println("<label>Area:</label><input type='text' name='area'><br>");
            out.println("<label>City:</label><input type='text' name='city'><br><br>");
            out.println("<button type='button' onclick='findProviderDetails()'>Find Provider</button><hr>");
            out.println("</center></form>");

            // Div to display the provider details
            out.println("<div id='providerDetails'></div>");

            out.println("<script>");
            out.println("function findProviderDetails() {");
            out.println("    var servicename = document.querySelector('[name=\"servicename\"]').value;");
            out.println("    var area = document.querySelector('[name=\"area\"]').value;");
            out.println("    var city = document.querySelector('[name=\"city\"]').value;");
            out.println("    var xhr = new XMLHttpRequest();");
            out.println("    xhr.open('GET', 'ShowProvider?servicename=' + encodeURIComponent(servicename) + '&area=' + encodeURIComponent(area) + '&city=' + encodeURIComponent(city), true);");
            out.println("    xhr.onreadystatechange = function() {");
            out.println("        if (xhr.readyState == 4 && xhr.status == 200) {");
            out.println("            document.getElementById('providerDetails').innerHTML = xhr.responseText;");
            out.println("        }");
            out.println("    };");
            out.println("    xhr.send();");
            out.println("}");
            out.println("</script>");
            out.println("</body></html>");
        } catch (Exception e) {
            out.println(e);
        }
    }
}