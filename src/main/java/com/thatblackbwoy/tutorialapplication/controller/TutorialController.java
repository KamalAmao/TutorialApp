package com.thatblackbwoy.tutorialapplication.controller;

import com.thatblackbwoy.tutorialapplication.dto.TutorialDetailsDto;
import com.thatblackbwoy.tutorialapplication.dto.TutorialDto;
import com.thatblackbwoy.tutorialapplication.dto.response.ApiResponse;

import com.thatblackbwoy.tutorialapplication.service.TutorialService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.Response;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class TutorialController {
    private final TutorialService tutorialService;

    @PostMapping("/tutorials")
    public ResponseEntity <ApiResponse> createTutorial(@RequestBody TutorialDto tutorialDto){
        try{
            return ResponseEntity.ok().body(tutorialService.createTutorial(tutorialDto));
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.builder().success(false).build());
        }
    }
    @GetMapping("/welcome")
    public ResponseEntity<String> welcome(){
        return ResponseEntity.ok().body("Welcome");
    }
    @GetMapping("/tutorials")
    public ResponseEntity<ApiResponse> getAllTutorial(){
        try {
            return ResponseEntity.ok().body(tutorialService.getAllTutorial());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(ApiResponse.builder().success(false).build());
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getById(@PathVariable long id){
        try{
            return ResponseEntity.ok().body(tutorialService.getById(id));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(ApiResponse.builder().success(false).build());
        }

    }
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateTutorial(@PathVariable long id, @RequestBody TutorialDto tutorialDto){
        try{
            return ResponseEntity.ok().body(tutorialService.updateTutorial(id, tutorialDto));
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.builder().success(false).build());
        }
    }
    @GetMapping("/search")
    public ResponseEntity<ApiResponse> searchByTitle(@RequestParam String title){
        try{
            return  ResponseEntity.ok().body(tutorialService.searchTutorialsContainingTitleLike(title));
        }catch(Exception e){
            return  ResponseEntity.status(HttpStatus.NO_CONTENT).body(ApiResponse.builder().success(false).build());
        }
    }
    @GetMapping("/published")
    public ResponseEntity<ApiResponse> getAllPublished(){
        try{
            return ResponseEntity.ok().body(tutorialService.getAllPublished());
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.builder().success(false).build());
        }
    }
    @DeleteMapping("/{tutorialId}")
    public  ResponseEntity<ApiResponse> deleteTutorial(@PathVariable long tutorialId) {
        try {
            return ResponseEntity.ok().body(tutorialService.deleteTutorialById(tutorialId));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(ApiResponse.builder().success(false).build());
        }
    }
    @DeleteMapping("tutorials/{tutorialId}")
    public  ResponseEntity<ApiResponse> deleteTutorialAndItsCommentsById(@PathVariable Long tutorialId) {
        try {
            return ResponseEntity.ok().body(tutorialService.deleteTutorialAndAllItsCommentsAndDetailsById(tutorialId));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(ApiResponse.builder().success(false).build());
        }
    }

//    @DeleteMapping("/{id}")
//    public  ResponseEntity<ApiResponse> deleteTutorialById(@PathVariable long id){
//        try{
//            return ResponseEntity.ok().body(tutorialService.deleteTutorial(id));
//        }catch(Exception e){
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.builder().success(false).build());
//        }
//    }
//    @DeleteMapping("/delete-all")
//    public ResponseEntity<ApiResponse> deleteAllTutorials(){
//        try{
//            return ResponseEntity.ok().body(tutorialService.deleteTutorials());
//        }catch(Exception e) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.builder().success(false).build());
//        }
//    }

}
