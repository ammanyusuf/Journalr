package Journalr.com.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import java.util.ArrayList;


//@Inheritance
@Entity
@DiscriminatorValue("ROLE_AUTHOR")
public class Author extends User{

	/*
	private ArrayList<Paper> papers = new ArrayList<Paper>();

	private Boolean enabled;
	*/
	public Author() {}

	public Author(String userName, String firstName, String lastName, String email, String password) {
		super(userName, firstName, lastName, email, password);
	}
	/*
	public void setPaper(ArrayList<Paper> aPaper) {
	}

	public void uploadFile () {
	
	}

	public Boolean submitFile(ArrayList<Paper> aPaper) {
		return true; 
	}
	*/
}

