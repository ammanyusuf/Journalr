package Journalr.com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import org.springframework.stereotype.Controller;
import org.springframework.security.core.context.SecurityContextHolder;

import Journalr.com.repositories.AuthorRepository;
import Journalr.com.repositories.PaperRepository;
import Journalr.com.repositories.UserRepository;

import java.util.*;
//import java.util.Map;

import Journalr.com.model.Paper;
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
    public String showAllPapers(Model model) {
        
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
		int id = user.getUserId();

		// Find the author in the author table by id
		Author author = authorRepository.findById(id).get();
        
        //Find all the papers written by the author
        List<Paper> listPapers = paperRepository.findPapersByAuthorId(author.getUserId());

        //List<Paper> listPapers = paperRepository.findAll();
        model.addAttribute("listPapers", listPapers);

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

    /**
     * This method parses the unique user id that was passed through the url.  It then retrieves
     * the user with that id and sets up the edituser template for the model to go to.  This is all
     * done with the ModelAndView object provided by Spring.  Model and View pretty much sets up the
     * model and also sets the view that will be displaying after the action.
     * @param userId This is the userId that we will be updating the data.
     * @return This method will return a new model and view ready for editing.
     */
    /*
    @RequestMapping(path="/edit/{userId}")
    public ModelAndView showEditUserPage (@PathVariable(name = "userId") int userId) {
        ModelAndView modelAndView = new ModelAndView("editUser");
        Optional<User> optional= userRepository.findById(userId);
        if (optional.isPresent()) {
            return modelAndView.addObject("user", optional.get());
        } else {
            return new ModelAndView("error");
        }   
    }*/
    /**
     * This method parses the unique user id that was passed through the url.  It then deletes
     * the user with that id from the database
     * @param userId This is the userId that we will be deleted.
     * @return This method will redirect to the admin page
    */
    /*
    @RequestMapping( path="/delete/{userId}")
    public String deleteUser(@PathVariable(name = "userId") int userId){
        userRepository.deleteById(userId);
        return "redirect:/admin";
    }*/
}