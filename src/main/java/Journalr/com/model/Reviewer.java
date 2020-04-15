package Journalr.com.model;

import java.util.*;
import javax.persistence.*;

@Entity
@PrimaryKeyJoinColumn(name = "reviewer_ID")
public class Reviewer extends User{

    @Column(name = "affiliation")
    private String affiliation;

    @Column(name = "favourite_topic")
    private String favouriteTopic;

    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
        name = "review_paper", 
        joinColumns = { @JoinColumn(name = "reviewer_ID") }, 
        inverseJoinColumns = { @JoinColumn(name = "paper_id") }
    )
    Set<Paper> papers = new HashSet<>();

    @OneToMany(mappedBy = "reviewer")
	private Set<Comment> comments = new HashSet<>();

    /**
     * The basic constructor for the reviewer
     */
    public Reviewer() {

    }

    /**
	 * A basic constructor of the reviewer class.  It calls the super constructor for the user class
	 * @param userName The username of the reviewer
	 * @param firstName The firstname of the reviewer
	 * @param lastName The last name of the reviewer
	 * @param email The email of the reviewer
	 * @param password The password of the reviewer
	 */
	public Reviewer(String userName, String firstName, String lastName, String email, String password) {
		super(userName, firstName, lastName, email, password);
    }

    /**
	 * A constructor that creates a reviewer given a user input
	 * Calls the super constructor.
	 * @param user The user that we wish to assign as an reviewer to
	 */
    public Reviewer(User user) {
        super(user);
    }
    
    //Setters and Getters

    /**
     * This method sets the reviewer id of the reviewer
     * @param Id teh reviewer id
     */
    public void setReviewerId(int Id) {
    	super.setUserId(Id);
    }
    
    /**
     * This method gets the affiliation of the reviewer
     * @return The affiliation
     */
    public String getAffiliation() {
        return affiliation;
    }

    /**
     * This method sets the affiliation of the reviewer
     * @param affiliation The affiliation of the reviewer
     */
    public void setAffiliation(String affiliation) {
        this.affiliation = affiliation;
    }

    
    /**
	 * This method retrieves the instance of papers of that reviewer.
	 * Not encapsulating since it allows access to the instances of papers
	 * directly in the database
	 * @return a set of papers
	 */
    public Set<Paper> getPapers() {
        return papers;                  
    }

    /**
	 * This method sets the instance of papers of that reviewer.
	 * Not encapsulating since it allows access to the instances of papers
	 * directly in the database
	 * @param papers the set of papers we wish to add to the reviewer
	 */
    public void setPapers(Set<Paper> papers) {
        this.papers = papers;
    }

    /**
	 * This method directly adds a paper to the reviewer's set
	 * @param paper The paper we wish to add
	 */
    public void addPapers(Paper paper) {
        this.papers.add(paper);
    }

    /**
	 * This method retrieves the instance of comments of that reviewer.
	 * Not encapsulating since it allows access to the instances of comments
	 * directly in the database
	 * @return a set of comments
	 */
    public Set<Comment> getComments() {
        return comments;                  // Not an encapsulating method for now
    }

    /**
	 * This method sets the instance of comments of that reviewer.
	 * Not encapsulating, since it allows access to the instances of comments
	 * directly in the database
	 * @param comments the set of comments we wish to add to the reviewer
	 */
    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    /**
	 * This method directly adds a comment to the reviewer's set
	 * @param comment The coment we wish to add
	 */
    public void addComment(Comment comment) {
        this.comments.add(comment);
    }

    /**
     * This method gets the favourtie topic of the reviewer
     * @return The favourite topic of the reviewer
     */
    public String getFavouriteTopic() {
        return favouriteTopic;
    }

    /**
     * This method sets the favourtie topic of the reviewer
     * @param topic the topic
     */
    public void setFavouriteTopic(String topic) {
        this.favouriteTopic = topic;
    }

}