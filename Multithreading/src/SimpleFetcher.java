import java.util.ArrayList;
import java.util.List;

public class SimpleFetcher implements Fetcher{
    @Override
    public List<String> fetch() {
        List<String> list = new ArrayList<>();
        list.add("Something");
        list.add("Som thing 2");
        list.add("Som thing 3");
        return list;
    }
}
