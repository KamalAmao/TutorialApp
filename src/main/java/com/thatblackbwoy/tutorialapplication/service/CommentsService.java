package com.thatblackbwoy.tutorialapplication.service;

import com.thatblackbwoy.tutorialapplication.dto.CommentsDto;
import com.thatblackbwoy.tutorialapplication.dto.response.ApiResponse;

public interface CommentsService {
    ApiResponse createComment(Long tutorialId, CommentsDto commentsDto);
    ApiResponse getByTutorialId(Long tutorialId);
    ApiResponse updateCommentById(Long commentId, CommentsDto commentsDto);
    ApiResponse deleteCommentById(Long commentId);
    ApiResponse deleteAllCommentsOfATutorialByTutorialId(Long tutorialId);
    ApiResponse deleteTutorialAndItsComments(Long tutorialId);
}
