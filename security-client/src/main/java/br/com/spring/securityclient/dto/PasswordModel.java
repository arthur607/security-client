package br.com.spring.securityclient.dto;

import lombok.Getter;

@Getter
public class PasswordModel {
        private String email;
        private String oldPassword;
        private String newPassword;
}

