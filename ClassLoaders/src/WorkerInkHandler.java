import loader.MyLoader;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class WorkerInkHandler implements InvocationHandler{
    private Worker worker;
    private MyLoader myLoader;

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        long startTime = System.currentTimeMillis();
//        if (worker == null) {
//            if (myLoader == null) {
                myLoader = new MyLoader();
//            }
            worker = (Worker) myLoader.loadClass("WorkerA").newInstance();
//        }
//        System.out.printf("%s costs %d%n", method.getName(), System.currentTimeMillis() - startTime);
        return method.invoke(worker, args);
    }
}

