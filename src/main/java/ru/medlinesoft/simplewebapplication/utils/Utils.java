package ru.medlinesoft.simplewebapplication.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Вспомогательные методы.
 */
public abstract class Utils {
    
    public static DateFormat getDateFormat() {
        return new SimpleDateFormat("MMM dd, yyyy", Locale.ENGLISH);
    }
    
}
