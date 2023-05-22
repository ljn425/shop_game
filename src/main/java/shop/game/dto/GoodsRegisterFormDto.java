package shop.game.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
import shop.game.enums.GameType;

@Data
public class GoodsRegisterFormDto {
    private String gameName;
    private String developer;
    private String publisher;
    private String description;
    //private GameType gameType;
    private MultipartFile thumbnail;
}
