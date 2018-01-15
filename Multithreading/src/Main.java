public class Main {

    public static void main(String[] args) throws InterruptedException {
        MultiThreadHandler handler = new MultiThreadHandler(new SecondVariant(), new SimpleFetcher());
        handler.handle();
    }
}
