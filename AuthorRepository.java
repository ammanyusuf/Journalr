package Journalr.com.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import Journalr.com.model.Author;
import Journalr.com.model.User;
import java.util.List;

@Repository
public interface AuthorRepository extends JpaRepository<User, Integer> {
    
    List<User> findByFirstNameContaining(String firstName);

    // If aPaper submission is true, return those papers that paper
    // Submission was succesful aka accepted
    public Iterable<Author> findByEnabledTrue(String aPaper);

}