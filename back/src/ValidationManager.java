import exceptions.ValidationException;

import java.util.Arrays;
import java.util.Map;

public class ValidationManager {

    protected static double[] ys = new double[] {-3, -2, -1, 0, 1, 2, 3, 4, 5};
    protected static double[] rs = new double[] {1, 2, 3, 4, 5};

    public static boolean checkData(Map<String, Double> data) {
        double[] arr = new double[] {data.get("x"), data.get("y"), data.get("r")};
        return checkData(arr);
    }

    public static boolean checkData(double[] data) {
        if (!validate(data)) {
            throw new ValidationException("некорректные данные");
        }
        
        return checkAxis(data[0], data[1], data[2]);
    }
    
    protected static boolean validate(double[] data) {
        return (data[0] <= 5.0) && (data[0] >= -3.0) && Arrays.binarySearch(ys, data[1]) >= 0 && Arrays.binarySearch(rs, data[2]) >= 0;
    }

    protected static boolean checkAxis(double x, double y, double r){
        return checkCircle(x, y, r) || checkTriangle(x, y, r) || checkRectangle(x, y, r);
    }

    protected static boolean checkTriangle(double x, double y, double r){
        return x <= 0 && y <= 0 && y >= -r - x;
    }
    protected static boolean checkRectangle(double x, double y, double r){
        return x <= 0 && y >= 0 && x >= -r/2 && y <= r;
    }
    protected static boolean checkCircle(double x, double y, double r){
        return x >= 0 && y <= 0 && x * x + y * y <= r * r;
    }
}
