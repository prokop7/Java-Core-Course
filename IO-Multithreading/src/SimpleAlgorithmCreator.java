public class SimpleAlgorithmCreator implements AlgorithmFactory {
    @Override
    public Algorithm newAlgorithm() {
        return new DoubleWordsAlgorithm();
    }
}
