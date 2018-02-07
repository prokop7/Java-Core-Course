import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/***
 * The algorithm for second variant
 *      <quote>
 *          Необходимо разработать программу, которая получает
 *          на вход список ресурсов, содержащих текст, и проверяет
 *          уникальность каждого слова. Каждый ресурс должен быть
 *          обработан в отдельном потоке, текст не должен содержать
 *          инностранных символов, только кириллица, знаки препинания и
 *          цифры. В случае нахождения дубликата, программа должна
 *          прекращать выполнение с соответсвующим сообщением.
 *          Все ошибки должны быть корректно обработаны.
 *      </quote>
 * @see Algorithm
 */
public class AlgorithmImpl1 implements Algorithm {
    private Set<Character> allowedLetters = new HashSet<>();
    // Set of separators
    private Set<Character> separatorChars = new HashSet<>();
    private static ConcurrentHashMap.KeySetView<String, Boolean> wordSet = ConcurrentHashMap.newKeySet();
    private List<Character> buffer = new ArrayList<>();
    private boolean isCutted = false;

    public AlgorithmImpl1() {
        fillSetWithLetters(allowedLetters);
        fillSetWithSeparators(separatorChars);
    }

    public void printResult() {
        for (String s : wordSet) {
            System.out.println(s);
        }
    }

    /***
     * Fill set with allowed letters
     *
     * @param set the set to be filled
     */
    private void fillSetWithLetters(Set<Character> set) {
        for (int i = 'а'; i <= 'я'; i++) {
            set.add((char) i);
        }
        for (int i = 'А'; i <= 'Я'; i++) {
            set.add((char) i);
        }
        set.add('ё');
        set.add('Ё');
    }

    /***
     * Fill set with allowed punctuation marks and other separators
     *
     * @param set the set to be filled
     */
    private void fillSetWithSeparators(Set<Character> set) {
        for (int i = '0'; i <= '9'; i++) {
            set.add((char) i);
        }

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
        set.add('—');
        set.add(' ');
    }

    /***
     * Processes a character after a character instead of
     * an entire string at once because of the need to stop.
     *
     * @param c character to process
     *
     * @see #buffer
     * @see #separatorChars
     * @see #wordSet
     */
    @Override
    public boolean processNextSymbol(char c) {
        if (separatorChars.contains(c)) {
            isCutted = false;
            if (buffer.size() > 0)
                return flush();
        } else if (buffer.size() == 9) {
            buffer.add(c);
            isCutted = true;
            return flush();
        } else if (allowedLetters.contains(c)) {
            if (!isCutted)
                buffer.add(c);
        } else
            return false;
        return true;
    }

    /***
     * Flush buffer and write remained characters <code>wordSet</code>
     *
     * @see #buffer
     * @see #wordSet
     */
    @Override
    public boolean flush() {
        if (buffer.size() == 0)
            return true;
        String newWord = buffer.stream().map(Object::toString).collect(Collectors.joining());
        if (wordSet.contains(newWord)) {
            System.out.println("Double word:" + newWord);
            return false;
        }
        wordSet.add(newWord);
        buffer.clear();
        return true;
    }

    @Override
    public void resetAlgorithm() {
        wordSet.clear();
    }
}
