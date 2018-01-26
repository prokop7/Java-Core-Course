import java.util.List;

public class MultiThreadHandler implements Handler {
    // Algorithm which will process strings
    private Class<?> algorithmClass;
    // Fetch algorithm
    private Fetcher fetcher;

    /***
     * Creates an instance of <code>MultiThreadHandler</code>
     * @param algorithmClass algorithm to handle Strings
     *                       should be inherited from <code>Algorithm</code>
     *                       and has constructor without parameters
     * @param fetcher fetch algorithm to obtain Strings
     *
     * @see Algorithm
     */
    MultiThreadHandler(Class<?> algorithmClass, Fetcher fetcher) {
        this.algorithmClass = algorithmClass;
        this.fetcher = fetcher;
    }

    /***
     * Fetch Strings by <code>fetcher</code> and
     * process them by <code>algorithmClass</code>
     *
     * @see #fetcher
     * @see #algorithmClass
     */
    public void handle() {
        List<String> list;
        list = fetcher.fetchAll();
        Thread[] threads = new Thread[list.size()];
        SynchronizedTask.resetStopped();
        for (int i = 0; i < list.size(); i++) {
            String s = list.get(i);
            Algorithm algorithm;
            try {
                algorithm = (Algorithm) algorithmClass.newInstance();
            } catch (InstantiationException | IllegalAccessException | ClassCastException e) {
                System.out.printf("Cannot create instance of %s%n", algorithmClass.getName());
                return;
            }
            Thread thread = new Thread(new SynchronizedTask(algorithm, s), String.format("Thread %d", i));
            threads[i] = thread;
            thread.start();
        }
        for (Thread thread : threads)
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        try {
            Algorithm algorithm = (Algorithm) algorithmClass.newInstance();
            algorithm.resetAlgorithm();
        } catch (InstantiationException | IllegalAccessException | ClassCastException ignored) {
            return;
        }
        SynchronizedTask.resetStopped();
    }
}
