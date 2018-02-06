public class Main {

    /***
     * @param inputFiles Array of paths to input file
     */
    public static void main(String[] inputFiles) {
        SynchronizedTask.setLogging(true);
        Handler handler = new ExecutorServiceHandler(
                new SimpleFetcher(inputFiles),
                DoubleWordsAlgorithm.class,
                new SimpleCreator());
        handler.handle();
    }

    /***
     * Runs <code>handler.handle</code> <code>iterations</code> times
     * and measure average time of execution
     * @param handler handler to test
     * @param iterations number of iterations to test
     */
    private static void measure(Handler handler, int iterations) {
        long startTime = System.nanoTime();
        for (int i = 0; i < iterations; i++) {
            handler.handle();
        }
        System.out.printf("Execution time: %,dμs%n", (System.nanoTime() - startTime) / 1000 / iterations);
    }
}
