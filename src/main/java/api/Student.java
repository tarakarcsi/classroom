package api;

import java.util.ArrayList;
import java.util.List;

public class Student {

    private String name;
    private String studentId;
    private List<String> subjectList;


    public Student(String name, String studentId, List<String> subjects) {
        this.name = name;
        this.studentId = studentId;
        this.subjectList = subjects;
    }

    public void addSubject(Subject subject) {
        subjectList.add(subject.getSubjectId());
    }

    public String getStudentId() {
        return studentId;
    }

    public String getName() {
        return name;
    }

    public List<String> getSubjectList() {
        return subjectList;
    }

    @Override
    public String toString() {
        return "student: " + name + '\'' + ", id: " + studentId + "|";
    }
}
