package classes;

import java.util.ArrayList;


public abstract class User {

    ArrayList<Subject> subjects = new ArrayList<>();
    String uid;
    String username;

    public abstract void addSubject (Subject subject);

    public User(String username, String uid){
        this.uid = uid;
        this.username = username;
    }
    public User(){};


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

    public String getUid(){return uid;}
}














