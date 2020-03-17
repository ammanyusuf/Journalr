package Journalr.com.service;

//import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

	public boolean validateUser(String userid, String password) {
		// in28minutes, dummy
		if (userid.equalsIgnoreCase("author")) {
			return userid.equalsIgnoreCase("author") && password.equalsIgnoreCase("user1");
		} 

		if (userid.equalsIgnoreCase("reviewer")) {
			return userid.equalsIgnoreCase("reviewer") && password.equalsIgnoreCase("user2");
		} 

		if (userid.equalsIgnoreCase("editor")) {
			return userid.equalsIgnoreCase("editor") && password.equalsIgnoreCase("user3");
		} 

		return false;

		//return userid.equalsIgnoreCase("user1")
		//		&& password.equalsIgnoreCase("useronepassword");
	}

}