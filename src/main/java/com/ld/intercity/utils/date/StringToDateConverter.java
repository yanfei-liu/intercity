package com.ld.intercity.utils.date;


import org.springframework.core.convert.converter.Converter;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author ld
 * @name
 * @table
 * @remarks
 */
public class StringToDateConverter implements Converter<String, Date> {
    private static final String DATEFORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final String SHORT_DATE_FORMAT = "yyyy-MM-dd";
    private static final String DATE_FORMAT2 = "yyyy/MM/dd HH:mm:ss";
    private static final String SHORT_DATE_FORMAT2 = "yyyy/MM/dd";
    private static final String GG = "-";
    private static final String MH = ":";
    private static final String ZXG = "/";

    @Override
    public Date convert(String source) {
        if (source == null || source.isEmpty()) {
            return null;
        }
        source = source.trim();
        try {
            SimpleDateFormat formatter;
            if (source.contains(GG)) {
                if (source.contains(MH)) {
                    formatter = new SimpleDateFormat(DATEFORMAT);
                } else {
                    formatter = new SimpleDateFormat(SHORT_DATE_FORMAT);
                }
                Date dtDate = formatter.parse(source);
                return dtDate;
            } else if (source.contains(ZXG)) {
                if (source.contains(MH)) {
                    formatter = new SimpleDateFormat(DATE_FORMAT2);
                } else {
                    formatter = new SimpleDateFormat(SHORT_DATE_FORMAT2);
                }
                Date dtDate = formatter.parse(source);
                return dtDate;
            }
        } catch (Exception e) {
            throw new RuntimeException(String.format("parser %s to Date fail", source));
        }

        throw new RuntimeException(String.format("parser %s to Date fail", source));

    }

}
