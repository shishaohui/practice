/*
 * Copyright 2017 - 数多科技
 * 
 * 北京数多信息科技有限公司 本公司保留所有下述内容的权利。 本内容为保密信息，仅限本公司内部使用。 非经本公司书面许可，任何人不得外泄或用于其他目的。
 */
package com.ssh.service.practice.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;


public class DateUtils {

  private static final String DATE_MSG = "预约日期必须为当前日期之后";
  private static final String TIME_MSG = "预约时间必须在当前日期时间之后";

  public static boolean sameDay(LocalDate d1, LocalDate d2) {
    return d1.compareTo(d2) == 0;
  }

  public static boolean isAfterNowDateTime(LocalDateTime dateTime) {
    return dateTime.isAfter(LocalDateTime.now());
  }

  public static boolean isAfterNowDate(LocalDate date) {
    return date.isAfter(LocalDate.now());
  }

  public static boolean isAfterNowTime(LocalTime time) {
    return time.isAfter(LocalTime.now());
  }
}
