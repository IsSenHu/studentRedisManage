package ecjtu.husen.exception;

import org.apache.shiro.authc.AuthenticationException;

/**
 * @author 11785
 */
public class UserHadForbbidenException extends AuthenticationException {
    public UserHadForbbidenException() {
        super();
    }
    public UserHadForbbidenException(String message) {
        super(message);
    }
}
