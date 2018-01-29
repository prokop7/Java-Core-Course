/***
 * Synchronized for stopping threads.
 * If error occurs in one of them then all of them will stop
 */
public class SynchronizedTask implements Runnable {
    private Algorithm algorithm;
    private String inputString;
    private static boolean stopped;
    private static boolean logging = true;

    SynchronizedTask(Algorithm algorithm, String inputString) {
        this.algorithm = algorithm;
        this.inputString = inputString;
    }

    public static void resetStopped() {
        stopped = false;
    }

    public static void setLogging(boolean logging) {
        SynchronizedTask.logging = logging;
    }

    public static boolean getLogging() {
        return logging;
    }

    @Override
    public void run() {
        for (int i = 0; i < inputString.length(); i++) {
            // Stop thread if in another error happens
            // Check each 10 iterations for increasing performance
            if (i % 10 == 0)
                synchronized (SynchronizedTask.class) {
                    if (stopped) {
                        if (logging)
                            System.out.println(Thread.currentThread().getName() + " interrupted.");
                        return;
                    }
                }
            if (!algorithm.processNextSymbol(inputString.charAt(i))) {
                stopThread();
                return;
            }
        }
        if (!algorithm.flush()) {
            stopThread();
            return;
        }
        if (logging)
            System.out.println(Thread.currentThread().getName() + " finished.");
    }


    /***
     * Stop all threads
     */
    private void stopThread() {
        synchronized (SynchronizedTask.class) {
            stopped = true;
        }
        if (logging) {
            System.out.printf("%s error caught.%n", Thread.currentThread().getName());
        }
    }
}
