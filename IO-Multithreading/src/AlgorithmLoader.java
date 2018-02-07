import java.io.ByteArrayOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class AlgorithmLoader extends ClassLoader {
    private String filePath;

    AlgorithmLoader(String filePath) {
        this.filePath = filePath;
    }

    @Override
    protected Class<?> findClass(String name) {
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            Files.copy(Paths.get(filePath + name + ".class"), bos);
            byte[] bytes = bos.toByteArray();
            return defineClass(name, bytes, 0, bytes.length);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return null;
    }
}
