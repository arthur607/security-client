package br.com.spring.securityclient.service;

import br.com.spring.securityclient.dto.NotificationEmail;
import br.com.spring.securityclient.dto.PasswordModel;
import br.com.spring.securityclient.dto.RegisterRequest;
import br.com.spring.securityclient.entity.PasswordResetToken;
import br.com.spring.securityclient.entity.UserEntity;
import br.com.spring.securityclient.entity.VerificationToken;
import br.com.spring.securityclient.error.UserAlreadyExistException;
import br.com.spring.securityclient.event.OnRegistrationCompleteEvent;
import br.com.spring.securityclient.repository.PasswordResetTokenRepository;
import br.com.spring.securityclient.repository.UserRepository;
import br.com.spring.securityclient.repository.VerificationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService{


    private final UserRepository repository;

    private final PasswordEncoder encoder;

    private final VerificationTokenRepository tokenRepository;

    private final ApplicationEventPublisher eventPublisher;

    private final PasswordResetTokenRepository resetTokenRepository;

    @Autowired
    public UserServiceImpl(UserRepository repository, PasswordEncoder encoder, VerificationTokenRepository tokenRepository, ApplicationEventPublisher eventPublisher, PasswordResetTokenRepository resetTokenRepository) {
        this.repository = repository;
        this.encoder = encoder;
        this.tokenRepository = tokenRepository;
        this.eventPublisher = eventPublisher;
        this.resetTokenRepository = resetTokenRepository;
    }

    @Override
    //@Async
    public void registerUser(RegisterRequest request) {
        if (emailExists(request.getEmail())){
            throw new UserAlreadyExistException("There is an account with that email address: "
                    + request.getEmail());
        }

        var save = repository.save(UserEntity.builder()
                .email(request.getEmail())
                .created(LocalDateTime.now())
                .password(encoder.encode(request.getPassword()))
                .role("USER")
                .name(request.getNome())
                .build());
        var token = generateVerificationToken(save);

        eventPublisher.publishEvent(new OnRegistrationCompleteEvent(new NotificationEmail("Please Active your Account",
                save.getEmail(),"Thank you for signing up to salsa-modas, " +
                "please click on the below url to activate your account : " +
                "http://localhost:8090/accountVerification/" + token)));
    }
    @Override
    public void verifyAccount(String token) {

        fetchUserAndEnable(tokenRepository.findByToken(token)
                .orElseThrow(() -> new EntityNotFoundException("invalid Token => " + token)));
    }

    @Override
    public void generateNewVerificationToken(String oldToken) {
        VerificationToken token = tokenRepository.findByToken(oldToken).orElseThrow();
        token.setToken(UUID.randomUUID().toString());
        tokenRepository.save(token);
        resendVerificationToken(token.getUser().getEmail(),token.getToken());
    }

    @Override
    public void resetPassword(PasswordModel passwordModel) {
        UserEntity UserByEmail = repository.findByEmail(passwordModel.getEmail()).orElseThrow(()
                -> new EntityNotFoundException("Email not found !"));
        createPasswordResetTokenForUser(UserByEmail, UUID.randomUUID().toString());
    }

    @Override
    public PasswordResetToken validatePasswordResetToken(String token) {
        PasswordResetToken passwordResetToken
                = resetTokenRepository.findByToken(token).orElseThrow(()
                -> new EntityNotFoundException("Email not found !"));

        Calendar cal = Calendar.getInstance();

        if ((passwordResetToken.getExpirationTime().getTime()
                - cal.getTime().getTime()) <= 0) {
            resetTokenRepository.delete(passwordResetToken);
            throw new RuntimeException("token expirado !");
        }
        return passwordResetToken;
    }

    @Override
    public void changePassword(PasswordResetToken result, String newPassword) {
        UserEntity user = result.getUser();
        user.setPassword(newPassword);
        repository.save(user);
    }

    private void createPasswordResetTokenForUser(UserEntity UserByEmail, String newToken) {

        resetTokenRepository.save(new PasswordResetToken(UserByEmail,newToken));

        eventPublisher.publishEvent(new OnRegistrationCompleteEvent(new NotificationEmail("Reset Password",
                UserByEmail.getEmail(), "Click the link to Reset your Password : " +
                "http://localhost:8090/savePassword?token=" + newToken)));
    }

    private void resendVerificationToken(String userEmail, String token) {
        eventPublisher.publishEvent(new OnRegistrationCompleteEvent(new NotificationEmail("Please Active your Account",
                userEmail,"Thank you for signing up to salsa-modas, " +
                "please click on the below url to activate your account : " +
                "http://localhost:8090/accountVerification/" + token)));
    }

    @Transactional
    private void fetchUserAndEnable(VerificationToken verificationToken) {
        if (verificationToken.getUser().isEnabled()){
            throw new RuntimeException("Conta já está habilitada !");
        }
        Calendar cal = Calendar.getInstance();
        if (verificationToken.getExpiryDate().getTime() - cal.getTime().getTime() <= 0){
            tokenRepository.delete(verificationToken);
            throw new RuntimeException("token expirado !");
        }
        UserEntity user = verificationToken.getUser();
        user.setEnabled(true);
        repository.save(user);
    }

    private boolean emailExists(String email) {
        return repository.findByEmail(email).isPresent();
    }
    private String generateVerificationToken(UserEntity user){

//        TreeSet<String> strings = new TreeSet<>();
//        strings.add()

        String token = UUID.randomUUID().toString();

        tokenRepository.save(new VerificationToken(token,user));

        return token;
    }
}
