package com.thatblackbwoy.tutorialapplication.service.impl;

import com.thatblackbwoy.tutorialapplication.dto.TutorialDetailsDto;
import com.thatblackbwoy.tutorialapplication.dto.TutorialDto;
import com.thatblackbwoy.tutorialapplication.dto.response.ApiResponse;
import com.thatblackbwoy.tutorialapplication.model.Tutorial;
import com.thatblackbwoy.tutorialapplication.model.TutorialDetails;
import com.thatblackbwoy.tutorialapplication.repository.CommentRepository;
import com.thatblackbwoy.tutorialapplication.repository.TutorialRepository;
import com.thatblackbwoy.tutorialapplication.repository.TutorialsDetailsRepository;
import com.thatblackbwoy.tutorialapplication.service.TutorialService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;


import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TutorialServiceImpl implements TutorialService {
    private final TutorialRepository tutorialRepository;
    private final CommentRepository commentRepository;
    private final TutorialsDetailsRepository tutorialsDetailsRepository;
    @Override
    public ApiResponse createTutorial(TutorialDto tutorialDto) {
         Tutorial tutorial = Tutorial.builder()
                 .title(tutorialDto.getTitle())
                 .description(tutorialDto.getDescription())
                 .published(tutorialDto.isPublished())
                 .build();
         Tutorial response = tutorialRepository.save(tutorial);
         log.info("New record persisted {}", response);
        return ApiResponse.builder()
                .success(true)
                .message("Your tutorial has been published")
                .data(response)
                .build();
    }
    @Override
    public ApiResponse getAllTutorial() {
        List<Tutorial> tutorials = tutorialRepository.findAll();
        log.info("All records {}", tutorials);
        return ApiResponse.builder()
                .success(true)
                .message("All available tutorials has been successfully retrieved")
                .data(tutorials)
                .build();
    }
    @Override
    public ApiResponse getById(Long id) {
        Tutorial tutorial = tutorialRepository.findById(id).orElseThrow(()-> new RuntimeException("Tutorial with id " +id+ " is not present"));
        log.info("Tutorial with id " +id+ "successfully retrieved", tutorial);
        return ApiResponse.builder()
                .success(true)
                .message("Tutorial successfully retrieved")
                .data(tutorial)
                .build();
    }

    @Override
    public ApiResponse updateTutorial(Long id, TutorialDto tutorialDto) {
        //Tutorial tutorial = tutorialRepository.findById(id).get();
        Tutorial tutorial = tutorialRepository.findById(id).orElseThrow(()-> new RuntimeException("Tutorial with id " +id+ " is not present"));
            tutorial.setTitle(tutorialDto.getTitle());
            tutorial.setDescription(tutorialDto.getDescription());
            tutorial.setPublished(tutorialDto.isPublished());

            Tutorial response = tutorialRepository.save(tutorial);
            log.info("Updated record persisted {}", response);
            return ApiResponse.builder()
                    .success(true)
                    .message("Tutorial successfully updated")
                    .data(response)
                    .build();
    }
    @Override
    public ApiResponse deleteTutorialById(Long tutorialId) {
        //List<Tutorial> tutorials =  tutorialRepository.findAll();
        Tutorial tutorial =  tutorialRepository.findById(tutorialId).orElseThrow(()-> new RuntimeException());
        tutorialRepository.deleteTutorialById(tutorialId);
        log.info("Tutorial with id " +tutorialId+ " Deleted {}", tutorial);
        return ApiResponse.builder()
                    .success(true)
                    .message("Record removed")
                    .build();
    }
    @Override
    public ApiResponse searchTutorialsContainingTitleLike(String title) {
        List<Tutorial> tutorials = tutorialRepository.findByTitleContaining(title);
        log.info("Retrieved all tutorials containing " +title+ " {}", tutorials);

        return ApiResponse.builder()
                .success(true)
                .message("Retrieved all tutorials containing title")
                .data(tutorials)
                .build();
    }

    @Override
    public ApiResponse getAllPublished() {
        List<Tutorial> tutorials = tutorialRepository.findByPublished(true);
        log.info("All published tutorials successfully retrieved {}", tutorials);

        return ApiResponse.builder()
                .success(true)
                .message("All published tutorials successfully retrieved")
                .data(tutorials)
                .build();
    }
    @Override
    public ApiResponse deleteTutorialAndAllItsCommentsAndDetailsById(Long tutorialId) {
        Tutorial tutorial = tutorialRepository.findById(tutorialId).orElseThrow(()-> new RuntimeException());
        commentRepository.deleteAllCommentsByTutorialId(tutorialId);
        tutorialsDetailsRepository.deleteTutorialDetailsByTutorialId(tutorialId);
        tutorialRepository.deleteTutorialById(tutorialId);
        log.info("Tutorial and all its comments and details deleted", tutorial);


        return ApiResponse.builder()
                .success(true)
                .message("Tutorial and all its comments and details deleted")
                .build();
    }

//    @Override
//    public ApiResponse searchTutorialsContainingTitleLike(String title) {
//
//        /*
//    String title;
//    ("SELECT * FROM TUTORIALDB WHERE title LIKE '%"+title+"%"')
//     */
//        List<Tutorial> tutorials = tutorialRepository.;
//    }


}
