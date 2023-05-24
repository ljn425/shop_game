package shop.game.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.game.enums.CategoryType;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category extends Base {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_id")
    private Game game;

    @Enumerated(EnumType.STRING)
    private CategoryType categoryType;
    @Builder
    public Category(Game game, CategoryType categoryType) {
        this.game = game;
        this.categoryType = categoryType;
    }

    public void changeGame(Game newGame){
      if(newGame == null) {
          throw new IllegalArgumentException("New game cannot be null");
      }
      if (game != null) {
          game.getCategories().remove(this);
      }

      game = newGame;
      game.getCategories().add(this);
    }
}
