public class Main {

    public static void main(String[] args) {
        String path = "./Multithreading/src/input/2.txt";
        SynchronizedTask.setLogging(false);
        measure(new MultiThreadHandler(DoubleWordsAlgorithm.class, new SimpleFetcher(path)), 100);
        measure(new ExecutorServiceHandler(DoubleWordsAlgorithm.class, new SimpleFetcher(path)), 100);
    }

    private static void measure(Handler handler, int iterations) {
        long startTime = System.nanoTime();
        for (int i = 0; i < iterations; i++) {
            handler.handle();
        }
        System.out.printf("Execution time: %,dμs%n", (System.nanoTime() - startTime) / 1000 / iterations);
    }
}
