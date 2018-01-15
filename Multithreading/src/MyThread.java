public class MyThread extends Thread {
    private Algorithm algorithm;
    private String inputString;

    MyThread(ThreadGroup group, String name) {
        super(group, name);
    }

    MyThread(ThreadGroup group, String name, Algorithm algorithm, String inputString) {
        super(group, name);
        this.algorithm = algorithm;
        this.inputString = inputString;
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + " starting.");
        try {
            algorithm.handle(inputString);
        } catch (DuplicateWordException | UnexpectedSymbolException e) {
            System.out.println(Thread.currentThread().getName() + " interrupted.");
            getThreadGroup().interrupt();
        }
        System.out.println(Thread.currentThread().getName() + " exiting.");
    }
}
