package cmd;

import api.*;
import exceptions.IdAlreadyTakenException;
import exceptions.NoSuchSubjectException;
import exceptions.StudentAlreadySubscribedException;
import exceptions.SubjectAlreadyExistsException;
import jdk.swing.interop.SwingInterOpUtils;
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
    BscSubject bsc = new BscSubject();
    MscSubject msc = new MscSubject();

    {
        try {
            classroom = new Classroom(xmlP.readStudents("src/main/java/api/Students.xml"), xmlP.getSubjects("src/main/java/api/Subjects.xml"));
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
    }

    public Menu() throws IOException, SAXException, ParserConfigurationException {
    }

    public void start() throws IOException, SAXException, ParserConfigurationException, IdAlreadyTakenException, NoSuchSubjectException, StudentAlreadySubscribedException {
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
                        if(isBsc.equals("bsc")) {
                            System.out.println("Please enter the subject's attributes(name/id/credit/teacher/lessontime) :");
                            classroom.addBscSubject(scanner.nextLine(), scanner.nextLine(), scanner.nextLine(), scanner.nextLine(), scanner.nextLine());
                        }else if(isBsc.equals("msc")) {
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
                    } catch(IdAlreadyTakenException ie) {
                        System.out.println(ie.getMessage());
                    }
                    break;
                case 5:
                    System.out.println("Please enter the student's id u'd like to remove:");
                    classroom.removeStudent(scanner.nextLine());
                    break;
                case 6:
                    System.out.println("Please enter a subject ID");
                    System.out.println(classroom.getStudentsbySubject(scanner.nextLine(), "src/api/Subjects.xml"));
                    break;
                case 7:
                    System.out.println("Please enter the subject and student to subscribe: ");
                    String subj = scanner.nextLine();
                    String stud = scanner.nextLine();
                    Subject tempSubject = null;
                    for(int i = 0; i < classroom.getSubjects().size(); i++) {
                        if(classroom.getSubjects().get(i).getSubjectId().equals(subj)) {
                            tempSubject = classroom.getSubjects().get(i);
                        }
                        if(tempSubject instanceof BscSubject) {
                            bsc.subscribe(stud, subj);
                        }else if(tempSubject instanceof MscSubject) {
                            msc.subscribe(stud, subj);
                        }
                    }

                    break;
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
        for(int i = 0; i < amount; i++) {
            prerequisiteList.add(scanner.nextLine());
        }
        return prerequisiteList;
    }

    public String getUserInput() {
        String input = scanner.nextLine();
        System.out.println();
        return input;
    }
}
