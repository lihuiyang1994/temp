package demo.docker.temp.config;

import demo.docker.temp.dto.User;
import demo.docker.temp.exception.UserValidateException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * lihui
 * 2022/3/16
 * CustomUserDetailService
 *
 * @description
 */
@Service
public class CustomUserDetailService implements UserDetailsService {

    private final RedisTemplate<String, String> redisTemplate;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Boolean exist = redisTemplate.hasKey(username);
        if (exist == null || !exist) {
            //用户名不存在
            throw new UserValidateException("用户名不存在");
        }
        String password = redisTemplate.boundValueOps(username).get();
        return new User(username, password);
    }


    public CustomUserDetailService(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }
}
