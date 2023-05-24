package shop.game.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.game.enums.GameType;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class Game extends Base{

    @Id @GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
    @Column(name="game_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "partner_id")
    private Partner partner;
    private String name;
    private String developer;
    private String publisher;
    @Lob
    private String description;
    @Enumerated(EnumType.STRING)
    private GameType gameType;

    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL)
    private List<GameImage> gameImages = new ArrayList<>();

    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL)
    private List<Category> categories = new ArrayList<>();

    @Builder
    public Game(Partner partner, String name, String developer, String publisher, String description, GameType gameType) {
        this.partner = partner;
        this.name = name;
        this.developer = developer;
        this.publisher = publisher;
        this.description = description;
        this.gameType = gameType;
    }
}
