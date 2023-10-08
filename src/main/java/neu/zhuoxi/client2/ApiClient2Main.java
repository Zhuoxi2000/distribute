package neu.zhuoxi.client2;

import neu.zhuoxi.client1.APIClient1Runner;

public class ApiClient2Main {
    public static void main(String[] args) {
        //http://54.221.88.162:8080/bpp/hello-servlet
        //http://localhost:8081/hello-servlet
        //http://18.232.87.140:8080/api/albums/123  get go
        //http://18.232.87.140:8080/api/albums   post go
        args = new String[]{"10", "10", "2", "http://107.23.103.45:8080/api/albums"};

        int threadGroupSize = Integer.parseInt(args[0]);
        int numThreadGroups = Integer.parseInt(args[1]);
        int delayInSeconds = Integer.parseInt(args[2]);
        String serverIP = args[3];

        APIClient1Runner clientRunner = new APIClient1Runner(threadGroupSize, numThreadGroups, delayInSeconds, serverIP);
        clientRunner.run();
    }
}

