package Journalr.com.controller;

import java.io.IOException;
import java.util.stream.Collectors;
import java.util.Optional;
import java.util.Date; 
import java.util.Calendar;  		// Need this since Date is pretty much depricated

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import Journalr.com.model.Paper;
import Journalr.com.model.User;
import Journalr.com.model.Author;
import Journalr.com.model.UserDetailsClass;
import Journalr.com.repositories.PaperRepository;
import Journalr.com.repositories.UserRepository;
import Journalr.com.repositories.AuthorRepository;

import Journalr.com.storage.StorageFileNotFoundException;
import Journalr.com.storage.StorageService;

@Controller
public class FileUploadController {

	@Autowired
	private AuthorRepository authorRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PaperRepository paperRepository;

	private final StorageService storageService;

	@Autowired
	public FileUploadController(StorageService storageService) {
		this.storageService = storageService;
	}

	@GetMapping("/uploadForm")
	public String listUploadedFiles(Model model) throws IOException {
		model.addAttribute("files", storageService.loadAll().map(
				path -> MvcUriComponentsBuilder.fromMethodName(FileUploadController.class,
						"serveFile", path.getFileName().toString()).build().toUri().toString())
				.collect(Collectors.toList()));

		return "uploadForm";
	}

	@GetMapping("/files/{filename:.+}")
	@ResponseBody
	public ResponseEntity<Resource> serveFile(@PathVariable String filename) {

		Resource file = storageService.loadAsResource(filename);
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
				"attachment; filename=\"" + file.getFilename() + "\"").body(file);
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
		paperRepository.save(paper);

		storageService.store(file);
		//redirectAttributes.addFlashAttribute("message", "You successfully uploaded " + file.getOriginalFilename() + "!");

		redirectAttributes.addFlashAttribute("paper", paper);


		return "redirect:/editpaper";
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
		Paper aPaper = paperRepository.findById(paper.getPaperID()).get();

		// Update the file name
		aPaper.setFileName(file.getOriginalFilename());
		
		// Set up the new submission date
		aPaper.setSubmissionDate(new Date());

		storageService.store(file);

		// Update the paper
		paperRepository.save(aPaper);

		redirectAttributes.addFlashAttribute("paper", aPaper);


		return "redirect:/editpaper";
	}

	@RequestMapping(path="/savePaperTitleAndTopic", method = RequestMethod.POST)
	public String savePaper (@ModelAttribute Paper paper) {

		Paper aPaper = paperRepository.findById(paper.getPaperID()).get();
		aPaper.setTitle(paper.getTitle());
		aPaper.setTopic(paper.getTopic());
		paperRepository.save(aPaper);
            return "redirect:/author";
    }

	@ExceptionHandler(StorageFileNotFoundException.class)
	public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
		return ResponseEntity.notFound().build();
	}

}