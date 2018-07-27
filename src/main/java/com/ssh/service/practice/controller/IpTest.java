package com.ssh.service.practice.controller;

import com.ssh.service.practice.util.IpUtil;

public class IpTest {

	public static void main(String[] args) {
		String ip = " 19 2.168.123.201 ";
		System.out.println("ip"+ip);


		long fromIp2 = IpUtil.ipToNumber(ip);
		System.out.println("fromIp2="+fromIp2);
		System.out.println("ip2="+IpUtil.numberToIp(fromIp2));
	}
}
