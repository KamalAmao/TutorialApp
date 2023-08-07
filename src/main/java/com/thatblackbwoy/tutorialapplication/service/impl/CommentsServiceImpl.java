package com.thatblackbwoy.tutorialapplication.service.impl;

import com.thatblackbwoy.tutorialapplication.dto.CommentsDto;
import com.thatblackbwoy.tutorialapplication.dto.response.ApiResponse;
import com.thatblackbwoy.tutorialapplication.model.Comments;
import com.thatblackbwoy.tutorialapplication.model.Tutorial;
import com.thatblackbwoy.tutorialapplication.repository.CommentRepository;
import com.thatblackbwoy.tutorialapplication.repository.TutorialRepository;
import com.thatblackbwoy.tutorialapplication.service.CommentsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.xml.stream.events.Comment;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentsServiceImpl implements CommentsService {
    private final CommentRepository commentRepository;
    private final TutorialRepository tutorialRepository;
    @Override
    public ApiResponse createComment(Long tutorialId, CommentsDto commentsDto) {
        Tutorial tutorial = tutorialRepository.findById(tutorialId).orElseThrow(()-> new RuntimeException());
        Comments comment = Comments.builder()
                .content(commentsDto.getContent())
                .tutorial(tutorial)
                .build();
        log.info("Comment created {}", comment);
       Comments response = commentRepository.save(comment);

        return ApiResponse.builder()
                .success(true)
                .message("New comment created")
                .data(response)
                .build();
    }

    @Override
    public ApiResponse getByTutorialId(Long tutorialId) {
        List<Comments> comments = commentRepository.findByTutorialId(tutorialId);
        log.info("All available comments retrieved successfully", comments);
        return ApiResponse.builder()
                .success(true)
                .message("All available comments retrieved successfully")
                .data(comments)
                .build();
    }

    @Override
    public ApiResponse updateCommentById(Long commentId, CommentsDto commentsDto) {
        Comments comment = commentRepository.findCommentsById(commentId);
        comment.setContent(commentsDto.getContent());
        log.info("Comment with id " +commentId+ " updated successfully", comment);
        Comments response = commentRepository.save(comment);
        return ApiResponse.builder()
                .success(true)
                .message("Your comment has been updated successfully")
                .data(response)
                .build();
    }

    @Override
    public ApiResponse deleteCommentById(Long commentId) {
        Comments comment = commentRepository.findCommentsById(commentId);
        commentRepository.deleteCommentsById(commentId);
        log.info("Comment with id " +commentId+ " has been deleted", comment);
        return ApiResponse.builder()
                .success(true)
                .message("Comment successfully deleted")
                .build();
    }
    @Override
    public ApiResponse deleteAllCommentsOfATutorialByTutorialId(Long tutorialId) {
        //Comments comment = tutorialRepository.findById(tutorialId);
        List<Comments> comments = commentRepository.findAll();
        commentRepository.deleteAllCommentsByTutorialId(tutorialId);
        log.info("All comments under tutorial with id " +tutorialId+ " deleted", comments);
        return ApiResponse.builder()
                .success(true)
                .message("All comments under tutorial with id " +tutorialId+ " deleted")
                .build();
    }
}
