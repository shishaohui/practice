/*
 * Copyright 2017 - 数多科技
 * 
 * 北京数多信息科技有限公司
 * 本公司保留所有下述内容的权利。
 * 本内容为保密信息，仅限本公司内部使用。
 * 非经本公司书面许可，任何人不得外泄或用于其他目的。
 */
package com.ssh.service.practice.util;

import java.math.BigDecimal;

public class BigDecimalUtils {
  private BigDecimalUtils() {}

  public static boolean isZero(BigDecimal bd) {
    return bd.compareTo(BigDecimal.ZERO) == 0;
  }
  public static boolean isPositive(BigDecimal bd) {
    return bd.compareTo(BigDecimal.ZERO) > 0;
  }
  public static boolean isNegative(BigDecimal bd) {
    return bd.compareTo(BigDecimal.ZERO) < 0;
  }
  
  public static boolean notNegative(BigDecimal bd) {
    return bd.compareTo(BigDecimal.ZERO) >= 0;
  }
  public static boolean notPositive(BigDecimal bd) {
    return bd.compareTo(BigDecimal.ZERO) <= 0;
  }
  
  public static boolean equals(BigDecimal bd1, BigDecimal bd2) {
    return bd1.compareTo(bd2) == 0;
  }
  public static boolean greaterThan(BigDecimal bd1, BigDecimal bd2) {
    return bd1.compareTo(bd2) > 0;
  }
  public static boolean lessThan(BigDecimal bd1, BigDecimal bd2) {
    return bd1.compareTo(bd2) < 0;
  }
  public static boolean greaterThanAndEqualTo(BigDecimal bd1, BigDecimal bd2) {
    return bd1.compareTo(bd2) >= 0;
  }
  public static boolean lessThanAndEqualTo(BigDecimal bd1, BigDecimal bd2) {
    return bd1.compareTo(bd2) <= 0;
  }
}
