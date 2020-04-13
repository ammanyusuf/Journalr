package Journalr.com.repositories;

import org.hibernate.query.NativeQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Modifying;

import org.springframework.stereotype.Repository;

import Journalr.com.model.Paper;
import java.util.List;

import javax.transaction.Transactional;

@Repository
public interface PaperRepository extends JpaRepository<Paper, Integer> {
        /**
         * This is the paper repository
         * It inherited methods from the JpaRepository for basic query
         * retrieval in the database
         */

        /* @Query(value = "SELECT * FROM paper WHERE paper.author_id = ?1", 
            nativeQuery = true)
    List<Paper> findPapersByAuthorId(int author_id);
    
    @Query(value = "SELECT * FROM paper WHERE paper.reviewer_id = ?1", 
            nativeQuery = true)
    List<Paper> findPapersByReviewerId(int reviewer_id);
*/
    /**
     * This method will retrieve the list of reivewer ids that have selected the paper to review
     * It will list out all of the reviewer id that have selected a given paper with the passed
     * through paper id
     * @param paper_id The paper id of the paper that we want to find all of the reviewer
     *                 for. 
     * @return The list of reviewer ids that selected to that paper
     */
    @Query(value = "SELECT reviewer_id FROM review_paper,paper WHERE review_paper.paper_id = ?1 AND review_paper.paper_id=paper.paperid",
            nativeQuery = true)
    List<Integer> findReviewersPerPaper(int paper_id);

    
    /**
     * This method will retrieve all of the reviewer ids that are able to review the given paper
     * with an id of paper_id.  Reviewers are able to review if an editor has selected them to review
     * @param paper_id the paper_id of the paper that we want to retrieve the able to review reviewers
     * @return The list of reviewer_ids that are able to review the paper
     */
    @Query(value = "SELECT DISTINCT reviewer_id FROM review_paper,paper WHERE review_paper.paper_id = ?1 AND review_paper.able_to_review=1",
            nativeQuery = true)
    List<Integer> findReviewersAbleToReview(int paper_id);

    /**
     * This method will retrieve all of the reviewer ids that are NOT able to review the given paper
     * with an id of paper_id.  Reviewers are NOT able to review if an editor has NOT selected them to review
     * @param paper_id the paper_id of the paper that we want to retrieve the not able to review reviewers
     * @return The list of reviewer_ids that are not able to review the paper
     */
    @Query(value = "SELECT DISTINCT reviewer_id FROM review_paper,paper WHERE review_paper.paper_id = ?1 AND review_paper.able_to_review=0",
            nativeQuery = true)
    List<Integer> findReviewersNotAbleToReview(int paper_id);

    /**
     * This method will update the able to review column in the review_paper table
     * @param true_or_false To set the column to true (1) or false (0)
     * @param paper_id The paper id that we want to set the column to true or false
     * @param reviewer_id The reviewerdi that we want to set the column to true or false
     */
    @Transactional
    @Modifying
    @Query(value = "UPDATE review_paper SET able_to_review=?1 where paper_ID = ?2 AND reviewer_ID = ?3",
            nativeQuery = true)
    void updateAbleToReview(int true_or_false, int paper_id, int reviewer_id);

    /**
     * This method will update the Major Revision column in the review_paper table
     * @param true_or_false To set the column to true (1) or false (0)
     * @param paper_id The paper id that we want to set the column to true or false
     * @param reviewer_id The reviewerdi that we want to set the column to true or false
     */
    @Transactional
    @Modifying
    @Query(value = "UPDATE review_paper SET major_rev=?1 where paper_ID = ?2 AND reviewer_ID = ?3",
            nativeQuery = true)
    void updateMajorRev(int true_or_false, int paper_id, int reviewer_id);

    /**
     * This method will update the Minor Revision column in the review_paper table
     * @param true_or_false To set the column to true (1) or false (0)
     * @param paper_id The paper id that we want to set the column to true or false
     * @param reviewer_id The reviewerdi that we want to set the column to true or false
     */
    @Transactional
    @Modifying
    @Query(value = "UPDATE review_paper SET minor_rev=?1 where paper_ID = ?2 AND reviewer_ID = ?3",
            nativeQuery = true)
    void updateMinorRev(int true_or_false, int paper_id, int reviewer_id);

    /**
     * This method will update the accept column in the review_paper table
     * @param true_or_false To set the column to true (1) or false (0)
     * @param paper_id The paper id that we want to set the column to true or false
     * @param reviewer_id The reviewerdi that we want to set the column to true or false
     */
    @Transactional
    @Modifying
    @Query(value = "UPDATE review_paper SET accept=?1 where paper_ID = ?2 AND reviewer_ID = ?3",
            nativeQuery = true)
    void updateAccept(int true_or_false, int paper_id, int reviewer_id);

    /**
     * This method will update the reject column in the review_paper table
     * @param true_or_false To set the column to true (1) or false (0)
     * @param paper_id The paper id that we want to set the column to true or false
     * @param reviewer_id The reviewerdi that we want to set the column to true or false
     */
    @Transactional
    @Modifying
    @Query(value = "UPDATE review_paper SET reject=?1 where paper_ID = ?2 AND reviewer_ID = ?3",
            nativeQuery = true)
    void updateReject(int true_or_false, int paper_id, int reviewer_id);

    /**
     * This method will find all of the papers with a certain topic
     * @param topic The topic that we want to search the papers for
     * @return A list of papers with the topic 'topic'
     */
    @Query(value = "SELECT * FROM paper WHERE paper.topic = ?1",
            nativeQuery = true)
        List<Paper> findPapersByTopic(String topic);


