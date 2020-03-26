package Journalr.com.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//This class tells spring that these APIs are to be used (kind of "registers" these pages)
@Configuration
public class MvcConfig implements WebMvcConfigurer {

	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/home").setViewName("home");
		registry.addViewController("/").setViewName("home");
		registry.addViewController("/hello").setViewName("hello");
		registry.addViewController("/login").setViewName("login");
		registry.addViewController("/signUp").setViewName("signUp");
		registry.addViewController("/author").setViewName("author");
		registry.addViewController("/uploadForm").setViewName("uploadForm");
		registry.addViewController("/reviewer").setViewName("reviewer");
		registry.addViewController("/editor").setViewName("editor");
		registry.addViewController("/add").setViewName("add");
		registry.addViewController("/all").setViewName("all");
		registry.addViewController("/delete").setViewName("delete");
		registry.addViewController("/authorHarry").setViewName("authorHarry");
		registry.addViewController("/user/lastname/{lastName}").setViewName("userLastName");
		registry.addViewController("/user/firstname/{firstName}").setViewName("userFirstName");
		registry.addViewController("/update/password/{id}").setViewName("updatePassword");
		registry.addViewController("/update/username/{id}").setViewName("updateUserName");
		
		
	}
	
}