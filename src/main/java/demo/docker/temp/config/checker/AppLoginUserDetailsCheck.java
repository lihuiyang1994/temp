package demo.docker.temp.config.checker;

import demo.docker.temp.dto.User;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * lihui
 * 2020/10/12
 * AppLoginUserDetailsCheck
 *
 * @description
 */
public abstract class AppLoginUserDetailsCheck extends UserDetailsCheckerAdapter {

    public void check(UserDetails userDetails) {
        checkApp((User) userDetails);
    }

    public abstract void checkApp(User user);

    public Class<? extends UserDetails> support() {
        return User.class;
    }
}
