package shop.game.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
import shop.game.enums.GameType;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class GoodsRegisterFormDto {
    @NotNull
    private String gameName;
    @NotBlank
    private String developer;
    @NotBlank
    private String publisher;

    private String description;
    //private GameType gameType;

    private MultipartFile thumbnail;
}
