package com.ssh.service.practice.controller;

import com.ssh.service.practice.domain.Student;
import java.util.*;
import org.apache.commons.lang.math.RandomUtils;

public class ForEachTest {

	public static void main(String[] args) {
		ForEachTest forEachTest = new ForEachTest();
//		forEachTest.listRemoveTest();
//		forEachTest.RandomTest();
		forEachTest.test();
	}


	/**
	 * list 循环处理 remove测试
	 */
	private void listRemoveTest() {
		List<String> a = new ArrayList<>();
		a.add("1");
		a.add("2");
		System.out.println("before remove list" + a);
/*		for (String temp : a) {
			if("2".equals(temp)){
				a.remove(temp);
			}
		}*/
		Iterator it = a.iterator();
		while (it.hasNext()) {
			if (it.next().equals("2")) {
				it.remove();
			}
		}

		System.out.println("after remove list" + a);
	}

	/**
	 * RandomUtils test
	 */
	private void RandomTest() {
		for (int i = 0; i < 10; i++) {
//			System.out.println("随机数=="+ RandomUtils.nextInt(100));
			System.out.println("RandomUtils随机数==" + RandomUtils.nextDouble());
			double r = Math.random();
			r = (double) Math.round(r * 100) / 100;
			System.out.println("Math随机数==" + r);
		}
	}

	private void test() {

		String name = "ssh";
//		System.out.println("name"+name);
		Student student = new Student();
		student.setName("小明");
		Optional.ofNullable(student).isPresent();
		name = Optional.ofNullable(student).
			map(s -> s.getName()).
			orElse("初始值");
		try {
			System.out.println("异常前");
			if (Objects.equals(name,"test")) {

			}
		} catch (Exception e) {
			throw e;
		} finally {
			System.out.println("final");
		}

		System.out.println("异常后" + 1);
		return;
	}
}
