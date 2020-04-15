package Journalr.com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import org.springframework.stereotype.Controller;

import Journalr.com.repositories.EditorRepository;
import Journalr.com.repositories.PaperRepository;
import Journalr.com.repositories.ReviewerRepository;
import Journalr.com.repositories.UserRepository;

import Journalr.com.model.User;
import Journalr.com.model.UserDetailsClass;

import java.util.*;

import Journalr.com.model.Paper;
import Journalr.com.model.Reviewer;

@Controller
public class EditorController {

    @Autowired
    PaperRepository paperRepository;

    @Autowired
    EditorRepository editorRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ReviewerRepository reviewerRepository;
    /**
     * This method takes in the current displaying model as input.  It responds to the mapping 
     * /editor (the edtior page).  Upon clicking this, it would re-display a admin view with a list
     * of all the papers in the system.
     * @param model The model is the current displaying template.
     * @return
     */
    @RequestMapping("/editor")
    public String showAllPapers(Model model) {
        List<Paper> allPapers = paperRepository.findAll();
        model.addAttribute("allPapers", allPapers);
        try {
        	int id = getCurrentlyLoggedInUser();
            
            User user = userRepository.findById(id).get();
            
            String firstName = user.getFirstName();
            model.addAttribute("firstName", firstName);
        }
        catch(Exception e) {
        	 model.addAttribute("message", "");
             return "redirect:/error";
        }

        return "editor";
    }

    /**
     * This method adds the paper to the approved journals.
     * @param paperID the id of the paper we want to add to the journal
     * @return this method will redirect back to the editor page
     */
    @RequestMapping(path="/addjournal/{paperID}")
    public String addJournal(@PathVariable(name = "paperID") int paperID, Model model) {
        // Check if the paper is in the database
        Paper paper = null;
        try {
            paper = paperRepository.findById(paperID).get();
        } catch (NoSuchElementException e) {
            model.addAttribute("message", "No paper with id: " + paperID + " is in the database.");
            return "error";
        }

        List<Integer> listOfReivewersPerpaper = paperRepository.findReviewersPerPaper(paperID);

        for (Integer id : listOfReivewersPerpaper) {
            if (id != null) {
                // Check if the paper has been rejected
                Boolean reject = paperRepository.retrieveRejectColumnForPaperReview(paperID, id);
                if (reject == true) {
                    User user = userRepository.findById(id).get();
                    model.addAttribute("message", "Reviewer " + user.getFirstName() + " " + user.getLastName() + " has REJECTED the paper with title " + paper.getTitle() + ".  Cannot add it to the system.");
                    return "error";
                }
                // Paper has not been rejected (reject=false).  Check if accept == false.  If it is
                // Then a decision has not been made
                Boolean accept = paperRepository.retrieveAcceptColumnForPaperReview(paperID, id);
                if (accept == false) {
                    User user = userRepository.findById(id).get();
                    model.addAttribute("message", "Reviewer " + user.getFirstName() + " " + user.getLastName() + " still needs to decide on accept/reject for the paper with title " + paper.getTitle());
                    return "error";
                }

                // We have reached here only if reject=false and accept=true for that paper-reviewer combo
                // This means that the paper has been accepted for that one reviewer

            } else {
                model.addAttribute("message", "Need to add reviewers before adding the Journal to the system");
                return "error";
            }
        }

        // Only reached here if all of the reviewers have accepted the paper
        // Now the editor can add the journal to the system

        paper.setApproved(true);
        paperRepository.save(paper);

        return "redirect:/editor";
    }

    /**
     * This method will allow the edtior to view the progress of a given paper.  The progress
     * includes all of the reviewers currently reviewing it and its accept/reject status
     * @param paperID The paper id of the paper we want to view the progress of
     * @param model The current working model
     * @return The page viewProgress with a list of reviewers approval progress
     */
    @RequestMapping(path = "/viewProgress/{paperID}")
    public String showTheProgressOfThePaper(@PathVariable(name = "paperID") int paperID, Model model) {

        // Find the reviewers who have accepted the paper and add it to the model
        List<Reviewer> listOfReviewersAcceptedThePaper = reviewerRepository.findApprovedReviewersAcceptPaper(paperID);
        model.addAttribute("listOfReviewersAcceptedThePaper", listOfReviewersAcceptedThePaper);
        // Find the reviewers who have accepted the paper and add it to the model
        List<Reviewer> listOfReviewersRejectedThePaper = reviewerRepository.findApprovedReviewersRejectPaper(paperID);
        model.addAttribute("listOfReviewersRejectedThePaper", listOfReviewersRejectedThePaper);
        // Find the reviewers who have accepted the paper and add it to the model
        List<Reviewer> listOfReviewersUndecidedPaper = reviewerRepository.findApprovedReviewersUnDecided(paperID);
        model.addAttribute("listOfReviewersUndecidedPaper", listOfReviewersUndecidedPaper);

        model.addAttribute("paperID", paperID);
        
        try {
        	int id = getCurrentlyLoggedInUser();
            
            User user = userRepository.findById(id).get();
            
            String firstName = user.getFirstName();
            model.addAttribute("firstName", firstName);
        }
        catch(Exception e) {
        	 model.addAttribute("message", "");
             return "redirect:/error";
        }

        return "viewProgress";
    }

