package demo.docker.temp.config.checker;

import demo.docker.temp.dto.User;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;

/**
 * lihui
 * DefaultAppLoginUserDetailsCheck
 * 默认 LoginAppUser 登录判断类
 */
public class DefaultAppLoginUserDetailsCheck extends AppLoginUserDetailsCheck {

    public void checkApp(User user) {

        if (!user.isAccountNonLocked()) {

            throw new LockedException("您的账户暂时被冻结，请耐心等待");
        }

        if (!user.isEnabled()) {

            throw new DisabledException("您的账户不可用");
        }

        if (!user.isAccountNonExpired()) {
            throw new AccountExpiredException("您的账户已过期，请重新登录");
        }


    }
}
