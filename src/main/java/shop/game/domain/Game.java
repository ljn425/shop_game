package shop.game.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.game.enums.GameType;

import javax.persistence.*;

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
    @Builder
    public Game(Partner partner, String name, String developer, String publisher, String description) {
        this.partner = partner;
        this.name = name;
        this.developer = developer;
        this.publisher = publisher;
        this.description = description;
    }
}
