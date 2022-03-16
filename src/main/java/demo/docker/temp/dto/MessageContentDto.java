package demo.docker.temp.dto;

import demo.docker.temp.util.StringUtil;

/**
 * lihui
 * 2022/3/16
 * MessageContentDto
 *
 * @description
 */
public class MessageContentDto {

    private String title;

    private String content;

    public MessageContentDto(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public boolean validate() {
        if (StringUtil.isBlank(title)) {
            return false;
        }

        if (StringUtil.isBlank(content)) {
            return false;
        }
        return true;
    }
}
