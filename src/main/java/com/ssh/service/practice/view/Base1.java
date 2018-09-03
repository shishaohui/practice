package com.ssh.service.practice.view;

public final class Base1 {

	public static void main(String[] args) {
		Base1 base1 = new Base1();
		test test2 = new test();
	}

	public static String name;

	private Integer test1() {
		System.out.println("test1被执行");
		test2 test = new test2();
		return 1;
	}

	public static class test {
	}

	public class test2 extends test{

	}

}
