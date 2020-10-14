package utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author lizilin
 */
public class DateUtils {

    public static String parseDate(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return simpleDateFormat.format(date);
    }

}
