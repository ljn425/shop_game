package shop.game.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.game.enums.ImageType;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class GameImage extends Base{
    @Id @GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
    @Column(name="game_image_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_id")
    private Game game;

    private String originalName;
    private String storedName;
    private String path;
    private Long size;
    private String extension;

    @Enumerated(EnumType.STRING)
    private ImageType imageType;

    @Builder
    public GameImage(Game game, String originalName, String storedName, Long size, String extension, ImageType imageType) {
        this.game = game;
        this.originalName = originalName;
        this.storedName = storedName;
        this.size = size;
        this.extension = extension;
        this.imageType = imageType;
    }
}
