package br.com.spring.securityclient.controller;


import br.com.spring.securityclient.dto.PasswordModel;
import br.com.spring.securityclient.dto.RegisterRequest;
import br.com.spring.securityclient.entity.PasswordResetToken;
import br.com.spring.securityclient.entity.VerificationToken;
import br.com.spring.securityclient.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping()
@Slf4j
public class AuthController {

    private final UserService userService;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public String registerUser(@RequestBody final @Valid RegisterRequest userModel, final HttpServletRequest request) {
        userService.registerUser(userModel);
        return "Success";
    }
    @GetMapping("/hello")
    public String hello() {

        return "Hello";
    }

    @GetMapping("accountVerification/{token}")
    public ResponseEntity<String> accountVerification(@PathVariable String token) {
        userService.verifyAccount(token);
        return ResponseEntity.ok("Conta ativada com sucesso !");
    }

    @GetMapping("resendVerifyToken")
    public ResponseEntity<String> resendVerificationToken(@RequestParam("token") String oldToken,HttpServletRequest request) {
        userService.generateNewVerificationToken(oldToken);
        return ResponseEntity.ok("Token reenviado com sucesso!");
    }

    @PostMapping("/resetPassword")
    public ResponseEntity<String> resetPassword(@RequestBody PasswordModel passwordModel, HttpServletRequest request) {

        userService.resetPassword(passwordModel);
        return ResponseEntity.ok("Email enviado com sucesso!");
    }

    @PostMapping("/savePassword")
    public ResponseEntity<String> savePassword(@RequestParam("token") String token,
                               @RequestBody PasswordModel passwordModel) {
        PasswordResetToken result = userService.validatePasswordResetToken(token);

        userService.changePassword(result,passwordModel.getNewPassword());

        return ResponseEntity.ok("Senha alterada com sucesso!");
    }
}
