public class Main {

    /***
     * @param inputFiles Array of paths to input file
     */
    public static void main(String[] inputFiles) {
        SynchronizedTask.setLogging(true);
        Handler handler = new ExecutorServiceHandler(DoubleWordsAlgorithm.class, new SimpleFetcher(inputFiles));
        handler.handle();
    }
}
