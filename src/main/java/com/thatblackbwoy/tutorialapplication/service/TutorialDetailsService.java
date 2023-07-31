package com.thatblackbwoy.tutorialapplication.service;

import com.thatblackbwoy.tutorialapplication.dto.TutorialDetailsDto;
import com.thatblackbwoy.tutorialapplication.dto.response.ApiResponse;

public interface TutorialDetailsService {
    ApiResponse createTutorialDetails(long tutorialId, TutorialDetailsDto tutorialDetailsDto);
    ApiResponse updateTutorialDetails(long tutorialId, TutorialDetailsDto tutorialDetailsDto);
    ApiResponse getAllTutorialDetails();
    ApiResponse searchTutorialDetailsContainingAuthorsLike(String createdBy);
    ApiResponse deleteTutorialDetailsById(Long tutorialId);

}
