package neu.zhuoxi.client1;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
public class Client1Get {

    private String serverIP;

    public Client1Get(String serverIP) {
        this.serverIP = serverIP;
    }

    public static void main(String[] args) throws Exception {
        // 创建ClientGet的实例
        String serverURI = "http://localhost:8081/Assignment00/hello-servlet?albumID=123";
        Client1Get client = new Client1Get(serverURI);

        // Execute Get()method;
        client.performGetRequest();
    }

    public CloseableHttpResponse performGetRequest() throws Exception {
        // Replace with your server's base URI

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            //HttpGet httpGet = new HttpGet(serverIP + "?albumID=123");
            HttpGet httpGet = new HttpGet(serverIP + "/123");
            try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
                String responseContent = EntityUtils.toString(response.getEntity());
                int responseCode = response.getStatusLine().getStatusCode();

                return response;
            }
        }

    }
}