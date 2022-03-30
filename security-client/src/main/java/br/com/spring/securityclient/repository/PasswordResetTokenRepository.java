package br.com.spring.securityclient.repository;

import br.com.spring.securityclient.entity.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken,Long> {

    Optional<PasswordResetToken> findByToken(String token);
}
