package Journalr.com.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import Journalr.com.model.Paper;
import java.util.List;

@Repository
public interface PaperRepository extends JpaRepository<Paper, Integer> {
    @Query(value = "SELECT * FROM paper WHERE paper.author_id = ?1", 
            nativeQuery = true)
    List<Paper> findPapersByAuthorId(int author_id);
    
}