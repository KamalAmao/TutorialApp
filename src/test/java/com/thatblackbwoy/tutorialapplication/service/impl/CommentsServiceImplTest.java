package com.thatblackbwoy.tutorialapplication.service.impl;

import com.thatblackbwoy.tutorialapplication.dto.CommentsDto;
import com.thatblackbwoy.tutorialapplication.dto.TutorialDto;
import com.thatblackbwoy.tutorialapplication.dto.response.ApiResponse;
import com.thatblackbwoy.tutorialapplication.model.Comments;
import com.thatblackbwoy.tutorialapplication.model.Tutorial;
import com.thatblackbwoy.tutorialapplication.repository.CommentRepository;
import com.thatblackbwoy.tutorialapplication.repository.TutorialRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringBootTest
class CommentsServiceImplTest {
    @MockBean
    private CommentRepository commentRepository;

    @MockBean
    private TutorialRepository tutorialRepository;

    @Autowired
    private CommentsServiceImpl commentsService;

    @Test
    void createComment() {
        Tutorial tutorial = Tutorial.builder().id(1L).title("Title A").description("Description A").published(true).build();
        Comments comments = Comments.builder().id(1L).content("Comment 1").tutorial(tutorial).build();
        Comments expectedResponse = Comments.builder().id(1L).content("Comment 1").tutorial(tutorial).build();

        ApiResponse expectedApiResponse = ApiResponse.builder()
                .success(true)
                .message("New comment created")
                .data(expectedResponse)
                .build();
        when(tutorialRepository.findById(1L)).thenReturn(Optional.ofNullable(tutorial));
        when(commentRepository.save(comments)).thenReturn(expectedResponse);
        ApiResponse actualResponse = commentsService.createComment(1L, new CommentsDto("Comment 1"));
        assertEquals(expectedApiResponse, actualResponse);
    }

    @Test
    void getByTutorialId() {
        List<Comments> expectedResponse = Stream.of(
                new Comments(1L, "Comment 1", new Tutorial(1L,"Title A", "Description A", true)),
                new Comments(2L, "Comment 2", new Tutorial(2L,"Title B", "Description B", true))
        ).collect(Collectors.toList());

        ApiResponse expextedApiResponse = ApiResponse.builder()
                .success(true)
                .message("All available comments retrieved successfully")
                .data(expectedResponse)
                .build();

        when(commentRepository.findByTutorialId(1)).thenReturn(expectedResponse);
        ApiResponse actualResponse = commentsService.getByTutorialId(1L);
        assertEquals(expextedApiResponse, actualResponse);
    }

    @Test
    void updateCommentById() {
//        Tutorial tutorial = Tutorial.builder().id(1L).title("Title A").description("Description A").build();
        Comments comment = Comments.builder().id(1L).content("Content A").build();
        comment.setContent("Content B");
        Comments expectedResponse = Comments.builder().id(1L).content("Content B").build();

        ApiResponse expectedApiResponse = ApiResponse.builder()
                .success(true)
                .message("Your comment has been updated successfully")
                .data(expectedResponse)
                .build();
        when(commentRepository.findCommentsById(1L)).thenReturn(comment);
        when(commentRepository.save(comment)).thenReturn(expectedResponse);
        ApiResponse actualResponse = commentsService.updateCommentById(1L, new CommentsDto("Content B"));
        assertEquals(expectedApiResponse, actualResponse);

    }

    @Test
    void deleteCommentById() {
        Comments comments = Comments.builder().content("Content A").build();
        comments.setId(1L);

        ApiResponse expectedApiResponse = ApiResponse.builder()
                .success(true)
                .message("Comment successfully deleted")
                .build();
        when(commentRepository.findById(1L)).thenReturn(Optional.of(comments));
        //doNothing().when(commentRepository).delete(comments);
        ApiResponse actualResponse = commentsService.deleteCommentById(1L);
        assertEquals(expectedApiResponse, actualResponse);
    }

    @Test
    void deleteAllCommentsOfATutorialByTutorialId() {
        Tutorial tutorial = Tutorial.builder().title("Title A").description("Description A").published(true).build();
        List<Comments> comments = Stream.of(
                new Comments(1L, "Content A", new Tutorial()),
                new Comments(1L, "Content B", new Tutorial())
        ).collect(Collectors.toList());
        tutorial.setId(1L);

        ApiResponse expectdApiResponse = ApiResponse.builder()
                .success(true)
                .message("Record deleted")
                .build();

       // when(commentRepository.deleteAllCommentsByTutorialId(1L);).thenReturn(Optional.of(tutorial));
        when(commentRepository.findAll()).thenReturn(comments);
        //when(commentRepository.findAll()).thenReturn((List<Comments>) comments);
//        doNothing().when(commentRepository).delete( comments);
        ApiResponse actualResponse = commentsService.deleteAllCommentsOfATutorialByTutorialId(1L);
        assertEquals(expectdApiResponse, actualResponse);

    }
}