import java.util.List;

public class MultiThreadHandler {
    private Algorithm algorithm;
    private Fetcher fetcher;

    MultiThreadHandler(Algorithm algorithm, Fetcher fetcher) {
        this.algorithm = algorithm;
        this.fetcher = fetcher;
    }

    public void handle() throws InterruptedException {
        List<String> list = fetcher.fetch();
        ThreadGroup threadGroup = new ThreadGroup("Group");
//        Thread[] threads = new Thread[list.size()];

        for (int i = 0; i < list.size(); i++) {
            String s = list.get(i);
            Thread thread = new MyThread(threadGroup, "Thread " + i, algorithm, s);
            thread.start();
        }
        Thread[] threads = new Thread[list.size()];
        threadGroup.enumerate(threads);
        Thread.sleep(1000);
        threads[0].interrupt();
    }
}
