package cmd;

import api.Classroom;
import api.Student;
import api.Subject;
import api.XMLParser;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.List;

public class Main {


    public static void main(String[] args) throws IOException, SAXException, ParserConfigurationException {
        XMLParser xmlP = new XMLParser();
        List<Student> students = xmlP.readStudents("src/main/java/api/Students.xml");
        List<Subject> subjects = xmlP.getSubjects("src/main/java/api/Subjects.xml");

        Classroom classroom = new Classroom(xmlP.readStudents("src/main/java/api/Students.xml"), xmlP.getSubjects("src/main/java/api/Subjects.xml"));

        System.out.println(xmlP.getSubjects("src/main/java/api/Subjects.xml"));

        Menu menu = new Menu();
        try {
            menu.start();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