    /**
     * This method will retireve a list of papers that have not been selected by the reviewer_id
     * that has been passed in
     * @param reviewer_id The reviewer id of the reviewer that we want to find the non-selected
     *                    papers of
     * @return Return the list of papers that have not been selected by the reviewer
     */
    @Query(value = "SELECT * FROM paper AS p1 WHERE p1.paper_ID NOT IN (SELECT pr.paper_ID FROM review_paper AS pr WHERE pr.reviewer_ID = ?1)",
           nativeQuery = true)
        List<Paper> findPapersNotSelectedByReviewerId(int reviewer_id);

    /**
     * This method will retireve a list of papers of the reviewer_id that are pending approval
     * to review from the editor.
     * @param reviewer_id The reviewer id of the reviewer that we want to find the pending
     *                    papers of
     * @return Return the list of papers that pending approval to review
     */
    @Query(value = "SELECT * FROM paper AS p1 WHERE p1.paper_ID IN (SELECT pr.paper_ID FROM review_paper AS pr WHERE pr.reviewer_ID=?1 AND able_to_review = 0)", 
        nativeQuery = true)
        List<Paper> findPendingPapersOfReviewer(int reviewer_id);

    /**
     * This method will retireve a list of papers that are approved to be reviewed by the 
     * reviewer_id that was passed through
     * @param reviewer_id The reviewer id of the reviewer that we want to find the approved
     *                    to review papers of
     * @return Return the list of papers that the reviewer is approved to review
     */
    @Query(value = "SELECT * FROM paper AS p1 WHERE p1.paper_ID IN (SELECT pr.paper_ID FROM review_paper AS pr WHERE pr.reviewer_ID=?1 AND able_to_review = 1 AND accept = 0 AND reject = 0)", 
        nativeQuery = true)
        List<Paper> findApprovedPapersOfReviewer(int reviewer_id);

    /**
     * This method will retrieve a list of papers that have the potential to be accepted or rejected
     * by the reviewer.  This status is achieved when a paper has recieved at least 1 Major Revision
     * and at least 1 Minor Revision
     * @param reviewer_id the reviewer id of the reviewer that we want to find papers to potentially accept
     *                    from.
     * @return The list of papers that have the potential to be accepted/rejected by the reviewer
     */
    @Query(value = "SELECT * FROM paper AS p1 WHERE p1.paper_ID IN (SELECT pr.paper_ID FROM review_paper AS pr WHERE pr.reviewer_ID=?1 AND major_rev=1 AND minor_rev=1 AND accept=0 AND reject=0)",
        nativeQuery = true)
        List<Paper> findPotentialAcceptedPapers(int reviewer_id);

    /**
     * This method will retrieve the papers accepted by the reviewer with the given reivewer_id
     * @param reviewer_id The reviewer id of the reviewer that we want to retrieve the accepted papers from
     * @return The list of papers that have been accepted by the reviewer
     */
    @Query(value = "SELECT * FROM paper AS p1 WHERE p1.paper_ID IN (SELECT pr.paper_ID FROM review_paper AS pr WHERE pr.reviewer_ID=?1 AND accept=1)",
        nativeQuery = true)
        List<Paper> findAcceptedPapersByReviewer(int reviewer_id);

    /**
     * This method will retrieve the papers rejected by the reviewer with the given reivewer_id
     * @param reviewer_id The reviewer id of the reviewer that we want to retrieve the rejected papers from
     * @return The list of papers that have been rejected by the reviewer
     */
    @Query(value = "SELECT * FROM paper AS p1 WHERE p1.paper_ID IN (SELECT pr.paper_ID FROM review_paper AS pr WHERE pr.reviewer_ID=?1 AND reject=1)",
        nativeQuery = true)
        List<Paper> findRejectedPapersByReviewer(int reviewer_id);

    // User case 15
    /**
     * This method retrieves the papers that have been approved into the system by the editor.
     * These papers are unique to the author that is passed through
     * @param author_id The author id of the author that we want to find approved papers of
     * @return The list of papers written by the author that have been approved into the system
     */
    @Query(value = "SELECT * FROM paper as p WHERE p.approved = 1 and p.author_id = ?1", 
        nativeQuery = true)
        List<Paper> findApprovedPapersForAuthors(int author_id);

    // User case 4: As an author, I should receive a notification when my work has been reviewed, so I can go find my reviewed file.
    /**
     * This method will retreive the papers that are being reviewed by a reviewer for a given
     * author
     * @param author_id The author id of the author that we want to retrieve the papers under review for
     * @return The list of papers witten by an author that are currently under review by a reviewer
     */
    @Query(value = "SELECT DISTINCT * FROM paper as p1 WHERE p1.paper_ID IN (SELECT p2.paper_ID FROM paper as p2, review_paper as rp WHERE able_to_review = 1 and (rp.major_rev = 1 or rp.minor_rev = 1) and rp.paper_ID = p2.paper_ID and p2.author_ID = ?1)", 
        nativeQuery = true)
        List<Paper> findReviewedPapersPerAuthors(int author_id);

    /**
     * This method will retrieve the number of apporved reviewers for a given paper
     * @param paper_id The paper id of the paper that we want to find the number of approved papers for
     * @return The number of approved reviewrs for that paper
     */
    @Query(value = "SELECT count(reviewer_ID) FROM review_paper WHERE paper_ID=?1 and able_to_review=1 GROUP BY paper_ID",
        nativeQuery = true)
        Integer findNumberOfApprovedReviewersPerPaper(int paper_id);
}