package com.app.flashgram.auth.appliction.dto;

public record CreateUserAuthRequestDto(String email, String password, String role, String name, String profileImgUrl) {

}
