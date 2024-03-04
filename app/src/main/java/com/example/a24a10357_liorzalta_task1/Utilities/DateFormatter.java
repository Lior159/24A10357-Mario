package com.example.a24a10357_liorzalta_task1.Utilities;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class DateFormatter {
    public static String getDatetime(){
        ZoneId israelTimeZone = ZoneId.of("Asia/Jerusalem");

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now(israelTimeZone);
        return dtf.format(now);
    }
}
