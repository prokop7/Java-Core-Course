import java.util.List;

public interface Fetcher {
    List<String> fetchAll();
    List<String> fetchNext();
}
