/*
 * Copyright 2017 - 数多科技
 * 
 * 北京数多信息科技有限公司 本公司保留所有下述内容的权利。 本内容为保密信息，仅限本公司内部使用。 非经本公司书面许可，任何人不得外泄或用于其他目的。
 */
package com.ssh.service.practice.util;

import java.util.Objects;


public class CheckUtils {


  public static boolean isKeyId(Integer... ids) {
    for (Integer id : ids) {
      if (Objects.isNull(id) || id <= 0) {
        return false;
      }
    }
    return true;
  }
}
