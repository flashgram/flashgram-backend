package com.app.flashgram.auth.domain;

public class UserAuth {

    private final Email email;
    private final Password password;
    private final UserRole userRole;
    private Long userId;

    public UserAuth(String email, String password, String role) {
        this.email = Email.createEmail(email);
        this.password = Password.creatEncryptPassword(password);
        this.userRole = UserRole.valueOf(role);
    }

    public UserAuth(String email, String password, String userRole, long userId) {
        this.email = Email.createEmail(email);
        this.password = Password.creatEncryptPassword(password);
        this.userRole = UserRole.valueOf(userRole);
        this.userId = userId;
    }

    public String getEmail() {

        return email.getEmailText();
    }

    public String getPassword() {

        return password.getEncryptPassword();
    }

    public String getUserRole() {

        return userRole.name();
    }

    public Long getUserId() {

        return userId;
    }

}
