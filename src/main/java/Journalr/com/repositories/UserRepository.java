package Journalr.com.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.Query;

import org.springframework.stereotype.Repository;

import Journalr.com.model.User;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    
    /**
     * This is the user repository
     * It inherited methods from the JpaRepository for basic query
     * retrieval in the database
     */

    /**
     * This method retrieves all the users containing firstName in their first name
     * @param firstName the firstname of the user
     * @return The list of users that contain the firstname in their first name
     */
    List<User> findByFirstNameContaining(String firstName);

    /**
     * This method retrieves all the users containing lastname in their last name
     * @param firstName the lastname of the user
     * @return The list of users that contain the lastname in their last name
     */
    List<User> findByLastNameContaining(String lastName);

    /**
     * Find a unique user based on the userName
     * @param userName The user name of the user
     * @return An optional object of the user class that has the userName as its userName
     */
    Optional<User> findByUserName(String userName);                     // checks the database for the user given the username

    /**
     * This method finds the users of a given roleType
     * @param roleType The role type that we want to find the users for
     * @return The list of users with a given role type
     */
    List<User> findByRolesContaining(String roleType);

}