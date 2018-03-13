package classes;

import java.util.ArrayList;

/**
 * Created by Beng Haun on 2/12/2017.
 */

public class Feedback {

    int rating;
    int dontUnderstand = 0;
    ArrayList<Student> voted = new ArrayList<>();
    String key;
    public Feedback(){
        //default constructor for Firebase
    }

    public Feedback(Question question){
        key = question.getKey();
    }


    public void decUnderstand(Student user){
        dontUnderstand+=1;
        voted.add(user);
    }

    //public getters for Firebase

    public int getDontUnderstand() {
        return dontUnderstand;
    }

    public int getRating(){
        return rating;
    }

    public void setRating(int rating){
        rating = rating;
    }

    public ArrayList<Student> getVoted() {
        return voted;
    }

    public String getKey(){return key;}
}