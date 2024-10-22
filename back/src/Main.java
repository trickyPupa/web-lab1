public class Main {
    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        ResponseManager responseManager = new ResponseManager(startTime);

        responseManager.run();
    }
}