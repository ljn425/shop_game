package shop.game.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.*;

@Data
public class PartnerJoinFormDto {
    @Email
    @NotBlank
    private String loginId;

    @NotBlank
    private String password;

    @NotBlank
    private String bankName;

    @NotBlank
    @Size(max = 16)
    private String bankAccount;
}
