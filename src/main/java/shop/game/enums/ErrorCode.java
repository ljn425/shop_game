package shop.game.enums;

public enum ErrorCode {
    LoginFail("loginFail"),
    DuplicatedId("duplicatedId");

    private final String codeName;

    ErrorCode(String codeName) {
        this.codeName = codeName;
    }

    public String getCodeName() {
        return codeName;
    }
}
