package website.hehe.utils;

import lombok.Getter;

@Getter
public enum Operations {
    Null(""),
    Login("Login"),
    CheckLogin("Check Login");

    private final String operation;

    Operations(String operation) {
        this.operation = operation;
    }
}
