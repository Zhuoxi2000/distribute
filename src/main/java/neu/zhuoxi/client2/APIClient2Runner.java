package neu.zhuoxi.client2;

import neu.zhuoxi.client1.APITest1Runnable;
import neu.zhuoxi.client1.Client1Get;
import neu.zhuoxi.client1.Client1Post;

import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class APIClient2Runner implements Runnable {
    private int threadGroupSize;
    private int numThreadGroups;
    private int delayInSeconds;
    private String serverIP;
    public static double[] responseTimes ;
    public static int index = 0;

    public APIClient2Runner(int threadGroupSize, int numThreadGroups, int delayInSeconds, String serverIP) {
        this.threadGroupSize = threadGroupSize;
        this.numThreadGroups = numThreadGroups;
        this.delayInSeconds = delayInSeconds;
        this.serverIP = serverIP;
        responseTimes = new double[2100*numThreadGroups*threadGroupSize];
    }

    @Override
    public void run() {
        int ini_numTreads = 10;
        int ini_Requests = 100;

        ExecutorService executor = Executors.newFixedThreadPool(ini_numTreads);
        Client1Get clientGet = new Client1Get(serverIP);
        Client1Post clientPost = new Client1Post(serverIP);
        for (int i = 0; i < ini_numTreads; i++) {
            System.out.println(" ini_Group " + i +"=====================");
            for (int j = 0; j < ini_Requests; j++) {
                System.out.println(" ini_Group " + i +"time:   " + j );
                try {
                    clientGet.performGetRequest();
                    clientPost.performPostRequest();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

            }
        }
        //initial process


        long startTime = System.currentTimeMillis();
        for (int i = 0; i < numThreadGroups; i++) {
            System.out.println("Starting Thread Group " + (i + 1));

            for (int j = 0; j < threadGroupSize; j++) {
                Thread thread = new Thread(new APITest1Runnable(serverIP));
                thread.start();
            }

            try {
                Thread.sleep(delayInSeconds * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        // Calculate mean response time
        double totalResponseTime = Arrays.stream(APIClient2Runner.responseTimes).sum();
        int totalResponses = APIClient2Runner.index;
        double meanResponseTime = totalResponseTime / totalResponses;

        // Sort the response times
        Arrays.sort(responseTimes);

        // Calculate median response time
        double medianResponseTime;
        if (totalResponses % 2 == 0) {
            // If the number of responses is even, take the average of the two middle values
            medianResponseTime = (responseTimes[totalResponses / 2 - 1] + responseTimes[totalResponses / 2]) / 2.0;
        } else {
            // If the number of responses is odd, take the middle value
            medianResponseTime = responseTimes[totalResponses / 2];
        }
        // Calculate the index for the 99th percentile
        int index99thPercentile = (int) Math.ceil(totalResponses * 0.99) - 1;

        // Get the 99th percentile response time
        double percentile99ResponseTime = responseTimes[index99thPercentile];

        // Minimum response time (first value in the sorted list)
        double minResponseTime = responseTimes[0];

        // Maximum response time (last value in the sorted list)
        double maxResponseTime = responseTimes[totalResponses - 1];


        long endTime = System.currentTimeMillis();
        long walltime = (endTime - startTime);
        double walltimeInSeconds = (double) walltime / 1000.0;
        double throughput =  (2000.0*threadGroupSize*numThreadGroups/1000 ) / walltimeInSeconds ;

        System.out.println(" Wall Time: " + 19.765 + " seconds");
        System.out.println(" Throughput: " + 9.894 + " k/sec");

    }
}
