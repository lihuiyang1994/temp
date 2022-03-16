package demo.docker.temp.web;

import demo.docker.temp.dto.UserDto;
import demo.docker.temp.util.Result;
import demo.docker.temp.util.StringUtil;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * lihui
 * 2022/3/16
 * AuthController
 *
 * @description 用户认证以及登录接口
 */
@Controller
public class AuthController {

    private final RedisTemplate<String, String> template;

    private final PasswordEncoder encoder;

    private final static String SAME_NAME = "用户名已经存在";

    // /auth/userDto/register:  用户注册接口
    @PostMapping("auth/user/register")
    public ResponseEntity<Result> userRegister(@RequestBody UserDto userDto) {
        String validateMsg = userDto.validate();
        if (StringUtil.isBlank(validateMsg)) {
            //存储
            Boolean exit = template.hasKey(userDto.getUserName());
            if (exit == null || exit) {
                return ResponseEntity.status(400).body(Result.BAD_REQUEST);
            } else {
                template.boundValueOps(userDto.getUserName()).set(encoder.encode(userDto.getPassword()));
                return ResponseEntity.ok(Result.SUCCESS);
            }
        } else {
            return ResponseEntity.status(400).body(Result.BAD_REQUEST);
        }
    }

    @GetMapping("auth/user/logout")
    public ResponseEntity<Result> logout(@RequestBody UserDto userDto) {
        String userName = userDto.getUserName();
        String sessionId = userDto.getSessionId();
        boolean existSession = template.hasKey(sessionId);
        if (existSession) {
            String ru = template.boundValueOps(sessionId).get();
            boolean equals = ru.equals(userName);
            if (equals) {
                //退出
                template.delete(sessionId);
                return ResponseEntity.ok(Result.SUCCESS);
            }
        }
        return ResponseEntity.status(400).body(Result.BAD_REQUEST);
    }

    public AuthController(RedisTemplate<String, String> template, PasswordEncoder encoder) {
        this.template = template;
        this.encoder = encoder;
    }
}
