package Journalr.com.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import Journalr.com.model.Author;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Integer> {
    /**
     * This is the author repository
     * It inherited methods from the JpaRepository for basic query
     * retrieval in the database
     */

}