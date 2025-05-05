package provider;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/SendMail")
public class SendMail extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            String public_key="N34gThkluBX1Yf-7X";
            String service_id="service_7rrpqlo";
            String template_id="template_7cvy1ng";
            out.println("<html lang='en'><head><meta charset='UTF-8'><meta name='viewport' content='width=device-width, initial-scale=1.0'>");
            out.println("<script type='text/javascript' src='https://cdn.jsdelivr.net/npm/@emailjs/browser@4/dist/email.min.js'></script>");
            out.println("<script type='text/javascript'>(function(){emailjs.init('"+public_key+"');})();</script>");
            out.println("<script src='script.js'></script></head><body>");
            out.println("<form id='contactForm'>");
            out.println("<label for='name'>Name</label><input type='text' id='name' name='name' required>");
            out.println("<label for='message'>Message</label><textarea id='message' name='message' required></textarea>");
            out.println("<label for='email'>Email</label><input type='email' id='email' name='email' required>");
            out.println("<button type='button' onclick='sendMail()'>Submit</button>");
            out.println("</form>");
            out.println("<script>");
            out.println("function sendMail() {");
            out.println("    let name = document.getElementById('name').value;");
            out.println("    let email = document.getElementById('email').value;");
            out.println("    let message = document.getElementById('message').value;");
            out.println("    if (!name || !email || !message) {");
            out.println("        alert('All fields are required!');");
            out.println("        return;");
            out.println("    }");
            out.println("    let params = {");
            out.println("        name: name,");
            out.println("        mail: email,");
            out.println("        message: message");
            out.println("    };");
            out.println("    emailjs.send('"+service_id+"', '"+template_id+"', params)");
            out.println("        .then(function(response) {");
            out.println("            alert('Email Sent!!');");
            out.println("            document.getElementById('contactForm').reset();");  // Clear form after success
            out.println("        })");
            out.println("        .catch(function(error) {");
            out.println("            console.error('Error sending email:', error);");
            out.println("            alert('Failed to send email. Please try again later.');");
            out.println("        });");
            out.println("}");
            out.println("</script></body></html>");
        }
    }

}
