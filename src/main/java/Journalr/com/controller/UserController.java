package Journalr.com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import Journalr.com.repositories.UserRepository;

import java.util.*;
//import java.util.Map;

import Journalr.com.model.User;

@RestController
public class UserController {
    @Autowired
    UserRepository userRepository;

    /*
    Mehtod that can add/create a new user
    */
    @RequestMapping(method = RequestMethod.POST, path="/add")
    public @ResponseBody String addNewUser (@RequestParam String userName, 
        @RequestParam String firstName, @RequestParam String lastName) {
            User aUser = new User();
            aUser.setUserName(userName);
            aUser.setFirstName(firstName);
            aUser.setLastName(lastName);
            userRepository.save(aUser);
            return "Saved";
    }

    /*
    Method that can delete a user given its unique ID
    */
    @RequestMapping(method = RequestMethod.POST, path="/delete")
    public @ResponseBody String deleteUser(@RequestParam String id) {
        int UID = Integer.parseInt(id);
        userRepository.deleteById(UID);
        return "Deleted User";
    }

    /*
    Method that can update an existing user and/or create one if it doesn't exist
    */
    @RequestMapping(method = RequestMethod.POST, path="/update/{id}")
    public @ResponseBody String updateUser (@PathVariable String id, @RequestParam String userName, 
        @RequestParam String firstName, @RequestParam String lastName) {
            int UID = Integer.parseInt(id);
            Optional<User> optional = userRepository.findById(UID);                 // Need to since Optional<User> type can possibly be null
            
            if(optional.isPresent()) {
                // value is present inside Optional
                User aUser = optional.get();
                aUser.setUserName(userName);
                aUser.setFirstName(firstName);
                aUser.setLastName(lastName);
                userRepository.save(aUser);
                return "User: " + id + " updated";
            } else {
                // user does not exist, so we create one
                User aUser = new User();
                aUser.setUserName(userName);
                aUser.setFirstName(firstName);
                aUser.setLastName(lastName);
                userRepository.save(aUser);
                return "User Created";
            }	
    }

    /*
    Method to show all users in the User table
    */
    @RequestMapping(path="/all")
    public @ResponseBody Iterable<User> getAllUsers() {
      // This returns a JSON or XML with the users
      return userRepository.findAll();
    }

    /*
    Method to show a given user by their first name
    */
    @RequestMapping(path="/user/{firstName}", method = RequestMethod.GET) 
    public @ResponseBody List<User> getUser(@PathVariable("firstName") String firstName) {
        return userRepository.findByFirstNameContaining(firstName);

    }

/*
    @RequestMapping(method = RequestMethod.POST, path="/search")
    public List<User> search(@RequestBody String first) {
      return userRepository.findAll();
    }
*/
/*
    @RequestMapping(method = RequestMethod.GET, value = "/users")
    @ResponseBody
    public List<User> search(@RequestParam(value = "search") String body){
        String searchTerm = body;
        return userRepository.findByFirstName(searchTerm);
    }*/
}
