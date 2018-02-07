import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.FileTime;

public class AlgorithmProxy implements InvocationHandler {
    private Algorithm algorithm;
    private String filePath;
    private ClassLoader classLoader = new AlgorithmLoader(filePath);
    private long lastModified = 0;

    public AlgorithmProxy(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws
            InvocationTargetException,
            IllegalAccessException {
        String name = "AlgorithmImpl";
        if (Files.exists(Paths.get(filePath + name + 1 + ".class")))
            name += 1;
        else
            name += 2;
        FileTime lastModifiedTime = null;
        try {
            lastModifiedTime = Files.getLastModifiedTime(Paths.get(filePath + name + ".class"));
        } catch (IOException e) {
            System.out.printf("Can't find a class %s%s.class%n", filePath, name);
        }

        if (algorithm == null || lastModified != lastModifiedTime.toMillis()) {
            lastModified = lastModifiedTime.toMillis();
            try {
                algorithm = (Algorithm) classLoader.loadClass(name).newInstance();
            } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
                System.out.println(e.toString());
                System.out.println("Terminating");
            }
        }
        return method.invoke(algorithm, args);
    }
}
