package classes;

import java.util.ArrayList;

/* TODO: Implement these classes. */

public abstract class User {

    ArrayList<Subject> subjects = new ArrayList<>();
    String emailAdr;
    String uid;
    String username;

    public abstract void addSubject (Subject subject);

    public User(String emailAdr, String uid){
        this.emailAdr=emailAdr;
        this.uid = uid;
    }
    public User(){};

    public void setEmailAdr(String email){
        emailAdr = email;
    }

    public void setUsername(String name){
        username = name;
    }

    public String getUsername(){
        return username;
    }

    public void removeSubject(Subject s){subjects.remove(s);}
    //public getters for Firebase
    public ArrayList<Subject> getSubjects() {
        return subjects;
    }

    public String getEmailAdr() {
        return emailAdr;
    }

    public String getUid(){return uid;}
}














