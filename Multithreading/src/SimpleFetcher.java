import java.util.ArrayList;
import java.util.List;

public class SimpleFetcher implements Fetcher{
    @Override
    public List<String> fetch() {
        List<String> list = new ArrayList<>();
        list.add("Something");
        list.add("Something 2");
        list.add("Something 3");
        return list;
    }
}
