package neu.zhuoxi.client1;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class APITest1Runnable implements Runnable {
    private String serverIP;

    public APITest1Runnable(String serverIP) {
        this.serverIP = serverIP;
    }

    @Override
    public void run() {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            for (int i = 0; i < 1000; i++) {
                // Perform a POST request
                Client1Post client1Post = new Client1Post(serverIP);
                CloseableHttpResponse postResponse = client1Post.performPostRequest();

                // Check if the POST request was successful (status code 200)
                if (postResponse.getStatusLine().getStatusCode() == 200) {
                    // Perform a GET request
                    Client1Get client1Get = new Client1Get(serverIP);
                    CloseableHttpResponse getResponse = client1Get.performGetRequest();

                    // Check if the GET request was successful (status code 200)
                    if (getResponse.getStatusLine().getStatusCode() == 200) {
                        // Continue to the next iteration of the loop
                        continue;
                    }
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
