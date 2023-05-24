package shop.game.enums;

import lombok.Getter;

@Getter
public enum CategoryType {
    RPG("RPG"), FPS("FPS"), RTS("전략시뮬레이션"), TPS("3인칭 슈팅");
    private String displayName;

    CategoryType(String displayName) {
        this.displayName = displayName;
    }
}
