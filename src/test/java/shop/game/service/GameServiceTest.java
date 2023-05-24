package shop.game.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;
import shop.game.WebConfig;
import shop.game.common.EntityAuditorAware;
import shop.game.constants.SessionConst;
import shop.game.domain.Game;
import shop.game.dto.GoodsRegisterFormDto;
import shop.game.dto.SessionLoginDto;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class GameServiceTest {

    @Autowired
    private ApplicationContext applicationContext;
        
    @Autowired
    private GameService gameService;
    private MockHttpSession session;

    @BeforeEach
    public void sessionLogin() {
        session = new MockHttpSession();
        SessionLoginDto sessionLoginDto = new SessionLoginDto(1L, "loginTest@test.com");
        session.setAttribute(SessionConst.LOGIN_PARTNER, sessionLoginDto);
    }

    @Test
    public void save() {
        SessionLoginDto sessionLogin = (SessionLoginDto) session.getAttribute(SessionConst.LOGIN_PARTNER);
        System.out.println("auditing 전 : sessionLogin: " + sessionLogin);
        GoodsRegisterFormDto goodsRegisterFormDto = new GoodsRegisterFormDto();
        goodsRegisterFormDto.setGameName("테스트");
        //gameService.save(goodsRegisterFormDto);
    }


}