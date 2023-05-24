package shop.game.enums;

import lombok.Getter;

@Getter
public enum GameType {
    GAME("게임본판"), DLC("DLC");

    private final String displayName;

    GameType(String displayName) {
        this.displayName = displayName;
    }
}
