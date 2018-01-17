/***
 * Synchronized for stopping threads.
 * If error occurs in one of them then all of them will stop
 */
public class SynchronizedTask implements Runnable {
    private Algorithm algorithm;
    private String inputString;
    private static boolean stopped;

    SynchronizedTask(Algorithm algorithm, String inputString) {
        this.algorithm = algorithm;
        this.inputString = inputString;
    }

    public static void resetStopped() {
        stopped = false;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < inputString.length(); i++) {
                // Stop thread if in another error happens
                // Check each 10 iterations for increasing performance
                if (i % 10 == 0)
                    synchronized (SynchronizedTask.class) {
                        if (stopped) {
                            System.out.println(Thread.currentThread().getName() + " interrupted.");
                            return;
                        }
                    }
                algorithm.processNextSymbol(inputString.charAt(i));
            }
            algorithm.flush();
            System.out.println(Thread.currentThread().getName() + " finished.");
        } catch (UnexpectedSymbolException | DuplicateWordException e) {
            System.out.println(e);
            // Stop all threads
            synchronized (SynchronizedTask.class) {
                stopped = true;
            }
            System.out.println(Thread.currentThread().getName() + " error caught.");
        }
    }
}
