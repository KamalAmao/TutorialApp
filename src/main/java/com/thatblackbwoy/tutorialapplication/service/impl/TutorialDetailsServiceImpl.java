package com.thatblackbwoy.tutorialapplication.service.impl;

import com.thatblackbwoy.tutorialapplication.dto.TutorialDetailsDto;
import com.thatblackbwoy.tutorialapplication.dto.response.ApiResponse;
import com.thatblackbwoy.tutorialapplication.model.Tutorial;
import com.thatblackbwoy.tutorialapplication.model.TutorialDetails;
import com.thatblackbwoy.tutorialapplication.repository.TutorialRepository;
import com.thatblackbwoy.tutorialapplication.repository.TutorialsDetailsRepository;
import com.thatblackbwoy.tutorialapplication.service.TutorialDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
@Service
@RequiredArgsConstructor
@Slf4j
public class TutorialDetailsServiceImpl implements TutorialDetailsService {
    private final TutorialRepository tutorialRepository;
    private final TutorialsDetailsRepository tutorialsDetailsRepository;

    @Override
    public ApiResponse createTutorialDetails(long tutorialId, TutorialDetailsDto tutorialDetailsDto) {
        Tutorial tutorial = tutorialRepository.findById(tutorialId).orElseThrow(()-> new RuntimeException());
        TutorialDetails tutorialDetails = TutorialDetails.builder()
                .createdOn(new Date())
                .createdBy(tutorialDetailsDto.getCreatedBy())
                .tutorial(tutorial)
                .build();
        TutorialDetails response = tutorialsDetailsRepository.save(tutorialDetails);

        return ApiResponse.builder()
                .success(true)
                .message("Tutorial details created successfully")
                .data(response)
                .build();
    }
    @Override
    public ApiResponse updateTutorialDetails(long tutorialId, TutorialDetailsDto tutorialDetailsDto){
//        Tutorial tutorial = tutorialRepository.findById(tutorialId).orElseThrow(()-> new RuntimeException());
        TutorialDetails tutorialDetails = tutorialsDetailsRepository.findById(tutorialId).orElseThrow(()-> new RuntimeException());

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

    @Override
    public ApiResponse deleteTutorialDetailsById(Long tutorialId) {
       List<TutorialDetails> tutorialDetails = tutorialsDetailsRepository.findAll();
        tutorialsDetailsRepository.deleteTutorialDetailsByTutorialId(tutorialId);
        log.info("Tutorial details with id " +tutorialId+ " has been deleted", tutorialDetails);
        return ApiResponse.builder()
                .success(true)
                .message("Tutorial details with id " +tutorialId+ " has been deleted")
                .build();
    }
}
