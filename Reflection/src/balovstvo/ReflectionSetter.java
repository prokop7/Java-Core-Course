package balovstvo;

import java.lang.reflect.Field;

public class ReflectionSetter {
    public static void setFieldValue(Object value, Object container, String fieldName, Class containerClass) throws NoSuchFieldException {
        try {
            Field field = containerClass.getDeclaredField(fieldName);
            boolean isAccessible = field.isAccessible();
            if (!isAccessible) {
                field.setAccessible(true);
            }
            field.set(container, value);
            if (!isAccessible) {
                field.setAccessible(false);
            }
        } catch (NoSuchFieldException e) {
            if (containerClass.getSuperclass() != null)
                setFieldValue(value, container, fieldName, containerClass.getSuperclass());
            else
                throw e;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static Object getFieldValue(Object container, String fieldName, Class containerClass) throws NoSuchFieldException {
        Object value = null;
        try {
            Field field = containerClass.getDeclaredField(fieldName);
            boolean isAccessible = field.isAccessible();
            if (!isAccessible) {
                field.setAccessible(true);
            }
            value = field.get(container);
            if (!isAccessible) {
                field.setAccessible(false);
            }
        } catch (NoSuchFieldException e) {
            if (containerClass.getSuperclass() != null)
                return getFieldValue(container, fieldName, containerClass.getSuperclass());
            else
                throw e;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return value;
    }
}
