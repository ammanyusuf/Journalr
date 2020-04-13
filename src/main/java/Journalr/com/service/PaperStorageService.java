package Journalr.com.service;

import Journalr.com.exception.FileStorageException;
import Journalr.com.exception.MyFileNotFoundException;

import Journalr.com.model.Paper;
import Journalr.com.repositories.PaperRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

@Service
public class PaperStorageService {

    @Autowired
    private PaperRepository paperRepository;

    public Paper storeFile(MultipartFile file) { // returns saved file
        // Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            // Check if the file's name contains invalid characters
            if(fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            Paper paper = new Paper(null, fileName, null, null, null, file.getContentType(), file.getBytes());  // put details of uploaded file into a file object to be put in database
            // some are null because only the fileName, fileType, data are uploaded to the database for now, the title, topic, other paper attributes are not yet implemented
            
            return paperRepository.save(paper);    // saves file in the database
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    public Paper getFile(String paperId) {
        return paperRepository.findById(Integer.parseInt(paperId))
                .orElseThrow(() -> new MyFileNotFoundException("Paper not found with id " + paperId));
    }
}
