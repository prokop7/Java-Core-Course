public class Main {

    public static void main(String[] args) {
        String path = "./src/input/2.txt";
        measure(new MultiThreadHandler(DoubleWordsAlgorithm.class, new SimpleFetcher(path)));
        measure(new ExecutorServiceHandler(DoubleWordsAlgorithm.class, new SimpleFetcher(path)));
    }

    private static void measure(Handler handler) {
        long startTime = System.nanoTime();
        handler.handle();
        System.out.printf("Execution time: %,dμs%n", (System.nanoTime() - startTime) / 1000);
    }
}
