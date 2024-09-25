import java.io.IOException;

public class Main{
    public static void main(String[] args) throws IOException {
        long startTime = System.currentTimeMillis();
        RequestManager requestManager = new RequestManager();
        ResponseManager responseManager = new ResponseManager(requestManager, startTime);

        responseManager.run();
    }
}