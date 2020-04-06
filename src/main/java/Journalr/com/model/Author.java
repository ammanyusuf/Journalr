package Journalr.com.model;

/*
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
*/
import javax.persistence.*;
import java.util.*;

//@Inheritance
@Entity
@PrimaryKeyJoinColumn(name = "author_ID")
public class Author extends User{

	@OneToMany(mappedBy = "author")
	private Set<Paper> papers = new HashSet<>();

	public Author() {

	}

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
	public Set<Paper> getPapers() {
        return papers;                  // Not an encapsulating method for now
    }

    public void setPapers(Set<Paper> papers) {
        this.papers = papers;
    }

    public void addPapers(Paper paper) {
        this.papers.add(paper);
    }

}

