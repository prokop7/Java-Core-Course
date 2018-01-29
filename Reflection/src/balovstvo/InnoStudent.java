package balovstvo;

public class InnoStudent extends Student {
    public String hymn = "/autism";
    protected Integer grade;
    private int scholarship;

    public InnoStudent(String hymn, Integer grade, int scholarship) {
        this.hymn = hymn;
        this.grade = grade;
        this.scholarship = scholarship;
    }

    public InnoStudent(String secret, int course, char prefix, String hymn, Integer grade, int scholarship) {
        super(secret, course, prefix);
        this.hymn = hymn;
        this.grade = grade;
        this.scholarship = scholarship;
    }

    public void setScholarship(int scholarship) {
        this.scholarship = scholarship;
    }

    protected void updateGrade(int grade) {
        this.grade = grade;
    }

    private void sayDrop() {
        System.out.println("DROP!");
    }
}
