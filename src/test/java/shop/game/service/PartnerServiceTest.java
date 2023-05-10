package shop.game.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import shop.game.domain.Partner;
import shop.game.repository.PartnerRepository;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class PartnerServiceTest {

    @Autowired
    PartnerRepository partnerRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    public void join() {
        Partner partner = new Partner("아이디", "password");
        partnerRepository.save(partner);
    }

    @Test
    public void passwordEncoder() {
        String pw = "1234pasdfa1!";
        String encode = passwordEncoder.encode(pw);

        String pw2 = "1234pasdfa1!";
        String encode2 = passwordEncoder.encode(pw);

        Assertions.assertThat(encode).isNotEqualTo(encode2);

        boolean result = passwordEncoder.matches(pw, encode);
        Assertions.assertThat(result).isTrue();
    }

}