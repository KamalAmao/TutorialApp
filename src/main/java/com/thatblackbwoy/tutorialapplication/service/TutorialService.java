package com.thatblackbwoy.tutorialapplication.service;

import com.thatblackbwoy.tutorialapplication.dto.TutorialDto;
import com.thatblackbwoy.tutorialapplication.dto.response.ApiResponse;
import com.thatblackbwoy.tutorialapplication.model.Tutorial;

import java.util.List;

public interface TutorialService {
    ApiResponse createTutorial(TutorialDto tutorialDto);
    ApiResponse getAllTutorial();
    ApiResponse getById(long id);
    ApiResponse updateTutorial(long id, TutorialDto tutorialDto);
//    ApiResponse removeTutorial(long id);
    ApiResponse searchTutorialsContainingTitleLike(String title);
//    List<Tutorial> searchTutorials(String title);
    ApiResponse getAllPublished();
    ApiResponse deleteTutorial(long id);
//    ApiResponse deleteAll(Tutorial tutorial);
//    ApiResponse deleteAllTutorials();

//    ApiResponse deleteTutorialById(long id);
}
