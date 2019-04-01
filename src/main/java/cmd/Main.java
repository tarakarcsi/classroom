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

        //System.out.println(xmlP.getSubjects("src/main/java/api/Subjects.xml"));

        Menu menu = new Menu();
        try {
            menu.start();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
