package xiaochen.jwt.util;

import org.springframework.util.StringUtils;

public class MyStrUtil extends StringUtils {

    public static boolean isNotBlank(String string) {
        if (string == null || string.length() == 0) {
            return false;
        }
        return true;
    }

    public static boolean isBlank(String string) {
        if (string == null || string.length() == 0) {
            return true;
        }
        return false;
    }


}
