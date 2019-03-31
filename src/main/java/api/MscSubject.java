package api;

import exceptions.MissingPrerequisiteException;
import exceptions.NoSuchSubjectException;
import exceptions.StudentAlreadySubscribedException;

import java.util.ArrayList;
import java.util.List;

public class MscSubject extends Subject {

    private List<String> prerequisites;

    public MscSubject(String name, String subjectId, String credit, List<String> students, String teacher, String time, List<String> prerequisites) {
        super(name, subjectId, credit, students, teacher, time);
        this.prerequisites = prerequisites;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getSubjectId() {
        return subjectId;
    }

    @Override
    public List<String> getStudents() {
        return students;
    }

    @Override
    public String getCredit() {
        return credit;
    }

    @Override
    public String getTeacher() {
        return teacher;
    }

    @Override
    public String getTime() {
        return time;
    }

    public List<String> getPrerequisites() {
        return prerequisites;
    }

    @Override
    public void subscribe(Student student) throws StudentAlreadySubscribedException, MissingPrerequisiteException {

        if (students.contains(student.getStudentId())) {
            throw new StudentAlreadySubscribedException("student already subscribed!");
        } else if (hasPreRequisites(student)) {
            student.addSubject(this);
            students.add(student.getStudentId());
        } else
            throw new MissingPrerequisiteException("Missing prerequisite!");
    }

    @Override
    public String toString() {
        return "subject: " + name + '\'' + ", id: " + subjectId + "|" + "students: " + getStudents() + "prerequitises: " + prerequisites;
    }


    public boolean hasPreRequisites(Student student) {
        for (String subject : prerequisites) {
            for (String studentSubject : student.getSubjectList()) {
                if (studentSubject.equals(subject)) {
                    return true;
                }
            }
        }
        return false;
    }
}