    /**
     * This method shows the reviewers for the given paper
     * @param paperID the id of the paper we want to show the reviewers of
     * @return this method will redirect to the reviewersperpaper page
     */
    @RequestMapping(path="/reviewersperpaper/{paperID}")
    public String showReviewersPerPaper(@PathVariable(name = "paperID") int paperID,
                                        Model model) {
        
        Paper paper = null;
        try {
            paper = paperRepository.findById(paperID).get();
        } catch (NoSuchElementException e) {
            model.addAttribute("message", "No paper with id: " + paperID + " is in the database.");
            return "error";
        }
        model.addAttribute("paper", paper);

        // Find the reviewers that are able to review the paper
        List<Integer> listApprovedReviewerIds = paperRepository.findReviewersAbleToReview(paperID);
        List<User> listApprovedReviewers = new ArrayList<User>();
        for (Integer reviewerId : listApprovedReviewerIds) {
            listApprovedReviewers.add(userRepository.findById(reviewerId).get());
        }

        // Find the reviewers that are able not able to review the paper
        List<Integer> listPendingReviewerIds = paperRepository.findReviewersNotAbleToReview(paperID);
        List<User> listPendingReviewers = new ArrayList<User>();
        for (Integer reviewerId : listPendingReviewerIds) {
            listPendingReviewers.add(userRepository.findById(reviewerId).get());
        }

        model.addAttribute("listApprovedReviewers", listApprovedReviewers);

        model.addAttribute("listPendingReviewers", listPendingReviewers);

        return "reviewersperpaper";
    }

    /**
     * This method approves a reviewer to a paper
     * @param userId The user/reviewer id that we want to assign to the paper
     * @param paperID The paper id that we want to assign the reviewer to
     * @param model This is the current model we are working with
     * @return This method redirects back to the reviewerspapaer/paperid page
     */
    @RequestMapping(path="/addreviewer/{userId}/{paperID}")
    public String addReviewerToPaper(@PathVariable(name = "userId") int userId,
                                     @PathVariable(name = "paperID") int paperID,
                                     Model model) {
        //asasa
        Paper paper;
        try {
            paper = paperRepository.findById(paperID).get();
            Reviewer reviewer = reviewerRepository.findById(userId).get();
        } catch (NoSuchElementException e) {
            return "redirect:/error";
        }

        // Check if the number of approved reviewers is below three
        Integer numberOfApprovedReviewer = paperRepository.findNumberOfApprovedReviewersPerPaper(paperID);
        // Check if its not null --> if it is null, no reviewers have been added yet
        if (!(numberOfApprovedReviewer == null)) {
            int num = Integer.valueOf(numberOfApprovedReviewer);
            if (num >= 3) {
                // If the number of approved reviewers is greater than or
                // equal to 3, then that paper has reached a maximum number of
                // approved reviewers.  Throw an error message and let the
                // editor now that the maximum numbers of reviewers has beeen reached
                model.addAttribute("message", "Reached maximum number of approved reviewers for the paper: " + paper.getTitle() + " .  Please remove assigned reviewers if you wish to add more.");
                return "error";
            }
        }

        paperRepository.updateAbleToReview(1, paperID, userId);  
        return "redirect:/reviewersperpaper/" + paperID;
    }

    /**
     * This method removes a reviewer from a paper
     * @param userId The user/reviewer id that we want to remove from the paper
     * @param paperID The paper id that we want to remove the reviewer from
     * @return This method redirects back to the reviewerspapaer/paperid page
     */
    @RequestMapping(path="/removereviewer/{userId}/{paperID}")
    public String removeReviewerFromPaper(@PathVariable(name = "userId") int userId,
                                     @PathVariable(name = "paperID") int paperID) {
        //asasa
        try {
            Paper paper = paperRepository.findById(paperID).get();
            Reviewer reviewer = reviewerRepository.findById(userId).get();
        } catch (NoSuchElementException e) {
            return "redirect:/error";
        }
        paperRepository.updateAbleToReview(0, paperID, userId);  
        return "redirect:/reviewersperpaper/" + paperID;
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