package Journalr.com.model;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
//@Inheritance
public class Editor extends User{

    public Editor() {}

	public Editor(String userName, String firstName, String lastName, String email, String password) {
		super(userName, firstName, lastName, email, password);
	}

    public void addJournals() {

    }

    public void createSubmissionDeadlines() {

    }
    
    public void checkNominatedReviewers() {

    }

    public void assignReviewers() {

    }
}
