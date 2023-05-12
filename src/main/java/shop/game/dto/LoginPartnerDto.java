package shop.game.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class LoginPartnerDto {
    private String loginId;

    public LoginPartnerDto(String loginId) {
        this.loginId = loginId;
    }
}
