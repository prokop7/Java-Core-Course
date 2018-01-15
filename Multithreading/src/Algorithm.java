public interface Algorithm {
    boolean handle(String s) throws DuplicateWordException, UnexpectedSymbolException;
}
