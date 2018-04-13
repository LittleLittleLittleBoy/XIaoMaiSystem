package cn.edu.nju.candleflame.tickets.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TimeUtil {
    private static SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy HH:mm aaa", Locale.ENGLISH);

    public static Timestamp getTimeStamp(String time){
        try {
            Date date = formatter.parse(time);
            return new Timestamp(date.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }

    }
}
