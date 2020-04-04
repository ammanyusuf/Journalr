package Journalr.com.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import Journalr.com.model.LoginForm;

@Controller
public class LoginController {

	@RequestMapping(value="/login", method=RequestMethod.GET)
	public String getLoginForm(Model model) {
		model.addAttribute("pageTitle", "Journalr | Login");
		return "login";
	}
	
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public String login(@ModelAttribute(name="loginForm") LoginForm loginForm, Model model) {
		
		String username = loginForm.getUsername();                     
		String password = loginForm.getPassword();
		
		if ("admin".equals(username) && "adminPassword".equals(password)) {
			return "home";                                              // this is where the validation is done
		}
		
		model.addAttribute("invalidCredentials", true);
		return "login";
		
	}

	// Harry's temporary author route
	@RequestMapping(value="/authorHarry", method=RequestMethod.GET)
	public String getAuthorHarry() {
		return "authorHarry";
	}
	
	// Kevin's temporary reviewer route
	@RequestMapping(value="/reviewer", method=RequestMethod.GET)
	public String reviewerHome(Model model) {
		model.addAttribute("pageTitle", "Reviewer | Home");
		model.addAttribute("firstName", "Reviewer");
		return "reviewer";
	}
		
	// Kevin's temporary reviewer papers route
	@RequestMapping(value="/reviewer/papers", method=RequestMethod.GET)
	public String reviewerPapers(Model model) {
		model.addAttribute("pageTitle", "Reviewer | Papers");
		model.addAttribute("firstName", "Reviewer");
		return "reviewerPapers";
	}
	
	// Kevin's temporary reviewer authors route
	@RequestMapping(value="/reviewer/authors", method=RequestMethod.GET)
	public String reviewerAuthors(Model model) {
		model.addAttribute("pageTitle", "Reviewer | Authors");
		model.addAttribute("firstName", "Reviewer");
		return "reviewerAuthors";
	}
	
	// Kevin's temporary reviewer settings route
	@RequestMapping(value="/reviewer/settings", method=RequestMethod.GET)
	public String reviewerSettings(Model model) {
		model.addAttribute("pageTitle", "Reviewer | Settings");
		model.addAttribute("firstName", "Reviewer");
		return "reviewerSettings";
	}
	
	//Kevin's temporary editor route
	@RequestMapping(value="/editor", method=RequestMethod.GET)
	public String editor(Model model) {
		model.addAttribute("pageTitle", "Editor | Home");
		return "editor";
	}
	
	//Kevin's temporary editor assignReviewer route
	@RequestMapping(value="/editor/assignReviewer", method=RequestMethod.GET)
	public String assignRev(Model model) {
		model.addAttribute("pageTitle", "Editor | Assign Reviewer");
		return "editorAssignReviewer";
	}
	
	//Kevin's temporary editor paper management route
		@RequestMapping(value="/editor/managePapers", method=RequestMethod.GET)
		public String managePapers(Model model) {
			model.addAttribute("pageTitle", "Editor | Paper Management");
			return "editorManagePapers";
		}
}