package Journalr.com.controller;

import Journalr.com.model.Paper;
import Journalr.com.model.User;
import Journalr.com.model.Author;
import Journalr.com.model.UserDetailsClass;

import Journalr.com.service.PaperStorageService;

import Journalr.com.payload.UploadFileResponse;

import Journalr.com.repositories.PaperRepository;
import Journalr.com.repositories.UserRepository;
import Journalr.com.repositories.AuthorRepository;

import java.util.List;
import java.io.IOException;
import java.util.stream.Collectors;
import java.util.Optional;
import java.util.Date; 
import java.util.Calendar;  		// Need this since Date is pretty much depricated

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

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

}
