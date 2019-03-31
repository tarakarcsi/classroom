package api;

import exceptions.MissingPrerequisiteException;
import exceptions.NoSuchSubjectException;
import exceptions.StudentAlreadySubscribedException;

import java.util.ArrayList;
import java.util.List;

public abstract class Subject {

    String name;
    String subjectId;
    List<String> students;
    String credit;
    String teacher;
    String time;


    public Subject(String name, String subjectId, String credit,List<String> students, String teacher, String time) {
        this.name = name;
        this.subjectId = subjectId;
        this.students = students;
        this.credit = credit;
        this.teacher = teacher;
        this.time = time;
    }

    public Subject() {
    }

    public List<String> getStudents() {
        return students;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public String getName() {
        return name;
    }

    public String getCredit() {
        return credit;
    }

    public String getTeacher() {
        return teacher;
    }

    public String getTime() {
        return time;
    }

    public abstract void subscribe(Student student) throws NoSuchSubjectException, StudentAlreadySubscribedException, MissingPrerequisiteException;

}
