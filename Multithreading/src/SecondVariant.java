import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class SecondVariant implements Algorithm {
    private Set<Character> allowedLetters = new HashSet<>();
    private Set<Character> specialChars = new HashSet<>();
    private Set<String> wordSet = new HashSet<>();

    SecondVariant() {
        fillSetWithLetters(allowedLetters);
        fillSetWithMarks(specialChars);
    }

    private void fillSetWithLetters(Set<Character> set) {
        for (int i = 'а'; i <= 'я'; i++) {
            set.add((char) i);
        }
        for (int i = 'А'; i <= 'Я'; i++) {
            set.add((char) i);
        }
    }

    private void fillSetWithMarks(Set<Character> set) {
        set.add(',');
        set.add('.');
        set.add('?');
        set.add('!');
        set.add(':');
        set.add('-');
        set.add('"');
        set.add(';');
        set.add('(');
        set.add(')');

        set.add(' ');
    }

    @Override
    public void handle(String s) throws UnexpectedSymbolException, DuplicateWordException {
        List<Character> buffer = new ArrayList<>();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (specialChars.contains(c)) {
                String newWord = buffer.stream().map(Object::toString).collect(Collectors.joining());
                if (wordSet.contains(newWord))
                    throw new DuplicateWordException(newWord);
                wordSet.add(newWord);
                buffer.clear();
                continue;
            }
            if (allowedLetters.contains(c)) {
                buffer.add(c);
                continue;
            }
            throw new UnexpectedSymbolException(c);
        }
    }
}
