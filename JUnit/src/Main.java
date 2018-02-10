import java.time.Clock;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        list.add("123 ");
        list.add(" 123");
        list.add(" 123 ");
        String reduce = list.stream().reduce("", (v, a) -> v + a);
        System.out.println(reduce);
    }
}
