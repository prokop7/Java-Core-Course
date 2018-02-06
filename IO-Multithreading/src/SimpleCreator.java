public class SimpleCreator implements AlgorithmFactory {
    @Override
    public Algorithm newAlgorithm(Class<?> algorithmClass) throws IllegalAccessException, InstantiationException {
        return (Algorithm) algorithmClass.newInstance();
    }
}
