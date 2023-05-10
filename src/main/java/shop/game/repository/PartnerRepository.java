package shop.game.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.game.domain.Partner;

public interface PartnerRepository extends JpaRepository<Partner, Long> {

    Integer countByLoginEmail(String loginEmail);

}
