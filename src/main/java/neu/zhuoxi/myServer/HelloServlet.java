package neu.zhuoxi.myServer;
import com.google.gson.Gson;

import java.io.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "helloServlet", value = "/hello-servlet")
public class HelloServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Read JSON data from the request
        Gson gson = new Gson();

        StringBuilder requestBody = new StringBuilder();
        try (BufferedReader reader = request.getReader()) {
            String line;
            while ((line = reader.readLine()) != null) {
                requestBody.append(line);
            }
            Status status = new Status();
            // Test case
            if (isValidJson(requestBody.toString())) {
                // Return a fixed album key as a response
                // Generate a unique albumID (can use UUID or other methods)
                String albumKey = generateAlbumKey();

                response.getWriter().write(gson.toJson(albumKey));
                response.getOutputStream().print(gson.toJson(status));
                response.getOutputStream().flush();

                // Get uploaded image data
                String imagePart = request.getParameter("image");
                // Save the image to the file system or database, as required

                // Create an album object
                Album album = (Album) gson.fromJson(requestBody.toString(), Album.class);

            } else {
                response.getWriter().write("Validation failed.");
                response.getOutputStream().print(gson.toJson(status));
                response.getOutputStream().flush();
            }
            // Get form data

        } catch (Exception ex) {
            ex.printStackTrace();
            Status status = new Status();
            status.setSuccess(false);
            status.setDescription(ex.getMessage());
            response.getOutputStream().print(gson.toJson(status));
            response.getOutputStream().flush();
        }
    }
    private String generateAlbumKey() {
        // Implement logic to generate an albumID here, can use UUID or other methods
        String albumKey = "myAlbum123";
        return albumKey;
    }
    private boolean isValidJson(String json) {
        // Directly return as valid
        return true;
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Return a fixed response (constant data)
        String albumID = request.getParameter("albumID");
        System.out.println(albumID);

        // Check if albumID is null or invalid
        if (albumID == null || albumID.isEmpty() || !isValidAlbumID(albumID)) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Invalid albumID");
            return;
        }

        // Get album information from a database or other data source based on albumID, using sample data here
        Album album = getAlbumInfo(albumID);

        // Check if album information was found
        if (album == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.getWriter().write("Album not found");
            return;
        }

        // Convert album information to JSON format and send the response
        String albumJsonString = new Gson().toJson(album);
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        out.print(albumJsonString);
        out.flush();
    }
    private boolean isValidAlbumID(String albumID) {
        // Add logic here to check if albumID is valid
        return true; // Assume all albumIDs are valid for this example
    }

    // Method to get album information, implement based on actual requirements
    private Album getAlbumInfo(String albumID) {
        // Add logic here to retrieve album information from a database or other data source
        // Returning a fixed AlbumInfo object in this example
        return new Album("Eason", "White Rose", "1996");
    }

    private boolean validateJson(String json) {
        // Execute JSON validation logic here
        // Return true if validation is successful, otherwise return false
        // You can customize the validation according to your requirements
        return true; // Simple example returns true here
    }
}
