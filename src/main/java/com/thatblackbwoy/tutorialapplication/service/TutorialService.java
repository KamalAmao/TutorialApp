package com.thatblackbwoy.tutorialapplication.service;

import com.thatblackbwoy.tutorialapplication.dto.TutorialDetailsDto;
import com.thatblackbwoy.tutorialapplication.dto.TutorialDto;
import com.thatblackbwoy.tutorialapplication.dto.response.ApiResponse;


import java.util.List;

public interface TutorialService {
    ApiResponse createTutorial(TutorialDto tutorialDto);
    ApiResponse getAllTutorial();
    ApiResponse getById(Long id);
    ApiResponse updateTutorial(Long id, TutorialDto tutorialDto);
//    ApiResponse removeTutorial(long id);
    ApiResponse searchTutorialsContainingTitleLike(String title);
//    List<Tutorial> searchTutorials(String title);
    ApiResponse getAllPublished();
    ApiResponse deleteTutorialById(Long id);
    ApiResponse deleteTutorialAndAllItsCommentsAndDetailsById(Long tutorialId);
//    ApiResponse deleteAll(Tutorial tutorial);
//    ApiResponse deleteAllTutorials();

//    ApiResponse deleteTutorialById(long id);
}
