package shop.game.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import shop.game.domain.Partner;
import shop.game.dto.LoginFormDto;
import shop.game.dto.PartnerJoinFormDto;
import shop.game.repository.PartnerRepository;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PartnerService {

    private final PartnerRepository partnerRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * 파트너 회원가입
     */
    @Transactional
    public Partner join(Partner partner) {

        return partnerRepository.save(partner);
    }

    /**
     * 파트너 아이디 중복키체크
     * @param loginEmail
     */
    public boolean isLoginEmailAvailable(String loginEmail) {
        Integer count = partnerRepository.countByLoginEmail(loginEmail);
        return count > 0 ? false : true;
    }

    /**
     * PartnerJoinFormDto -> Partner
     * @param partnerJoinFormDto
     */
    public Partner convertToPartner(PartnerJoinFormDto partnerJoinFormDto) {
        //비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(partnerJoinFormDto.getPassword());

        Partner partner = Partner.builder()
                .loginEmail(partnerJoinFormDto.getLoginId())
                .password(encodedPassword)
                .bankName(partnerJoinFormDto.getBankName())
                .bankAccount(partnerJoinFormDto.getBankAccount())
                .createdDate(LocalDateTime.now())
                .build();
        return partner;
    }

    /**
     * 로그인 처리
     * @param loginId
     * @param password
     * @return null 이면 로그인 실패
     */
    public Partner login(String loginId, String password) {
        Optional<Partner> findPartner = partnerRepository.findByLoginEmail(loginId);
        return findPartner
                .filter(partner -> passwordEncoder.matches(password, partner.getPassword()))
                .orElse(null);
    }

    public void rememberId(LoginFormDto loginFormDto, HttpServletResponse response) {
        Cookie cookie;
        if (loginFormDto.isLoginIdCheck()) {
            cookie = new Cookie("rememberId", loginFormDto.getLoginId());
            cookie.setMaxAge(60 * 60 * 24 * 30); //30일
        } else {
            cookie = new Cookie("rememberId", null);
            cookie.setMaxAge(0);
        }
        response.addCookie(cookie);
    }

}
