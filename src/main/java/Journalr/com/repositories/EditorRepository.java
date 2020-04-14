package Journalr.com.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import Journalr.com.model.Editor;

@Repository
public interface EditorRepository extends JpaRepository<Editor, Integer> {
    /**
     * This is the editor repository
     * It inherited methods from the JpaRepository for basic query
     * retrieval in the database
     */
}