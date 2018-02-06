import loader.MyLoader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Proxy;

public class Main {
    public static void main(String[] args) throws IOException {
        while(true) {
            Worker worker = (Worker) Proxy.newProxyInstance(
                    Worker.class.getClassLoader(),
                    new Class[]{Worker.class},
                    new WorkerInkHandler());
            worker.init();
            worker.doWork();
            new BufferedReader(new InputStreamReader(System.in)).readLine();
        }
    }
}
