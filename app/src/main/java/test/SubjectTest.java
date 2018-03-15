package test;


import org.junit.Test;

import java.util.ArrayList;

import classes.Subject;
import classes.Week;

import static org.junit.Assert.assertEquals;

/**
 * Created by John on 16/3/2018.
 */

public class SubjectTest {

    @Test
    public void TestSubjectConstructor(){
        Subject ESC = new Subject("50.003", "Elements of Software Construction");
        assertEquals(ESC.getSubjectCode(),"50.003");
        assertEquals(ESC.getSubjectTitle(),"Elements of Software Construction");
    }

    @Test
    public void TestAddWeek(){
        Week week1 = new Week("Week 8");
        ArrayList<Week> actual = new ArrayList<>();
        actual.add(week1);
        Subject ESC = new Subject("50.003", "Elements of Software Construction");
        ESC.addWeek(week1);
        assertEquals(ESC.getWeeks(),actual);
    }

    @Test
    public void TestRemoveWeek(){
        Week week1 = new Week("Week 8");
        ArrayList<Week> actual = new ArrayList<>();
        Subject ESC = new Subject("50.003", "Elements of Software Construction");
        ESC.addWeek(week1);
        ESC.removeWeek(week1);
        assertEquals(ESC.getWeeks(),actual);
    }

    @Test
    public void TestAddStudent(){
        Subject ESC = new Subject("50.003", "Elements of Software Construction");
        ESC.addStudent("Bob");
        assertEquals(ESC.getTotalStudents("Bob"), 1,0);
    }

    @Test
    public void TestRemoveStudent(){
        Subject ESC = new Subject("50.003", "Elements of Software Construction");
        ESC.addStudent("Bob");
        ESC.removeStudent("Bob");
        assertEquals(ESC.getTotalStudents("Bob"), -1,0);
    }
}
