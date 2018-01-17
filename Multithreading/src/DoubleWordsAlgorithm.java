import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
public class DoubleWordsAlgorithm implements Algorithm {
    private Set<Character> allowedLetters = new HashSet<>();
    // Set of separators
    private Set<Character> specialChars = new HashSet<>();
    private Set<String> wordSet = new HashSet<>();
    private List<Character> buffer = new ArrayList<>();

    DoubleWordsAlgorithm() {
        fillSetWithLetters(allowedLetters);
        fillSetWithMarks(specialChars);
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
    }

    /***
     * Fill set with allowed punctuation marks
     *
     * @param set the set to be filled
     */
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

    /***
     * Processes a character after a character instead of
     * an entire string at once because of the need to stop.
     *
     * @param c character to process
     * @throws  DuplicateWordException if the buffer contains word
     *          which already was in the <code>wordSet</code>
     * @throws  UnexpectedSymbolException if the character doesn't exists
     *          in <code>allowedLetters</code> or <code>specialChars</code>
     *
     * @see #buffer
     * @see #specialChars
     * @see #wordSet
     */
    @Override
    public void processNextSymbol(char c) throws DuplicateWordException, UnexpectedSymbolException {
        if (specialChars.contains(c)) {
            if (buffer.size() > 0)
                flush();
        } else if (allowedLetters.contains(c)) {
            buffer.add(c);
        } else
            throw new UnexpectedSymbolException(c);
    }

    /***
     * Flush buffer and write remained characters <code>wordSet</code>
     *
     * @throws DuplicateWordException if the buffer contains word
     *          which already was in the <code>wordSet</code>
     *
     * @see #buffer
     * @see #wordSet
     */
    @Override
    public void flush() throws DuplicateWordException {
        String newWord = buffer.stream().map(Object::toString).collect(Collectors.joining());
        if (wordSet.contains(newWord))
            throw new DuplicateWordException(newWord);
        wordSet.add(newWord);
        buffer.clear();
    }
}
