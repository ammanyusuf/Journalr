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
//import java.util.Map;

import Journalr.com.model.Paper;
import Journalr.com.model.Reviewer;
import Journalr.com.model.User;
import Journalr.com.model.UserDetailsClass;
import Journalr.com.model.Author;

//@RestController
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

    // The methods below are really for the admin
    /**
     * This method takes in the current displaying model as input.  It responds to the mapping 
     * /newuser on the admin page.  Upon clicking this, it would display a new view called adduser.
     * @param model The model is the current displaying template.
     * @return This method will redirect the /newuser mapping to the template page adduser
     */
    /*
    @RequestMapping("/newuser")
    public String showAddUserPage(Model model) {
        User user = new User();
        model.addAttribute("user", user);
         
        return "adduser";
    }*/

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

            // Find all the author's papers that have been reviewed by a reviewer
            List<Paper> reviewedPapers = paperRepository.findReviewedPapersPerAuthors(author.getUserId());

            model.addAttribute("reviewedPapers", reviewedPapers);
            
        } catch(Exception e) {
            
            model.addAttribute("message", "");


            return "redirect:/error";
        }
        return "author";
    }
    
    // This method is useless

    /**
     * this method will download the given paper with the corresponding paper ID
     * @param paperId this is the paper id that we wish to download
     * @return this method will redirect to the downloadpaper page
     */
     /*@RequestMapping(path="/download/{paperId}")
    public ModelAndView showDownloadPaperPage (@PathVariable(name = "paperId") int paperId) {
        ModelAndView modelAndView = new ModelAndView("downloadpaper");
        Optional<Paper> optional= paperRepository.findById(paperId);
        if (optional.isPresent()) {
            return modelAndView.addObject("paper", optional.get());
        } else {
            return new ModelAndView("error");
        }   
    }*/


    /**
     * this method will populate the authorAddReviewer page iwth all the potential reviewers
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

    /**
     * This method will add the reviewers that the author selected to the paper passed
     * through
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
                                    @RequestParam("revId1") int reviewerId1,
                                    @RequestParam("revId2") int reviewerId2,
                                    @RequestParam("revId3") int reviewerId3) {
        //
        //
        // Find the paper in our database
        Paper paper = paperRepository.findById(paperId).get();
        // Check if the reviewer id is not equal to 1
        if (reviewerId1 != -1) {
            
            Reviewer reviewer = reviewerRepository.findById(reviewerId1).get();

            // Assign the papers to the reviewers, and vice versa
            reviewer.getPapers().add(paper);
            paper.getReviewers().add(reviewer);

            // Update database
            paperRepository.save(paper);
            reviewerRepository.save(reviewer);

            //Update major,minor,accept,able to review attributes
            paperRepository.updateMajorRev(0, paperId, reviewerId1);
            paperRepository.updateMinorRev(0, paperId, reviewerId1);
            paperRepository.updateAccept(0, paperId, reviewerId1);
            paperRepository.updateAbleToReview(0, paperId, reviewerId1);
            paperRepository.updateReject(0, paperId, reviewerId1);
        }
        // Check if the reviewer id is not equal to 1
        if (reviewerId2 != -1) {
            Reviewer reviewer = reviewerRepository.findById(reviewerId2).get();

            // Assign the papers to the reviewers, and vice versa
            reviewer.getPapers().add(paper);
            paper.getReviewers().add(reviewer);

            // Update database
            paperRepository.save(paper);
            reviewerRepository.save(reviewer);

            //Update major,minor,accept,able to review attributes
            paperRepository.updateMajorRev(0, paperId, reviewerId2);
            paperRepository.updateMinorRev(0, paperId, reviewerId2);
            paperRepository.updateAccept(0, paperId, reviewerId2);
            paperRepository.updateAbleToReview(0, paperId, reviewerId2);
            paperRepository.updateReject(0, paperId, reviewerId2);
        }
        // Check if the reviewer id is not equal to 1
        if (reviewerId3 != -1) {
            Reviewer reviewer = reviewerRepository.findById(reviewerId3).get();

            // Assign the papers to the reviewers, and vice versa
            reviewer.getPapers().add(paper);
            paper.getReviewers().add(reviewer);

            // Update database
            paperRepository.save(paper);
            reviewerRepository.save(reviewer);

            //Update major,minor,accept,able to review attributes
            paperRepository.updateMajorRev(0, paperId, reviewerId3);
            paperRepository.updateMinorRev(0, paperId, reviewerId3);
            paperRepository.updateAccept(0, paperId, reviewerId3);
            paperRepository.updateAbleToReview(0, paperId, reviewerId3);
            paperRepository.updateReject(0, paperId, reviewerId3);
            
        }
        
        return "redirect:/author";   
    
    }

    /**
     * This method will retireved the currently logged in user's id
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