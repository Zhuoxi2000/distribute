package neu.zhuoxi.client2;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
public class Client2Get {

    private String serverIP;

    public Client2Get(String serverIP) {
        this.serverIP = serverIP;
    }

    public static void main(String[] args) throws Exception {

        //test
        String serverURI = "http://localhost:8081/Assignment00/hello-servlet?albumID=123";
        Client2Get client = new Client2Get(serverURI);

        // Execute Get()method;
        client.performGetRequest();
    }

    public CloseableHttpResponse performGetRequest() throws Exception {
        // Replace with your server's base URI

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            //HttpGet httpGet = new HttpGet(serverIP + "?albumID=123");
            HttpGet httpGet = new HttpGet(serverIP + "/123");
            long startTime = System.currentTimeMillis(); // get the start time
            try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
                long endTime = System.currentTimeMillis(); // get the end time
                long latency = endTime - startTime; // calculate the latency
                APIClient2Runner.responseTimes[APIClient2Runner.index] = latency;
                APIClient2Runner.index= APIClient2Runner.index + 1;
                String responseContent = EntityUtils.toString(response.getEntity());
                int responseCode = response.getStatusLine().getStatusCode();

                // Write out to CSV
                writeRecordToCSV(startTime, "GET", latency, responseCode);

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
