import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/***
 * Simple fetch algorithm, synchronously gets
 * String from the input file
 */
public class SimpleFetcher implements Fetcher {
    private String path;

    /***
     * Create instance of <code>SimpleFetcher</code> with
     * <code>path</code> to the working file
     * @param path A path to the working file
     */
    SimpleFetcher(String path) {
        this.path = path;
    }

    /***
     * Obtain synchronously all lines of file as Strings at once.
     * @return lines of file
     */
    @Override
    public List<String> fetchAll() {
        List<String> list = new ArrayList<>();
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(path));
            String line;
            while ((line = reader.readLine()) != null) {
                list.add(line);
            }
        } catch (IOException e) {
            System.out.printf("Could not open/read file %s%n", path);
            return null;
        }
        return list;
    }
}
