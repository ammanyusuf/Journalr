package Journalr.com.controller;

import Journalr.com.model.Paper;
import Journalr.com.model.User;
import Journalr.com.service.PaperStorageService;

import Journalr.com.payload.UploadFileResponse;
import Journalr.com.repositories.PaperRepository;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class PaperController {

    private static final Logger logger = LoggerFactory.getLogger(PaperController.class);

    @Autowired
    private PaperStorageService PaperStorageService;

    
    @PostMapping("/uploadFile")
    public UploadFileResponse uploadFile(@RequestParam("file") MultipartFile file) {
        Paper paper = PaperStorageService.storeFile(file); // call service to save the file in the database (can be get)

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")       // download url, when clicked, maps to downloadFile method where download starts 
        		.path(paper.getPaperId())
                .toUriString();               // this redirects to 

        return new UploadFileResponse(paper.getFileName(), fileDownloadUri,
                file.getContentType(), file.getSize());
    }

    @GetMapping("/downloadFile/{fileId}")    //url to download
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileId) {  // response from clicking download link
        // Load file from database
        Paper paper = PaperStorageService.getFile(fileId);

        return ResponseEntity.ok()             // initializes download
                .contentType(MediaType.parseMediaType(paper.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + paper.getFileName() + "\"")  // name of the downloaded file
                .body(new ByteArrayResource(paper.getData()));  // the file in the database
    }

    /**
     * This method takes in the current displaying model as input.  It responds to the mapping 
     * /admin (the admin page).  Upon clicking this, it would re-display a admin view with a list
     * of all the users in the system.
     * @param model The model is the current displaying template.
     * @return
     */

}
