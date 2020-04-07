package Journalr.com.controller;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


import	java.util.Collection;
//import Journalr.com.model.LoginForm;

@Controller
public class LoginController {

	/*
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
		
	}*/

	@RequestMapping(value="/signUp", method=RequestMethod.GET)
	public String getSignUpForm() {
		return "signUp";
	}
	/*
	private UserRepository userRepository;
	
	@PostMapping(path="/signUp")
	public @ResponseBody String signUp(@RequestParam User user) {
		
		userRepository.save(user);
		return "login";
	}
  */

	//this method redirects the newly authenticated user to the appropriate page based on their role (ex. admin goes to adminPage , author, etc...)
	@RequestMapping("/home")
	public String processLogin() {
	  Collection<GrantedAuthority> authorities = (Collection<GrantedAuthority>)
	    SecurityContextHolder.getContext().getAuthentication().getAuthorities();

	  if (authorities.contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
	    return "redirect:/admin";
	  } else if (authorities.contains(new SimpleGrantedAuthority("ROLE_USER"))) {
	    return "redirect:/user";
	  } else if (authorities.contains(new SimpleGrantedAuthority("ROLE_AUTHOR"))) {
	    return "redirect:/authorHarry";
	  } else if (authorities.contains(new SimpleGrantedAuthority("ROLE_REVIEWER"))) {
		return "redirect:/reviewer";
	  } else if (authorities.contains(new SimpleGrantedAuthority("ROLE_EDITOR"))) {   // these are the roles in the database
		return "redirect:/editor";
	  }
	  // catch else
	  return "redirect:/";
	}
/*
	// Harry's temporary author route
	@RequestMapping(value="/authorHarry", method=RequestMethod.GET)
	public String getAuthorHarry() {
		return "authorHarry";
	}
	
	// Kevin's temporary reviewer route
		@RequestMapping(value="/reviewer", method=RequestMethod.GET)
		public String reviewerHome(Model model) {
			model.addAttribute("pageTitle", "Reviewer");
			return "reviewer";
		}
		*/
}