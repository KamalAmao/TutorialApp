package com.thatblackbwoy.tutorialapplication.controller;

import com.thatblackbwoy.tutorialapplication.dto.TutorialDto;
import com.thatblackbwoy.tutorialapplication.dto.response.ApiResponse;
import com.thatblackbwoy.tutorialapplication.model.Tutorial;
import com.thatblackbwoy.tutorialapplication.service.TutorialService;
import com.thatblackbwoy.tutorialapplication.service.impl.TutorialServiceImpl;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.stereotype.Repository;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;
import repository.TestH2Repository;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TutorialControllerTest {

    @LocalServerPort
    private int port;
//    @Autowired
//    private MockMvc mockMvc ;
    HttpHeaders headers = new HttpHeaders();
    private static TestRestTemplate restTemplate; // call external api
    //private String baseUrl = "http://localhost";
    @BeforeAll
    public static void init() {
//        this.mockMvc = MockMvcBuilders.standaloneSetup(TutorialController.class);
        restTemplate = new TestRestTemplate();
    }

    @BeforeEach
    void setUp() {
        headers.setContentType(MediaType.APPLICATION_JSON);

    }
    private URI createUrlWithPort(String uri){
        return URI.create("http://localhost:" + port + uri);
    }
    @Test
    void createTutorial() {
        Tutorial tutorial = Tutorial.builder().title("Title A").description("Description A").published(true).build();

        HttpEntity<Tutorial> entity = new HttpEntity<>(tutorial, headers);
        ResponseEntity<ApiResponse> response = restTemplate.exchange(createUrlWithPort("/tutorials"), HttpMethod.POST, entity, ApiResponse.class);
        System.out.println(response);

        ApiResponse expectedApiResponse = ApiResponse.builder()
                .success(true)
                .message("Your tutorial has been published")
                .data(response.getBody().getData())
                .build();
        assertEquals(expectedApiResponse, response.getBody());
        assertEquals(200, response.getStatusCode().value());
    }
    @Test
    void getAllTutorial(){
        List<Tutorial> tutorial = Stream.of(
                new Tutorial(1L, "Title A", "Description A", true),
                new Tutorial(2L, "Title B", "Description B", true)
                ).collect(Collectors.toList());
        HttpEntity<List<Tutorial>> entity = new HttpEntity<>(tutorial, headers);
        ResponseEntity<ApiResponse> response = restTemplate.exchange(createUrlWithPort("/tutorials"), HttpMethod.GET, entity, ApiResponse.class);

        ApiResponse expectedApiResponse = ApiResponse.builder()
                .success(true)
                .message("All available tutorials has been successfully retrieved")
                .data(response.getBody().getData())
                .build();
        assertEquals(expectedApiResponse, response.getBody());
    }
    @Test
    void getById(){
        Tutorial tutorial = Tutorial.builder().title("Title A").description("Description A").published(true).build();
        HttpEntity<Tutorial> entity = new HttpEntity<>(tutorial, headers);
        ResponseEntity<ApiResponse> response = restTemplate.exchange(createUrlWithPort("/id"), HttpMethod.GET, entity, ApiResponse.class);

        ApiResponse expectedApiResponse = ApiResponse.builder()
                .success(true)
                .message("Tutorial successfully retrieved")
                .data(response.getBody().getData())
                .build();
        assertEquals(expectedApiResponse, response.getBody());
    }
    @Test
    void testWelcome(){
        HttpEntity<Tutorial> entity = new HttpEntity<>(null, headers);
        ResponseEntity<String> response = restTemplate.exchange(createUrlWithPort("/welcome"), HttpMethod.GET, entity, String.class);
        assertEquals("Welcome", response.getBody());
    }
//    @Test
//    void updateTutorial(){
//        Tutorial tutorial = Tutorial.builder().id(1L).title("Title A").description("Description A").published(true).build();
//
//    }
}