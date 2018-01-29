public interface Algorithm {
    boolean processNextSymbol(char c);
    boolean flush();
    void printResult();
    void resetAlgorithm();
}
