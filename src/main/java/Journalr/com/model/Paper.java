package Journalr.com.model;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

import java.util.*;

@Entity
public class Paper {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "paper_ID")
    private int paperId;
    
    @Column(name = "title")
    private String title;

    @Column(name = "file_name")
    private String fileName;

    @Temporal(TemporalType.DATE)
    @Column(name = "submission_date")
    private Date submissionDate;

    @Column(name = "topic")
    private String topic;

    @Temporal(TemporalType.DATE)
    @Column(name = "submission_deadline")
    private Date submissionDeadline;
    
    @Column(name = "file_type")
    private String fileType;     // eg. pdf, txt
    
    @Lob
    @Column(name = "data")
    private byte[] data;   // this is the actual data of the file to be stored in the database
    
    @ManyToMany(mappedBy = "papers")
    private Set<Reviewer> reviewers= new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "author_ID")
    private Author author;

    @OneToMany(mappedBy = "paper")
    private Set<Comment> comments = new HashSet<>();
    
    @Column(name = "approved")
    private Boolean approved = false;

    public Paper() {
		
	}
	
    public Paper(String title, String fileName, Date submissionDate, String aTopic, 
                Date submissionDeadline, String fileType, byte[] data) {
        this.title = title;
        this.fileName = fileName;
        this.submissionDate = submissionDate;
        this.topic = aTopic;
        this.submissionDeadline = submissionDeadline;
        this.fileType = fileType;
        this.data = data;
		
    }


    // The id of the paper is a int, not a string
    public int getPaperId() {
        return paperId;
    }
    
    public void setPaperId(int paperId) {
        this.paperId = paperId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Date getSubmissionDate() {
        return submissionDate;
    }

    public void setSubmissionDate(Date date) {
        this.submissionDate = date;
    }

    public void setTopic(String aTopic) {
        this.topic = aTopic;
    }

    public String getTopic() {
        return topic;
    }

    public void setSubmissionDeadline(Date aDate) {
        this.submissionDeadline = aDate;
    }

    public Date getSubmissionDeadline() {
        return submissionDeadline;
    }

    public Set<Reviewer> getReviewers() {
        return reviewers;                  // Not an encapsulating method for now
    }

    public void setReviewers(Set<Reviewer> reviewer) {
        this.reviewers = reviewer;
    }

    public void addReviewer(Reviewer reviewer) {
        this.reviewers.add(reviewer);
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
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

    public Boolean getApproved() {
        return approved;
    }

    public void setApproved(Boolean approved) {
        this.approved = approved;
    }
    
	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	
	 public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }	
}