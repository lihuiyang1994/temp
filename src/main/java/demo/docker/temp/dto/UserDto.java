package demo.docker.temp.dto;

import demo.docker.temp.util.StringUtil;

/**
 * lihui
 * 2022/3/16
 * UserDto
 *
 * @description 用户表
 */
public class UserDto {
    //用户名密码验证错误信息
    private static final String PWD_BLANK_ERROR = "密码不能为空";
    private static final String NAME_BLANK_ERROR = "用户名不能为空";

    private static final String NAME_LEN_ERROR = "请输入3-32长度的用户名";
    private static final String PWD_LEN_ERROR = "请输入8-64长度的用户名";

    private static final String NAME_FORMAT_ERROR = "用户名只允许字符与大小写字母组合";
    private static final String PWD_FORMAT_ERROR = "密码只允许字符与大小写字母组合";


    private String sessionId;

    private String userName;

    private String password;

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String validate() {
        if (StringUtil.isBlank(userName)) {
            return NAME_BLANK_ERROR;
        }
        if (StringUtil.isBlank(password)) {
            return PWD_BLANK_ERROR;
        }

        if (!StringUtil.lenMatch(password, 8, 64)) {
            return PWD_LEN_ERROR;
        }

        if (!StringUtil.lenMatch(userName, 3, 32)) {
            return NAME_LEN_ERROR;
        }

        if (!StringUtil.numberFormatMatch(password)) {
            return PWD_FORMAT_ERROR;
        }

        if (!StringUtil.numberFormatMatch(userName)) {
            return NAME_FORMAT_ERROR;
        }
        return null;
    }


}
