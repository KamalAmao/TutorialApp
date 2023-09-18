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
import org.junit.jupiter.api.Assertions;
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
import static org.mockito.Mockito.*;

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
        Tutorial expectedResponse = Tutorial.builder().id(1L).title("Title A").description("Description A").published(true).build();

        ApiResponse expectedApiResponse = ApiResponse.builder()
                .success(true)
                .message("Your tutorial has been published")
                .data(Tutorial.builder()
                        .id(1L)
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
                new Tutorial(1L,"Title A", "Description A", true),
                new Tutorial(2L,"Title B", "Description B", true)
        ).collect(Collectors.toList());

        ApiResponse expectedApiResponse = ApiResponse.builder()
                .success(true)
                .message("All available tutorials has been successfully retrieved")
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
        Tutorial expectedResponse = Tutorial.builder().id(1L).title("Title A").description("Description A").published(true).build();

        ApiResponse expectedApiResponse = ApiResponse.builder()
                .success(true)
                .message("Tutorial successfully retrieved")
                .data(expectedResponse)
                .build();

        when(tutorialRepository.findById(1L)).thenReturn(Optional.ofNullable(expectedResponse));
        ApiResponse actualResponse = tutorialService.getById(1L);
        assertEquals(expectedApiResponse, actualResponse);
    }
    @Test
    void updateTutorial(){
        Tutorial tutorial = Tutorial.builder().title("Title A").description("Description A").build();
         tutorial.setTitle("Title B");
         tutorial.setDescription("Description B");
         tutorial.setPublished(true);
        Tutorial expectedResponse = Tutorial.builder().title("Title B").description("Description B").published(true).build();

        ApiResponse expectedApiResponse = ApiResponse.builder()
                .success(true)
                .message("Tutorial successfully updated")
                .data(expectedResponse)
                .build();
        when(tutorialRepository.findById(1L)).thenReturn(Optional.ofNullable(expectedResponse));
        when(tutorialRepository.save(tutorial)).thenReturn(expectedResponse);
        ApiResponse actualResponse = tutorialService.updateTutorial(1L, new TutorialDto("Title B", "Description B", true));
        assertEquals(expectedApiResponse, actualResponse);

    }
    @Test
    void getPublished(){
        List<Tutorial> expectedResponse = Stream.of(
                new Tutorial(1L, "Title A", "Description A", true),
                new Tutorial(2L, "Title B", "Description B", true)).collect(Collectors.toList());

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
                new Tutorial(1L, "Title A", "Description A", true)
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
    @Test
    void removeTutorial(){
        Tutorial tutorial = Tutorial.builder().title("Title A").description("Description A").build();
        //tutorialRepository.delete(tutorial);
        tutorial.setId(1L);
        //tutorialRepository.deleteTutorialById(1L);
        ApiResponse expectedApiResponse = ApiResponse.builder()
                .success(true)
                .message("Record removed")
                .build();

        when(tutorialRepository.findById(1L)).thenReturn(Optional.of(tutorial));
        doNothing().when(tutorialRepository).delete(tutorial);
        ApiResponse actualResponse = tutorialService.deleteTutorialById(1L);
        assertEquals(expectedApiResponse, actualResponse);
    }
    @Test
    void deleteTutorialAndItsComments(){
        Tutorial tutorial = Tutorial.builder().title("Title A").description("Description A").build();
        tutorial.setId(1L);
        ApiResponse expectedApiResponse = ApiResponse.builder()
                .success(true)
                .message("Tutorial and all its comments and details deleted")
                .build();

        when(tutorialRepository.findById(1L)).thenReturn(Optional.of(tutorial));
        doNothing().when(tutorialRepository).delete(tutorial);
        ApiResponse actualResponse = tutorialService.deleteTutorialAndAllItsCommentsAndDetailsById(1L);
        assertEquals(expectedApiResponse, actualResponse);
    }
//    @Test
//    void createTutorialDetails(){
//        Tutorial tutorial = Tutorial.builder().title("Title A").description("Description A").build();
////        TutorialDetails tutorialDetails = TutorialDetails.builder().createdOn(new Date()).createdBy("kamal").tutorial(Tutorial.builder().id(1L).title("Title A").description("Description A").published(true).build()).build();
////        TutorialDetails expectedResponse = TutorialDetails.builder().createdOn(new Date()).createdBy("kamal").tutorial(Tutorial.builder().id(1L).title("Title A").description("Description A").published(true).build()).build();
//        TutorialDetails tutorialDetails = TutorialDetails.builder().createdOn(new Date()).createdBy("kamal").tutorial(tutorial).build();
//        TutorialDetails expectedResponse = TutorialDetails.builder().Id(1L).createdOn(new Date()).createdBy("kamal").tutorial(tutorial).build();
//        ApiResponse expectedApiResponse = ApiResponse.builder()
//                .success(true)
//                .message("Tutorial details created successfully")
//                .data(expectedResponse)
//                .build();
//        when(tutorialRepository.findById(1L)).thenReturn(Optional.ofNullable(tutorial));
//        when(tutorialsDetailsRepository.save(tutorialDetails)).thenReturn(expectedResponse); //mock dependencies
//        ApiResponse actualResponse = tutorialDetailsService.createTutorialDetails(1, new TutorialDetailsDto(new Date(), "kamal"));
//        assertEquals(expectedApiResponse, actualResponse);
//    }
}