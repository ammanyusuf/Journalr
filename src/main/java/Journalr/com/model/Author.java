package Journalr.com.model;

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

	public Author(User user) {
		super(user);
	}	
	
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

