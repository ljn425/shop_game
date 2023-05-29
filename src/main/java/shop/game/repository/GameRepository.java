package shop.game.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.game.domain.Game;

public interface GameRepository extends JpaRepository<Game, Long> {
}
