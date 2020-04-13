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
import Journalr.com.model.CommentOfReviewer;
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
	
	@RequestMapping(path="**/addComment/{paperId}")
    public String showCommentPage (Model model, @PathVariable(name = "paperId") int paperId) {
		
    	model.addAttribute("paperId", paperId);
    	
		return "addComment";
	}

	// method that adds the comment to the database
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

	@RequestMapping(path="**/viewComments/{paperId}", method=RequestMethod.GET) 
	public String viewComments (@PathVariable(name="paperId") int paperId, Model model){

		Paper paper;
		try {
			paper = paperRepository.findById(paperId).get();
		} catch (Exception e) {
			model.addAttribute("message", "No paper with id: " + paperId + " was found in the system");
			return "error";
		}
		model.addAttribute("paperTitle", paper.getTitle());

		// Find all users with the role type of 'reviewer'
		List<User> listOfReviewers = userRepository.findByRolesContaining("REVIEWER");

		// Initialize an array fo commentsOfReviewer objects
		List<CommentOfReviewer> listOfCommentsOfReviewerMajorRev = new ArrayList<>();
		// Find all of the comments under a major revision for the given paper
		List<Comment> listMajorRevComments = commentRepository.findMajorRevCommentsPerPaper(paperId);
		// This will iterate through every comment in the list of retrieved comments
		// and every reviewer in the list of users and will join the two.  This is done
		// so the full name of the reviewer shows up with the comment and the comment date
		for (Comment comment : listMajorRevComments) {
			for (User reviewer : listOfReviewers) {
				if (comment.getReviewer().getUserId() == reviewer.getUserId()) {
					CommentOfReviewer commentOfReviewer = new CommentOfReviewer(comment,reviewer);
					listOfCommentsOfReviewerMajorRev.add(commentOfReviewer);
				}
			}
		}

		model.addAttribute("listOfCommentsOfReviewerMajorRev", listOfCommentsOfReviewerMajorRev);

		// Initialize an array fo commentsOfReviewer objects
		List<CommentOfReviewer> listOfCommentsOfReviewerMinorRev = new ArrayList<>();
		// Find all of the comments under a minor revision for the given paper
		List<Comment> listMinorRevComments = commentRepository.findMinorRevCommentsPerPaper(paperId);
		// This will iterate through every comment in the list of retrieved comments
		// and every reviewer in the list of users and will join the two.  This is done
		// so the full name of the reviewer shows up with the comment and the comment date
		for (Comment comment : listMinorRevComments) {
			for (User reviewer : listOfReviewers) {
				if (comment.getReviewer().getUserId() == reviewer.getUserId()) {
					CommentOfReviewer commentOfReviewer = new CommentOfReviewer(comment,reviewer);
					listOfCommentsOfReviewerMinorRev.add(commentOfReviewer);
				}
			}
		}

		model.addAttribute("listOfCommentsOfReviewerMinorRev", listOfCommentsOfReviewerMinorRev);

		// Initialize an array fo commentsOfReviewer objects
		List<CommentOfReviewer> listOfCommentsOfReviewerGeneral = new ArrayList<>();
		// Find all of the comments under a general revision for the given paper
		List<Comment> listGeneralComments = commentRepository.findGeneralCommentsPerPaper(paperId);
		// This will iterate through every comment in the list of retrieved comments
		// and every reviewer in the list of users and will join the two.  This is done
		// so the full name of the reviewer shows up with the comment and the comment date
		for (Comment comment : listGeneralComments) {
			for (User reviewer : listOfReviewers) {
				if (comment.getReviewer().getUserId() == reviewer.getUserId()) {
					CommentOfReviewer commentOfReviewer = new CommentOfReviewer(comment,reviewer);
					listOfCommentsOfReviewerGeneral.add(commentOfReviewer);
				}
			}
		}

		model.addAttribute("listOfCommentsOfReviewerGeneral", listOfCommentsOfReviewerGeneral);

		/*
		List<Comment> listComments = commentRepository.findCommentsPerPaper(paperId);

		// This will iterate through every comment in the list of retrieved comments
		// and every reviewer in the list of users and will join the two.  This is done
		// so the full name of the reviewer shows up with the comment and the comment date
		for (Comment comment : listComments) {
			for (User reviewer : listOfReviewers) {
				if (comment.getReviewer().getUserId() == reviewer.getUserId()) {
					CommentOfReviewer commentOfReviewer = new CommentOfReviewer(comment,reviewer);
					listOfCommentsOfReviewer.add(commentOfReviewer);
				}
			}
		}

		model.addAttribute("listOfCommentsOfReviewer", listOfCommentsOfReviewer);
		*/
		return "viewComments";

	}

}
