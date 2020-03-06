package com.example.handlingformsubmission;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class GreetingController {

	
	@GetMapping("/loginPage")
	public String greetingForm1(Model model) {
		model.addAttribute("userObj", new User());
	    return "loginPage";                                            // greeting.html, returns html file page
	}	
	
	@PostMapping("/loginPage")                                             // after submit is clicked
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