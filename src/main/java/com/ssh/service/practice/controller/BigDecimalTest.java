package com.ssh.service.practice.controller;

import com.ssh.service.practice.domain.Student;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class BigDecimalTest {

	public static void main(String[] args) {
		System.out.println("最大值===" + Integer.MAX_VALUE);
		//Lambda表达式中变量不能重新赋值
		BigDecimalTest test = new BigDecimalTest();
		List<Student> studentList = test.dataList();
		final BigDecimal[] amount = {new BigDecimal(0)};
		studentList.stream().forEach(o -> amount[0] = amount[0].add(o.getAmount()));
		System.out.println("amount[0]" + amount[0]);

		BigDecimal amount3 = studentList.stream().map(Student::getAmount).reduce(BigDecimal::add).get();
		System.out.println("amount3" + amount3);
	}


	private List<Student> dataList() {
		BigDecimal amount1 = new BigDecimal(100);
		BigDecimal amount2 = new BigDecimal(50);
		amount1.divideAndRemainder(amount2);
		amount1.add(amount2);
		System.out.println("amount1" + amount1);

		List<Student> studentList = new ArrayList<>();

		Student student = new Student();
		student.setAmount(amount1);
		Student student2 = new Student();
		student2.setAmount(amount2);
		studentList.add(student);
		studentList.add(student2);
		return studentList;
	}
}
