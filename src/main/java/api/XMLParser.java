package api;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class XMLParser {

    public Student getStudentById(List<Student> students, String id) {
        Student student = new Student();

        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).getStudentId().equals(id)) {
                student = students.get(i);
            }
        }
        return student;
    }

    public List<Student> readStudents(String xmlPath) throws ParserConfigurationException, IOException, SAXException { /* parsing the Students.xml file*/
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(new File(xmlPath));
        doc.getDocumentElement().normalize();
        List<Student> result = new ArrayList<>();


        NodeList studentNode = doc.getElementsByTagName("Student");

        for (int i = 0; i < studentNode.getLength(); i++) {
            Node studentNodes = studentNode.item(i);

            if (studentNodes.getNodeType() == Node.ELEMENT_NODE) {
                Element firstStudentElement = (Element) studentNodes;

                String name = (firstStudentElement.getAttribute("name"));
                String id = (firstStudentElement.getAttribute("id"));

                Student stud = new Student(name, id);
                result.add(stud);
            }
        }
        return result;
    }

    public List<Subject> getSubjects(String xmlPath) throws IOException, SAXException, ParserConfigurationException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(new File(xmlPath));
        doc.getDocumentElement().normalize();
        List<Subject> result = new ArrayList<>();

        NodeList subjectNode = doc.getElementsByTagName("Subject");

        for (int i = 0; i < subjectNode.getLength(); i++) {
            Node subjectNodes = subjectNode.item(i);

            if (subjectNodes.getNodeType() == Node.ELEMENT_NODE) {
                Element firstSubjectElement = (Element) subjectNodes;

                NodeList nameList = firstSubjectElement.getElementsByTagName("name");
                String name = nameList.item(0).getTextContent();

                NodeList idList = firstSubjectElement.getElementsByTagName("id");
                String id = idList.item(0).getTextContent();

                NodeList teacherList = firstSubjectElement.getElementsByTagName("teacher");
                String teacher = teacherList.item(0).getTextContent();

                NodeList timeList = firstSubjectElement.getElementsByTagName("time");
                String time = timeList.item(0).getTextContent();

                NodeList creditList = firstSubjectElement.getElementsByTagName("credit");
                String credit = creditList.item(0).getTextContent();

                NodeList sIdList = firstSubjectElement.getElementsByTagName("studentIds");
                List<String> students = new ArrayList<>();
                for (int j = 0; j < sIdList.getLength(); j++) {
                    Node sIdNode = sIdList.item(j);
                    if (sIdNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element idElement = (Element) sIdNode;

                        NodeList studIdList = idElement.getElementsByTagName("studentId");
                        for(int k = 0; k < studIdList.getLength(); k++) {
                            students.add(studIdList.item(k).getTextContent());
                        }
                    }
                }
                NodeList prerequisiteList = firstSubjectElement.getElementsByTagName("prerequisites");
                List<String> prerequisites = new ArrayList<>();
                for(int j = 0; j < prerequisiteList.getLength(); j++){
                    Node prerequisiteNode = prerequisiteList.item(j);
                    if(prerequisiteNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element prerequisiteElement = (Element) prerequisiteNode;

                        NodeList requisiteList = prerequisiteElement.getElementsByTagName("prerequisite");
                        for(int k = 0; k < requisiteList.getLength(); k++) {
                            prerequisites.add(requisiteList.item(k).getTextContent());
                        }
                    }
                }
                System.out.println(prerequisites);
                if (prerequisites.size() == 0) {
                    BscSubject bscSubject = new BscSubject(name, credit, id, students, teacher, time);
                    result.add(bscSubject);
                } else {
                    MscSubject mscSubject = new MscSubject(name, credit, id, students, teacher, time, prerequisites);
                    result.add(mscSubject);
                }
            }
        }
        return result;
    }
}
