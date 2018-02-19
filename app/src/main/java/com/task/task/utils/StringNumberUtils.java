package com.task.task.utils;


public class StringNumberUtils {

    public static boolean isEmpty(final CharSequence text) {
        return text == null || text.length() == 0;
    }

    public static String itOrDefault(final String text, final String defValue) {
        return !StringNumberUtils.isEmpty(text) ? text : defValue;
    }
}
