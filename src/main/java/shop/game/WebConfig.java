package shop.game;

import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.web.context.annotation.SessionScope;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import shop.game.common.EntityAuditorAware;
import shop.game.interceptor.LoginCheckInterceptor;

import javax.servlet.http.HttpSession;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginCheckInterceptor())
                .order(1)
                .addPathPatterns("/**")
                .excludePathPatterns( "/assets/**", "/css/*", "/js/*", "/*.ico", "/error", "/partner/login", "/partner/join", "/partner/join-finish", "/h2-console/**");
    }

}
