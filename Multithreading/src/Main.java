public class Main {

    /***
     * @param args 1) Path to input file
     *             2) Number of iterations (integer)
     *             3) Boolean flag for logging
     */
    public static void main(String[] args) {
        String path = args.length > 0 ? args[0] : "./src/input/2.txt";
        int numIterations = args.length > 1 ? Integer.parseInt(args[1]) : 1;
        SynchronizedTask.setLogging(args.length <= 2 || Boolean.parseBoolean(args[2]));
        measure(new MultiThreadHandler(DoubleWordsAlgorithm.class, new SimpleFetcher(path)), numIterations);
        measure(new ExecutorServiceHandler(DoubleWordsAlgorithm.class, new SimpleFetcher(path)), numIterations);
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
