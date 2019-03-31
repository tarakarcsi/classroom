package api;

public class Student {

    private String name;
    private String studentId;


    public Student(String name, String studentId) {
        this.name = name;
        this.studentId = studentId;
    }

    public Student() {
    }

    public String getStudentId() {
        return studentId;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "student: " + name + '\'' + ", id: " + studentId + "|";
    }
}
