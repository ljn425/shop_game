package shop.game.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.game.domain.Movie;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Long> {

    List<Movie> findByGameId(Long gameId);
}
