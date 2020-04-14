package Journalr.com.controller;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import Journalr.com.model.Comment;
import Journalr.com.model.Paper;
import Journalr.com.model.Reviewer;
import Journalr.com.model.User;
import Journalr.com.model.UserDetailsClass;
import Journalr.com.repositories.CommentRepository;
import Journalr.com.repositories.PaperRepository;
import Journalr.com.repositories.ReviewerRepository;
import Journalr.com.repositories.UserRepository;

@Controller
public class CommentController {

    @Autowired
    CommentRepository commentRepository;
    
    @Autowired
    PaperRepository paperRepository;
    
    @Autowired
	ReviewerRepository reviewerRepository;
	
	@Autowired
	UserRepository userRepository;
	
	/**
	 * This method is a get mapping and will show the comment page with the corresponding paperId
	 * @param model The model that we are working with
	 * @param paperId The paperId that we want to add a comment to
	 * @return Displayes the addComment page
	 */
	@RequestMapping(path="**/addComment/{paperId}")
    public String showCommentPage (Model model, @PathVariable(name = "paperId") int paperId) {
		
    	model.addAttribute("paperId", paperId);
    	
		return "addComment";
	}

	// method that adds the comment to the database
	/**
	 * This method is a post mapping that will add the comment that was passed through
	 * into the database
	 * @param paperId The paperId of the paper that we want to add the comment to
	 * @param comment The comment block that we want the comment to be about
	 * @param topic The topic of the comment, which can be a major, minor, or general
	 * @return redirects back to the reviewer home page
	 * @throws ParseException
	 */
    @RequestMapping(path="**/addComment/{paperId}", method = RequestMethod.POST)
	public String saveDeadline (@PathVariable(name = "paperId") int paperId, 
								@ModelAttribute(name="comment") String comment,
								@ModelAttribute(name="topic") String topic) throws ParseException {
		 
		Comment commentObj = new Comment();

		//Get the current date
		Date date = new Date(System.currentTimeMillis());     

		//Get the current user
		UserDetailsClass userDetails = (UserDetailsClass) SecurityContextHolder.getContext().getAuthentication().getPrincipal(); 
		
		//Put current user into reviewer object
		int id = userDetails.getId();
		Reviewer reviewer;
		try {
			reviewer = reviewerRepository.findById(id).get();         // to be passed to the comment object
		} catch (NoSuchElementException e) {
			return "redirect:/error";
		}
		//Find the paper in the database using given paperId
		Paper paper;
		try {
			paper = paperRepository.findById(paperId).get();
		} catch (NoSuchElementException e) {
			return "redirect:/error";
		}
		//Save comment
	    commentObj.setComment(comment);
	    commentObj.setReviewer(reviewer);
	    commentObj.setPaper(paper);
		commentObj.setCommentDate(date);
		commentObj.setTopic(topic);
		commentRepository.save(commentObj);
		
		// Set the paper to a major revision or minor revision accoringly
		if (topic.contains("major")) {
			paperRepository.updateMajorRev(1, paperId, reviewer.getUserId());
		} else if (topic.contains("minor")) {
			paperRepository.updateMinorRev(1, paperId, reviewer.getUserId());
		}
	            
	    return "redirect:/reviewer";
	}

	/**
	 * This method will display all of the comments of a given paper, seperated
	 * by comment topic
	 * @param paperId The paperId of the paper that we want to display the comments of
	 * @param model The model that we are working with
	 * @return This method displays the viewComments page
	 */
	@RequestMapping(path="**/viewComments/{paperId}", method=RequestMethod.GET) 
	public String viewComments (@PathVariable(name="paperId") int paperId, Model model){

		// Add the paper title to the page
		Paper paper;
		try {
			paper = paperRepository.findById(paperId).get();
		} catch (Exception e) {
			model.addAttribute("message", "No paper with id: " + paperId + " was found in the system");
			return "error";
		}
		model.addAttribute("paperTitle", paper.getTitle());

		List<Comment> listMajorRevComments = commentRepository.findMajorRevCommentsPerPaper(paperId);
		model.addAttribute("listOfCommentsOfReviewerMajorRev", listMajorRevComments);

		List<Comment> listMinorRevComments = commentRepository.findMinorRevCommentsPerPaper(paperId);
		model.addAttribute("listOfCommentsOfReviewerMinorRev", listMinorRevComments);

		List<Comment> listGeneralComments = commentRepository.findGeneralCommentsPerPaper(paperId);
		model.addAttribute("listOfCommentsOfReviewerGeneral", listGeneralComments);

		return "viewComments";
	}

	@RequestMapping(path="**/viewMyCommentsReviewer/{paperId}", method = RequestMethod.GET)
	public String viewCommentsMadeByReviewer(@PathVariable(name="paperId") int paperId, Model model) {
		//Get the current user
		UserDetailsClass userDetails = (UserDetailsClass) SecurityContextHolder.getContext().getAuthentication().getPrincipal(); 
		
		//Put current user into reviewer object
		int id = userDetails.getId();
		Reviewer reviewer;
		try {
			reviewer = reviewerRepository.findById(id).get();         // to be passed to the comment object
		} catch (NoSuchElementException e) {
			model.addAttribute("message", "No reviwer found with id: " + id + " in the system.");
			return "error";
		}
		//Find the paper in the database using given paperId
		Paper paper;
		try {
			paper = paperRepository.findById(paperId).get();
		} catch (NoSuchElementException e) {
			model.addAttribute("message", "No paper found with id: " + paperId + " in the system.");
			return "error";
		}
		model.addAttribute("paperTitle", paper.getTitle());

		// Find the majore revision comments made by the reviewer for the given paper
		List<Comment> listMajorRevCommentsByReviewer = commentRepository.findMajorRevCommentsPerPaperPerReviewer(paperId, id);
		model.addAttribute("listMajorRevCommentsByReviewer", listMajorRevCommentsByReviewer);

		//Find the minor revision comments made by the reviewer for the given paper
		List<Comment> listMinorRevCommentsReviewer = commentRepository.findMinorRevCommentsPerPaperPerReviewer(paperId, id);
		model.addAttribute("listMinorRevCommentsReviewer", listMinorRevCommentsReviewer);

		// Find the general comments made by the reviewer for the given paper
		List<Comment> listGeneralCommentsReviewer = commentRepository.findGeneralCommentsPerPaperPerReviewer(paperId, id);
		model.addAttribute("listGeneralCommentsReviewer", listGeneralCommentsReviewer);

		return "viewMyCommentsReviewer";
	}
}
