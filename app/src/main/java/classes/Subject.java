package classes;

import android.annotation.TargetApi;
import android.os.Build;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Beng Haun on 2/12/2017.
 */

public class Subject {

    String subjectCode;
    String subjectTitle;
    ArrayList<Week> weeks = new ArrayList<>();
    static ArrayList<Week> allWeeks = new ArrayList<>();
    static Map<String,Integer> totalStudentsByCourse = new HashMap<>();
    int totalStudents = 0;

    public Subject(){
        //default constructor for Firebase
    }

    public Subject(String subjectCode, String subjectTitle) {
        this.subjectCode = subjectCode;
        this.subjectTitle = subjectTitle;
        totalStudentsByCourse.put(subjectCode,0);
    }

    public void addWeek(Week week) {
        week.setKey(getKey()+" "+ week.getTitle());
        weeks.add(week);
        allWeeks.add(week);
    }

    public void removeWeek(Week week){
        for (int i = 0; i < weeks.size(); i++){
            if (weeks.get(i).getKey().matches(week.getKey())){
                weeks.remove(i);
            }
        }
    }
    public void addStudent(String key){
        if (totalStudentsByCourse.containsKey(key)){
            totalStudentsByCourse.put(key,totalStudentsByCourse.get(key) + 1);
        }
        else{
            totalStudentsByCourse.put(key,1);
        }
    }

    @TargetApi(Build.VERSION_CODES.N)
    public void removeStudent(String key){
        if(totalStudentsByCourse.containsKey(key)){
            totalStudentsByCourse.remove(key,totalStudentsByCourse.get(key)-1);
        }
        else{
            totalStudentsByCourse.remove(key,-1);
        }
    }

    public static double getTotalStudents(String key){
        if (totalStudentsByCourse.containsKey(key)){
            return totalStudentsByCourse.get(key);
        }
        return -1;

    }

    //get database key, for ease in retrieving value from database
    public String getKey(){
        return subjectCode.replace(".","");
    }

    //public getters for Firebase
    public String getSubjectCode() {
        return subjectCode;
    }

    public String getSubjectTitle() {
        return subjectTitle;
    }

    public ArrayList<Week> getWeeks() {
        return weeks;
    }

}