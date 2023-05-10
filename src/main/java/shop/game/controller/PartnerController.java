package shop.game.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import shop.game.domain.Partner;
import shop.game.dto.LoginFormDto;
import shop.game.dto.PartnerJoinFormDto;
import shop.game.service.PartnerService;

import java.time.LocalDateTime;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/partner")
public class PartnerController {

    private final PartnerService partnerService;
    private final PasswordEncoder passwordEncoder;

    /**
     * 파트너 로그인 폼
     * @param loginFormDto
     * @param model
     */
    @GetMapping("/login")
    public String loginForm(LoginFormDto loginFormDto, Model model) {
        model.addAttribute("loginForm", loginFormDto);
        return "partner/login";
    }

    /**
     * 파트너 로그인 처리
     * @param loginFormDto
     */
    @PostMapping("/login")
    public String login(@ModelAttribute LoginFormDto loginFormDto) {
        log.debug("loginFormDto = {}", loginFormDto);
        //todo: 로그인세션 처리, 인터셉터
        return "redirect:/";
    }

    /**
     * 파트너 회원가입 폼
     * @param partnerJoinFormDto
     * @param model
     */
    @GetMapping("/join")
    public String joinForm(PartnerJoinFormDto partnerJoinFormDto, Model model) {
        model.addAttribute("joinForm", partnerJoinFormDto);
        return "partner/join";
    }

    /**
     * 파트너 회원가입 처리
     * @param partnerJoinFormDto
     */
    @PostMapping("/join")
    public String join(@ModelAttribute PartnerJoinFormDto partnerJoinFormDto) {
        log.debug("partnerJoinFormDto = {}", partnerJoinFormDto);

        //중복아이디 체크
        if (!partnerService.isLoginEmailAvailable(partnerJoinFormDto.getLoginId())) {
            throw new DuplicateKeyException("중복아이디 예외 발생");
//            return "partner/join";
        }

        Partner partner = convertToPartner(partnerJoinFormDto);

        partnerService.join(partner);

        return "redirect:/partner/join-finish";
    }

    /**
     * PartnerJoinFormDto -> Partner
     * @param partnerJoinFormDto
     * @return Partner
     */
    private Partner convertToPartner(PartnerJoinFormDto partnerJoinFormDto) {
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

    @GetMapping("/join-finish")
    public String joinFinish() {
        return "partner/join-finish";
    }

}
