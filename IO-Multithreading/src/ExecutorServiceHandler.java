import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ExecutorServiceHandler implements Handler {
    private AlgorithmFactory factory;
    // Algorithm which will process strings
    private Class<?> algorithmClass;
    // Fetch algorithm
    private Fetcher fetcher;

    ExecutorServiceHandler(Fetcher fetcher, Class<?> algorithmClass, AlgorithmFactory factory) {
        this.algorithmClass = algorithmClass;
        this.fetcher = fetcher;
        this.factory = factory;
    }

    @Override
    public void handle() {
        List<String> list;
        list = fetcher.fetchAll();
        if (list.size() == 0) {
            System.out.println("No sources were found");
            return;
        }
        ExecutorService executor = Executors.newFixedThreadPool(list.size());
        for (String s : list) {
            Algorithm algorithm = factory.newAlgorithm();
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

        try {
            Algorithm algorithm = (Algorithm) algorithmClass.newInstance();
            algorithm.printResult();
            algorithm.resetAlgorithm();
        } catch (InstantiationException | IllegalAccessException | ClassCastException ignored) {
            return;
        }
        SynchronizedTask.resetStopped();
    }
}
