import com.fastcgi.FCGIInterface;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ResponseManager {
    Logger logger = Logger.getLogger(this.getClass().getName());

    private final long startTime;

    public ResponseManager(long time) {
        startTime = time;
    }

    public void run() {
        var fcgiInterface = new FCGIInterface();
        logger.info("Waiting for requests...");
        while (fcgiInterface.FCGIaccept() >= 0) {
            try {
                var request = getRequest();
                logger.info("Received request: " + request);

                var values = parseData(request);
                if (values == null) {
                    logger.log(Level.SEVERE, "Error parsing data");
                    break;
                }
                logger.info("Received data: %s, %s, %s".formatted(values.get("x"), values.get("y"), values.get("r")));

                var response = sendResponse(values);

                System.out.println(response);
            } catch (Exception e) {
                logger.warning(Arrays.toString(e.getStackTrace()) + "\n" + e.getMessage());
            }
        }
    }

    protected String sendResponse(Map<String, Double> values) {
        var status = ValidationManager.checkData(values);
        var time = String.format(Locale.ENGLISH,"%.4f",(double) (System.currentTimeMillis() - startTime) / 1000000);
        var content = """
                    {
                    "status": %s,
                    "time": %s
                    }
                    """;
        logger.info("Status: %s, time: %s".formatted(status, time));
        content = content.formatted(status, time);

        var httpResponse = """
                        HTTP/1.1 200 OK
                        Content-Type: application/json
                        Content-Length: %d
                        
                        %s
                        """.formatted(content.getBytes(StandardCharsets.UTF_8).length, content);

        return httpResponse;
    }

    protected String getRequest() throws IOException {
        FCGIInterface.request.inStream.fill();

        return FCGIInterface.request.params.getProperty("QUERY_STRING");
    }

    protected Map<String, Double> parseData(String params) throws IOException {
        var arr = params.split("&");

        HashMap<String, Double> data = new HashMap<>();
        data.put("x", Double.valueOf(arr[0].substring(2)));
        data.put("y", Double.valueOf(arr[1].substring(2)));
        data.put("r", Double.valueOf(arr[2].substring(2)));

        return data;
    }
}
