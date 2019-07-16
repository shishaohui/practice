package com.ssh.service.practice.view;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ReversalTest {
    public static final String STR = "my name is Mr.shi";

    public static void main(String[] args) {
        System.out.println("reversalByStringBuffer===========:" + reversalByStringBuffer(STR));
        System.out.println("reversalByArray==================:" + reversalByArray(STR));
        System.out.println("reversalByArray2=================:" + reversalByArray2(STR));
        System.out.println("reversalByCollection=============:" + reversalByCollection(STR));
    }

    /**
     * StringBuffer 线程安全
     * StringBuilder 线程不安全
     *
     * @param str
     * @return
     */
    private static String reversalByStringBuffer(String str) {
        StringBuffer stringBuffer = new StringBuffer(str);
        return stringBuffer.reverse().toString();
    }

    /**
     * 两个数组转换
     *
     * @param str
     * @return
     */
    private static String reversalByArray(String str) {
        char[] chars = str.toCharArray();
        int y = chars.length;
        char[] newChars = new char[y];
        for (int i = 0; i < chars.length; i++) {
            newChars[y - 1] = chars[i];
            y--;
        }
        return new String(newChars);
    }

    /**
     * 一个数组转换
     *
     * @param str
     * @return
     */
    private static String reversalByArray2(String str) {
        char[] chars = str.toCharArray();
        char middle;
        int y = chars.length;
        for (int i = 0; i < y; i++,y--) {
            middle = chars[i];
            chars[i] = chars[y - 1];
            chars[y - 1] = middle;
        }
        return new String(chars);
    }

    private static String reversalByCollection(String str) {
        List strList = new ArrayList();
        for (char c : str.toCharArray()) {
            strList.add(c);
        }
        Collections.reverse(strList);
        char[] chars = new char[str.length()];
        int i = 0;
        for (Object s : strList) {
            chars[i] = (char) s;
            i++;
        }
        return String.valueOf(chars);
    }
}
