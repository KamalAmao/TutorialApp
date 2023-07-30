package com.thatblackbwoy.tutorialapplication.service.impl;

import com.thatblackbwoy.tutorialapplication.dto.TutorialDetailsDto;
import com.thatblackbwoy.tutorialapplication.dto.TutorialDto;
import com.thatblackbwoy.tutorialapplication.dto.response.ApiResponse;
import com.thatblackbwoy.tutorialapplication.model.Tutorial;
import com.thatblackbwoy.tutorialapplication.model.TutorialDetails;
import com.thatblackbwoy.tutorialapplication.repository.TutorialRepository;
import com.thatblackbwoy.tutorialapplication.repository.TutorialsDetailsRepository;
import com.thatblackbwoy.tutorialapplication.service.TutorialService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TutorialServiceImpl implements TutorialService {
    private final TutorialRepository tutorialRepository;
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
                .message("Tutorials available")
                .data(tutorials)
                .build();
    }
    @Override
    public ApiResponse getById(long id) {
        Tutorial tutorial = tutorialRepository.findById(id).orElseThrow(()-> new RuntimeException("Tutorial with id " +id+ " is not present"));
        return ApiResponse.builder()
                .success(true)
                .message("get by id")
                .data(tutorial)
                .build();
    }

    @Override
    public ApiResponse updateTutorial(long id, TutorialDto tutorialDto) {
        //Tutorial tutorial = tutorialRepository.findById(id).get();
        Tutorial tutorial = tutorialRepository.findById(id).orElseThrow(()-> new RuntimeException("Tutorial with id " +id+ " is not present"));
        if(tutorial.isPublished()){
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
        }else{
            return ApiResponse.builder()
                    .success(false)
                    .message("Your tutorial cannot be updated because you are yet to publish it")
                    .build();
        }
    }
    @Override
    public ApiResponse deleteTutorial(long id) {
        List<Tutorial> tutorial =  tutorialRepository.findAll();
        //List<Tutorial> tutorials =  tutorialRepository.findAll();
        if(tutorialRepository.existsById(id)){
            tutorialRepository.deleteById(id);
            log.info("Tutorial with id " +id+ " Deleted {}", tutorial);
            return  ApiResponse.builder()
                    .success(true)
                    .message("Tutorial with id " +id+ " Deleted")
                    .build();
        }
        return ApiResponse.builder()
                .success(false)
                .message("Tutorial with id " +id+ " not found")
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
    public ApiResponse createTutorialDetails(long tutorialId, TutorialDetailsDto tutorialDetailsDto) {
        Tutorial tutorial = tutorialRepository.findById(tutorialId).orElseThrow(()-> new RuntimeException());
        TutorialDetails tutorialDetails = TutorialDetails.builder()
                .createdOn(new Date())
                .createdBy(tutorialDetailsDto.getCreatedBy())
                .tutorial(tutorial)
                .build();
        tutorialsDetailsRepository.save(tutorialDetails);

        return ApiResponse.builder()
                .success(true)
                .message("Tutorial details created successfully")
                .data(tutorial)
                .build();
    }
    @Override
    public ApiResponse updateTutorialDetails(long tutorialId, TutorialDetailsDto tutorialDetailsDto){
//        Tutorial tutorial = tutorialRepository.findById(tutorialId).orElseThrow(()-> new RuntimeException());
        TutorialDetails tutorialDetails = tutorialsDetailsRepository.findById(tutorialId).orElseThrow(()-> new RuntimeException());
//        if(tutorialDetails != null) {
        tutorialDetails.setCreatedBy(tutorialDetailsDto.getCreatedBy());
        TutorialDetails response = tutorialsDetailsRepository.save(tutorialDetails);
        return ApiResponse.builder()
                .success(true)
                .message("Tutorial details updated successfully")
                .data(response)
                .build();
    }
    @Override
    public ApiResponse getAllTutorialDetails() {
        List<TutorialDetails> tutorials = tutorialsDetailsRepository.findAll();
        log.info("All tutorial details successfully retrieved {}", tutorials);
        return ApiResponse.builder()
                .success(true)
                .message("All tutorial details successfully retrieved")
                .data(tutorials)
                .build();
    }

    @Override
    public ApiResponse searchTutorialDetailsContainingAuthorsLike(String createdBy) {
        List<TutorialDetails> tutorialDetails = tutorialsDetailsRepository.findByCreatedByContaining(createdBy);
        log.info("Successfully retrieved all tutorial details with author name containing  " +createdBy+ " {}", tutorialDetails);
        return ApiResponse.builder()
                .success(true)
                .message("Successfully retrieved all tutorial details with author name containing " +createdBy+ "")
                .data(tutorialDetails)
                .build();
    }

//    @Override
//    public ApiResponse deleteTutorialDetailsById(long tutorialId) {
//        tutorialsDetailsRepository.deleteByTutorialId(tutorialId);
//        return ApiResponse.builder()
//                .success(true)
//                .message("Tutorial details with id " +tutorialId+ " has been deleted")
//                .build();
//    }
    //    @Override
//    public ApiResponse deleteAll(Tutorial tutorial) {
//        List<Tutorial> tutorials = tutorialRepository.findAll();
//        if(tutorialRepository.)
//        return tutorialRepository.deleteAll();
//
//    }
    //    @Override
//    public ApiResponse deleteAllTutorials() {
//        Tutorial tutorial = tutorialRepository.delete(tutorialRepo);
//
//        return ApiResponse.builder()
//                .success(true)
//                .message("All tutorials deleted")
//                .data(tutorialRepository)
//                .build();
//    }

    //    @Override
//    public ApiResponse deleteTutorial(long id) {
//        Tutorial tutorial =  tutorialRepository.findById(id).orElseThrow(()-> new RuntimeException("Tutorial with id " +id+ " is not present"));
//        //List<Tutorial> tutorials =  tutorialRepository.findAll();
//         if(tutorialRepository.existsById(id)){
//             tutorialRepository.deleteById(id);
//             log.info("Record deleted {}", tutorial);
//             return  ApiResponse.builder()
//                     .success(true)
//                     .message("Tutorial with id " +id+ " Deleted")
//                     .data(tutorial)
//                     .build();
//         }
//        return null;
//    }



//    @Override
//    public ApiResponse deleteTutorialById(long id) {
//         Tutorial tutorial = (Tutorial) tutorialRepository.deleteTutorialById(id);
//            log.info("Tutorial with id " +id+ " removed {}", tutorial);
//
//            return ApiResponse.builder()
//                    .success(true)
//                    .message("Tutorial with id " +id+ " removed")
//                    .data(tutorial)
//                    .build();
//    }

//    @Override
//    public ApiResponse checkPublished() {
//        List<Tutorial> tutorials = tutorialRepository.findAll();
//        if(tutorials.stream().filter(myTutorials -> myTutorials.isPublished()).equals(true)){
//            tutorials.addAll(tutorials);
////            tutorialRepository.findAll().stream().collect(Collectors.toList());
////            tutorial.get(((List) tutorialRepository.findAll()).size());
////            tutorial.stream().allMatch(Tutorial::isPublished);
//
//            log.info("Number of published tutorials {}", tutorials);
//            return ApiResponse.builder()
//                    .success(true)
//                    .message("Get total Published")
//                    .data(tutorials)
//                    .build();
//        }
//        return null;
//    }
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
