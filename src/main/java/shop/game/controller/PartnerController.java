package shop.game.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import shop.game.constants.SessionConst;
import shop.game.constants.UrlConst;
import shop.game.constants.ViewConst;
import shop.game.domain.Partner;
import shop.game.dto.GoodsRegisterFormDto;
import shop.game.dto.LoginFormDto;
import shop.game.dto.PartnerJoinFormDto;
import shop.game.dto.SessionLoginDto;
import shop.game.enums.CategoryType;
import shop.game.enums.ErrorCode;
import shop.game.enums.GameType;
import shop.game.service.GameService;
import shop.game.service.PartnerService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/partner")
public class PartnerController {

    private final PartnerService partnerService;
    private final GameService gameService;

    @GetMapping("/main")
    public String main() {
        return ViewConst.PARTNER_MAIN;
    }

    /**
     * 파트너 로그인 폼
     * @param loginFormDto
     * @param model
     */
    @GetMapping("/login")
    public String loginForm(
            @CookieValue(value = "rememberId", required = false) String rememberId,
            LoginFormDto loginFormDto,
            Model model) {

        rememberIdCheck(rememberId, loginFormDto);
        model.addAttribute("loginFormDto", loginFormDto);
        return ViewConst.PARTNER_LOGIN;
    }

    private void rememberIdCheck(String rememberId, LoginFormDto loginFormDto) {
        if (rememberId != null) {
            loginFormDto.setLoginId(rememberId);
            loginFormDto.setLoginIdCheck(true);
        }
    }

    /**
     * 파트너 로그인 처리
     * @param loginFormDto
     */
    @PostMapping("/login")
    public String login(
            @Validated @ModelAttribute LoginFormDto loginFormDto,
            BindingResult bindingResult,
            @RequestParam(defaultValue = UrlConst.PARTNER_MAIN) String redirectURL,
            HttpServletRequest request,
            HttpServletResponse response
           ) {

        if (bindingResult.hasErrors()) {
            return ViewConst.PARTNER_LOGIN;
        }

        //로그인 처리
        Partner loginPartner = partnerService.login(loginFormDto.getLoginId(), loginFormDto.getLoginPassword());

        if (loginPartner == null) {
            bindingResult.reject(ErrorCode.LoginFail.getCodeName(), "아이디 또는 비밀번호가 맞지 않습니다.");
            return ViewConst.PARTNER_LOGIN;
        }

        //쿠키로 아이디 기억 & 기억삭제
        partnerService.rememberId(loginFormDto, response);

        //세션에 로그인 회원정보 보관
        SessionLoginDto sessionLoginDto = partnerService.convertToSessionLoginDto(loginPartner);
        request.getSession()
                .setAttribute(SessionConst.LOGIN_PARTNER, sessionLoginDto);

        log.debug("redirectURL = {}", redirectURL);

        return redirectURL.equals("/")? "redirect:" + UrlConst.PARTNER_MAIN : "redirect:" + redirectURL;
    }

    /**
     * 파트너 회원가입 폼
     * @param partnerJoinFormDto
     * @param model
     */
    @GetMapping("/join")
    public String joinForm(PartnerJoinFormDto partnerJoinFormDto, Model model) {
        model.addAttribute("partnerJoinFormDto", partnerJoinFormDto);
        return ViewConst.PARTNER_JOIN;
    }

    /**
     * 파트너 회원가입 처리
     * @param partnerJoinFormDto
     */
    @PostMapping("/join")
    public String join(@Validated @ModelAttribute PartnerJoinFormDto partnerJoinFormDto, BindingResult bindingResult) {
        log.debug("joinForm = {}", partnerJoinFormDto);

        //중복아이디 체크
        if (!partnerService.isLoginEmailAvailable(partnerJoinFormDto.getLoginId())) {
            bindingResult.addError(new ObjectError("partnerJoinFormDto", new String[]{ErrorCode.DuplicatedId.getCodeName()}, null, "아이디가 이미 존재합니다."));
            return ViewConst.PARTNER_JOIN;
        }

        if (bindingResult.hasErrors()) {
            log.debug("errors={}", bindingResult);
            return ViewConst.PARTNER_JOIN;
        }

        Partner partner = partnerService.convertToPartner(partnerJoinFormDto);

        partnerService.join(partner);

        return "redirect:/partner/join-finish";
    }

    /**
     * 회원가입완료 페이지
     */
    @GetMapping("/join-finish")
    public String joinFinish() {
        return ViewConst.PARTNER_JOIN_FINISH;
    }

    /**
     * 로그아웃
     * @param request
     */
    @PostMapping("/logout")
    public String logout(HttpServletRequest request) {
        log.debug("로그아웃");
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/partner/login";
    }

    /**
     * 게임 등록리스트
     */
    @GetMapping("/game/goods")
    public String goodsList() {
        return ViewConst.PARTNER_GAME_GOODS;
    }

    /**
     * 게임 등록폼
     */
    @GetMapping("/game/goods/register")
    public String goodsRegisterForm(GoodsRegisterFormDto registerFormDto, Model model) {
        model.addAttribute("registerFormDto", registerFormDto);
        model.addAttribute("gameTypes", GameType.values());
        model.addAttribute("categoryTypes", gameService.createMapFromCategoryEnums());
        return ViewConst.PARTNER_GAME_GOODS_REGISTER;
    }

    @PostMapping("/game/goods/register")
    public String goodsRegister(@Validated @ModelAttribute("registerFormDto") GoodsRegisterFormDto registerFormDto,
                                BindingResult bindingResult,
                                @SessionAttribute(SessionConst.LOGIN_PARTNER) SessionLoginDto sessionLoginDto,
                                Model model) throws IOException {
        log.debug("partner = {}", sessionLoginDto);
        log.debug("registerFormDto = {}", registerFormDto);
        log.debug("bindingResult = {}", bindingResult);

        if(bindingResult.hasErrors()) {
            model.addAttribute("categoryTypes", gameService.createMapFromCategoryEnums());
            return ViewConst.PARTNER_GAME_GOODS_REGISTER;
        }

        gameService.register(registerFormDto, sessionLoginDto);

        return "redirect:/partner/game/goods";
    }
}
