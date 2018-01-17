public class Main {

    public static void main(String[] args) {
        String path = "./src/input/3.txt";
        Handler handler;
//        handler = new MultiThreadHandler(DoubleWordsAlgorithm.class, new SimpleFetcher(path));
        handler = new ExecutorServiceHandler(DoubleWordsAlgorithm.class, new SimpleFetcher(path));
        handler.handle();
    }
}
