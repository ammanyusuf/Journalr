package Journalr.com.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import Journalr.com.model.Author;
//import Journalr.com.model.User;
//import java.util.List;
//import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Integer> {
    /*
    // If aPaper submission is true, return those papers that paper
    // Submission was succesful aka accepted
    public Iterable<Author> findByEnabledTrue(String aPaper);
    */

}