package xiaochen.jwt.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xiaochen.jwt.common.StatusCodeEnum;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author chentaikuang
 * 令牌长短转换工具类：模拟存储shortToken->getLongToken
 * 不让过长的jwt字符串进行网络传输
 * db实现方式：rowid -> token?注意失效时间（多次登录情况下，如果存储判断已存在token，则延长有效时间）
 */
public class TokenConvertUtil {

    private static Logger logger = LoggerFactory.getLogger(TokenConvertUtil.class);
    static ConcurrentHashMap<String, String> tokenCm = new ConcurrentHashMap<>();

    public static String getLongToken(String shortToken) {
        return tokenCm.get(shortToken);
    }

    public static String getShortToken(String longToken) {
        String shortToken = Math.abs(longToken.hashCode()) + getToken3Char(longToken);
        tokenCm.put(shortToken, longToken);
        printTokenCm();
        return shortToken;
    }

    /**
     * 获取token前中后三位字符
     *
     * @param longToken
     * @return
     */
    private static String getToken3Char(String longToken) {
        char f = longToken.charAt(0);
        char e = longToken.charAt(longToken.length() - 1);
        char m = longToken.charAt((longToken.length() - 1) / 2);
        StringBuffer charStr = new StringBuffer();
        return charStr.append(f).append(m).append(e).toString();
    }

    public static int rmToken(String shortToken) {
        tokenCm.remove(shortToken);
        printTokenCm();
        return StatusCodeEnum.SUCCESS.getCode();
    }

    private static void printTokenCm() {
        Iterator<Map.Entry<String, String>> itr = tokenCm.entrySet().iterator();
        while (itr.hasNext()){
            Map.Entry<String, String> map = itr.next();
            logger.info("-->> {}",map);
        }
    }
}
