public interface Algorithm {
    void handle(String s) throws DuplicateWordException, UnexpectedSymbolException, InterruptedException;
}
