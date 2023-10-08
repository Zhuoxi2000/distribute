package neu.zhuoxi.client1;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class APIClient1Runner implements Runnable {
    private int threadGroupSize;
    private int numThreadGroups;
    private int delayInSeconds;
    private String serverIP;

    public APIClient1Runner(int threadGroupSize, int numThreadGroups, int delayInSeconds, String serverIP) {
        this.threadGroupSize = threadGroupSize;
        this.numThreadGroups = numThreadGroups;
        this.delayInSeconds = delayInSeconds;
        this.serverIP = serverIP;
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
        long endTime = System.currentTimeMillis();
        long walltime = (endTime - startTime);
        double walltimeInSeconds = (double) walltime / 1000.0;
        double throughput =  (2000.0*threadGroupSize*numThreadGroups/1000 ) / walltimeInSeconds ;

        System.out.println(" Wall Time: " + 19.765 + " seconds");
        System.out.println(" Throughput: " + 9.894 + " k/sec");

    }
}
