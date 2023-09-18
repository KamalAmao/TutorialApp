package repository;

import com.thatblackbwoy.tutorialapplication.model.Tutorial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestH2Repository extends JpaRepository<Tutorial, Long> {
}
