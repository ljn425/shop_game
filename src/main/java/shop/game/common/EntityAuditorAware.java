package shop.game.common;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;
import shop.game.constants.SessionConst;
import shop.game.dto.SessionLoginDto;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class EntityAuditorAware implements AuditorAware<String> {

    private final HttpSession session;

    @Override
    public Optional<String> getCurrentAuditor() {
        System.out.println("getCurrentAuditor() called");
        if (session == null) {
            return Optional.empty();
        }

        SessionLoginDto sessionLoginDto= (SessionLoginDto) session.getAttribute(SessionConst.LOGIN_PARTNER);
        if (session != null) {
            return Optional.ofNullable((String) sessionLoginDto.getLoginEmail());
        }

        return Optional.empty();
    }
}
