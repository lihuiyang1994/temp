package demo.docker.temp.dto;

import demo.docker.temp.util.StringUtil;

/**
 * lihui
 * 2022/3/16
 * MessageDto
 *
 * @description 消息发送体
 */
public class MessageDto {

    private String tel;

    private String qos;

    private String userName;

    private String sessionId;

    private MessageContentDto content;

    public MessageContentDto getContent() {
        return content;
    }

    public void setContent(MessageContentDto content) {
        this.content = content;
    }

    public MessageDto(String tel, String qos, String userName, String sessionId) {
        this.tel = tel;
        this.qos = qos;
        this.userName = userName;
        this.sessionId = sessionId;
    }

    private static final String PHONE_RE = "^1(3|4|5|7|8)\\d{9}$";

    public boolean validate() {
        if (StringUtil.isBlank(tel) || !tel.matches(PHONE_RE)) {
            return false;
        }

        if (StringUtil.isBlank(sessionId)) {
            return false;
        }

        if (!"1".equals(qos) && !"2".equals(qos) && !"3".equals(qos)) {
            return false;
        }
        return true;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getQos() {
        return qos;
    }

    public void setQos(String qos) {
        this.qos = qos;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
}
