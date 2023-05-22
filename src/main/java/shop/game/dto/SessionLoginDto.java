package shop.game.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class SessionLoginDto {
    private Long id;
    private String loginEmail;

    @Builder
    public SessionLoginDto(Long id, String loginEmail) {
        this.id = id;
        this.loginEmail = loginEmail;
    }
}
