package shop.game.dto;

import lombok.Data;

@Data
public class LoginFormDto {
    private String loginId;
    private String loginPassword;
    private boolean loginIdCheck;
}
