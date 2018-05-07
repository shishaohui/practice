package com.ssh.service.practice.test;

public class ItemsTest {

	public static void main(String[] args) {
		test("name", "age");
	}

	private static void test(String... items) {
		for (String str : items) {
			System.out.println("" + str);
		}
	}

}
