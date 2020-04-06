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
        List<Paper> listPapers = paperRepository.findPapersByTopic(reviewer.getFavoriteTopic());

        //List<Paper> listPapers = paperRepository.findAll();
        model.addAttribute("listPapersByTopic", listPapers);

        return "paperTopics";
    }

}