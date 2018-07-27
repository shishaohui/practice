package com.ssh.service.practice.controller;

public class SwitchTest {

	public static void main(String[] args) {
		int a = 9;
		switch (a) {
			case 1:
				System.out.println("a=="+a);
				break;
			case 10:
				System.out.println("a10=="+a);
				break;
			default:
				System.out.println("default=="+a);
		}
	}
}
