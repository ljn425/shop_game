package shop.game.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.game.domain.Partner;
import shop.game.repository.PartnerRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PartnerService {

    private final PartnerRepository partnerRepository;



    /**
     * 파트너 회원가입
     */
    @Transactional
    public Partner join(Partner partner) {

        return partnerRepository.save(partner);
    }

    public boolean isLoginEmailAvailable(String loginEmail) {
        Integer count = partnerRepository.countByLoginEmail(loginEmail);
        return count > 0 ? false : true;
    }

}
