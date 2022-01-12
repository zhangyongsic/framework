package com.zhangyongsic.framework.lib.helper;

import org.apache.commons.lang3.time.DateUtils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class DateHelper extends DateUtils {

    /**
     *
     * @param date
     * @return
     */
    public static Date getLastTimeOfDay(Date date){
        return Date.from(LocalDateTime.ofInstant(Instant.ofEpochMilli(date.getTime()), ZoneId.systemDefault())
                .withHour(23).withMinute(59).withSecond(59)
                //mysql毫秒数超过500将加1秒
                .withNano(0)
                .atZone(ZoneId.systemDefault()).toInstant());
    }

    public static Date getLastTimeOfDay(){
        return getLastTimeOfDay(new Date());
    }


    public static void main(String[] args) {
        System.out.println(getLastTimeOfDay().getTime());
    }
}
