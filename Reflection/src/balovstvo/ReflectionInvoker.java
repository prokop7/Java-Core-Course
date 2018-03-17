package balovstvo;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ReflectionInvoker {
    public static void invokeMethodWithoutArgs(Object container, String methodName, Class containerClass) throws NoSuchMethodException {
        try {
            Method method = containerClass.getDeclaredMethod(methodName);
            boolean isAccessible = method.isAccessible();
            if (!isAccessible)
                method.setAccessible(true);
            method.invoke(container, null);
            if (!isAccessible)
                method.setAccessible(false);
        } catch (NoSuchMethodException e) {
            if (containerClass.getSuperclass() != null)
                invokeMethodWithoutArgs(container, methodName, containerClass.getSuperclass());
            else
                throw e;
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
