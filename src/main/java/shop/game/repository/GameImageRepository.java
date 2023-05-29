package shop.game.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.game.domain.GameImage;

import java.util.List;

public interface GameImageRepository extends JpaRepository<GameImage, Long> {
    List<GameImage> findByGameIdAndImageType(Long gameId, Enum imageType);
}

