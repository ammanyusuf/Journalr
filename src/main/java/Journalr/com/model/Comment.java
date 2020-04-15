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

    @Column(name = "topic")
    private String topic;

    /**
     * The default constructor of the comment class
     */
    public Comment() {

    }

    /**
     * The constructor of the comment class
     * @param comment The comment part of the comment
     * @param commentDate The commentdate of the comment
     * @param reviewer The reviewer that wrote the comment
     * @param paper The paper the comment was  about
     */
    public Comment(String comment, Date commentDate, Reviewer reviewer, Paper paper) {
        this.comment = comment;
        this.commentDate = commentDate;
        this.reviewer = reviewer;
        this.paper = paper;
    }

    // Setters and getters

    /**
     * This method gets the comment id of the comment
     * @return The comment id
     */
    public int getCommentId() {
        return commentId;
    }

    /**
     * This method sets the comment id of the comment
     * @param commentId The comment id
     */
    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    /**
     * This method gets the comment of the comment
     * @return The comment of the comment
     */
    public String getComment() {
        return comment;
    }

    /**
     * This method sets the comment of the comment
     * @param comment the comment that we want to set the comment to be about
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * This method gets the comment date
     * @return The comment date
     */
    public Date getCommentDate() {
        return commentDate;
    }

    /**
     * This method sets the comment date
     * @param commentDate The comment date
     */
    public void setCommentDate(Date commentDate) {
        this.commentDate = commentDate;
    } 

    /**
     * This method gets the reviewer of the comment
     * @return The reviewer
     */
    public Reviewer getReviewer() {
        return reviewer;
    }

    /**
     * This method sets the reviewer for that comment
     * @param reivewer the reviewer
     */
    public void setReviewer(Reviewer reviewer) {
        this.reviewer = reviewer;
    }

    /**
     * This method gets the paper of the comment
     * @return The paper
     */
    public Paper getPaper() {
        return paper;
    }

    /**
     * This method sets the paper of the comment
     * @param paper The paper
     */
    public void setPaper(Paper paper) {
        this.paper = paper;
    }

    /**
     * This method gets the topic of the comment
     * @return the topic of the comment
     */
    public String getTopic() {
        return this.topic;
    }

    /**
     * This method sets the topic of the comment
     * @param topic the topic
     */
    public void setTopic(String topic) {
        this.topic = topic;
    }

}