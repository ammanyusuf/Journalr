package Journalr.com.controller;

import java.sql.Date;
import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import Journalr.com.model.Comment;
import Journalr.com.model.Reviewer;
import Journalr.com.model.User;
import Journalr.com.model.UserDetailsClass;
import Journalr.com.repositories.CommentRepository;

@Controller
public class CommentController {

    @Autowired
    CommentRepository commentRepository;

    // post method from the submit comment page
		// adds the comment into the comment table in database with the attribut
	
	@RequestMapping(value="/comment", method=RequestMethod.GET)
	public String getComment() {
		return "comment";              // get method to acces the comment page
	}

	// method that adds the comment to the database
	@RequestMapping(value="/comment", method = RequestMethod.POST)//, path="/comment")
	public String addComment (@ModelAttribute(name="comment") String comment, Model model) {
		 
		Comment commentObj = new Comment();
			
		//SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
		Date date = new Date(System.currentTimeMillis());     // this is to get the current date
		//formatter.format(date)

		UserDetailsClass userDetails = (UserDetailsClass) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
			
		Reviewer reviewer = new Reviewer();            // to be passed to the comment object
		reviewer.setReviewerId(userDetails.getId());   //sets the Id of the current user into the reviewer object
			
		//find the reviewer in the database using the Id (reviewerId) then get that reviewer (since our reviewer only has the Id field initialized)
			
	    commentObj.setComment(comment);             // this is entered by the user (reviewer)
	    commentObj.setReviewer(reviewer);          // this should get the reviewerId
	    //commentObj.setPaperId(paperId);                // this should get the paperId
	    commentObj.setCommentDate(date);        // this should get the current date
	    commentRepository.save(commentObj);
	            
	    return "paperTopics";
	}

}
