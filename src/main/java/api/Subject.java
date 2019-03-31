package api;

import exceptions.NoSuchSubjectException;
import exceptions.StudentAlreadySubscribedException;

import java.util.List;

public abstract class Subject {

    protected String name;
    protected String subjectId;
    protected List<String> students;
    protected String credit;
    protected String teacher;
    protected String time;
    protected List<String> prerequisites;
    protected Classroom classroom;

    public List<String> getStudents() {
        return students;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public List<String> getPrerequisites() {
        return prerequisites;
    }

    public abstract void subscribe(String studentId, String subjectId) throws NoSuchSubjectException, StudentAlreadySubscribedException;

}
