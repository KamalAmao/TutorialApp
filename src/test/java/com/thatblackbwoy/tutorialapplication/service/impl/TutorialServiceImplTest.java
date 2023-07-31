package com.thatblackbwoy.tutorialapplication.service.impl;

import com.thatblackbwoy.tutorialapplication.dto.TutorialDetailsDto;
import com.thatblackbwoy.tutorialapplication.dto.TutorialDto;
import com.thatblackbwoy.tutorialapplication.dto.response.ApiResponse;
import com.thatblackbwoy.tutorialapplication.model.Tutorial;
import com.thatblackbwoy.tutorialapplication.model.TutorialDetails;
import com.thatblackbwoy.tutorialapplication.repository.TutorialRepository;
import com.thatblackbwoy.tutorialapplication.repository.TutorialsDetailsRepository;
import com.thatblackbwoy.tutorialapplication.service.TutorialDetailsService;
import com.thatblackbwoy.tutorialapplication.service.TutorialService;
import org.assertj.core.api.Descriptable;
import org.hibernate.sql.Update;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockBeans;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class TutorialServiceImplTest {

    @MockBean
    private TutorialRepository tutorialRepository;

    @MockBean
    private TutorialsDetailsRepository tutorialsDetailsRepository;

    @Autowired
    private TutorialServiceImpl tutorialService;

    @Autowired
    private TutorialDetailsServiceImpl tutorialDetailsService;
    /*
    (PMCVA)
    Prepare Test Data
    Mock Dependencies
    Call the service
    Verify interactions
    Assertion
     */

    @Test
    void createTutorial() {
        Tutorial tutorial = Tutorial.builder().title("Title A").description("Description A").published(true).build();
        Tutorial expectedResponse = Tutorial.builder().id(1).title("Title A").description("Description A").published(true).build();

        ApiResponse expectedApiResponse = ApiResponse.builder()
                .success(true)
                .message("Your tutorial has been published")
                .data(Tutorial.builder()
                        .id(1)
                        .title("Title A")
                        .description("Description A")
                        .published(true)
                        .build())
                .build();

        when(tutorialRepository.save(tutorial)).thenReturn(expectedResponse); //mock dependencies
        ApiResponse actualResponse = tutorialService.createTutorial(new TutorialDto("Title A", "Description A", true));
        assertEquals(expectedApiResponse, actualResponse);
    }

    @Test
    void getAllTutorial() {
        List<Tutorial> expectedResponse = Stream.of(
                new Tutorial(1,"Title A", "Description A", true),
                new Tutorial(2,"Title B", "Description B", true)
        ).collect(Collectors.toList());

        ApiResponse expectedApiResponse = ApiResponse.builder()
                .success(true)
                .message("Retrieved all tutorials")
                .data(expectedResponse)
                .build();

        when(tutorialRepository.findAll()).thenReturn(expectedResponse);
        ApiResponse actualResponse = tutorialService.getAllTutorial();
        assertEquals(expectedApiResponse, actualResponse);
        List<Tutorial> actualSize = (List<Tutorial>) actualResponse.getData();
        assertEquals(2, actualSize.size());
    }

    @Test
    void getById() {
        Tutorial expectedResponse = Tutorial.builder().id(1).title("Title A").description("Description A").published(true).build();

        ApiResponse expectedApiResponse = ApiResponse.builder()
                .success(true)
                .message("get by id")
                .data(expectedResponse)
                .build();

        when(tutorialRepository.findById(expectedResponse.getId())).thenReturn(Optional.of(expectedResponse));
        ApiResponse actualResponse = tutorialService.getById(expectedResponse.getId());
        assertEquals(expectedApiResponse, actualResponse);
    }
    @Test
    void updateTutorial(){
        Tutorial tutorial = Tutorial.builder().id(1).title("Title A").description("Description A").published(true).build();
        Tutorial expectedResponse = Tutorial.builder().id(1).title("Title A").description("Description A").published(true).build();

        ApiResponse expectedApiResponse = ApiResponse.builder()
                .success(true)
                .message("Tutorial details updated successfully")
                .data(expectedResponse)
                .build();
        when(tutorialRepository.save(tutorial)).thenReturn(expectedResponse);
        ApiResponse actualResponse = tutorialService.updateTutorial(tutorial.getId(), new TutorialDto("Title B", "Description B", true));
        assertEquals(expectedApiResponse, actualResponse);
    }
    @Test
    void getPublished(){
        List<Tutorial> expectedResponse = Stream.of(
                new Tutorial(1, "Title A", "Description A", true),
                new Tutorial(2, "Title B", "Description B", true)).collect(Collectors.toList());

        ApiResponse expectedApiResponse = ApiResponse.builder()
                .success(true)
                .message("All published tutorials successfully retrieved")
                .data(expectedResponse)
                .build();

        when(tutorialRepository.findByPublished(true)).thenReturn(expectedResponse);
        ApiResponse actualResponse = tutorialService.getAllPublished();
        assertEquals(expectedApiResponse, actualResponse);
    }
    @Test
    void searchTutorialsContainingTitleLike(){ //explicit
        List<Tutorial> expectedResponse = Stream.of(
                new Tutorial(1, "Title A", "Description A", true)
               ).collect(Collectors.toList());
//        Tutorial expectedResponse = Tutorial.builder().id(1).title("Title A").description("Description A").published(true).build();

        ApiResponse expectedApiResponse = ApiResponse.builder()
                .success(true)
                .message("Retrieved all tutorials containing title")
                .data(expectedResponse)
                .build();

        when(tutorialRepository.findByTitleContaining("Title A")).thenReturn(expectedResponse);
        ApiResponse actualResponse =  tutorialService.searchTutorialsContainingTitleLike("Title A");
        assertEquals(expectedApiResponse, actualResponse);
    }
//    @Test
//    void createTutorialDetails(){
//        TutorialDetails tutorialDetails = TutorialDetails.builder().createdOn(new Date()).createdBy("kamal").tutorial(Tutorial.builder().id(1).title("").description("").published(true).build()).build();
//        TutorialDetails expectedResponse = TutorialDetails.builder().id(1).createdOn(new Date()).createdBy("kamal").tutorial(Tutorial.builder().id(1).title("").description("").published(true).build()).build();
//
//        ApiResponse expectedApiResponse = ApiResponse.builder()
//                .success(true)
//                .message("Your tutorial has been published")
//                .data(Tutorial.builder()
//                        .id(1)
//                        .title("Title A")
//                        .description("Description A")
//                        .published(true)
//                        .build())
//                .build();
//
//        when(tutorialsDetailsRepository.save(tutorialDetails)).thenReturn(expectedResponse); //mock dependencies
//        ApiResponse actualResponse = tutorialDetailsService.createTutorialDetails(1, new TutorialDetailsDto(new Date(), "kamal"));
//        assertEquals(expectedApiResponse, actualResponse);
//    }
    @Test
    void getAllTutorialDetails() {
        List<TutorialDetails> expectedResponse = Stream.of(
                new TutorialDetails(1, new Date(), "Author 1", new Tutorial()),
                new TutorialDetails(2, new Date(), "Author 2", new Tutorial())
        ).collect(Collectors.toList());

        ApiResponse expectedApiResponse = ApiResponse.builder()
                .success(true)
                .message("All tutorial details successfully retrieved")
                .data(expectedResponse)
                .build();

        when(tutorialsDetailsRepository.findAll()).thenReturn(expectedResponse);
        ApiResponse actualResponse = tutorialDetailsService.getAllTutorialDetails();
        assertEquals(expectedApiResponse, actualResponse);
//        List<TutorialDetails> actualSize = (List<Tutorial>) actualResponse.getData();
//        assertEquals(2, actualSize.size());
    }
//    @Test
//    void deleteTutorialDetails(){
//    }

//    @Test
//    void removeTutorial(){
////        Tutorial expectedResponse = Tutorial.builder().id(1).title("Title A").description("Description A").published(true).build();
//                List<Tutorial> expectedResponse = Stream.of(
//                new Tutorial(1, "Title A", "Description A", true),
//                new Tutorial(2, "Title B", "Description B", true)).collect(Collectors.toList());
//        ApiResponse expectedApiResponse = ApiResponse.builder()
//                .success(true)
//                .message("Record removed")
//                .data(expectedResponse)
//                .build();
//
//        when(tutorialRepository.findById(expectedResponse.stream().findFirst().get().getId())).thenReturn();
//        ApiResponse actualResponse = tutorialService.deleteTutorial(id);
//        assertEquals(expectedApiResponse, actualResponse);
//    }
//    @Test
//    void removeTutorial(){
//        List<Tutorial> expectedResponse =  Stream.of(
//                new Tutorial(1,"Title A", "Description A", true),
//                new Tutorial(2,"Title B", "Description B", true)
//        ).collect(Collectors.toList());
//
//        ApiResponse expectedApiResponse = ApiResponse.builder()
//                .success(true)
//                .message("Record removed")
//                .data(expectedResponse)
//                .build();
//
//            when(tutorialRepository.findById(expectedResponse.stream().filter(Tutorial::getId))).thenReturn(expectedResponse)
//    }

}