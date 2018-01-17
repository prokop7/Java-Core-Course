import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ExecutorServiceHandler implements Handler {
    // Algorithm which will process strings
    private Class<?> algorithmClass;
    // Fetch algorithm
    private Fetcher fetcher;

    ExecutorServiceHandler(Class<?> algorithmClass, Fetcher fetcher) {
        this.algorithmClass = algorithmClass;
        this.fetcher = fetcher;
    }

    @Override
    public void handle() {
        List<String> list = fetcher.fetchAll();
        ExecutorService executor = Executors.newFixedThreadPool(list.size());
        for (String s : list) {
            Algorithm algorithm;
            try {
                algorithm = (Algorithm) algorithmClass.newInstance();
            } catch (InstantiationException | IllegalAccessException | ClassCastException e) {
                System.out.printf("Cannot create instance of %s%n", algorithmClass.getName());
                return;
            }
            executor.submit(new SynchronizedTask(algorithm, s));
        }
        // Join all threads
        executor.shutdown();
        try {
            executor.awaitTermination(100, TimeUnit.SECONDS);
            executor.shutdownNow();
        } catch (InterruptedException ignored) {
            executor.shutdownNow();
        }
        SynchronizedTask.resetStopped();
    }
}
