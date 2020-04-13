package Journalr.com.model;

import java.util.Date;

public class CommentOfReviewer {

    // This is the comment id of the comment made by the reviewer
    private int commentId;

    // This is the actual contents of the comment
    private String comment;

    // This is the date that the comment was made
    private Date commentDate;

    // This is the full name of the reviewer that made that comment
    private String reviewerName;

    // Constructor
    // It is assumed that the passed in user object is a reviewer
    public CommentOfReviewer(Comment comment, User user) {
        this.commentId = comment.getCommentId();
        this.comment = comment.getComment();
        this.commentDate = comment.getCommentDate();
        this.reviewerName = user.getFirstName() + " " + user.getLastName();
    }

    // Setter and Getters
    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
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

    public String getReviewerName() {
        return reviewerName;
    }

    public void setReviewerName(String reviewerName) {
        this.reviewerName = reviewerName;
    }

    public void setReviewerName(User user) {
        this.reviewerName = user.getFirstName() + " " + user.getLastName();
    }

}