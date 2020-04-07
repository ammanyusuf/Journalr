package Journalr.com.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import Journalr.com.model.User;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    
    List<User> findByFirstNameContaining(String firstName);
    List<User> findByLastNameContaining(String lastName);

    Optional<User> findByUserName(String userName);                     // checks the database for the user given the username

}