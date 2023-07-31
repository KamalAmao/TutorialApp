package com.thatblackbwoy.tutorialapplication.controller;

import com.thatblackbwoy.tutorialapplication.dto.TutorialDetailsDto;
import com.thatblackbwoy.tutorialapplication.dto.response.ApiResponse;
import com.thatblackbwoy.tutorialapplication.service.TutorialDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class TutorialDetailsController {
    private  final TutorialDetailsService tutorialDetailsService;
    //    POST	/api/v1/tutorials/:id/details	create new Details for a Tutorial //create details for tutorial with this particular id
    @PostMapping("/tutorials/{tutorialId}/details")
    public ResponseEntity<ApiResponse> createTutorialDetails(@PathVariable long tutorialId, @RequestBody TutorialDetailsDto tutorialDetailsDto){
        try{
            return ResponseEntity.ok().body(tutorialDetailsService.createTutorialDetails(tutorialId, tutorialDetailsDto));
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.builder().success(false).build());
        }
    }
    @PutMapping("/tutorials/{tutorialId}/details")
    public ResponseEntity<ApiResponse> updateTutorialDetails(@PathVariable long tutorialId, @RequestBody TutorialDetailsDto tutorialDetailsDto){
        try{
            return ResponseEntity.ok().body(tutorialDetailsService.updateTutorialDetails(tutorialId, tutorialDetailsDto));
        }catch (Exception e){
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.builder().success(false).build());
        }
    }
    @GetMapping("/tutorials/details")
    public ResponseEntity<ApiResponse> getAllTutorialDetails(){
        try {
            return ResponseEntity.ok().body(tutorialDetailsService.getAllTutorialDetails());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(ApiResponse.builder().success(false).build());
        }
    }
    @GetMapping("/search/details")
    public ResponseEntity<ApiResponse> searchTutorialDetailsContainingAuthorsLike(@RequestParam String createdBy){
        try{
            return ResponseEntity.ok().body(tutorialDetailsService.searchTutorialDetailsContainingAuthorsLike(createdBy));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(ApiResponse.builder().success(false).build());
        }
    }
    @DeleteMapping("/tutorials/{tutorialId}/details")
    public ResponseEntity<ApiResponse> deleteTutorialDetailsById(@PathVariable Long tutorialId){
        try{
            return ResponseEntity.ok().body(tutorialDetailsService.deleteTutorialDetailsById(tutorialId));
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.builder().success(false).build());
        }
    }
}
