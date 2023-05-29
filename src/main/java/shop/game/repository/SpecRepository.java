package shop.game.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.game.domain.Spec;

import java.util.List;

public interface SpecRepository extends JpaRepository<Spec, Long> {

    List<Spec> findByGameId(Long gameId);
}
