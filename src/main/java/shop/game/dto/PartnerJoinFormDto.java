package shop.game.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class PartnerJoinFormDto {
    private String loginId;
    private String password;
    private String bankName;
    private String bankAccount;
}
