package Journalr.com.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import Journalr.com.model.Reviewer;
import Journalr.com.model.User;

import java.util.List;

import javax.transaction.Transactional;

@Transactional
@Repository
public interface ReviewerRepository extends JpaRepository<Reviewer, Integer> {
    /**
     * This is the reviewer repository
     * It inherited methods from the JpaRepository for basic query
     * retrieval in the database
     */
    /**
     * This method finds all of the approved reviewers that have also accepted the paper
     * @param paper_id The paper id of the paper we are querying
     * @return The list of reviewers that have accepted that paper
     */
    @Query(value = "SELECT * FROM (reviewer AS r JOIN user AS u ON user_ID=reviewer_ID) WHERE r.reviewer_ID IN (SELECT rp.reviewer_ID FROM review_paper AS rp WHERE rp.paper_ID=?1 AND accept=1)",
        nativeQuery = true)
    List<Reviewer> findApprovedReviewersAcceptPaper(int paper_id);

    /**
     * This method finds all of the approved reviewers that have also rejected the paper
     * @param paper_id The paper id of the paper we are querying
     * @return The list of reviewers that have rejected that paper
     */
    @Query(value = "SELECT * FROM (reviewer AS r JOIN user AS u ON user_ID=reviewer_ID) WHERE r.reviewer_ID IN (SELECT rp.reviewer_ID FROM review_paper AS rp WHERE rp.paper_ID=?1 AND reject=1)",
        nativeQuery = true)
    List<Reviewer> findApprovedReviewersRejectPaper(int paper_id);

    /**
     * This method finds all of the approved reviewers that have not decided yet
     * @param paper_id The paper id of the paper we are querying
     * @return The list of reviewers that not decided yet
     */
    @Query(value = "SELECT * FROM (reviewer AS r JOIN user AS u ON user_ID=reviewer_ID) WHERE r.reviewer_ID IN (SELECT rp.reviewer_ID FROM review_paper AS rp WHERE rp.paper_ID=?1 AND accept=0 AND reject=0)",
        nativeQuery = true)
    List<Reviewer> findApprovedReviewersUnDecided(int paper_id);
}