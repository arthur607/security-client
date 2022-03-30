package br.com.spring.securityclient.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@AllArgsConstructor @Getter
public class RegisterRequest {
    @NotBlank(message = "name is required")
    @NotNull
    @NotEmpty
    private String nome;
    @NotNull
    @NotEmpty
    private String password;
    @NotNull
    @NotEmpty
    private String matchingPassword;

    @NotNull
    @NotEmpty
    @NotBlank(message = "email is required")
    private String email;

    public RegisterRequest() {
    }
}
