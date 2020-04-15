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

    /**
     * The basic constructor of the paper class
     */
    public Paper() {
		
	}
    
    /**
     * A basic contructor of the paper class
     * @param title The title of the paper
     * @param fileName The fileName of the paper
     * @param submissionDate The submissioNdate of the paper
     * @param aTopic the topic of the paper
     * @param submissionDeadline The submission deadline of the paper
     * @param fileType The file type of the paper
     * @param data the data of the paper
     */
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


    /**
     * Thie method gets the paper id of the paper
     * @return the paper id
     */
    public int getPaperId() {
        return paperId;
    }
    
    /**
     * This method sets the paper id of the paper
     * @param paperId the paper id
     */
    public void setPaperId(int paperId) {
        this.paperId = paperId;
    }

    /**
     * Thie method gets the title of the paper
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * This method sets the title of the paper
     * @param title the paper title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Thie method gets the file name of the paper
     * @return the filename 
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * This method sets the filename of the paper
     * @param fileName the paper filename
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * Thie method gets the submisison date of the paper
     * @return the submisison date 
     */
    public Date getSubmissionDate() {
        return submissionDate;
    }

    /**
     * This method sets the submission date of the paper
     * @param date the paper submission date
     */
    public void setSubmissionDate(Date date) {
        this.submissionDate = date;
    }

    /**
     * Thie method gets the topic of the paper
     * @return the paper topic
     */
    public String getTopic() {
        return topic;
    }

    /**
     * This method sets the topic of the paper
     * @param aTopic the paper topic
     */
    public void setTopic(String aTopic) {
        this.topic = aTopic;
    }

    /**
     * This method sets the submisison deadline of the paper
     * @param aDate the submission dead line
     */
    public void setSubmissionDeadline(Date aDate) {
        this.submissionDeadline = aDate;
    }

    /**
     * Thie method gets the submission deadline of the paper
     * @return the submission deadline
     */
    public Date getSubmissionDeadline() {
        return submissionDeadline;
    }

    /**
	 * This method retrieves the instance of reviewer of that paper.
	 * Not encapsulating since it allows access to the instances of reviewers
	 * directly in the database
	 * @return a set of reviewers
	 */
    public Set<Reviewer> getReviewers() {
        return reviewers;                  // Not an encapsulating method for now
    }

    /**
	 * This method sets the instance of reviewers of that paper.
	 * Not encapsulating since it allows access to the instances of reviewers
	 * directly in the database
	 * @param reviewer the set of reviewers we wish to add to the paper
	 */
    public void setReviewers(Set<Reviewer> reviewer) {
        this.reviewers = reviewer;
    }

    /**
	 * This method directly adds a reviewer to the paper's set
	 * @param reviewer The reviewer we wish to add
	 */
    public void addReviewer(Reviewer reviewer) {
        this.reviewers.add(reviewer);
    }

    /**
     * This method gets the author of the paper
     * @return The author
     */
    public Author getAuthor() {
        return author;
    }

    /**
     * This method sets the author of the paper
     * @param author the author of the paper
     */
    public void setAuthor(Author author) {
        this.author = author;
    }

    /**
	 * This method retrieves the instance of comments of that paper.
	 * Not encapsulating since it allows access to the instances of comments
	 * directly in the database
	 * @return a set of comments
	 */
    public Set<Comment> getComments() {
        return comments;                  
    }

    /**
	 * This method sets the instance of comments of that paper.
	 * Not encapsulating since it allows access to the instances of comments
	 * directly in the database
	 * @param comments the set of comments we wish to add to the paper
	 */
    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    /**
	 * This method directly adds a comment to the paper's set
	 * @param comment The coment we wish to add
	 */
    public void addComment(Comment comment) {
        this.comments.add(comment);
    }

    /**
     * This method gets the approved status of the paper
     * @return the approved status
     */
    public Boolean getApproved() {
        return approved;
    }

    /**
     * This method sets the approved status of the paper
     * @param approved The approved status
     */
    public void setApproved(Boolean approved) {
        this.approved = approved;
    }
    
    /**
     * This method gets the file type of the paper
     * @return The file type
     */
	public String getFileType() {
		return fileType;
	}

    /**
     * This method sets the file type of the paper
     * @param fileType The file type
     */
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
    
    /**
     * This method retreives the data of the paper
     * @return the data of the paper
     */
	 public byte[] getData() {
        return data;
    }

    /**
     * This method sets the data of the paper
     * @param data The data of the paper
     */
    public void setData(byte[] data) {
        this.data = data;
    }	
}