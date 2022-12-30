package com.project.simpleboard.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;

@Getter
@NoArgsConstructor
public class SignUpRequestDto {

    @Pattern(regexp = "^(?=.*[a-z])(?=.*\\d)[a-z\\d]{4,10}$", message = "아이디는 알파벳 소문자(a~z), 숫자(0~9)를 포함하며 4자 이상, 10자 이하여야 합니다.")
    private String username;

    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[(?=.*[^\\w\\s])])[A-Za-z\\d(?=.*[^\\w\\s])]{8,15}$", message = "비밀번호는 알파벳 대소문자(a~z, A~Z), 숫자(0~9), 특수 문자를 포함하며 8자 이상, 15자 이하여야 합니다.")
    private String password;

    private boolean admin = false;

    private String adminToken = "";

    public void adminSignUp(String adminToken) {
        this.admin = true;
        this.adminToken = adminToken;
    }

}
