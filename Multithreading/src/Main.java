public class Main {

    public static void main(String[] args) {
        String path = "./src/input/1.txt";
        MultiThreadHandler handler = new MultiThreadHandler(DoubleWordsAlgorithm.class, new SimpleFetcher(path));
        handler.handle();
    }
}
