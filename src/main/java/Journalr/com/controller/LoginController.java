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
	public String getLoginForm() {
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
		public String reviewerHome() {
			return "reviewer";
		}
}