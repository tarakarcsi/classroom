package api;


import exceptions.NoSuchSubjectException;
import exceptions.StudentAlreadySubscribedException;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BscSubject extends Subject {

    public BscSubject(String name, String subjectId, String credit, List<String> students, String teacher, String time) {
        super(name, subjectId, credit, students, teacher, time);
    }

    @Override
    public void subscribe(Student student) throws NoSuchSubjectException, StudentAlreadySubscribedException {

        if (students.contains(student.getStudentId())) {
            throw new StudentAlreadySubscribedException("student already subscribed!");
        } else {
            student.addSubject(this);
            students.add(student.getStudentId());
        }
    }

    @Override
    public String toString() {

        return "subject: " + name + '\'' + ", id: " + subjectId + "|" + "students: " + getStudents();
    }
}
