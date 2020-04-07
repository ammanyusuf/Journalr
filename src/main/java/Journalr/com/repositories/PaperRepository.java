package Journalr.com.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Modifying;

import org.springframework.stereotype.Repository;

import Journalr.com.model.Paper;
import java.util.List;

import javax.transaction.Transactional;

@Repository
public interface PaperRepository extends JpaRepository<Paper, String> {
    @Query(value = "SELECT * FROM paper WHERE paper.author_id = ?1", 
            nativeQuery = true)
    List<Paper> findPapersByAuthorId(int author_id);
    
    @Query(value = "SELECT * FROM paper WHERE paper.reviewer_id = ?1", 
            nativeQuery = true)
    List<Paper> findPapersByReviewerId(int reviewer_id);

    @Query(value = "SELECT reviewer_id FROM review_paper,paper WHERE review_paper.paper_id = ?1 AND review_paper.paper_id=paper.paperid",
            nativeQuery = true)
    List<Integer> findReviewersPerPaper(String paper_id);

    @Query(value = "SELECT DISTINCT reviewer_id FROM review_paper,paper WHERE review_paper.paper_id = ?1 AND review_paper.able_to_review=1",
            nativeQuery = true)
    List<Integer> findReviewersAbleToReview(String paper_id);

    @Query(value = "SELECT DISTINCT reviewer_id FROM review_paper,paper WHERE review_paper.paper_id = ?1 AND review_paper.able_to_review=0",
            nativeQuery = true)
    List<Integer> findReviewersNotAbleToReview(String paper_id);

    @Transactional
    @Modifying
    @Query(value = "UPDATE review_paper SET able_to_review=1 WHERE paper_id = ?1 AND reviewer_id = ?2",
            nativeQuery = true)
    void setAbleToReview(String paper_id, int reviewer_id);

    @Transactional
    @Modifying
    @Query(value = "UPDATE review_paper SET able_to_review=0 WHERE paper_id = ?1 AND reviewer_id = ?2",
            nativeQuery = true)
    void setNotAbleToReview(String paper_id, int reviewer_id);
    
    @Query(value = "SELECT * FROM paper WHERE paper.topic = ?1",
            nativeQuery = true)
        List<Paper> findPapersByTopic(String topic);
}