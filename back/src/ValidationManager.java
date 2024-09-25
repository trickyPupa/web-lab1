public class ValidationManager {

    public static boolean checkData(double[] data) {
        return checkAxis(data[0], data[1], data[2]);
    }

    private static boolean checkAxis(double x, double y, double r){
        if (x == 0 && y == 0) {
            return true;
        } else if (x > 0 && y > 0) {
            return false;
        } else if(x <= 0 && y >= 0) {
            return checkRectangle(x, y, r);
        } else if(x <= 0 && y <= 0) {
            return checkTriangle(x, y, r);
        } else {
            return checkCircle(x, y, r);
        }
    }

    private static boolean checkTriangle(double x, double y, double r){
        return y >= -r - x;
    }
    private static boolean checkRectangle(double x, double y, double r){
        return x >= -r/2 && y <= r;
    }
    private static boolean checkCircle(double x, double y, double r){
        return x * x + y * y <= r * r;
    }
}
