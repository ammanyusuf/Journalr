package Journalr.com.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import  Journalr.com.model.UserDetailsClass;
import  Journalr.com.repositories.UserRepository;
import  Journalr.com.model.User;
import  java.util.Optional;

//everytime you need a user, this method is called, and returns the user object
@Service
public class UserDetailService implements UserDetailsService {
	
	@Autowired
	UserRepository userRepository;
	
	/**
	 * This method finds the user details by a given user name
	 * @param userName the userName we wish to find the userdetail of
	 * @return the userdetail object that corresponds to the given user name
	 */
	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		
		Optional<User> user = userRepository.findByUserName(userName);               // checks the database by username
		
		user.orElseThrow(() -> new UsernameNotFoundException("Not found: " + userName));
		
		return user.map(UserDetailsClass::new).get();              // returns the user object containing the information about the user
	}

}