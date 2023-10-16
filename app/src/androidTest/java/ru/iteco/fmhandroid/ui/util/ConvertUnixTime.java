package ru.iteco.fmhandroid.ui.util;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class ConvertUnixTime {
    public static String convert(long unix, String type) {

        Instant instant = Instant.ofEpochSecond(unix);

        ZoneId zoneId = ZoneId.of("Asia/Omsk");
        String formattedDateTime = null;
        if (type == "DATE") {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
                    .withZone(zoneId);
            formattedDateTime = formatter.format(instant);
        } else if (type == "TIME") {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm")
                    .withZone(zoneId);
            formattedDateTime = formatter.format(instant);
        }
        return formattedDateTime;
    }
}
