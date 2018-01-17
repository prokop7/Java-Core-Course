import java.util.List;

public class MultiThreadHandler {
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
        List<String> list = fetcher.fetchAll();
        for (int i = 0; i < list.size(); i++) {
            String s = list.get(i);
            Algorithm algorithm;
            try {
                algorithm = (Algorithm) algorithmClass.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                System.out.printf("Cannot create instance of %s%n", algorithmClass.getName());
                return;
            } catch (ClassCastException e) {
                System.out.printf("Class %s cannot be casted to Algorithm%n", algorithmClass.getName());
                return;
            }
            SynchronizedThread thread = new SynchronizedThread(String.format("Thread %d", i), algorithm, s);
            thread.start();
        }
    }
}
