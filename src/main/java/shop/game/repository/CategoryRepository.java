package shop.game.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.game.domain.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
