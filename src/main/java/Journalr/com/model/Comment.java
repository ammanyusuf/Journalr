package Journalr.com.model;

import javax.persistence.*;

import java.util.*;

@Entity
public class Comment {

    @Id
     @GeneratedValue(strategy=GenerationType.AUTO)
     @Column(name = "comment_ID")
	private int commentId;

    @Column(name = "comment")
    private String comment;

    @Temporal(TemporalType.DATE)
    @Column(name = "comment_date")
    private Date commentDate;

    @ManyToOne
    @JoinColumn(name = "reviewer_ID")
    private Reviewer reviewer;

    @ManyToOne
    @JoinColumn(name = "paper_ID")
    private Paper paper;

    public Comment() {

    }

    public Comment(String comment, Date commentDate, Reviewer reviewer, Paper paper) {
        this.comment = comment;
        this.commentDate = commentDate;
        this.reviewer = reviewer;
        this.paper = paper;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getCommentDate() {
        return commentDate;
    }

    public void setCommentDate(Date commentDate) {
        this.commentDate = commentDate;
    } 

    public Reviewer getReviewer() {
        return reviewer;
    }

    public void setReviewer(Reviewer reviewer) {
        this.reviewer = reviewer;
    }
    public Paper getPaper() {
        return paper;
    }

    public void setPaper(Paper paper) {
        this.paper = paper;
    }

}