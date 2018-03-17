package balovstvo;

import java.util.Random;

public class
Student {
    private String secret;
    protected int course;
    public char prefix;

    public Student() {
    }

    public Student(String secret, int course, char prefix) {
        this.secret = secret;
        this.course = course;
        this.prefix = prefix;
    }

    public void sayHello() {
        System.out.println("Hello");
    }
    protected int getRandom() {
        Random random = new Random();
        return random.nextInt();
    }

    private void updateSecret(String secret) {
        this.secret = secret;
    }

    private void saySecret(){
        System.out.println(secret);
    }
}
