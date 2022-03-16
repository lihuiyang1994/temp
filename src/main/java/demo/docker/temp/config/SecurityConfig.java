package demo.docker.temp.config;

import demo.docker.temp.config.filter.SessionIdAuthFilter;
import demo.docker.temp.config.filter.UserNamePasswordAuthFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * lihui
 * 2022/3/16
 * SecurityConfig
 *
 * @description
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final RedisTemplate<String, String> redisTemplate;

    private final CustomUserDetailService detailService;

    private final AuthenticationManager authenticationManager;

    public SecurityConfig(RedisTemplate<String, String> redisTemplate, CustomUserDetailService detailService, AuthenticationManager authenticationManager) {
        this.redisTemplate = redisTemplate;
        this.detailService = detailService;
        this.authenticationManager = authenticationManager;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //权限认证
        http.authorizeHttpRequests()
                .antMatchers("/").permitAll().and().csrf().disable();
        http.userDetailsService(detailService);
        UserNamePasswordAuthFilter filter = new UserNamePasswordAuthFilter();
        SessionIdAuthFilter sessionIdAuthFilter = new SessionIdAuthFilter();
        sessionIdAuthFilter.setTemplate(redisTemplate);
        filter.setAuthenticationManager(authenticationManager);
        filter.setAuthenticationSuccessHandler(LoginHandler.successLoginHandler(redisTemplate));
        filter.setAuthenticationFailureHandler(LoginHandler.FAILED_LOGIN_HANDLER);
        http.addFilterAt(filter, UsernamePasswordAuthenticationFilter.class);
        http.addFilterAfter(sessionIdAuthFilter, UserNamePasswordAuthFilter.class);
    }

}
