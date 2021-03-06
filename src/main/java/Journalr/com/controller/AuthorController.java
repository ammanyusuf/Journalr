package Journalr.com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import org.springframework.stereotype.Controller;
import org.springframework.security.core.context.SecurityContextHolder;

import Journalr.com.repositories.AuthorRepository;
import Journalr.com.repositories.PaperRepository;
import Journalr.com.repositories.ReviewerRepository;
import Journalr.com.repositories.UserRepository;

import java.util.*;

import Journalr.com.model.Paper;
import Journalr.com.model.Reviewer;
import Journalr.com.model.User;
import Journalr.com.model.UserDetailsClass;
import Journalr.com.model.Author;

@Controller
public class AuthorController {

    @Autowired
    AuthorRepository authorRepository;

    @Autowired
    PaperRepository paperRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ReviewerRepository reviewerRepository;

    /**
     * This method will display all the papers that the author currently has uploaded
     * to the system.
     * @param model The model is the current displaying template.
     * @return 
     */
    @RequestMapping("/author")
    public String showAllPapersPerAuthor(Model model) {
        
        try {
            int id = getCurrentlyLoggedInUser();
            
            User user = userRepository.findById(id).get();
            
            String firstName = user.getFirstName();
            model.addAttribute("firstName", firstName);

            // Find the author in the author table by id
            Author author = authorRepository.findById(id).get();
            
            //Find all the papers written by the author
            List<Paper> listPapers = paperRepository.findPapersByAuthorId(author.getUserId());

            //List<Paper> listPapers = paperRepository.findAll();
            model.addAttribute("listPapers", listPapers);

            // Find all the papers that have been approved for that author
            List<Paper> approvedPapers = paperRepository.findApprovedPapersForAuthors(author.getUserId());

            model.addAttribute("approvedPapers", approvedPapers);

            // Find all the papers that have been rejected for that author
            List<Paper> rejectedPapers = paperRepository.findRejectedPapersForAuthors(author.getUserId());

            model.addAttribute("rejectedPapers", rejectedPapers);

            // Find all the author's papers that are under review
            List<Paper> reviewedPapers = paperRepository.findReviewedPapersPerAuthors(author.getUserId());

            model.addAttribute("reviewedPapers", reviewedPapers);

        } catch(Exception e) {
            
            model.addAttribute("message", "");


            return "redirect:/error";
        }
        return "author";
    }
    

    /**
     * this method will populate the authorAddReviewer page with all the potential reviewers
     * that the author wishes to add to the paper
     * @param paperId The paper id of the paper we want to add reviewers to
     * @param model The current model that is being passed through
     * @return returns to the authorAddReviewer page
     */
    @RequestMapping(path="/authorAddReviewer/{paperId}")
    public String populateAuthorAddReviewer(@PathVariable(name = "paperId") int paperId, Model model) {

        // Find the paper in the paper repository
        Paper paper;
        try {
            paper = paperRepository.findById(paperId).get();
        } catch(NoSuchElementException e) {
            // If no paper is found, add the message to the model,
            // and redirect to the error page.
            model.addAttribute("message", "No paper with id: " + paperId + " exists in the system.");
            return "error";
        }
        model.addAttribute("paper1", paper);

        // List all of the potential reviewers
        List<User> listPotentialReviewers = null;
        try{
            listPotentialReviewers = userRepository.findByRolesContaining("ROLE_REVIEWER");
        } catch(Exception e) {
            // If an exception is cause, there are no reviewers in the system
            model.addAttribute("message", "No reviewers in the system.");
        }
        model.addAttribute("listPotentialReviewers", listPotentialReviewers);
        
        return "authorAddReviewer";
    }

    
    // NEW with search reviewer
    /**
     * This method will add the reviewers that the author selected to the paper passed
     * through
     * 
     * Details: RequestParam parameters will be Strings since the html input returns 
     * 		   a string in the form "FirstName LastName (ID: userId)"
     * 
     * @param paperId the papers that we want to add the reviewer to
     * @param model the current working model
     * @param reviewerId1 the first reviewer that we want to add.  If it is -1 we should not
     *                    add this reviewer
     * @param reviewerId2 the second reviewer that we want to add.  If it is -1 we should not
     *                    add this reviewer
     * @param reviewerId3 the third reviewer that we want to add.  If it is -1 we should not
     *                    add this reviewer
     * @return this method returns back to the author page
     */
    
