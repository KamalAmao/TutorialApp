package com.thatblackbwoy.tutorialapplication.repository;

import com.thatblackbwoy.tutorialapplication.dto.TutorialDetailsDto;
import com.thatblackbwoy.tutorialapplication.model.TutorialDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import  java.util.List;

@Repository
public interface TutorialsDetailsRepository extends JpaRepository<TutorialDetails, Long> {
    @Transactional
    void deleteById(long id);

    @Transactional
    void deleteByTutorialId(long tutorialId);

    @Transactional
    void deleteTutorialDetailsByTutorialId(Long tutorialId);
    List<TutorialDetails> findByCreatedByContaining(String createdBy);
//    TutorialDetails updateTutorialDetailsById(long tutorialId, TutorialDetailsDto tutorialDetailsDto);
//    @Transactional
//    List<TutorialDetails> findAll(TutorialDetailsDto tutorialDetailsDto);
}
