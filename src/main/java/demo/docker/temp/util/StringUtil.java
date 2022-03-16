package demo.docker.temp.util;

/**
 * lihui
 * 2022/3/16
 * StringUtil
 *
 * @description String 工具类
 */
public class StringUtil {

    private static final String NUMBER_MATCH_RE = "[A-Za-z0-9-]+";


    public static boolean isBlank(final CharSequence cs) {
        int strLen;
        if (cs == null || (strLen = cs.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(cs.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean lenMatch(String password, int minLen, int maxLen) {
        return password.length() >= minLen && password.length() <= maxLen;
    }


    public static boolean numberFormatMatch(String password) {
        return password.matches(NUMBER_MATCH_RE);
    }
}
