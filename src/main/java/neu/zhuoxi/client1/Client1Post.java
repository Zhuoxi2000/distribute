package neu.zhuoxi.client1;

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

public class Client1Post {

    private String serverIP;

    public Client1Post(String serverIP) {
        this.serverIP = serverIP;
    }
    public static void main(String[] args) throws Exception {
        String serverURI = "http://localhost:8081/Assignment00/hello-servlet";
        Client1Post client = new Client1Post(serverURI);

        client.performPostRequest();
    }

    public CloseableHttpResponse performPostRequest() throws IOException {


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


                HttpEntity responseEntity = response.getEntity();
                //String responseContent = EntityUtils.toString(responseEntity);

                return response;
            }
        }
    }

}
