public class Main {

    public static void main(String[] args) {
        String path = "./src/input/2.txt";
        MultiThreadHandler handler = new MultiThreadHandler(DoubleWordsAlgorithm.class, new SimpleFetcher(path));
        handler.handle();
    }
}
