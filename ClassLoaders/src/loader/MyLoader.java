package loader;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.ProtectionDomain;

public class MyLoader extends ClassLoader {
    private static final String LIB_PATH = "C:\\Users\\Monopolis\\IdeaProjects\\Java-Core-Course\\ClassLoaders\\lib\\";

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            Files.copy(Paths.get(LIB_PATH + name + ".class"), bos);
            byte[] bytes = bos.toByteArray();
            return defineClass(name, bytes, 0, bytes.length);
        } catch (IOException e) {
            throw new ClassNotFoundException();
        }
    }
}
