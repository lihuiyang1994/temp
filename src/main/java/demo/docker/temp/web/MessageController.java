package demo.docker.temp.web;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import demo.docker.temp.dto.MessageContentDto;
import demo.docker.temp.dto.MessageDto;
import demo.docker.temp.util.Result;
import demo.docker.temp.util.SMSTask;
import demo.docker.temp.util.SMSThreadPoolExecutor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Vector;
import java.util.concurrent.*;

/**
 * lihui
 * 2022/3/16
 * MessageController
 *
 * @description
 */
@Controller
public class MessageController {

    @Value("${sms.host}")
    private String host;

    @Value("${sms.port}")
    private Integer port;

    private final RedisTemplate<String, String> template;

    public MessageController(RedisTemplate<String, String> template) {
        this.template = template;
    }

    private  static final ThreadPoolExecutor executor = new SMSThreadPoolExecutor(15, 30, 1, TimeUnit.MICROSECONDS, new PriorityBlockingQueue<>());


    @PostMapping("directmessage")
    @SentinelResource("message")
    public ResponseEntity<Result> directMessage(@RequestBody MessageContentDto dto) throws ExecutionException, InterruptedException {
        if (dto.validate()) {
            MessageDto messageDto = getMessageDto();
            String tel = messageDto.getTel();
            boolean exist = template.hasKey(tel);
            if (!exist) {
                //发送消息
                messageDto.setContent(dto);
                template.boundValueOps(tel).set("od",1L, TimeUnit.SECONDS);
                Future<Integer> response = executor.submit(new SMSTask(messageDto, "http://" + host + ":" + port + "/v2/emp/templateSms/sendSms"));
                Integer status = response.get();
                if (status!=200) {
                    return ResponseEntity.status(400).body(Result.SERVER_ERROR);
                } else {
                    return ResponseEntity.ok(Result.SUCCESS);
                }
            }
        }
        return ResponseEntity.status(400).body(Result.BAD_REQUEST);
    }

    public MessageDto getMessageDto() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof AnonymousAuthenticationToken) {
            AnonymousAuthenticationToken token = (AnonymousAuthenticationToken) authentication;
            return (MessageDto) token.getPrincipal();
        }

        return null;
    }
}
