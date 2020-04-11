package Journalr.com.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Modifying;

import org.springframework.stereotype.Repository;

import Journalr.com.model.Paper;
import java.util.List;

import javax.transaction.Transactional;

@Repository
public interface PaperRepository extends JpaRepository<Paper, Integer> {
    @Query(value = "SELECT * FROM paper WHERE paper.author_id = ?1", 
            nativeQuery = true)
    List<Paper> findPapersByAuthorId(int author_id);
    
    @Query(value = "SELECT * FROM paper WHERE paper.reviewer_id = ?1", 
            nativeQuery = true)
    List<Paper> findPapersByReviewerId(int reviewer_id);

    @Query(value = "SELECT reviewer_id FROM review_paper,paper WHERE review_paper.paper_id = ?1 AND review_paper.paper_id=paper.paperid",
            nativeQuery = true)
    List<Integer> findReviewersPerPaper(int paper_id);

    @Query(value = "SELECT DISTINCT reviewer_id FROM review_paper,paper WHERE review_paper.paper_id = ?1 AND review_paper.able_to_review=1",
            nativeQuery = true)
    List<Integer> findReviewersAbleToReview(int paper_id);

    @Query(value = "SELECT DISTINCT reviewer_id FROM review_paper,paper WHERE review_paper.paper_id = ?1 AND review_paper.able_to_review=0",
            nativeQuery = true)
    List<Integer> findReviewersNotAbleToReview(int paper_id);

    @Transactional
    @Modifying
    @Query(value = "UPDATE review_paper SET able_to_review=?1 where paper_ID = ?2 AND reviewer_ID = ?3",
            nativeQuery = true)
    void updateAbleToReview(int true_or_false, int paper_id, int reviewer_id);

    @Transactional
    @Modifying
    @Query(value = "UPDATE review_paper SET major_rev=?1 where paper_ID = ?2 AND reviewer_ID = ?3",
            nativeQuery = true)
    void updateMajorRev(int true_or_false, int paper_id, int reviewer_id);

    @Transactional
    @Modifying
    @Query(value = "UPDATE review_paper SET minor_rev=?1 where paper_ID = ?2 AND reviewer_ID = ?3",
            nativeQuery = true)
    void updateMinorRev(int true_or_false, int paper_id, int reviewer_id);

    @Transactional
    @Modifying
    @Query(value = "UPDATE review_paper SET accept=?1 where paper_ID = ?2 AND reviewer_ID = ?3",
            nativeQuery = true)
    void updateAccept(int true_or_false, int paper_id, int reviewer_id);

    
    @Query(value = "SELECT * FROM paper WHERE paper.topic = ?1",
            nativeQuery = true)
        List<Paper> findPapersByTopic(String topic);


    @Query(value = "SELECT * FROM paper AS p1 WHERE p1.paper_ID NOT IN (SELECT pr.paper_ID FROM review_paper AS pr WHERE pr.reviewer_ID = ?1)",
           nativeQuery = true)
        List<Paper> findPapersNotSelectedByReviewerId(int reviewer_id);

    @Query(value = "SELECT * FROM paper AS p1 WHERE p1.paper_ID IN (SELECT pr.paper_ID FROM review_paper AS pr WHERE pr.reviewer_ID=?1 AND able_to_review = 0)", 
        nativeQuery = true)
        List<Paper> findPendingPapersOfReviewer(int reviewer_id);

    @Query(value = "SELECT * FROM paper AS p1 WHERE p1.paper_ID IN (SELECT pr.paper_ID FROM review_paper AS pr WHERE pr.reviewer_ID=?1 AND able_to_review = 1)", 
        nativeQuery = true)
        List<Paper> findApprovedPapersOfReviewer(int reviewer_id);
}