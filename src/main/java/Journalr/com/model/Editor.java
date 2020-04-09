package Journalr.com.model;

/*import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;*/

import javax.persistence.*;

//@Inheritance
@Entity
@PrimaryKeyJoinColumn(name = "editor_ID")
public class Editor extends User{

    public Editor() {}

	public Editor(String userName, String firstName, String lastName, String email, String password) {
		super(userName, firstName, lastName, email, password);
	}

    public Editor(User user) {
        super(user);
    }

    /*
    public void addJournals() {

    }

    public void createSubmissionDeadlines() {

    }
    
    public void checkNominatedReviewers() {

    }

    public void assignReviewers() {

    }*/
}
