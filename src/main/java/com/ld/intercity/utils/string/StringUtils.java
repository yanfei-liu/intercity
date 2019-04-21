package com.ld.intercity.utils.string;

import lombok.extern.slf4j.Slf4j;

/**
 * @author: LD
 * @date:
 * @description:
 */
@Slf4j
public class StringUtils {

    /**
     * 字符串前边补0
     *
     * @param str   要补0的int值
     * @param lengs 字符串总长度
     * @return 补0之后的字符串
     */
    public static String stringFormat(int str, int lengs) {
        // 0 代表前面补充0
        // 10代表长度为10
        // d 代表参数为正数型
        return String.format("%0" + lengs + "d", str);
    }

}
