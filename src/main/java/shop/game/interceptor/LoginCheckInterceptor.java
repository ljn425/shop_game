package shop.game.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import shop.game.constants.SessionConst;
import shop.game.domain.Partner;
import shop.game.dto.LoginPartnerDto;
import shop.game.dto.SessionLoginDto;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Slf4j
public class LoginCheckInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String requestURI = request.getRequestURI();

        HttpSession session = request.getSession(false);
        Object loginPartner = null;

        if (session != null ) {
            loginPartner = session.getAttribute(SessionConst.LOGIN_PARTNER);
        }

        if (session == null || loginPartner == null) {
            log.debug("미인증 사용자 요청");
            log.debug("requestURI = {}", requestURI);
            response.sendRedirect("/partner/login?redirectURL=" + requestURI);
            return false;
        }

        if (loginPartner != null) {
            log.debug("인증 사용자 요청");
            SessionLoginDto partner = (SessionLoginDto) loginPartner;
            request.setAttribute("partner", new LoginPartnerDto(partner.getLoginEmail()));
        }

        return true;
    }
}
