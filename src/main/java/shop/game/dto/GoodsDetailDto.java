package shop.game.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;
import shop.game.common.annotation.ValidMultipartFile;
import shop.game.domain.Category;
import shop.game.domain.GameImage;
import shop.game.domain.Movie;
import shop.game.domain.Spec;
import shop.game.enums.CategoryType;
import shop.game.enums.ImageType;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class GoodsDetailDto {

    private Long id;

    private String gameName;

    private String developer;

    private String publisher;

    private String description;
    private String storedFileName;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime created_date;

    private List<Category> categories = new ArrayList<>();

    private List<GameImage> gameImages = new ArrayList<>();

    private List<Movie> gameMovies = new ArrayList<>();

    private List<Spec> specs = new ArrayList<>();

    @QueryProjection
    public GoodsDetailDto(Long id, String gameName, String developer, String publisher, String description, String storedFileName, LocalDateTime created_date) {
        this.id = id;
        this.gameName = gameName;
        this.developer = developer;
        this.publisher = publisher;
        this.description = description;
        this.storedFileName = storedFileName;
        this.created_date = created_date;
    }
}
