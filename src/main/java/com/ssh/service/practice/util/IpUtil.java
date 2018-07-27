package com.ssh.service.practice.util;

import java.util.ArrayList;
import java.util.List;
import org.springframework.util.Assert;

public class IpUtil {

	public static long ipToLong(String ip) {
		String[] ipArray = ip.split("\\.");
		List ipNums = new ArrayList();
		for (int i = 0; i < 4; ++i) {
			ipNums.add(Long.valueOf(Long.parseLong(ipArray[i].trim())));
		}
		long ZhongIPNumTotal = ((Long) ipNums.get(0)).longValue() * 256L * 256L * 256L
			+ ((Long) ipNums.get(1)).longValue() * 256L * 256L + ((Long) ipNums.get(2)).longValue() * 256L
			+ ((Long) ipNums.get(3)).longValue();

		return ZhongIPNumTotal;
	}


	public static String getIP(long ipaddr) {
		long y = ipaddr % 256;
		long m = (ipaddr - y) / (256 * 256 * 256);
		long n = (ipaddr - 256 * 256 * 256 * m - y) / (256 * 256);
		long x = (ipaddr - 256 * 256 * 256 * m - 256 * 256 * n - y) / 256;
		return m + "." + n + "." + x + "." + y;
	}


	public static Long ipToNumber(String ip) {
		Long ips = 0L;
		String[] numbers = ip.replace(" ","").split("\\.");
		for (int i = 0; i < 4; i++) {
			int ipNumber = Integer.parseInt(numbers[i]);
			Assert.isTrue(ipNumber<=255,"ip地址错误!");
			ips = ips << 8 | ipNumber;
		}
		return ips;
	}

	public static String numberToIp(Long number) {
		StringBuffer ip = new StringBuffer();
		for (int i = 3; i >= 0; i--) {
			ip.append(String.valueOf((number >> 8*i & 0xff)));
			if(i>0){
				ip.append(".");
			}
		}

		return ip.toString();
	}

}
