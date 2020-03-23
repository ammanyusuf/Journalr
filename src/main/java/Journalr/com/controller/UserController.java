package Journalr.com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import Journalr.com.repositories.UserRepository;

import java.util.List;
//import java.util.Map;

import Journalr.com.model.User;

@RestController
public class UserController {
    @Autowired
    UserRepository userRepository;


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

    @RequestMapping(method = RequestMethod.POST, path="/delete")
    public @ResponseBody String deleteUser(@RequestParam String id) {
        int UID = Integer.parseInt(id);
        userRepository.deleteById(UID);
        return "Deleted User";
    }

    @RequestMapping(path="/all")
    public @ResponseBody Iterable<User> getAllUsers() {
      // This returns a JSON or XML with the users
      return userRepository.findAll();
    }

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
