package Journalr.com.model;

public class Paper {
    
    private String title;

    private String submissionDate;

    private String fileName;

    public Paper() {
		
	}
	
	public Paper(String title, String fileName,String submissionDate) {
        this.title = title;
        this.fileName = fileName;
		this.submissionDate = submissionDate;
		
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

}