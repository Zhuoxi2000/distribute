package neu.zhuoxi.client2;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Client2Post {

    private String serverIP;

    public Client2Post(String serverIP) {
        this.serverIP = serverIP;
    }
    public static void main(String[] args) throws Exception {
        String serverURI = "http://localhost:8081/Assignment00/hello-servlet";
        Client2Post client = new Client2Post(serverURI);

        client.performPostRequest();
    }

    public CloseableHttpResponse performPostRequest() throws IOException {
        // Replace with your server's base URI


        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(serverIP);
            long startTime = System.currentTimeMillis(); // get the start time

            // Define the JSON data you want to send in the request body
            String jsonBody = "{\"artist\": \"Joker\", \"title\": \"beauty\", \"year\": \"2020\"}";

            // Set the request body as a StringEntity
            StringEntity entity = new StringEntity(jsonBody);
            httpPost.setEntity(entity);

            // Set the Content-Type header to indicate JSON data
            httpPost.setHeader("Content-Type", "application/json");

            try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
                long endTime = System.currentTimeMillis(); // get the end time
                long latency = endTime - startTime; // calculate the latency
                APIClient2Runner.responseTimes[APIClient2Runner.index] = latency;
                APIClient2Runner.index= APIClient2Runner.index + 1;

                HttpEntity responseEntity = response.getEntity();
                String responseContent = EntityUtils.toString(responseEntity);
                int responseCode = response.getStatusLine().getStatusCode();
                writeRecordToCSV(startTime, "POST", latency, responseCode);

                //System.out.println("Response Code: " + response.getStatusLine().getStatusCode());
                //System.out.println("Response Body: " + responseContent);
                return response;
            }
        }
    }
    private static void writeRecordToCSV(long startTime, String requestType, long latency, int responseCode) {
        try (FileWriter writer = new FileWriter("request_latency.csv", true)) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String formattedTime = dateFormat.format(new Date(startTime));

            writer.append(formattedTime).append(",");
            writer.append(requestType).append(",");
            writer.append(String.valueOf(latency)).append(",");
            writer.append(String.valueOf(responseCode)).append("\n");

            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
