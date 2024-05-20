package website.hehe.utils.result;

import lombok.Getter;

@Getter
public enum ResultEnum {
    SUCCESS(200, "Operation successful."),
    ACCOUNT_EXPIRED(500, "Account expired."),
    ACCOUNT_BANNED(500, "Account has been banned."),
    PASSWORD_ERROR(500, "Username or password error."),
    NOTLOGIN(500, "User not login."),
    USERNAME_USED(500, "Username already used.");

    private final Integer code;
    private final String message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
