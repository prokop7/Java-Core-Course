import java.lang.reflect.Proxy;

public class ProxyAlgorithmFactory implements AlgorithmFactory {
    private String filePath;

    public ProxyAlgorithmFactory(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public Algorithm newAlgorithm() {
        return (Algorithm) Proxy.newProxyInstance(
                Algorithm.class.getClassLoader(),
                new Class[]{Algorithm.class},
                new AlgorithmProxy(filePath));
    }
}
