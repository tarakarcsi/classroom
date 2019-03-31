package api;

import api.Student;
import api.Subject;
import api.XMLParser;
import exceptions.IdAlreadyTakenException;
import exceptions.NoSuchSubjectException;
import exceptions.StudentAlreadySubscribedException;
import exceptions.SubjectAlreadyExistsException;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Classroom {

    private List<Student> students;
    private List<Subject> subjects;
    private XMLParser xmlP = new XMLParser();

    public Classroom(List<Student> students, List<Subject> subjects) {
        try {
            this.students = xmlP.readStudents("src/main/java/api/Students.xml");
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
        try {
            this.subjects = xmlP.getSubjects("src/main/java/api/Subjects.xml");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
    }

    public List<Student> getStudents() {
        return this.students;
    }

    public List<Subject> getSubjects() {
        return this.subjects;
    }

    public void addBscSubject(String name, String id, String credit, String teacher, String time) throws SubjectAlreadyExistsException, IOException, SAXException, ParserConfigurationException {
        for (int i = 0; i < subjects.size(); i++) {
            if (subjects.get(i).getSubjectId().equals(id)) {
                throw new SubjectAlreadyExistsException("This subject already exists.");
            }
        }
        subjects.add(new BscSubject(name, credit, id, null, teacher, time));
    }

    public void addMscSubject(String name, String id, String credit, String teacher, String time, List<String> prerequisites) throws SubjectAlreadyExistsException {
        for(int i = 0; i < subjects.size(); i++) {
            if(subjects.get(i).getSubjectId().equals(id)) {
                throw new SubjectAlreadyExistsException("This subject already exists+");
            }
        }
        subjects.add(new MscSubject(name, credit, id, null, teacher, time, prerequisites));
    }


    public void removeSubject(String id) {
        List<Subject> toRemove = new ArrayList<>();
        for (Subject subj : subjects) {
            if (subj.getSubjectId().equals(id))
                toRemove.add(subj);
        }
        subjects.removeAll(toRemove);
    }

    public void addStudent(String name, String id) throws IdAlreadyTakenException {
        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).getStudentId().equals(id)) {
                throw new IdAlreadyTakenException("This student ID is already booked.");
            }
        }
        students.add(new Student(name, id));
    }

    public void removeStudent(String id) {
        List<Student> toRemove = new ArrayList<>();
        for (Student student : students) {
            if (student.getStudentId().equals(id))
                toRemove.add(student);
        }
        students.removeAll(toRemove);
    }

    public List<String> getStudentsbySubject(String subjectId, String xmlPath) throws ParserConfigurationException, SAXException, IOException { //Lists the subscribed students' names for a subject (id given by user)
        List<String> studentsBySubject = new ArrayList<>();

        for (int i = 0; i < xmlP.getSubjects(xmlPath).size(); i++) {
            if (xmlP.getSubjects(xmlPath).get(i).getSubjectId().equals(subjectId)) {
                for (String id : xmlP.getSubjects(xmlPath).get(i).getStudents()) {
                    studentsBySubject.add(id);
                }
            }
        }
        return studentsBySubject;
    }

    public Subject checkSubject(String subjectId) throws NoSuchSubjectException {
        Subject tempSubject;
        for (int i = 0; i < subjects.size(); i++) {
            if (subjects.get(i).getSubjectId().equals(subjectId)) {
                tempSubject = subjects.get(i);
                return tempSubject;
            }
        }
        throw new NoSuchSubjectException("No such subject!");
    }
    public boolean isStudentSubscribed(String studentId, Subject subject) throws StudentAlreadySubscribedException {
        for (int i = 0; i < subject.getStudents().size(); i++) {
            if (subject.getStudents().get(i).equals(studentId)) {
                return true;
            }
        }
        return false;
    }
    @Override
    public String toString() {
        return "Classroom:" +
                "students=" + students + "\n" +
                "\t\t" + "subjects= " + subjects +
                "|";
    }
}
