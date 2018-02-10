import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

public class Main {
    private final static Set<String> set = new HashSet<>();

    public static void main(String[] args) {
        boolean unique = Arrays
                .stream(args)
                .parallel()
                .map(Main::openFile)
                .filter(Objects::nonNull)
                .flatMap(BufferedReader::lines)
                .flatMap(s -> Arrays.stream(s.split(" ")))
                .anyMatch(Main::checkWord);
        System.out.println(unique);
    }

    private static BufferedReader openFile(String s) {
        try {
            return new BufferedReader(new FileReader(s));
        } catch (FileNotFoundException e) {
            System.err.println(e.toString());
            return null;
        }
    }

    private static boolean checkWord(String s) {
        synchronized (set) {
            return !set.add(s);
        }
    }
}
