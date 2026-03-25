package dk.kinoxp.repository;

import dk.kinoxp.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
public interface MovieRepository extends JpaRepository<Movie, Long> {}
