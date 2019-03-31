package api;


import exceptions.NoSuchSubjectException;
import exceptions.StudentAlreadySubscribedException;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.List;

public class BscSubject extends Subject {

    private List<Subject> subjects;
    private XMLParser xmlP = new XMLParser();


    public BscSubject(String name, String credit, String subjectId, List<String> students, String teacher, String lessonTime) throws ParserConfigurationException, SAXException, IOException {
        this.name = name;
        this.subjectId = subjectId;
        this.credit = credit;
        this.students = students;
        this.teacher = teacher;
        this.time = lessonTime;
        this.subjects = xmlP.getSubjects("src/main/java/api/Subjects.xml");
    }

    public BscSubject(){

    }
    
    @Override
    public void subscribe(String studentId, String subjectId) throws NoSuchSubjectException, StudentAlreadySubscribedException {

        Subject tempSubject = classroom.checkSubject(subjectId);
        if(!classroom.isStudentSubscribed(studentId, tempSubject))
            tempSubject.getStudents().add(studentId);
        else
            throw new StudentAlreadySubscribedException("students already subscribed!");
    }

    @Override
    public String toString() {

        return "subject: " + name + '\'' + ", id: " + subjectId + "|" + "students: " + getStudents();
    }
}
