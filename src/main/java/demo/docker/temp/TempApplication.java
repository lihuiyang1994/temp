package demo.docker.temp;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import demo.docker.temp.config.CustomUserDetailService;
import demo.docker.temp.config.TempManagerProvider;
import demo.docker.temp.config.checker.DefaultAppLoginUserDetailsCheck;
import demo.docker.temp.config.checker.PreUserAuthenticationChecksList;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.authentication.AnonymousAuthenticationProvider;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@EnableAsync
public class TempApplication {

    public static void main(String[] args) {
        SpringApplication.run(TempApplication.class, args);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }


    @Bean
    public AuthenticationManager authenticationManager(RedisTemplate<String, String > redisTemplate){
        List<AuthenticationProvider> providers = new ArrayList<>();
        DaoAuthenticationProvider daoAuthenticationProvider = new TempManagerProvider();
        daoAuthenticationProvider.setUserDetailsService(new CustomUserDetailService(redisTemplate));
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setPreAuthenticationChecks(userAuthenticationChecksList());
        providers.add(daoAuthenticationProvider);
        providers.add(new AnonymousAuthenticationProvider("default"));
        return new ProviderManager(providers);
    }

    /**
     * 初始化checker
     * @return 用户验证checks
     */
    public PreUserAuthenticationChecksList userAuthenticationChecksList(){
        PreUserAuthenticationChecksList userAuthenticationChecksList = new PreUserAuthenticationChecksList();
        userAuthenticationChecksList.addChecker(new DefaultAppLoginUserDetailsCheck());
        return userAuthenticationChecksList;
    }
}
