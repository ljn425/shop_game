package shop.game.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.game.enums.OsType;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Spec extends Base{
    @Id @GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
    @Column(name="spec_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_id")
    private Game game;

    @Enumerated(EnumType.STRING)
    private OsType os;
    private String processor;
    private String memory;
    private String graphics;
    private String storage;
    private String directX;
    private String network;
    private String soundCard;

    @Lob
    private String additionalNotes;
}
