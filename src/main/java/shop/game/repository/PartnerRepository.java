package shop.game.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.game.domain.Partner;

import java.util.Optional;

public interface PartnerRepository extends JpaRepository<Partner, Long> {
    Integer countByLoginEmail(String loginEmail);

    Optional<Partner> findByLoginEmail(String loginEmail);

}
