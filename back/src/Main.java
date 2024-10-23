public class Main {
    public static void main(String[] args) {
        long startTime = System.nanoTime();
        ResponseManager responseManager = new ResponseManager(startTime);

        responseManager.run();
    }
}