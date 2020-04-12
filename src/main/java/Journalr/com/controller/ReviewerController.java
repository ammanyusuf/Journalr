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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


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
		
		String firstName = user.getFirstName();
		model.addAttribute("firstName", firstName);
		
		return "reviewer";
    }
    
    // Kevin's temporary reviewer papers route
    /**
     * This method will retrieve all of the papers that the currently logged in user,
     * reviewer, has not selected
     * @param model The current model that is passed though
     * @return the reviewerPapers page
     */
	@RequestMapping(value="/reviewer/availablePapers", method=RequestMethod.GET)
	public String reviewerPapers(Model model) {
		model.addAttribute("pageTitle", "Reviewer | Available Papers");
        
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
		
		String firstName = user.getFirstName();
		model.addAttribute("firstName", firstName);

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
        paperRepository.updateReject(0, paperId, reviewerId);

        return "redirect:/reviewer/availablePapers";

    }


    /**
     * This method will deselct the paper from the reviewer in the review_paper table
     * in mysql
     * @param paperId the paperId that the reviewer wishes to -deselect
     * @return this method returns to the /reviewer/papers page
     */
    @RequestMapping("**/deselectPaperToReview/{paperId}")
    public String deselectPaperToReview(@PathVariable int paperId) {

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

		// Find the reviewer in the reviewer table by id as well as the paper
        Reviewer reviewer = reviewerRepository.findById(id).get();
        Paper paper = paperRepository.findById(paperId).get();

        // Assign the papers to the reviewers, and vice versa
        reviewer.getPapers().remove(paper);
        paper.getReviewers().remove(reviewer);

        // Update database
        paperRepository.save(paper);
        reviewerRepository.save(reviewer);

        return "redirect:/reviewer/mypapersReviewer";

    }

    /**
     * Description: This function will retrieve all papers based on reviewer's topic
     *              of interest.
     * 
     * @param model The model is the current displaying template.
     * @return
     */
    @RequestMapping("/reviewer/paperTopics")
    public String showAllPapersToReview(Model model) {
        model.addAttribute("pageTitle", "Reviewer | Favorite Topic Papers");
        
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
        
        String firstName = user.getFirstName();
		model.addAttribute("firstName", firstName);
        
        return "paperTopics";
    }

    @RequestMapping(value="/reviewer/mypapersReviewer", method=RequestMethod.GET)
    public String populateMyPapersReviewer(Model model) {

        model.addAttribute("pageTitle", "Reviewer | My Papers");

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
		
		String firstName = user.getFirstName();
		model.addAttribute("firstName", firstName);

		// Find the reviewer in the reviewer table by id
        Reviewer reviewer = reviewerRepository.findById(id).get();
        
        List<Paper> listMyPendingPapers = paperRepository.findPendingPapersOfReviewer(reviewer.getUserId());

        model.addAttribute("listMyPendingPapers", listMyPendingPapers);

        List<Paper> listMyApprovedPapers = paperRepository.findApprovedPapersOfReviewer(reviewer.getUserId());

        model.addAttribute("listMyApprovedPapers", listMyApprovedPapers);

        return "mypapersReviewer";
    }
    
    @RequestMapping(value = "/reviewer/paperAccept")
    public String populatePaperAccepts(Model model) {
        model.addAttribute("pageTitle", "Reviewer | Papers To Accept");

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
        
        String firstName = user.getFirstName();
		model.addAttribute("firstName", firstName);
        
        List<Paper> listPotentialAcceptedPapers = paperRepository.findPotentialAcceptedPapers(id);
        model.addAttribute("listPotentialAcceptedPapers", listPotentialAcceptedPapers);

        List<Paper> listAcceptedPapersByReviewer = paperRepository.findAcceptedPapersByReviewer(id);
        model.addAttribute("listAcceptedPapersByReviewer", listAcceptedPapersByReviewer);

        List<Paper> listRejectedPapersByReviewer = paperRepository.findRejectedPapersByReviewer(id);
        model.addAttribute("listRejectedPapersByReviewer", listRejectedPapersByReviewer);

        return "paperAccept";
    }

    /**
     * this method will have the reviewer accept the paper passed through the url
     * @param paperId the paper id that we wish to accept
     * @return return to the paperAccept page
     */
    @RequestMapping(value="**/acceptPaper/{paperId}", method=RequestMethod.GET)
    public String acceptPaper(@PathVariable int paperId) {
        
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

        paperRepository.updateAccept(1, paperId, id);
        
        return "redirect:/reviewer/paperAccept";
    }

    /**
     * this method will have the reviewer reject a paper by the given paperId
     * @param paperId the paper Id that we wish to reject
     * @return this method will return back to the accept paper page
     */
    @RequestMapping(value="**/rejectPaper/{paperId}", method=RequestMethod.GET)
    public String rejectPaper(@PathVariable int paperId) {
        
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

        paperRepository.updateReject(1, paperId, id);
        
        return "redirect:/reviewer/paperAccept";
    }
    
}