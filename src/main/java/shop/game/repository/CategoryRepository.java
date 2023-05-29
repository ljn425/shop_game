package shop.game.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.game.domain.Category;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findByGameId(Long gameId);
}
