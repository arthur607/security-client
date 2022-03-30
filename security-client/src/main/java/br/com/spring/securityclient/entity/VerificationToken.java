package br.com.spring.securityclient.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import static javax.persistence.GenerationType.SEQUENCE;


@Entity @AllArgsConstructor @Getter @Table(name = "tbl_token")
public class VerificationToken implements Serializable {
    private static final long serialVersionUID = 2094294321170118998L;

    private static final int EXPIRATION_TIME = 10;
    @Id
    @GeneratedValue(strategy = SEQUENCE)
    private Long id;
    @Setter
    private String token;
    @OneToOne(targetEntity = UserEntity.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false,
                name = "user_id",
                foreignKey = @ForeignKey(name = "FK_USER_VERIFY_TOKEN"))
    @Setter
    private UserEntity user;
    private Date expiryDate;

    public VerificationToken(){
    }

    public VerificationToken(String token, UserEntity user) {
        this.token = token;
        this.user = user;
        this.expiryDate = calculateExpirationDate();
    }

    private Date calculateExpirationDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(new Date().getTime());
        calendar.add(Calendar.MINUTE, VerificationToken.EXPIRATION_TIME);
        return new Date(calendar.getTime().getTime());
    }
}
