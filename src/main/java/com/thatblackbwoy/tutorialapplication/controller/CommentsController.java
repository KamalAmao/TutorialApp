package com.thatblackbwoy.tutorialapplication.controller;

import com.thatblackbwoy.tutorialapplication.dto.CommentsDto;
import com.thatblackbwoy.tutorialapplication.dto.response.ApiResponse;
import com.thatblackbwoy.tutorialapplication.service.CommentsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CommentsController {
    private final CommentsService commentsService;

    @PostMapping("/tutorials/{tutorialId}/comment")
    public ResponseEntity<ApiResponse> createComment(@PathVariable Long tutorialId, @RequestBody CommentsDto commentsDto){
        try{
            return ResponseEntity.ok().body(commentsService.createComment(tutorialId, commentsDto));
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.builder().success(false).build());
        }
    }

    @GetMapping("/tutorial/{tutorialId}/comment")
    public ResponseEntity<ApiResponse> getCommentByTutorialId(@PathVariable Long tutorialId){
        try{
            return ResponseEntity.ok().body(commentsService.getByTutorialId(tutorialId));
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(com.thatblackbwoy.tutorialapplication.dto.response.ApiResponse.builder().success(false).build());
        }
    }
    @PutMapping("/comments/{commentId}")
    public ResponseEntity<ApiResponse> updateTutorialCommentsByCommentId(@PathVariable Long commentId, @RequestBody CommentsDto commentsDto){
        try{
            return ResponseEntity.ok().body(commentsService.updateCommentById(commentId, commentsDto));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.builder().success(false).build());
        }
    }
    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<ApiResponse> deleteTutorialCommentByCommentId(@PathVariable Long commentId){
        try{
            return ResponseEntity.ok().body(commentsService.deleteCommentById(commentId));
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.builder().success(false).build());
        }
    }
    @DeleteMapping("/tutorials/{tutorialId}/comments")
    public ResponseEntity<ApiResponse> deleteAllCommentsOfATutorial(@PathVariable Long tutorialId){
        try{
            return ResponseEntity.ok().body(commentsService.deleteAllCommentsOfATutorialByTutorialId(tutorialId));
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.builder().success(false).build());
        }
    }
}
