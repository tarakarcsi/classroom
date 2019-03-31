package api;


import exceptions.NoSuchSubjectException;
import exceptions.StudentAlreadySubscribedException;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BscSubject extends Subject {


    String name;
    String subjectId;
    List<String> students;
    String credit;
    String teacher;
    String time;

    public BscSubject(String name, String credit, String subjectId, List<String> students, String teacher, String lessonTime){
        this.name = name;
        this.subjectId = subjectId;
        this.credit = credit;
        this.teacher = teacher;
        this.time = lessonTime;
        this.students = students;
    }
    @Override
    public void subscribe(Student student) throws NoSuchSubjectException, StudentAlreadySubscribedException {

        if(students.contains(student.getStudentId())) {
            throw new StudentAlreadySubscribedException("student already subscribed!");
        }else {
            student.addSubject(this);
            students.add(student.getStudentId());
        }
    }

    @Override
    public String toString() {

        return "subject: " + name + '\'' + ", id: " + subjectId + "|" + "students: " + getStudents();
    }
}
