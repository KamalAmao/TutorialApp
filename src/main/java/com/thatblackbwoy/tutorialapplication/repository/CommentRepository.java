package com.thatblackbwoy.tutorialapplication.repository;

import com.thatblackbwoy.tutorialapplication.model.Comments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comments, Long> {
    List<Comments> findByTutorialId(long tutorialId);
//    Comments updateCommentsByContent(Comments content);
    Comments findCommentsById(Long commentId);
    @Transactional
    void deleteById(Long id);
    @Transactional
    void deleteCommentsById(Long commentId);
    @Transactional
    void deleteAllCommentsByTutorialId(Long tutorialId);

//    void
}
