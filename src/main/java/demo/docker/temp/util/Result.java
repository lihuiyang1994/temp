package demo.docker.temp.util;

/**
 * lihui
 * 2022/3/16
 * Result
 *
 * @description Result
 */
public class Result {
    public static final Result BAD_REQUEST = new Result(400, "请求参数错误");
    public static final Result SERVER_ERROR = new Result(500, "内部服务调用错误");
    public static final Result SUCCESS = new Result(200, "success");

    private String sessionId;

    private int code;

    private String message;

    Result(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public Result sessionId(String sessionId) {
        this.sessionId = sessionId;
        return this;
    }

    public int getCode() {
        return code;
    }

    public String getSessionId() {
        return sessionId;
    }

    public String getMessage() {
        return message;
    }

}
