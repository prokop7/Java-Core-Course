package worksample;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class QuoterInvoker {
    public static void invoke(Quoter quoter) {
        try {
            Method method = quoter.getClass().getMethod("sayQuote");
            ShutupTimeout annotation = method.getAnnotation(ShutupTimeout.class);
            if (annotation != null) {
                Thread.sleep(annotation.timeout());
            method.invoke(quoter, null);
            }
        } catch (NoSuchMethodException | InterruptedException | IllegalAccessException | InvocationTargetException ignored) {
        }
    }
}
