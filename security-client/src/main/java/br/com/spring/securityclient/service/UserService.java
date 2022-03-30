package br.com.spring.securityclient.service;

import br.com.spring.securityclient.dto.PasswordModel;
import br.com.spring.securityclient.dto.RegisterRequest;
import br.com.spring.securityclient.entity.PasswordResetToken;

public interface UserService {

    void registerUser(RegisterRequest request);

    void verifyAccount(String token);

    void generateNewVerificationToken(String oldToken);

    void resetPassword(PasswordModel passwordModel);

    PasswordResetToken validatePasswordResetToken(String token);

    void changePassword(PasswordResetToken result, String newPassword);
}
