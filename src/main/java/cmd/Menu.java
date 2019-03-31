package cmd;

import api.*;
import exceptions.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Menu {

    private Scanner scanner = new Scanner(System.in);
    private XMLParser xmlP = new XMLParser();
    private Classroom classroom;




    public Menu() throws IOException, SAXException, ParserConfigurationException {
    }

    public void start() throws IOException, SAXException, ParserConfigurationException, IdAlreadyTakenException, NoSuchSubjectException, StudentAlreadySubscribedException {
        try {
            classroom = new Classroom(xmlP.readStudents("src/main/java/api/Students.xml"), xmlP.getSubjects("src/main/java/api/Subjects.xml"));
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
        int menuOption;
        do {
            while (true) {

                menuPrinter(new String[]{
                        "Show current classroom",
                        "Add a subject",
                        "Remove a subject by id",
                        "Add student",
                        "Remove a student by id",
                        "Find the students of a subject",
                        "Subscribe for subject"
                });
                System.out.print("Please select an option: ");
                try {
                    menuOption = Integer.parseInt(getUserInput());
                    break;
                } catch (NumberFormatException ne) {
                    System.out.println("Invalid input. Please try again!");
                    continue;
                }
            }
            switch (menuOption) {
                case 1:
                    System.out.println(classroom);
                    break;
                case 2:
                    System.out.println("Is this subject Bsc or Msc?");
                    String isBsc = scanner.nextLine();
                    try {
                        if (isBsc.equals("bsc")) {
                            System.out.println("Please enter the subject's attributes(name/id/credit/teacher/lessontime) :");
                            classroom.addBscSubject(scanner.nextLine(), scanner.nextLine(), scanner.nextLine(), scanner.nextLine(), scanner.nextLine());
                        } else if (isBsc.equals("msc")) {
                            System.out.println("Please enter how many prerequisites this subject has: ");
                            int amount = Integer.parseInt(scanner.nextLine());
                            System.out.println("Please enter the subject's attributes(name/id/credit/teacher/lessontime/prerequisites) :");
                            classroom.addMscSubject(scanner.nextLine(), scanner.nextLine(), scanner.nextLine(), scanner.nextLine(), scanner.nextLine(), getPrerequisites(amount));
                        }
                    } catch (SubjectAlreadyExistsException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 3:
                    System.out.println("Please enter the subject's id u'd like to remove:");
                    classroom.removeSubject(scanner.nextLine());
                    break;
                case 4:
                    System.out.println("Please enter the student's attributes (name, id): ");
                    try {
                        classroom.addStudent(scanner.nextLine(), scanner.nextLine());
                    } catch (IdAlreadyTakenException ie) {
                        System.out.println(ie.getMessage());
                    }
                    break;
                case 5:
                    System.out.println("Please enter the student's id u'd like to remove:");
                    classroom.removeStudent(scanner.nextLine());
                    break;
                case 6:
                    System.out.println("Please enter a subject ID");
                    Subject tempSubject = checkSubject(scanner.nextLine(), classroom.getSubjects());
                    getStudentBySubject(tempSubject);
                    break;
                case 7:
                    System.out.println("Please enter the subject and student to subscribe: ");
                    String subj = scanner.nextLine();
                    String stud = scanner.nextLine();
                    Subject temporarySubject = checkSubject(subj, classroom.getSubjects());
                    Student tempStudent = findStudent(stud);
                    if(tempStudent != null) {
                        try {
                            temporarySubject.subscribe(findStudent(stud));
                        } catch (MissingPrerequisiteException e) {
                            System.out.println(e.getMessage());
                        }
                    }else
                        System.out.println("No student found!");
            }
        }
        while (menuOption != 0);
    }

    private void menuPrinter(String[] options) {
        int counter = 1;
        for (String option : options) {
            System.out.println(counter + ". " + option);
            counter++;
        }
        System.out.println("0. Exit\n");
    }

    public List<String> getPrerequisites(int amount) {
        List<String> prerequisiteList = new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            try {
                prerequisiteList.add(checkSubject(scanner.nextLine(), classroom.getSubjects()).getSubjectId());
            } catch (NoSuchSubjectException e) {
                System.out.println(e.getMessage());
            }
        }
        return prerequisiteList;
    }

    public String getUserInput() {
        String input = scanner.nextLine();
        System.out.println();
        return input;
    }

    public void getStudentBySubject(Subject subject) {

        for (String student : subject.getStudents()) {
            System.out.println(student);
        }
    }

    public Subject checkSubject(String subjectId, List<Subject> subjects) throws NoSuchSubjectException {
        Subject tempSubject;
        for (int i = 0; i < subjects.size(); i++) {
            if (subjects.get(i).getSubjectId().equals(subjectId)) {
                tempSubject = subjects.get(i);
                return tempSubject;
            }
        }
        throw new NoSuchSubjectException("No such subject!");
    }

    private Student findStudent(String studentId) {

        for (Student student : classroom.getStudents()) {
            if (student.getStudentId().equals(studentId))
                return student;
        }
        return null;
    }
}
