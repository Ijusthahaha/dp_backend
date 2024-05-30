package website.hehe.utils;

import lombok.Getter;

@Getter
public enum Operations {
    Null(""),
    Login("Login"),
    CheckLogin("Check Login"),
    ChangePassword("Change Password"),
    Get("Get User"),
    Insert("Insert User"),
    Modify("Modify User"),
    Delete("Delete User"),
    Ban("Ban Operation");

    private final String operation;

    Operations(String operation) {
        this.operation = operation;
    }
}
