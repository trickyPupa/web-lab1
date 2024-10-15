import java.io.IOException;
import java.util.Locale;

public class Main{
    public static void main(String[] args) throws IOException {
        long startTime = System.currentTimeMillis();
        ResponseManager responseManager = new ResponseManager(startTime);

        responseManager.run();
    }
}