package Journalr.com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import org.springframework.stereotype.Controller;

import Journalr.com.repositories.AuthorRepository;
import Journalr.com.repositories.EditorRepository;
import Journalr.com.repositories.ReviewerRepository;
import Journalr.com.repositories.UserRepository;

import java.util.*;
//import java.util.Map;

import Journalr.com.model.Author;
import Journalr.com.model.Editor;
import Journalr.com.model.Reviewer;
import Journalr.com.model.User;

//@RestController
@Controller
public class UserController {

    @Autowired
    private ReviewerRepository reviewerRepository;

    @Autowired
    private EditorRepository editorRepository;

    @Autowired
	private AuthorRepository authorRepository;

	@Autowired
	private UserRepository userRepository;
    // The methods below are really for the admin
    /**
     * This method takes in the current displaying model as input.  It responds to the mapping 
     * /newuser on the admin page.  Upon clicking this, it would display a new view called adduser.
     * @param model The model is the current displaying template.
     * @return This method will redirect the /newuser mapping to the template page adduser
     */
    @RequestMapping("/newuser")
    public String showAddUserPage(Model model) {
        User user = new User();
        model.addAttribute("user", user);
         
        return "adduser";
    }

    /**
     * This method takes in the current displaying model as input.  It responds to the mapping 
     * /admin (the admin page).  Upon clicking this, it would re-display a admin view with a list
     * of all the users in the system.
     * @param model The model is the current displaying template.
     * @return
     */
    @RequestMapping("/admin")
    public String showAllUsers(Model model) {
        List<User> listUsers = userRepository.findAll();
        model.addAttribute("listUsers", listUsers);

        return "admin";
    }

    /**
     * This method recieves the current displaying model and the User form that was passed through
     * the model as input.  It then adds the inputed User into the database and then redirects to 
     * the main admin page.
     * @param user The model (consider it like an html page) prompts the admin to put information
     *             about the new User in the form field.  Spring then converts that info to a User
     *             object and passes it through as a paramter.
     * @return This method will return a redirect link back to the index page of the admin page.
     */
    @RequestMapping(path="/save", method = RequestMethod.POST)
    public String saveUser (@ModelAttribute("user") User user) {

        //userRepository.save(user);

        if (user.getRoles().contains("AUTHOR")) {
            
            // Check wether or not he author already exists in database
            Author author;
            if (authorRepository.findById(user.getUserId()).isPresent()) {
                author = authorRepository.findById(user.getUserId()).get();
                author.copyValues(user);
            } else {
                author = new Author(user);
            }

            authorRepository.save(author);

        } else if (user.getRoles().contains("REVIEWER")) {
            
            // Check wether or not he reviewer already exists in database
            Reviewer reviewer;
            if (reviewerRepository.findById(user.getUserId()).isPresent()) {
                reviewer = reviewerRepository.findById(user.getUserId()).get();
                reviewer.copyValues(user);
                
            } else {
                reviewer = new Reviewer(user);
            }

            reviewerRepository.save(reviewer);

        } else if (user.getRoles().contains("EDITOR")) {

            // Check wether or not he editor already exists in database
            Editor editor;
            if (editorRepository.findById(user.getUserId()).isPresent()) {
                editor = editorRepository.findById(user.getUserId()).get();
                editor.copyValues(user);
            } else {
                editor = new Editor(user);
            }
            
            editorRepository.save(editor);

        }

        //userRepository.save(user);

        return "redirect:/admin";
    }

    /**
     * This method parses the unique user id that was passed through the url.  It then retrieves
     * the user with that id and sets up the edituser template for the model to go to.  This is all
     * done with the ModelAndView object provided by Spring.  Model and View pretty much sets up the
     * model and also sets the view that will be displaying after the action.
     * @param userId This is the userId that we will be updating the data.
     * @return This method will return a new model and view ready for editing.
     */
    @RequestMapping(path="/edit/{userId}")
    public ModelAndView showEditUserPage (@PathVariable(name = "userId") int userId) {
        ModelAndView modelAndView = new ModelAndView("editUser");
        Optional<User> optional= userRepository.findById(userId);
        if (optional.isPresent()) {
            return modelAndView.addObject("user", optional.get());
        } else {
            return new ModelAndView("error");
        }   
    }
    /**
     * This method parses the unique user id that was passed through the url.  It then deletes
     * the user with that id from the database
     * @param userId This is the userId that we will be deleted.
     * @return This method will redirect to the admin page
    */
    @RequestMapping( path="/delete/{userId}")
    public String deleteUser(@PathVariable(name = "userId") int userId, Model model){
        try {
            userRepository.deleteById(userId);
        } catch (Exception e) {
            model.addAttribute("message", "No user with id: " + userId + " exists in the database.");
            return "error";
        }
        
        return "redirect:/admin";
    }
}
