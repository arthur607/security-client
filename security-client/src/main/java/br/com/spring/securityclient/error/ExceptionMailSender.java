package br.com.spring.securityclient.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.mail.MailException;
@Getter
public class ExceptionMailSender extends RuntimeException {

    private final Integer codigo;

    private final String mensagem;

    public ExceptionMailSender(String msgError, MailException e) {
        this.codigo = HttpStatus.INTERNAL_SERVER_ERROR.value();;
        this.mensagem = msgError;
    }
}
