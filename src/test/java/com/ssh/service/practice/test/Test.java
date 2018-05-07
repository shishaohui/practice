package com.ssh.service.practice.test;

public class Test {

	public static void main(String[] args) {

		Integer a = 127;
		Integer b = Integer.valueOf(127);
		System.out.println("a==b:" + (a == b));

		Integer a1 = 128;
		Integer b1 = Integer.valueOf(128);
		System.out.println("a1==b1" + (a1 == b1));
		
			test1();
			test2();
			test3();
			test4();
	}

	public static void test1() {
		Integer a = 1000;
		int b = 1000;
		System.out.println("a == b : " + a.equals(b));
		System.out.println("a == b: " + (a == b));
	}

	public static void test2() {
		Integer a = 1000;
		Integer b = new Integer(1000);
		Integer c = Integer.valueOf(1000);

		System.out.println("a==b :" + (a == b));
		System.out.println("a==c :" + (a == c));
		System.out.println("b==c :" + (b == c));
		System.out.println("a.equals(b) :" + a.equals(b));
		System.out.println("b.equals(c) :" + b.equals(c));
		System.out.println("c.equals(a) :" + c.equals(a));
	}

	public static void test3() {
		Integer a = 1000;
		long b = 1000L;

		System.out.println("a==b :" + (a == b));
		System.out.println("a.equals(b) :" + a.equals(b));

	}

	public static void test4() {
		Integer a = 1000;
		Long b = 1000L;

		System.out.println("a==b 报错");
		System.out.println("a.equals(b) :" + a.equals(b));

	}


}
