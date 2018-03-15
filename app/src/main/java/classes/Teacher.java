package classes;

/**
 * Created by Beng Haun on 2/12/2017.
 */

public class Teacher extends User {

    public Teacher(){
        super();
        //default constructor for Firebase
    }

    public Teacher(String username,String uid) {
        super(username, uid);
    }

    public void addSubject(Subject subject){
        subjects.add(subject);
    }

    public void createSubject(String subjectCode, String subjectTitle) {
        Subject subject = new Subject(subjectCode,subjectTitle);
    }

    public void createWeek(String title, Subject subject) {
        Week week = new Week(title);
        subject.addWeek(week);
    }

    public void removeWeek(String title, Subject subject){
        Week week = new Week(title);
        subject.removeWeek(week);
    }

    //public Integer[] getStudentAttendance(int studentID){

    //}

    //public Integer[][] getTotalAttendance(){

    //}

    //public float getStudentPerformance(int studentID){

    //}

    //public float[] getTotalPerformance(){

    //}

    public void startQuiz(Question question){
        question.open();
    }


    public void markAsCompleted(Question question) {
        question.close();
    }
}
