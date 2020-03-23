package Journalr.com.model;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import Journalr.com.model.*;

@Entity
//@Inheritance
public class Reviewer extends User{

    //private Paper aPaper;                 this part is giving some issues because it could not find a Paper type in sql, may have to join with preimary keys

    public Reviewer() {}

	public Reviewer(String userName, String firstName, String lastName, String email, String password) {
		super(userName, firstName, lastName, email, password);
    }

    /*public Reviewer (Paper aPaper) {
        //this.aPaper = aPaper;
    }*/
    
	public void reviewPaper () {
        //Search by topic that interests reviewer -> topic 
        //Paper aPaper.getTopic();
    }

}