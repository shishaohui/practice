package com.ssh.service.practice.enums;

public class TestEnum {

	public static void main(String[] args) {
		StudentStatus studentStatus = StudentStatus.valueOf(1);
		StudentStatus studentStatus1 = StudentStatus.valueOf("IN_SCHOOL");
		StudentStatus studentStatus2 = StudentStatus.fromString("辍业 ");

		System.out.println("" + studentStatus);
	}
}
