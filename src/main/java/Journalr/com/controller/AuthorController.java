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
     * 
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
        } catch(Exception e) {
            
            model.addAttribute("goBack", "/author");

            return "redirect:/error";
        }
        return "author";
    }
    
    @RequestMapping(path="/download/{paperId}")
    public ModelAndView showDownloadPaperPage (@PathVariable(name = "paperId") int paperId) {
        ModelAndView modelAndView = new ModelAndView("downloadpaper");
        Optional<Paper> optional= paperRepository.findById(paperId);
        if (optional.isPresent()) {
            return modelAndView.addObject("paper", optional.get());
        } else {
            return new ModelAndView("error");
        }   
    }


    @RequestMapping(path="/authorAddReviewer/{paperId}")
    public String populateAuthorAddReviewer(@PathVariable(name = "paperId") int paperId, Model model) {

        Paper paper = paperRepository.findById(paperId).get();
        model.addAttribute("paper1", paper);

        List<User> listPotentialReviewers = userRepository.findByRolesContaining("ROLE_REVIEWER");
        model.addAttribute("listPotentialReviewers", listPotentialReviewers);
        
        return "authorAddReviewer";
    }

    @RequestMapping(path="/authorAddReviewer/{paperId}", method = RequestMethod.POST)
    public String authorAddReviewer (@PathVariable("paperId") int paperId, Model model,
                                    @RequestParam("revId1") int reviewerId1,
                                    @RequestParam("revId2") int reviewerId2,
                                    @RequestParam("revId3") int reviewerId3) {
        //
        //
        //
        Paper paper = paperRepository.findById(paperId).get();
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