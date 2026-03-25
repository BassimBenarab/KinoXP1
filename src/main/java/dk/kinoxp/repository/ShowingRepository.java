package dk.kinoxp.repository;

import dk.kinoxp.model.Showing;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ShowingRepository extends JpaRepository<Showing, Long> {
    List<Showing> findByActiveTrue();
}
