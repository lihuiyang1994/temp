package demo.docker.temp.util;

import com.alibaba.fastjson.JSONObject;
import demo.docker.temp.dto.MessageDto;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.concurrent.Callable;

/**
 * lihui
 * 2022/3/16
 * SMSTask
 *
 * @description
 */
public class SMSTask implements Callable<Integer>, Comparable<SMSTask>{

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private MessageDto msg;

    private String url;


    public void setUrl(String url) {
        this.url = url;
    }

    public SMSTask(MessageDto msg, String url) {
        this.msg = msg;
        this.url = url;
    }

    public void setMsg(MessageDto msg) {
        this.msg = msg;
    }

    @Override
    public Integer call() {
        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(url);
        JSONObject obj = new JSONObject();
        obj.put("qos", msg.getQos());
        obj.put("acceptor_tel", msg.getTel());
        obj.put("template_param", msg.getContent());
        obj.put("timestamp", LocalDateTime.now().format(DATE_TIME_FORMATTER));
        ByteArrayEntity entity = new ByteArrayEntity(obj.toString().getBytes(StandardCharsets.UTF_8));
        entity.setContentType("application/json");
        post.setEntity(entity);
        try {
            HttpResponse response = client.execute(post);
            return response.getStatusLine().getStatusCode();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 500;
    }

    @Override
    public int compareTo(SMSTask o) {
        return msg.getQos().compareTo(o.msg.getQos());
    }
}
