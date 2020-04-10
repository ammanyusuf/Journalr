package Journalr.com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import org.springframework.stereotype.Controller;
import org.springframework.security.core.context.SecurityContextHolder;

import Journalr.com.repositories.PaperRepository;
import Journalr.com.repositories.UserRepository;
import Journalr.com.repositories.ReviewerRepository;

import java.util.*;
//import java.util.Map;

import Journalr.com.model.Paper;
import Journalr.com.model.User;
import Journalr.com.model.UserDetailsClass;
import Journalr.com.model.Reviewer;

//@RestController
@Controller
public class ReviewerController {

    @Autowired
    PaperRepository paperRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ReviewerRepository reviewerRepository;


    // Kevin's temporary reviewer route
	@RequestMapping(value="/reviewer", method=RequestMethod.GET)
	public String reviewerHome(Model model) {
		model.addAttribute("pageTitle", "Reviewer | Home");
		model.addAttribute("firstName", "Reviewer");
		return "reviewer";
    }
    
    // Kevin's temporary reviewer papers route
    /**
     * This method will retrieve all of the papers that the currently logged in user,
     * reviewer, has not selected
     * @param model The current model that is passed though
     * @return the reviewerPapers page
     */
	@RequestMapping(value="/reviewer/papers", method=RequestMethod.GET)
	public String reviewerPapers(Model model) {
		model.addAttribute("pageTitle", "Reviewer | Papers");
        model.addAttribute("firstName", "Reviewer");
        
        // Get the credentials of the currently logged in user
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		String userName;

		// Get the instance of that user
		if (principal instanceof UserDetailsClass) {
			userName = ((UserDetailsClass)principal).getUsername();
		} else {
			userName = principal.toString();
		}

		// Find the user in the user table by their username
		User user = userRepository.findByUserName(userName).get();
		int id = user.getUserId();

        //List<Paper> listAllPapers = paperRepository.findAll();
        List<Paper> listAllPapers = paperRepository.findPapersNotSelectedByReviewerId(id);
        model.addAttribute("listAllPapers", listAllPapers);

		return "reviewerPapers";
    }
    
    /**
     * This method will add the paper selected to the review_paper table in the sql server,
     * resulting in the paper to show up under the reviewer's selections
     * @param paperId the paperId that the reviewer wishes to select
     * @return this method returns to the /reviewer/papers page
     */
    @RequestMapping("**/selectPaperToReview/{paperId}")
    public String selectPaperToReview(@PathVariable int paperId) {

        // Get the credentials of the currently logged in user
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		String userName;

		// Get the instance of that user
		if (principal instanceof UserDetailsClass) {
			userName = ((UserDetailsClass)principal).getUsername();
		} else {
			userName = principal.toString();
		}

		// Find the user in the user table by their username
		User user = userRepository.findByUserName(userName).get();
		int id = user.getUserId();

		// Find the reviewer in the reviewer table by id
        Reviewer reviewer = reviewerRepository.findById(id).get();
        Paper paper = paperRepository.findById(paperId).get();

        // Assign the papers to the reviewers, and vice versa
        reviewer.getPapers().add(paper);
        paper.getReviewers().add(reviewer);

        // Update database
        paperRepository.save(paper);
        reviewerRepository.save(reviewer);

        int reviewerId = reviewer.getUserId();

        //Update major,minor,accept,able to review attributes
        paperRepository.updateMajorRev(0, paperId, reviewerId);
        paperRepository.updateMinorRev(0, paperId, reviewerId);
        paperRepository.updateAccept(0, paperId, reviewerId);
        paperRepository.updateAbleToReview(0, paperId, reviewerId);

        return "redirect:/reviewer/papers";

    }

    /**
     * Description: This function will retrieve all papers based on reviewer's topic
     *              of interest.
     * 
     * @param model The model is the current displaying template.
     * @return
     */
    @RequestMapping("/paperTopics")
    public String showAllPapersToReview(Model model) {
        
        // Get the credentials of the currently logged in user
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		String userName;

		// Get the instance of that user
		if (principal instanceof UserDetailsClass) {
			userName = ((UserDetailsClass)principal).getUsername();
		} else {
			userName = principal.toString();
		}

		// Find the user in the user table by their username
		User user = userRepository.findByUserName(userName).get();
		int id = user.getUserId();

		// Find the reviewer in the reviewer table by id
		Reviewer reviewer = reviewerRepository.findById(id).get();
        
        //Find all the papers based on the reviewer's topic of interest
        List<Paper> listPapers = paperRepository.findPapersByTopic(reviewer.getFavouriteTopic());

        //List<Paper> listPapers = paperRepository.findAll();
        model.addAttribute("listPapersByTopic", listPapers);
        
        return "paperTopics";
    }


    

}