package api;

import exceptions.NoSuchSubjectException;
import exceptions.StudentAlreadySubscribedException;

import java.util.List;

public class MscSubject extends Subject {

    private List<String> prerequisites;
    private Classroom classroom;

    public MscSubject(String name, String credit, String subjectId, List<String> students, String teacher, String lessonTime, List<String> prerequisites) {
        this.name = name;
        this.subjectId = subjectId;
        this.credit = credit;
        this.students = students;
        this.teacher = teacher;
        this.prerequisites = prerequisites;
    }
    public MscSubject () {

    }

    @Override
    public void subscribe(String studentId, String subjectId) throws NoSuchSubjectException, StudentAlreadySubscribedException {

        Subject tempSubject = classroom.checkSubject(subjectId);
        if (!classroom.isStudentSubscribed(studentId, tempSubject) && hasPreRequisites(this.prerequisites, studentId)) {
            tempSubject.getStudents().add(studentId);
        }
    }

    @Override
    public String toString() {
        return "subject: " + name + '\'' + ", id: " + subjectId + "|" + "students: " + getStudents() + "prerequitises: " + prerequisites;
    }


    public boolean hasPreRequisites(List<String> prerequisiteList, String studentId){
        Subject tempSubject;
        for(int i = 0; i < prerequisiteList.size(); i++) {
            for (int j = 0; j < classroom.getSubjects().size(); j++) {
                if (classroom.getSubjects().get(j).subjectId.equals(prerequisiteList.get(i))) {
                    tempSubject = classroom.getSubjects().get(j);
                    if(tempSubject.getStudents().contains(studentId)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}