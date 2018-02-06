public interface AlgorithmFactory {
    Algorithm newAlgorithm(Class<?> algorithmClass) throws IllegalAccessException, InstantiationException;
}
