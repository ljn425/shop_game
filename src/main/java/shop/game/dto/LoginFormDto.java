package shop.game.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class LoginFormDto {
    @NotBlank
    private String loginId;
    @NotBlank
    private String loginPassword;
    private boolean loginIdCheck;
}
