import java.io.IOException;
import java.util.List;

public interface Fetcher {
    List<String> fetchAll() throws IOException;
}
