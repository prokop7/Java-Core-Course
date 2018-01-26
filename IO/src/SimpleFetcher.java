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
    private String[] inputs;
    private int offset = 0;

    /***
     * Create instance of <code>SimpleFetcher</code> with
     * <code>inputs</code> to the working file
     * @param path A inputs to the working file
     */
    SimpleFetcher(String path) {
        this.inputs = new String[1];
        this.inputs[0] = path;
    }

    SimpleFetcher(String[] inputFiles) {
        inputs = inputFiles;
    }

    /***
     * Obtain synchronously all lines from all files at once.
     * @return lines of file
     */
    @Override
    public List<String> fetchAll() {
        List<String> list = new ArrayList<>();
        for (String s : inputs) {
            list.addAll(fetchNext());
        }
        return list;
    }

    /***
     * Obtain synchronously all lines from the next file at once.
     * @return lines of file
     */
    @Override
    public List<String> fetchNext() {
        List<String> list = new ArrayList<>();
        if (offset >= inputs.length)
            return null;
        String input = inputs[offset++];
        try (BufferedReader reader = new BufferedReader(new FileReader(input))) {
            String line;
            while ((line = reader.readLine()) != null) {
                list.add(line);
            }
        } catch (IOException e) {
            System.out.printf("Could not open/read file %s%n", input);
        }
        return list;
    }
}
