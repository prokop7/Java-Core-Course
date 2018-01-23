public interface Algorithm {
    void processNextSymbol(char c) throws DuplicateWordException, UnexpectedSymbolException;
    void flush() throws DuplicateWordException;
    void resetAlgorithm();
}
