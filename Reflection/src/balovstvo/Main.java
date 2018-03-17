package balovstvo;

public class Main {

    public static void main(String[] args) throws NoSuchFieldException, NoSuchMethodException {
        Student student = new Student();
        InnoStudent innoStudent = new InnoStudent(
                "Secret",
                3,
                'b',
                "#Autism",
                5,
                24);
//        balovstvo.ReflectionPrinter.printClass(student.getClass());
//        balovstvo.ReflectionPrinter.printClass(balovstvo.InnoStudent.class);
//        balovstvo.ReflectionPrinter.printClass(innoStudent.getClass());
//        balovstvo.ReflectionSetter.setFieldValue("new secret", innoStudent, "secret", innoStudent.getClass());
//        Object secret = balovstvo.ReflectionSetter.getFieldValue(innoStudent, "secret", innoStudent.getClass());
//        System.out.println(secret);
//        balovstvo.ReflectionInvoker.invokeMethodWithoutArgs(innoStudent, "saySecret", innoStudent.getClass());
    }
}
