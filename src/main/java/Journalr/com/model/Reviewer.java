package Journalr.com.model;
/*
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import Journalr.com.model.*;*/

import java.util.*;
import javax.persistence.*;

//@Inheritance
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

    public Reviewer() {}

	public Reviewer(String userName, String firstName, String lastName, String email, String password) {
		super(userName, firstName, lastName, email, password);
    }
    
    public void setReviewerId(int Id) {
    	super.setUserId(Id);
    }
    
    public String getAffiliation() {
        return affiliation;
    }

    public void setAffiliation(String affiliation) {
        this.affiliation = affiliation;
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

    public Set<Comment> getComments() {
        return comments;                  // Not an encapsulating method for now
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    public void addComment(Comment comment) {
        this.comments.add(comment);
    }

    public String getFavouriteTopic() {
        return favouriteTopic;
    }

    public void setFavouriteTopic(String topic) {
        this.favouriteTopic = topic;
    }

}