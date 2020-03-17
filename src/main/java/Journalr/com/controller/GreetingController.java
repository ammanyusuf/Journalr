package Journalr.com.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import Journalr.com.model.*;

@Controller
public class GreetingController {

	
	@GetMapping("/login")
	public String greetingForm1(Model model) {
		model.addAttribute("userObj", new User());
	    return "login";                                            // greeting.html, returns html file page
	}	
	
	@PostMapping("/login")                                             // after submit is clicked
	public String loginSubmit(@ModelAttribute User user) {
		//verify login
		return "afterLogin";
	}
	
	@GetMapping("/greeting")
	public String greetingForm(Model model) {
		model.addAttribute("greeting", new Greeting());
		return "greeting";                                            // greeting.html, returns html file page
	}
  
	@PostMapping("/greeting")
	public String greetingSubmit(@ModelAttribute Greeting greeting) {
		return "result";
	}
 
}