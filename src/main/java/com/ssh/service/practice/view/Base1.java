package com.ssh.service.practice.view;

import java.time.LocalTime;

public final class Base1 {

	public static void main(String[] args) {
		LocalTime localTime = LocalTime.now();
		System.out.println("LocalTime.now().plusMinutes(1L)" + localTime.plusMinutes(1L));
		System.out.println("LocalTime.now()" + localTime);

/*		Map<String, Object> data = new HashMap<>();
		data.put("id", 10);
		data.put("mobile", "13714671456");
		System.out.println("map.toString=" + data.toString());*/

		/*Base1 base1 = new Base1();
		test test2 = new test();

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		//获取当前月最后一天
		Calendar ca = Calendar.getInstance();
		ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));
		String last = format.format(ca.getTime());
		System.out.println("===============last:"+last);*/
	}

	public static String name;

	private Integer test1() {
		System.out.println("test1被执行");
		test2 test = new test2();
		return 1;
	}

	public static class test {

	}

	public class test2 extends test {

	}

}
