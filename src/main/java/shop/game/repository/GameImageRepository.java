package shop.game.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.game.domain.GameImage;

public interface GameImageRepository extends JpaRepository<GameImage, Long> {
}
