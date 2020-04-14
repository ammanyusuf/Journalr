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
    /**
     * This method is gets the current name of the reviewre that is logged in
     * @param model The current mode we are working with
     * @return This method displays the reviewer page
     */
	@RequestMapping(value="/reviewer", method=RequestMethod.GET)
	public String reviewerHome(Model model) {
        
        // Add the page title to the model
        model.addAttribute("pageTitle", "Reviewer | Home");    
        
        // Find the id of the currently logged in user
        int id = returnIdOfCurrentlyLoggedInUser();
        // Check that the paper and reviewer are in the database
        // Find the reviewer in the reviewer table by id
        Reviewer reviewer;
        try {
            reviewer = reviewerRepository.findById(id).get();
        } catch (Exception e) {
            model.addAttribute("message", "No REVIEWER with id: " + id + ".  Try logging in as a REVIEWER");
            return "error";
        }
        model.addAttribute("updateReviewerTandA", reviewer);;
        
        // Set the first name in the page
		String firstName = reviewer.getFirstName();
        model.addAttribute("firstName", firstName);

        model.addAttribute("reviewerHomePage", reviewer);

		return "reviewer";
    }

    @RequestMapping(value = "/reviewer", method=RequestMethod.POST)
    public String updatedTopicAndAffiliation(@RequestParam String favouriteTopic,
                                             @RequestParam String affiliation,
                                             Model model) {
        
        // Find the id of the currently logged in user
        int id = returnIdOfCurrentlyLoggedInUser();
        // Check that the paper and reviewer are in the database
        // Find the reviewer in the reviewer table by id
        Reviewer reviewer;
        try {
            reviewer = reviewerRepository.findById(id).get();
        } catch (Exception e) {
            model.addAttribute("message", "No REVIEWER with id: " + id + ".  Try loging in as a REVIEWER");
            return "error";
        }
        reviewer.setAffiliation(affiliation);
        reviewer.setFavouriteTopic(favouriteTopic);                                
        reviewerRepository.save(reviewer);

        return "redirect:/reviewer";
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
        
        // Add the page title to the model
        model.addAttribute("pageTitle", "Reviewer | Available Papers");

        // find the id of the currently logged in user
		int id = returnIdOfCurrentlyLoggedInUser();
        
        User user = userRepository.findById(id).get();

        // Get the first name of the user
		String firstName = user.getFirstName();
		model.addAttribute("firstName", firstName);

        // List all of the papers not selected by the reviewer
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
    public String selectPaperToReview(@PathVariable int paperId, Model model) {

        // Find the id of the currently logged in suer
		int id = returnIdOfCurrentlyLoggedInUser();

        // Find the reviewer in the reviewer table by id
        Reviewer reviewer;
        try {
            reviewer = reviewerRepository.findById(id).get();
        } catch (Exception e) {
            model.addAttribute("message", "No REVIEWER with id: " + id + ".  Try loging in as a REVIEWER");
            return "error";
        }
        
        // Find the paper in the database as well
        Paper paper;
        try {
            paper = paperRepository.findById(paperId).get();
        } catch (Exception e) {
            model.addAttribute("message", "No PAPER with id: " + paperId + " in system.");
            return "error";
        }
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
    public String deselectPaperToReview(@PathVariable int paperId, Model model) {

        // Find the id of the currently logged in user
		int id = returnIdOfCurrentlyLoggedInUser();

		// Find the reviewer in the reviewer table by id
        Reviewer reviewer;
        try {
            reviewer = reviewerRepository.findById(id).get();
        } catch (Exception e) {
            model.addAttribute("message", "No REVIEWER with id: " + id + ".  Try loging in as a REVIEWER");
            return "error";
        }
        
        // Find the paper in the database as well
        Paper paper;
        try {
            paper = paperRepository.findById(paperId).get();
        } catch (Exception e) {
            model.addAttribute("message", "No PAPER with id: " + paperId + " in system.");
            return "error";
        }
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
     * @return this method returns to the page /reviewer/paperTopics
     */
    @RequestMapping("/reviewer/paperTopics")
    public String showAllPapersToReview(Model model) {
        
        // Add the page title to the model
        model.addAttribute("pageTitle", "Reviewer | Favorite Topic Papers");
        
        // Get the id of the currently logged in user
		int id = returnIdOfCurrentlyLoggedInUser();

        // Find the reviewer in the reviewer table by id
        Reviewer reviewer;
        try {
            reviewer = reviewerRepository.findById(id).get();
        } catch (Exception e) {
            model.addAttribute("message", "No REVIEWER with id: " + id + ".  Try loging in as a REVIEWER");
            return "error";
        }
        //Find all the papers based on the reviewer's topic of interest
        List<Paper>  listPapers = null;
        if (reviewer.getFavouriteTopic() != null) {
            listPapers = paperRepository.findPapersByTopic(reviewer.getFavouriteTopic());
        }
        model.addAttribute("listPapersByTopic", listPapers);
        
        // Get the user that is loggd in
        User user = userRepository.findById(id).get();

        // Add the first name of the user to the page
        String firstName = user.getFirstName();
		model.addAttribute("firstName", firstName);
        
        return "paperTopics";
    }

    @RequestMapping(value="/reviewer/mypapersReviewer", method=RequestMethod.GET)
    public String populateMyPapersReviewer(Model model) {

        // Add the title to the model
        model.addAttribute("pageTitle", "Reviewer | My Papers");

        // Find the id of the currently logged in user
        int id = returnIdOfCurrentlyLoggedInUser();
        
        // Get the currently logged in user
        User user = userRepository.findById(id).get();

        // Add the name of the user to the page
		String firstName = user.getFirstName();
		model.addAttribute("firstName", firstName);

		// Find the reviewer in the reviewer table by id
        Reviewer reviewer;
        try {
            reviewer = reviewerRepository.findById(id).get();
        } catch (Exception e) {
            model.addAttribute("message", "No REVIEWER with id: " + id + ".  Try loging in as a REVIEWER");
            return "error";
        }
        // Add a list of pending papers (papers that are waiting for editor approval) to the model
        List<Paper> listMyPendingPapers = paperRepository.findPendingPapersOfReviewer(id);
        model.addAttribute("listMyPendingPapers", listMyPendingPapers);

        // Add a list of approved papers (papers approved by the editor to reviewe) to the model
        List<Paper> listMyApprovedPapers = paperRepository.findApprovedPapersOfReviewer(id);
        model.addAttribute("listMyApprovedPapers", listMyApprovedPapers);

        return "mypapersReviewer";
    }
    
    @RequestMapping(value = "/reviewer/paperAccept")
    public String populatePaperAccepts(Model model) {
        
        // Add the page title to the model
        model.addAttribute("pageTitle", "Reviewer | Papers To Accept");

        // Retrieve the id of the currently logged in user
        int id = returnIdOfCurrentlyLoggedInUser();

        // Retrieve the currently logged in user
        User user = userRepository.findById(id).get();
        
        // Add the first name of the currently logged in user to the model
        String firstName = user.getFirstName();
		model.addAttribute("firstName", firstName);
        
        // Find the reviewer in the reviewer table by id
        Reviewer reviewer;
        try {
            reviewer = reviewerRepository.findById(id).get();
        } catch (Exception e) {
            model.addAttribute("message", "No REVIEWER with id: " + id + ".  Try loging in as a REVIEWER");
            return "error";
        }

        // Add a list of potential to be accepted/rejected papers to the model
        List<Paper> listPotentialAcceptedPapers = paperRepository.findPotentialAcceptedPapers(id);
        model.addAttribute("listPotentialAcceptedPapers", listPotentialAcceptedPapers);

        // Add a list of accepted papers by the reviewer to the model
        List<Paper> listAcceptedPapersByReviewer = paperRepository.findAcceptedPapersByReviewer(id);
        model.addAttribute("listAcceptedPapersByReviewer", listAcceptedPapersByReviewer);

        // Add a list of rejected papers by the reviewer to the model
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
    public String acceptPaper(@PathVariable int paperId, Model model) {
        
        // Find the id of the currently logged in user
        int id = returnIdOfCurrentlyLoggedInUser();
        // Find the reviewer in the reviewer table by id
        Reviewer reviewer;
        try {
            reviewer = reviewerRepository.findById(id).get();
       } catch (Exception e) {
            model.addAttribute("message", "No REVIEWER with id: " + id + ".  Try loging in as a REVIEWER");
            return "error";
        }
        
        // Find the paper in the database as well
        Paper paper;
        try {
            paper = paperRepository.findById(paperId).get();
        } catch (Exception e) {
            model.addAttribute("message", "No PAPER with id: " + paperId + " in system.");
            return "error";
        }
        // Updated the accept column in the paper repository
        paperRepository.updateAccept(1, paperId, id);
        
        return "redirect:/reviewer/paperAccept";
    }

    /**
     * this method will have the reviewer reject a paper by the given paperId
     * @param paperId the paper Id that we wish to reject
     * @return this method will return back to the accept paper page
     */
    @RequestMapping(value="**/rejectPaper/{paperId}", method=RequestMethod.GET)
    public String rejectPaper(@PathVariable int paperId, Model model) {
        
        // Find the id of the currently logged in user
        int id = returnIdOfCurrentlyLoggedInUser();
        // Check that the paper and reviewer are in the database
        // Find the reviewer in the reviewer table by id
        Reviewer reviewer;
        try {
            reviewer = reviewerRepository.findById(id).get();
        } catch (Exception e) {
            model.addAttribute("message", "No REVIEWER with id: " + id + ".  Try loging in as a REVIEWER");
            return "error";
        }
        
        // Find the paper in the database as well
        Paper paper;
        try {
            paper = paperRepository.findById(paperId).get();
        } catch (Exception e) {
            model.addAttribute("message", "No PAPER with id: " + paperId + " in system.");
            return "error";
        }
        // Updated the accept column in the paper repository
        paperRepository.updateReject(1, paperId, id);
        
        return "redirect:/reviewer/paperAccept";
    }


    /**
     * This method will retrieve the id of the currently logged
     * in user
     * @return The id of the currently logged in user
     */
    public int returnIdOfCurrentlyLoggedInUser() {
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
        
		return user.getUserId();
    }
    
}