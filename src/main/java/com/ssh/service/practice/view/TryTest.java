package com.ssh.service.practice.view;

import org.apache.commons.lang.math.RandomUtils;

public class TryTest {

	public static void main(String[] args) {
		double v = Math.random();
		System.out.println("math.random" + v);
		int a = (int) (v * 100);
		System.out.println("a======" + a);

		//RandomUtils.nextInt(5)  0到5  包含0 不包含5
		for (int i = 0; i < 100; i++) {
			Integer c = RandomUtils.nextInt(5);
			if(c==0){
				System.out.println("RandomUtils c 包含0======= c = "+c);
			} else if (c == 5) {
				System.out.println("RandomUtils c 包含5=======0" + c);
			}
		}
	}
}
