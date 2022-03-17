package demo.docker.temp.config;

import com.alibaba.fastjson.JSONObject;
import demo.docker.temp.dto.User;
import demo.docker.temp.util.Result;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

/**
 * lihui
 * 2022/3/16
 * LoginHandler
 *
 * @description 登录handler
 */
public class LoginHandler {

    public final static FailedLoginHandler FAILED_LOGIN_HANDLER = new FailedLoginHandler();

    public static SuccessLoginHandler successLoginHandler(RedisTemplate<String, String> redisTemplate) {
        return new SuccessLoginHandler(redisTemplate);
    }
    //登录成功后
    static class SuccessLoginHandler implements AuthenticationSuccessHandler {

        private static final String SESSION_KEY = ":session";

        public SuccessLoginHandler successLoginHandler;

        private final RedisTemplate<String, String> redisTemplate;

        @Override
        public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
            //登录成功
            response.setStatus(200);
            UUID uuid = UUID.randomUUID();
            String sessionId = uuid.toString().replaceAll("-", "");
            User user = (User) authentication.getPrincipal();
            redisTemplate.boundValueOps(sessionId).set(JSONObject.toJSONString(user.getUsername()));

            response.getWriter().write(JSONObject.toJSONString(Result.SUCCESS.sessionId(sessionId)));
        }

        private SuccessLoginHandler(RedisTemplate<String, String> redisTemplate) {
            this.redisTemplate = redisTemplate;
        }
    }
    //登录失败
    static class FailedLoginHandler implements AuthenticationFailureHandler {

        @Override
        public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
            //登录失败
            response.setStatus(400);
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(JSONObject.toJSONString(Result.BAD_REQUEST));
        }
    }
}
