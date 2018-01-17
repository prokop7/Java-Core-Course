/***
 * Synchronized for stopping threads.
 * If error occurs in one of them then all of them will stop
 */
public class SynchronizedThread extends Thread {
    private Algorithm algorithm;
    private String inputString;
    private static boolean stopped;

    SynchronizedThread(String name, Algorithm algorithm, String inputString) {
        super(name);
        this.algorithm = algorithm;
        this.inputString = inputString;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < inputString.length(); i++) {
                // Stop thread if in another error happens
                // Check each 100 iterations for increasing performance
                if (i % 100 == 0)
                    synchronized (SynchronizedThread.class) {
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
            synchronized (SynchronizedThread.class) {
                stopped = true;
            }
            System.out.println(Thread.currentThread().getName() + " error caught.");
        }
    }
}
