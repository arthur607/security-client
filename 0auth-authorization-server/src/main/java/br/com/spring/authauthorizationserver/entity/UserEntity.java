package br.com.spring.authauthorizationserver.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;

import static java.util.Collections.singletonList;
import static javax.persistence.GenerationType.SEQUENCE;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity @NoArgsConstructor @AllArgsConstructor @Builder @Getter @Table(name = "tbl_user")
@NamedQueries({@NamedQuery(name = "user.findAll",query = "select c from UserEntity c")})
public class UserEntity implements Serializable, UserDetails {
    private static final long serialVersionUID = 1076203466408633327L;

    @Id @GeneratedValue(strategy = SEQUENCE) @EqualsAndHashCode.Include
    @SequenceGenerator(name = "sequence_user",sequenceName = "sequence_name_user",allocationSize = 53)
    private Long id;
    @Column(name = "user_name",nullable = false)
    private String name;
    @Column(name = "user_email",nullable = false,unique = true)
    private String email;
    @Column(name = "user_password",nullable = false,length = 60)
    @Setter
    private String password;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime created;
    private String role;
    @Setter
    private boolean enabled = false;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return singletonList(new SimpleGrantedAuthority("USER"));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }
}
