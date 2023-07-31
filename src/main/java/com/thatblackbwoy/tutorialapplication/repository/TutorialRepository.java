package com.thatblackbwoy.tutorialapplication.repository;

import com.thatblackbwoy.tutorialapplication.model.Tutorial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface TutorialRepository extends JpaRepository<Tutorial, Long>{
    List<Tutorial> findByTitleContaining(String title);
    List<Tutorial> findByPublished(boolean published);

//    List<Tutorial> deleteTutorialById(long id);

    @Transactional
    void deleteById(Long Id);
    @Transactional
    void deleteTutorialById(Long tutorialId);
}
