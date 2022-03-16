package demo.docker.temp.config.checker;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsChecker;

/**
 * lihui
 * UserDetailsCheckerAdapter
 *
 * @description
 */
public abstract class UserDetailsCheckerAdapter implements UserDetailsChecker {

    public abstract Class<? extends UserDetails> support();
}
