package Journalr.com.model;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Inheritance
public class Reviewer extends User{

    @Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    private int reviewerID; 
    
    private Paper aPaper;

	public Reviewer(String userName, String firstName, String lastName, String email, String password) {
		super(userName, firstName, lastName, email, password);
    }

    public Reviewer (Paper aPaper) {
        this.aPaper = aPaper;
    }
    
	public void reviewPaper () {
        //Search by topic that interests reviewer -> topic 
        //Paper aPaper.getTopic();
    }

}