package br.com.spring.securityclient.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST,reason = "User already exists", code = HttpStatus.BAD_REQUEST)
public abstract class BaseLoginException extends RuntimeException{
    public BaseLoginException(String email) {
        super(email);
    }

    public BaseLoginException(BaseLoginException e) {
        super(e);
    }

    public BaseLoginException(BaseLoginException e, String message, String localizedMessage) {
        super(message,e);
    }

    public BaseLoginException(UserAlreadyExistException e, String message) {
        super(message,e);
    }
}
