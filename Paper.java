package Journalr.com.model;

import java.util.ArrayList;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Paper {

    @Id
 	@GeneratedValue(strategy=GenerationType.AUTO)
    private int paperID;
    
    private String title;

    private String submissionDate;

    private String fileName;

    private ArrayList<String> topic;

    public Paper() {
		
	}
	
	public Paper(String title, String fileName, String submissionDate, ArrayList<String> aTopic) {
        this.title = title;
        this.fileName = fileName;
        this.submissionDate = submissionDate;
        this.topic = aTopic;
		
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

    public String getSubmissionDate() {
        return submissionDate;
    }

    public void setSubmissionDate(String date) {
        this.submissionDate = date;
    }

    public void setTopic(ArrayList<String> aTopic) {
        this.topic = aTopic;
    }

    // Not implemented properly
    public String getTopic() {
        return "hi";
    }
}