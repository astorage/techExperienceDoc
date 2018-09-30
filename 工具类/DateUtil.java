package com.gmv.util;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.SignStyle;

import static java.time.temporal.ChronoField.DAY_OF_MONTH;
import static java.time.temporal.ChronoField.MONTH_OF_YEAR;
import static java.time.temporal.ChronoField.YEAR;

/**
 * Author: Boris
 * Date: 2018/8/3 14:13
 * Copyright (C), 2017-2018, 浙江执御信息技术有限公司
 * Description: 日期工具类
 */
public class DateUtil {

    public static DateTimeFormatter FORMATTER = new DateTimeFormatterBuilder()
            .appendValue(YEAR, 4, 10, SignStyle.EXCEEDS_PAD)
            .appendValue(MONTH_OF_YEAR, 2)
            .appendValue(DAY_OF_MONTH, 2)
            .toFormatter();


}
