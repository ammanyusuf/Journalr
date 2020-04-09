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

import Journalr.com.exception.FileStorageException;
import Journalr.com.exception.MyFileNotFoundException;

import java.util.List;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

import org.springframework.stereotype.Controller;

import org.springframework.util.StringUtils;

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

@Controller
public class PaperController {

    private static final Logger logger = LoggerFactory.getLogger(PaperController.class);

    @Autowired
    private PaperStorageService PaperStorageService;

    @Autowired
	private AuthorRepository authorRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PaperRepository paperRepository;

    
    @PostMapping("/uploadFile")
    public UploadFileResponse uploadFile(@RequestParam("file") MultipartFile file) {
        Paper paper = PaperStorageService.storeFile(file); // call service to save the file in the database (can be get)

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")       // download url, when clicked, maps to downloadFile method where download starts 
        		.path(String.valueOf(paper.getPaperId()))
                .toUriString();               // this redirects to 

        return new UploadFileResponse(paper.getFileName(), fileDownloadUri,
                file.getContentType(), file.getSize());
    }

    @GetMapping("/uploadForm")
	public String listUploadedFiles(Model model) throws IOException {
		return "uploadForm";
	}


    @PostMapping("/uploadForm")
	public String handleFileUpload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {

		// Get the credentials of the currently logged on user
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		String userName;

		// Get the isntance of that user
		if (principal instanceof UserDetailsClass) {
			userName = ((UserDetailsClass)principal).getUsername();
		} else {
			userName = principal.toString();
		}

		// Find the user in the user table by their username
		User user = userRepository.findByUserName(userName).get();
		int id = user.getUserId();

		// Find the author in the author table by id
		Author author = authorRepository.findById(id).get();

		// Create a new paper and set the author to the paper just submitted
        Paper paper = new Paper();
        
        //Normalize File name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            // Check if the file's name contains invalid characters
            if(fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }
            
            paper.setFileName(file.getOriginalFilename());
            paper.setAuthor(author);
            
            paper.setSubmissionDate(new Date());

            // Sets the deadline date
            Calendar deadline = Calendar.getInstance();
            deadline.clear();

            deadline.set(Calendar.YEAR, 2020);
            deadline.set(Calendar.MONTH, 7);
            deadline.set(Calendar.DATE, 1);

            Date deadlinedate = deadline.getTime();
            paper.setSubmissionDeadline(deadlinedate);

            // Set the the fily type of the file
            paper.setFileType(file.getContentType());

            // Set the data of the file
            paper.setData(file.getBytes());

            // save the paper to the database
            paperRepository.save(paper);

            redirectAttributes.addFlashAttribute("paper", paper);


            return "redirect:/editpaper";
        
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }

		
    }
    
    @RequestMapping("/reuploadForm/{paperID}")
	public ModelAndView showReuploadPage(@PathVariable("paperID") String paperID) {
		ModelAndView modelAndView = new ModelAndView("reuploadForm");
		int pid = Integer.parseInt(paperID);
        Optional<Paper> optional= paperRepository.findById(pid);
        if (optional.isPresent()) {
            return modelAndView.addObject("paper", optional.get());
		} else {
            return new ModelAndView("error");
        }   
	}

	@PostMapping("/reuploadForm")
	public String handleFileReUpload(@ModelAttribute("file") MultipartFile file,
									 @ModelAttribute("paper") Paper paper,
									 RedirectAttributes redirectAttributes) {

		// Get the paper that was passed through to get reuploaded
		//int pid = Integer.parseInt(paperID);
		Paper aPaper = paperRepository.findById(paper.getPaperId()).get();


        //Normalize File name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            // Check if the file's name contains invalid characters
            if(fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }
            
            aPaper.setFileName(file.getOriginalFilename());
            
            aPaper.setSubmissionDate(new Date());

            // Set the the file type of the file
            aPaper.setFileType(file.getContentType());

            // Set the data of the file
            aPaper.setData(file.getBytes());

            // save the paper to the database
            paperRepository.save(aPaper);

            redirectAttributes.addFlashAttribute("paper", aPaper);


            return "redirect:/editpaper";
        
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }

	}

	@RequestMapping(path="/savePaperTitleAndTopic", method = RequestMethod.POST)
	public String savePaper (@ModelAttribute Paper paper) {

		Paper aPaper = paperRepository.findById(paper.getPaperId()).get();
		aPaper.setTitle(paper.getTitle());
		aPaper.setTopic(paper.getTopic());
		paperRepository.save(aPaper);
        return "redirect:/author";
    }

    @GetMapping("/downloadFile/{fileId}")    //url to download
    public ResponseEntity<Resource> downloadFile(@PathVariable int fileId) {  // response from clicking download link
        // Load file from database
        //Paper paper = PaperStorageService.getFile(fileId);

        Paper paper = paperRepository.findById(fileId).get();

        return ResponseEntity.ok()             // initializes download
                .contentType(MediaType.parseMediaType(paper.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + paper.getFileName() + "\"")  // name of the downloaded file
                .body(new ByteArrayResource(paper.getData()));  // the file in the database
    }
    
    @RequestMapping(path="/editDeadline/{paperId}")
    public String showEditDeadlinePage (Model model, @PathVariable(name = "paperId") int paperId) {
    		
    	model.addAttribute("paperId", paperId);
		
    	return "editDeadline";
    }
    
    @RequestMapping(path="/editDeadline/{paperId}", method = RequestMethod.POST)
    public String saveDeadline (@PathVariable(name = "paperId") int paperId, @ModelAttribute(name="date") String date) throws ParseException {

    		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");          // html takes dates as strings
			Date deadline = format.parse(date);                                    // so convert the Date to a String
			
    		Paper paper = paperRepository.findById(paperId).get(); 
	    	paper.setSubmissionDeadline(deadline);
	    	
			paperRepository.save(paper);

	        return "redirect:/editor";
    }	      

}
