import java.util.Map;

public class ValidationManager {

    public static boolean checkData(Map<String, Double> data) {
        return checkAxis(data.get("x"), data.get("y"), data.get("r"));
    }

    public static boolean checkData(double[] data) {
        return checkAxis(data[0], data[1], data[2]);
    }

    private static boolean checkAxis(double x, double y, double r){
        return checkCircle(x, y, r) || checkTriangle(x, y, r) || checkRectangle(x, y, r);
    }

    private static boolean checkTriangle(double x, double y, double r){
        return x <= 0 && y <= 0 && y >= -r - x;
    }
    private static boolean checkRectangle(double x, double y, double r){
        return x <= 0 && y >= 0 && x >= -r/2 && y <= r;
    }
    private static boolean checkCircle(double x, double y, double r){
        return x >= 0 && y <= 0 && x * x + y * y <= r * r;
    }
}
