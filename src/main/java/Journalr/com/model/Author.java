package Journalr.com.model;

import javax.persistence.*;
import java.util.*;

//@Inheritance
@Entity
@PrimaryKeyJoinColumn(name = "author_ID")
public class Author extends User{

	@OneToMany(mappedBy = "author")
	private Set<Paper> papers = new HashSet<>();

	/**
	 * The default constuctor for the author class
	 */
	public Author() {

	}

	/**
	 * A basic constructor of the author class.  It calls the super constructor for the user class
	 * @param userName The username of the author
	 * @param firstName The firstname of the author
	 * @param lastName The last name of the author
	 * @param email The email of the author
	 * @param password The password of the author
	 */
	public Author(String userName, String firstName, String lastName, String email, String password) {
		super(userName, firstName, lastName, email, password);
	}

	/**
	 * A constructor that creates a author given a user input
	 * Calls the super constructor.
	 * @param user The user that we wish to assign as an author to
	 */
	public Author(User user) {
		super(user);
	}	

	// Setters and Getters
	/**
	 * This method retrieves the instance of papers of that authors.
	 * Not encapsulating since it allows access to the instances of papers
	 * directly in the database
	 * @return a set of papers
	 */
	public Set<Paper> getPapers() {
        return papers;
    }

	/**
	 * This method sets the instance of papers of that authors.
	 * Not encapsulating since it allows access to the instances of papers
	 * directly in the database
	 * @param papers the set of papers we wish to add to the author
	 */
    public void setPapers(Set<Paper> papers) {
        this.papers = papers;
    }

	/**
	 * This method directly adds a paper to the author's set
	 * @param paper The paper we wish to add
	 */
    public void addPapers(Paper paper) {
        this.papers.add(paper);
    }

}

