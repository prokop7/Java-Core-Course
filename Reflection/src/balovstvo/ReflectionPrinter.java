package balovstvo;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class ReflectionPrinter {
    public static void printClass(Class cls) {
//        printConstructors(cls);
//        printFields(cls);
        printMethods(cls);
        printDeclaredMethods(cls);
    }

    private static void printMethods(Class cls) {
        for (Method method : cls.getMethods()) {
            printMethod(method);
        }
        System.out.println();
    }

    private static void printDeclaredMethods(Class cls) {
        for (Method method : cls.getDeclaredMethods()) {
            printMethod(method);
        }
        System.out.println();
    }

    private static void printMethod(Method method) {
        StringBuilder params = new StringBuilder();
        for (Class<?> type : method.getParameterTypes()) {
            params.append(type.getName()).append(", ");
        }
        Modifier modifier = new Modifier();
        System.out.printf("%s(%s):%s\n", method.getName(), params, method.getReturnType());
    }

    private static void printConstructors(Class cls) {
        for (Constructor constructor : cls.getConstructors()) {
            System.out.println(constructor.getName());
            System.out.println(constructor.getModifiers());
            for (Class type : constructor.getParameterTypes()) {
                System.out.println(type.getCanonicalName());
            }
            System.out.println();
        }
        System.out.println();
    }

    public static void printFields(Class cls) {
        System.out.println("Get fields:");
        for (Field field : cls.getFields()) {
            System.out.printf("%d - %s: %s%n", field.getModifiers(), field.getType().getName(), field.getName());
        }
        System.out.println("Get declared fields");
        for (Field field : cls.getDeclaredFields()) {
            System.out.printf("%d - %s: %s%n", field.getModifiers(), field.getType().getName(), field.getName());
        }
    }
}
