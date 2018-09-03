package com.ssh.service.practice.view;

public class TryTest {
	public static void main(String[] args){
		TryTest tryTest = new TryTest();
//		int a = tryTest.test();
		int a = tryTest.getValue(2);
		System.out.println("a==="+a);
/*		double d = 29.0 * 0.01;
		System.out.println(d);
		System.out.println(d * 100);
		System.out.println((int) (d * 100));*/
	}

	private Integer test(){
		try{
			int a = 10/0;
			System.out.println("try");
			return 1;
		}catch(Exception e){
			System.out.println("catch");
			return 2;
		}finally{
			System.out.println("finally");
//			return 3;
		}
	}

	private Integer getValue(int i) {
		int result = 0;
		switch (i) {
			case 1:
				result = result + i;
			case 2:
				result = result + i*2;
			case 3:
				result = result + i*3;
				break;
			case 4:
				result = result + i*4;
			default:
		}
		return result;
	}
}
