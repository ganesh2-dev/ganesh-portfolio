package provider;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Base64;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

@WebServlet("/ShowProvider")
public class ShowProvider extends HttpServlet {

    // Utility method to check if a parameter exists or return a default value
    private String checkParameter(String param, String defaultValue) {
        return (param == null || param.isEmpty()) ? defaultValue : param;
    }

    // Method to check if the provider is available based on work time (AM/PM format)
    private boolean checkAvailabilityBasedOnWorkTime(String startTimeStr, String endTimeStr) {
        // Get the current time
        LocalTime currentTime = LocalTime.now();

        // Define the date format for AM/PM (12-hour format)
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a");

        try {
            // Ensure the start and end times have a space between time and AM/PM (e.g., "07:00 AM" instead of "07:00AM")
            startTimeStr = startTimeStr.substring(0, 5) + " " + startTimeStr.substring(5);
            endTimeStr = endTimeStr.substring(0, 5) + " " + endTimeStr.substring(5);

            // Parse start and end times to 24-hour format
            LocalTime startTime = LocalTime.parse(startTimeStr, formatter);
            LocalTime endTime = LocalTime.parse(endTimeStr, formatter);

            // Adjust the time logic if the end time is less than the start time (e.g., overnight shifts)
            if (endTime.isBefore(startTime)) {
                // End time is the next day, so we add 1 day to it
                endTime = endTime.plus(1, ChronoUnit.DAYS);
            }

            // Check if current time is within the range
            if (!currentTime.isBefore(startTime) && !currentTime.isAfter(endTime)) {
                return true;  // Provider is available
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;  // Provider is not available
    }

    // This method handles the GET request to display providers
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        // CSS for styling the provider cards
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
        out.println("    color: orchid;");
        out.println("    font-weight: bold;");
        out.println("}");
        out.println(".card a:hover {");
        out.println("    text-decoration: underline;");
        out.println("}");
        // New style for unavailable providers
        out.println(".not-available {");
        out.println("    background-color: #f0f0f0;");
        out.println("    color: #888;");
        out.println("    border: 1px solid #ccc;");
        out.println("}");
        out.println(".not-available p {");
        out.println("    font-style: italic;");
        out.println("}");
        out.println(".not-available a {");
        out.println("    pointer-events: none;");
        out.println("    color: #ccc;");
        out.println("}");
        out.println("</style>");
        out.println("</head>");
        out.println("<body>");

        // Get parameters from the request, with default values if not provided
        String servicename = checkParameter(request.getParameter("servicename"), "Unknown Service");
        String area = checkParameter(request.getParameter("area"), "Unknown Area");
        String city = checkParameter(request.getParameter("city"), "Unknown City");

        boolean isAvailable = false;
        boolean providerFound = false;

        try {
            // Setup MySQL connection
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sample", "root", "");

            // SQL query to retrieve provider details
            String sql = "SELECT provid, name, phnno, area, city, filedata, amount, start_time, end_time FROM provdetails WHERE area=? AND city=? AND service=? AND status='Approved'";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, area);
            pstmt.setString(2, city);
            pstmt.setString(3, servicename);

            ResultSet rs = pstmt.executeQuery();

            // Process the results from the provider details query
            while (rs.next()) {
                String provid = rs.getString("provid");
                String name = rs.getString("name");
                long phnno = rs.getLong("phnno");
                String area1 = rs.getString("area");
                String city1 = rs.getString("city");
                String amountPerHour = rs.getString("amount");
                String start = rs.getString("start_time");
                String end = rs.getString("end_time");

                // Get the provider's image data from the database and encode it to Base64
                InputStream imageData = rs.getBinaryStream("filedata");
                String base64Image = encodeImageToBase64(imageData);

                // Check if the provider is available based on the work time
                if (checkAvailabilityBasedOnWorkTime(start, end)) {
                    isAvailable = true;
                }

                // Display provider details if they are available
                if (isAvailable) {
                    providerFound = true; // At least one provider is available
                    out.println("<div class='card'>");
                    out.println("<img src='data:image/jpeg;base64," + base64Image + "' alt='Image' width='100' height='100' />");
                    out.println("<p><strong>Provid:</strong> " + provid + "</p>");
                    out.println("<p><strong>Name:</strong> " + name + "</p>");
                    out.println("<p><strong>Phone number:</strong> " + phnno + "</p>");
                    out.println("<p><strong>Amount/hr:</strong> " + amountPerHour + "</p>");
                    out.println("<p><strong>Available</strong></p>");
                    out.println("<p><strong><a href='Book?provid=" + provid + "'>BOOK</a></strong></p>");
                    out.println("</div>");
                } else {
                    // If provider is not available, display the "Not Available" card
                    out.println("<div class='card not-available'>");
                    out.println("<img src='data:image/jpeg;base64," + base64Image + "' alt='Image' width='100' height='100' />");
                    out.println("<p><strong>Provid:</strong> " + provid + "</p>");
                    out.println("<p><strong>Name:</strong> " + name + "</p>");
                    out.println("<p><strong>Phone number:</strong> " + phnno + "</p>");
                    out.println("<p><strong>Amount/hr:</strong> " + amountPerHour + "</p>");
                    out.println("<p><strong>Not Available</strong></p>");
                    out.println("<p><strong><a href='#'>BOOK</a></strong></p>");
                    out.println("</div>");
                }
            }

            // If no **available** providers were found, display a message
            if (!providerFound) {
                out.println("<p>No providers available.</p>");
            }

            rs.close();
            pstmt.close();
            con.close();

        } catch (Exception e) {
            out.println("<p>Error: " + e.getMessage() + "</p>");
            e.printStackTrace(out);
        }

        out.println("</body>");
        out.println("</html>");
    }

    // Method to encode image data to Base64
    private String encodeImageToBase64(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[4096];
        int bytesRead;
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            byteArrayOutputStream.write(buffer, 0, bytesRead);
        }
        byte[] imageBytes = byteArrayOutputStream.toByteArray();
        return Base64.getEncoder().encodeToString(imageBytes);
    }
}
