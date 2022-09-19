package com.example.SSO_APP_VTP.util;

import java.text.DateFormat;
import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

public class Utils {

    public static Long dateToEpoch(Date date) {
        if (date == null)
            return null;
        return date.getTime();
    }

    public static String dateToStringByFormat(String format, Date date) {
        try {
            DateFormat dateFormat = new SimpleDateFormat(format);
            return dateFormat.format(date);
        } catch (Exception e) {
            return "";
        }
    }

    public static java.sql.Date StringToSqlDate(String d) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
            Date parsed = format.parse(d);
            java.sql.Date sql = new java.sql.Date(parsed.getTime());
            return sql;
        } catch (Exception ex) {

        }
        return null;
    }

    public static Date dateToDateFormat(String date, String strFormat) {
        try {
            SimpleDateFormat format = new SimpleDateFormat(strFormat);
            Date parsed = format.parse(date);
            java.sql.Date sql = new java.sql.Date(parsed.getTime());
            return sql;
        } catch (Exception ex) {

        }
        return null;
    }

    public static boolean isNullOrEmpty(Object input) {
        if (input instanceof String) {
            return input == null || ((String) input).trim().isEmpty();
        }

        if (input instanceof List) {
            return input == null || ((List) input).isEmpty();
        }
        return input == null;
    }

    public static String convertNoUnicodeNormal(String str) {
        try {
            str = str.trim();
            String temp = Normalizer.normalize(str, Normalizer.Form.NFD);
            Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
            return pattern.matcher(temp).replaceAll("").replaceAll("đ", "d").replaceAll("\u0111", "d").replaceAll("\u0110", "d");
        } catch (Exception e) {
            //ignored
        }
        return "";
    }

    public static String phoneHr(String phone) {
        StringBuilder result = null;
        if (phone != null && phone.length() >= 9) {
            try {
                String tmp = Long.valueOf(phone).toString();
                if (tmp.startsWith("84") && tmp.length() >= 11) {
                    result = new StringBuilder(tmp);
                } else {
                    result = new StringBuilder("84").append(tmp);
                }
            } catch (Exception e) {
            }
        }
        return result == null ? null : result.toString();
    }
}
