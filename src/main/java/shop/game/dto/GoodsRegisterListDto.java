package shop.game.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
public class GoodsRegisterListDto {
    private Long id;
    private String thumbnailName;
    private String gameName;
    private String developer;
    private String publisher;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdDate;

    @QueryProjection
    public GoodsRegisterListDto(Long id, String thumbnailName, String gameName, String developer, String publisher, LocalDateTime created_date) {
        this.id = id;
        this.thumbnailName = thumbnailName;
        this.gameName = gameName;
        this.developer = developer;
        this.publisher = publisher;
        this.createdDate = created_date;
    }
}
