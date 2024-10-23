import com.fastcgi.FCGIInterface;
import exceptions.ValidationException;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Logger;

public class ResponseManager {
    Logger logger = Logger.getLogger(this.getClass().getName());

    private long startTime;

    public final static HashMap<Integer, String> codesStatus = new HashMap<>();
    static {
        codesStatus.put(200, "OK");
        codesStatus.put(400, "Bad Request");
    }

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
                startTime = System.nanoTime();

                var values = parseData(request);
                logger.info("Received data: %s, %s, %s".formatted(values.get("x"), values.get("y"), values.get("r")));

                response(values);
            }
            catch (NumberFormatException | ValidationException e) {
                error(e.getMessage());
            }
            catch (Exception e) {
                error("неизвестная ошибка");
                logger.warning(Arrays.toString(e.getStackTrace()) + "\n" + e.getMessage());
            }
        }
    }

    protected void response(Map<String, Double> values) {
        var status = ValidationManager.checkData(values);
        var time = String.format(
                Locale.ENGLISH,
                "%.4f",
                (double) (System.nanoTime() - startTime) / 1000000);
        var content = """
                    {
                    "status": %s,
                    "time": %s
                    }
                    """;
        logger.info("Status: %s, time: %s".formatted(status, time));
        content = content.formatted(status, time);

        send(200, content);
    }

    protected void error(String message) {
        String content = """
                {
                "message": "%s"
                }
                """.formatted(message);

        send(400, content);
    }

    protected void send(int statusCode, String content) {
        var httpResponse = """
                        HTTP/1.1 %d %s
                        Content-Type: application/json
                        Content-Length: %d
                        
                        %s
                        """
                .formatted(
                        statusCode,
                        codesStatus.get(statusCode),
                        content.getBytes(StandardCharsets.UTF_8).length,
                        content
                );

        System.out.println(httpResponse);
    }

    protected String getRequest() throws IOException {
        FCGIInterface.request.inStream.fill();

        return FCGIInterface.request.params.getProperty("QUERY_STRING");
    }

    protected Map<String, Double> parseData(String params) {
        var arr = params.split("&");

        try {
            HashMap<String, Double> data = new HashMap<>();
            data.put("x", Double.valueOf(arr[0].substring(2)));
            data.put("y", Double.valueOf(arr[1].substring(2)));
            data.put("r", Double.valueOf(arr[2].substring(2)));

            return data;
        } catch (NumberFormatException e) {
            throw new NumberFormatException("некорректный формат данных");
        }
    }
}
