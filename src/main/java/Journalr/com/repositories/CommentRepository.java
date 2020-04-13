package Journalr.com.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

import Journalr.com.model.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {
    
    List<Comment> findByCommentId(int commentId);


    @Query(value = "SELECT * FROM comment WHERE paper_ID=?1",
            nativeQuery = true)
    List<Comment> findCommentsPerPaper(int paperid);

    @Query(value = "SELECT * FROM comment WHERE paper_ID=?1 AND topic='major_rev'", 
            nativeQuery = true)
    List<Comment> findMajorRevCommentsPerPaper(int paperId);

    @Query(value = "SELECT * FROM comment WHERE paper_ID=?1 AND topic='minor_rev'", 
            nativeQuery = true)
    List<Comment> findMinorRevCommentsPerPaper(int paperId);

    @Query(value = "SELECT * FROM comment WHERE paper_ID=?1 AND topic='general'", 
            nativeQuery = true)
    List<Comment> findGeneralCommentsPerPaper(int paperId);

}
