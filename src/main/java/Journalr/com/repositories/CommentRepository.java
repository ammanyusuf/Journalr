package Journalr.com.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

import Journalr.com.model.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {
    /**
     * This is the comment repository
     * It inherited methods from the JpaRepository for basic query
     * retrieval in the database
     */

    /**
     * This method finds a unique comment by the comment id
     * @param commentId the unique comment id that corresponds to the comment retrieved
     * @return A list of comments that have the unique comment id
     */
    List<Comment> findByCommentId(int commentId);

    /**
     * This method retrieves all of the comments about a given paper
     * @param paperid The paper id of the paper that we want to retrieve all of the comments for
     * @return The list of comments that were written for the paper
     */
    @Query(value = "SELECT * FROM comment WHERE paper_ID=?1",
            nativeQuery = true)
    List<Comment> findCommentsPerPaper(int paperid);

    /**
     * This method retrieves all of the comments written for a major revision for a given paper
     * @param paperId The paper id of the paper that we want to retrieve all major revision comments for
     * @return The list of major revision comments that were written for the paper
     */
    @Query(value = "SELECT * FROM comment WHERE paper_ID=?1 AND topic='major_rev'", 
            nativeQuery = true)
    List<Comment> findMajorRevCommentsPerPaper(int paperId);

    /**
     * This method retrieves all of the comments written for a minor revision for a given paper
     * @param paperId The paper id of the paper that we want to retrieve all minor revision comments for
     * @return The list of minor revision comments that were written for the paper
     */
    @Query(value = "SELECT * FROM comment WHERE paper_ID=?1 AND topic='minor_rev'", 
            nativeQuery = true)
    List<Comment> findMinorRevCommentsPerPaper(int paperId);

    /**
     * This method retrieves all of the comments written for a general comment for a given paper
     * @param paperId The paper id of the paper that we want to retireve all general comments for
     * @return The list of general comments that were written for the paper
     */
    @Query(value = "SELECT * FROM comment WHERE paper_ID=?1 AND topic='general'", 
            nativeQuery = true)
    List<Comment> findGeneralCommentsPerPaper(int paperId);

    /**
     * This method retrieves all of the comments written by a reviewr for a paper that were
     * for major revisions
     * @param paperId The paper id of the paper that we want to retrieve the comments for
     * @param reviewerId The reviewer id of the reviewr that we want to retrieve the comments
     *                   written by them
     * @return The list of major revision comments that were written by the reviewer for a paper
     */
    @Query(value = "SELECt * FROM comment WHERE paper_ID=?1 AND reviewer_ID=?2 AND topic='major_rev'",
             nativeQuery = true)
    List<Comment> findMajorRevCommentsPerPaperPerReviewer(int paperId, int reviewerId);

    /**
     * This method retrieves all of the comments written by a reviewr for a paper that were
     * for minor revisions
     * @param paperId The paper id of the paper that we want to retrieve the comments for
     * @param reviewerId The reviewer id of the reviewr that we want to retrieve the comments
     *                   written by them
     * @return The list of minor revision comments that were written by the reviewer for a paper
     */
    @Query(value = "SELECt * FROM comment WHERE paper_ID=?1 AND reviewer_ID=?2 AND topic='minor_rev'",
             nativeQuery = true)
    List<Comment> findMinorRevCommentsPerPaperPerReviewer(int paperId, int reviewerId);

    /**
     * This method retrieves all of the comments written by a reviewr for a paper that were
     * for general comments
     * @param paperId The paper id of the paper that we want to retrieve the comments for
     * @param reviewerId The reviewer id of the reviewr that we want to retrieve the comments
     *                   written by them
     * @return The list of general comments that were written by the reviewer for a paper
     */
    @Query(value = "SELECt * FROM comment WHERE paper_ID=?1 AND reviewer_ID=?2 AND topic='general'",
             nativeQuery = true)
    List<Comment> findGeneralCommentsPerPaperPerReviewer(int paperId, int reviewerId);

}
