package Journalr.com.payload;

//this class is for the paper upload functionality
public class UploadFileResponse {
    private String fileName;
    private String fileDownloadUri;
    private String fileType;
    private long size;

    /**
     * A constructor of the upload file response class
     * @param fileName The file name of the file
     * @param fileDownloadUri the file download url of the file
     * @param fileType the file type of the file
     * @param size the size of the file
     */
    public UploadFileResponse(String fileName, String fileDownloadUri, String fileType, long size) {
        this.fileName = fileName;
        this.fileDownloadUri = fileDownloadUri; // url of file download
        this.fileType = fileType;
        this.size = size;
    }

    // Setters and getters

    /**
     * This method gets the file name of the file
     * @return the file name
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * This method sets the file name of the file
     * @param fileName the file name
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * This method gets the file download url of the file
     * @return the file download url
     */
    public String getFileDownloadUri() {
        return fileDownloadUri;
    }

    /**
     * This method sets the file download url of the file
     * @param fileDownloadUri the file downlaod url
     */
    public void setFileDownloadUri(String fileDownloadUri) {
        this.fileDownloadUri = fileDownloadUri;
    }

    /**
     * This method returns the file type of the file
     * @return the file type
     */
    public String getFileType() {
        return fileType;
    }

    /**
     * This method sets the file type of the file
     * @param fileType The file type
     */
    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    /**
     * This method gets the size of the file
     * @return The file size
     */
    public long getSize() {
        return size;
    }

    /**
     * This method sets the size of the file
     * @param size file size
     */
    public void setSize(long size) {
        this.size = size;
    }
}
