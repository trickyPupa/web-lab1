import com.fastcgi.FCGIInterface;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ResponseManager {
    Logger logger = Logger.getLogger(this.getClass().getName());

    private final RequestManager requestManager;
    private final long startTime;
    private long time;

    public ResponseManager(RequestManager requestManager, long time) {
        this.requestManager = requestManager;
        startTime = time;
        this.time = time;
    }

    public void run() throws IOException {
        var fcgiInterface = new FCGIInterface();
        logger.info("Waiting for requests...");
        while (fcgiInterface.FCGIaccept() >= 0) {
            var data = requestManager.handleRequest();
            var values = JSONParser.parse(data);
            if (values == null) {
                logger.log(Level.SEVERE, "Wrong JSON format");
                break;
            }
            logger.info("Request received! %s, %s, %s".formatted(values[0], values[1], values[2]));
            time = System.currentTimeMillis();

            var response = sendResponse(values);

            System.out.println(response);
        }
    }

    private String sendResponse(double[] values) {
        var status = ValidationManager.checkData(values);
        var content = """
                    {
                    "status": %s,
                    "time": %s
                    }
                    """;
        logger.info("Status: %s, time: %s".formatted(status, (double) (System.currentTimeMillis() - startTime)/1000000));
        content = content.formatted(status, String.format( "%.4f",
                (double) (System.currentTimeMillis() - startTime)/1000000));
        /*content = content.formatted(status, String.format( "%.4f",
                (double) (System.currentTimeMillis() - time)/1000000));*/

        var httpResponse = """
                        HTTP/1.1 200 OK
                        Content-Type: application/json
                        Content-Length: %d
                        
                        %s
                        """.formatted(content.getBytes(StandardCharsets.UTF_8).length, content);

        logger.warning("status: %s".formatted(status));

        return httpResponse;
    }
}
