package Journalr.com.model;

import java.util.ArrayList;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Inheritance
public class Author extends User{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int authorID; 

	private ArrayList<Paper> papers = new ArrayList<Paper>();

	public Author(String userName, String firstName, String lastName, String email, String password) {
		super(userName, firstName, lastName, email, password);
	}

	public void setPaper(ArrayList<Paper> aPaper) {
	}

	public void uploadFile () {
	
	}

	public Boolean submitFile(ArrayList<Paper> aPaper) {
		return true; 
	}
}

