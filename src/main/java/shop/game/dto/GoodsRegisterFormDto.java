package shop.game.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
import shop.game.common.annotation.ValidMultipartFile;
import shop.game.enums.CategoryType;
import shop.game.enums.GameType;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class GoodsRegisterFormDto {
    @NotBlank
    private String gameName;

    @NotBlank
    private String developer;

    @NotBlank
    private String publisher;

    @NotBlank
    private String description;

    @ValidMultipartFile
    private MultipartFile thumbnail;

    @Size(min =1, message = "최소 1개 이상의 카테고리를 선택해주세요.")
    private List<CategoryType> categoryTypes;
}
