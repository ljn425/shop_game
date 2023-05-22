package shop.game.enums;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ErrorCodeTest {

    @Test
    public void enumTest() {
        // given
        String codeName = "loginFail";
        // when
        ErrorCode errorCode = ErrorCode.LoginFail;
        // then
        assertEquals(codeName, errorCode.getCodeName());
    }

}