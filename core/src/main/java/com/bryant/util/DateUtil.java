package com.bryant.util;

import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    public static Date getNow()
    {
        Calendar calendar = Calendar.getInstance();
        return calendar.getTime();
    }


    public static Date getToday()
    {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }
}
