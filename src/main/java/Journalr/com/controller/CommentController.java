package Journalr.com.controller;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

@Controller
public class CommentController {

    @Autowired
    CommentRepository commentRepository;
    
    @Autowired
    PaperRepository paperRepository;
    
    @Autowired
    ReviewerRepository reviewerRepository;
	
	@RequestMapping(path="**/addComment/{paperId}")
    public String showCommentPage (Model model, @PathVariable(name = "paperId") int paperId) {
		
    	model.addAttribute("paperId", paperId);
    	
		return "addComment";
	}

	// method that adds the comment to the database
    @RequestMapping(path="**/addComment/{paperId}", method = RequestMethod.POST)
    public String saveDeadline (@PathVariable(name = "paperId") int paperId, @ModelAttribute(name="comment") String comment) throws ParseException {
		 
		Comment commentObj = new Comment();

		//Get the current date
		Date date = new Date(System.currentTimeMillis());     

		//Get the current user
		UserDetailsClass userDetails = (UserDetailsClass) SecurityContextHolder.getContext().getAuthentication().getPrincipal(); 
		
		//Put current user into reviewer object
		int id = userDetails.getId();
		Reviewer reviewer = reviewerRepository.findById(id).get();         // to be passed to the comment object
		
		//Find the paper in the database using given paperId
		Paper paper = paperRepository.findById(paperId).get();
		
		//Save comment
	    commentObj.setComment(comment);
	    commentObj.setReviewer(reviewer);
	    commentObj.setPaper(paper);
	    commentObj.setCommentDate(date);
	    commentRepository.save(commentObj);
	            
	    return "redirect:/paperTopics";
	}

}
