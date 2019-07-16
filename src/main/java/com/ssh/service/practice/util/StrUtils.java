package com.ssh.service.practice.util;

/**
 * 1、如果已知支付串的当前字符不是大于0的数字字符，则复制该字符于新字符串中
 * 2、如果是一个数字字符，且它之后没有后续字符，则简单地将它复制到新字符串中
 * 3、如果当前字符是一个大于0的数字字符，并且还有后续字符，
 * 设该数字字符的面值为n，则将它的后续字符（包括后续字符是一个数字字符）重复复制n+1到新字符串中
 * 4、若已知支付串的当前字符是下划线'_'，则将当前支付串变更为'\UL'
 * 5、以上述一次变更为一组，在不同组之间另插入一个下划线'_'用于分割
 * <p>
 * 例如 24ab_2t2 ---->   444_aaaaa_a_b_\UL_ttt_t_2
 * *
 */
public class StrUtils {
    private static String data = "24ab_2t205cd0";

    private static String encode(String str) {
        StringBuffer result = new StringBuffer();
        char[] charArray = str.toCharArray();
        int length = charArray.length;
        for (int i = 0; i < length; i++) {
            if (i > 0) {
                result.append("_");
            }
            char c = charArray[i];

            if (c == '_') {
                result.append("\\UL");
//                System.out.println("我是:" + c + " [是一个下滑线]");
            } else if (c == '0' || !Character.isDigit(c)) {
//                System.out.println("我是:" + c + " [不是大于0的数字字符 就是等于0或者不是数字]");
                result.append(c);
            } else if (Character.isDigit(c) && i == length - 1) {
                result.append(c);
//                System.out.println("我是:" + c + " [一个数字字符，且它之后没有后续字符]");
            } else if (Character.isDigit(c) && i != length - 1) {
                int value = Character.getNumericValue(c);
                for (int j = 0; j <= value; j++) {
                    result.append(charArray[i + 1]);
                }
//                System.out.println("我是:" + c + " [大于0的数字字符，且它之后有后续字符]");
            } else {
                System.out.println("我是:" + c + " [不处理]");
            }
        }
        return result.toString();
    }

    private static String decode(String str) {
        StringBuffer data = new StringBuffer();
        String[] strArray = str.split("_");
        for (String s : strArray) {
            System.out.println("str===========:"+s);
            if (s.equals("\\UL")) {
                data.append("_");
                continue;
            }

            int length = s.length();
            if (length == 1) {
                data.append(s);
            } else if (length > 1) {
                data.append(length - 1);
            }
        }

        return data.toString();
    }

    public static void main(String[] args) {
        String encodeStr = encode(data);
        String decodeStr = decode(encodeStr);
        System.out.println("data:" + data + "\nencode:" + encodeStr);
        System.out.println("decode:"+decodeStr);
        System.out.println("加密解密成功？:"+data.equals(decodeStr));
    }


}
