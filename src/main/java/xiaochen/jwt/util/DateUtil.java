package xiaochen.jwt.util;

import java.util.Calendar;
import java.util.Date;

/**
 * @author chentaikuang
 */
public class DateUtil {

    public static Date atTomorrow() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
//        calendar.add(Calendar.DATE, 1);
        calendar.add(Calendar.MINUTE,2);
        return calendar.getTime();
    }
}
