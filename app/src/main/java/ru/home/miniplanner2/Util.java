package ru.home.miniplanner2;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by privod on 24.08.2016.
 */
public class Util {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", new Locale("ru"));

    public static String dateToString(Date date) {
        return dateFormat.format(date);
    }
}
