package br.com.spring.securityclient.error;

public class UserAlreadyExistException extends BaseLoginException {

    public UserAlreadyExistException(String email) {
        super(email);
    }

    public UserAlreadyExistException(BaseLoginException e) {
        super(e);
    }

    public UserAlreadyExistException(BaseLoginException e, String message, String localizedMessage) {
        super(e, message, localizedMessage);
    }

    public UserAlreadyExistException(UserAlreadyExistException e, String message) {
        super(e, message);
        System.out.println("error " + message);
    }
}