    @RequestMapping(path="/authorAddReviewer/{paperId}", method = RequestMethod.POST)
    public String authorAddReviewer (@PathVariable("paperId") int paperId, Model model,
                                    @RequestParam("revName1") String reviewerId1,
                                    @RequestParam("revName2") String reviewerId2,
                                    @RequestParam("revName3") String reviewerId3) {

        Paper paper = paperRepository.findById(paperId).get();
        
        if (!reviewerId1.isEmpty()) {
            
        	// Take the userId part of the returned value (firstName lastName (userId)) from html, then convert into int
        	int revId1 = Integer.parseInt(reviewerId1.substring(reviewerId1.indexOf(":") + 1, reviewerId1.indexOf(")")));
        	
            Reviewer reviewer = reviewerRepository.findById(revId1).get();
        
            // Check if paper/reviewer is already stored in database (check for duplicates)
            // Also helps if the author chooses the same reviewer twice, it will only add it once
            // If not(already in review_paper)
            if(!((paper.getReviewers()).contains(reviewer) && (reviewer.getPapers().contains(paper))))
            {	
	            // Assign the papers to the reviewers, and vice versa
	            reviewer.getPapers().add(paper);
	            paper.getReviewers().add(reviewer);
	
	            // Update database
	            paperRepository.save(paper);
	            reviewerRepository.save(reviewer);
	
	            //Update major,minor,accept,able to review attributes
	            paperRepository.updateMajorRev(0, paperId, revId1);
	            paperRepository.updateMinorRev(0, paperId, revId1);
	            paperRepository.updateAccept(0, paperId, revId1);
	            paperRepository.updateAbleToReview(0, paperId, revId1);
	            paperRepository.updateReject(0, paperId, revId1);
            }
        }
        
        if (!reviewerId2.isEmpty()) {
            
        	// Take the userId part of the returned value (firstName lastName (userId)) from html, then convert into int
        	int revId2 = Integer.parseInt(reviewerId2.substring(reviewerId2.indexOf(":") + 1, reviewerId2.indexOf(")")));
        	
            Reviewer reviewer = reviewerRepository.findById(revId2).get();
        
            // Check if reviewer/paper is not(already in review_paper)
            if(!((paper.getReviewers()).contains(reviewer) && (reviewer.getPapers().contains(paper))))
            {
	            // Assign the papers to the reviewers, and vice versa
	            reviewer.getPapers().add(paper);
	            paper.getReviewers().add(reviewer);
	
	            // Update database
	            paperRepository.save(paper);
	            reviewerRepository.save(reviewer);
	
	            //Update major,minor,accept,able to review attributes
	            paperRepository.updateMajorRev(0, paperId, revId2);
	            paperRepository.updateMinorRev(0, paperId, revId2);
	            paperRepository.updateAccept(0, paperId, revId2);
	            paperRepository.updateAbleToReview(0, paperId, revId2);
	            paperRepository.updateReject(0, paperId, revId2);
            }
        }
        
        if (!(reviewerId3.isEmpty())) {
            
        	// Take the userId part of the returned value (firstName lastName (userId)) from html, then convert into int
        	int revId3 = Integer.parseInt(reviewerId3.substring(reviewerId3.indexOf(":") + 1, reviewerId3.indexOf(")")));
        	
            Reviewer reviewer = reviewerRepository.findById(revId3).get();
        
            // Check if reviewer/paper is not(already in review_paper)
            if(!((paper.getReviewers()).contains(reviewer) && (reviewer.getPapers().contains(paper))))
            {
	            // Assign the papers to the reviewers, and vice versa
	            reviewer.getPapers().add(paper);
	            paper.getReviewers().add(reviewer);
	
	            // Update database
	            paperRepository.save(paper);
	            reviewerRepository.save(reviewer);
	
	            //Update major,minor,accept,able to review attributes
	            paperRepository.updateMajorRev(0, paperId, revId3);
	            paperRepository.updateMinorRev(0, paperId, revId3);
	            paperRepository.updateAccept(0, paperId, revId3);
	            paperRepository.updateAbleToReview(0, paperId, revId3);
	            paperRepository.updateReject(0, paperId, revId3);
            }
        }
        
        return "redirect:/author";   
    }
    
    /**
     * This method will retrieve the currently logged in user's id
     * @return this method returns the currently logged in user's id
     */
    public int getCurrentlyLoggedInUser() {
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
        
        return user.getUserId();
    }

}