package com.ssh.service.practice.view;

import com.alibaba.fastjson.JSON;
import com.ssh.service.practice.domain.Student;
import com.ssh.service.practice.enums.StudentStatus;

public class FastJson {

	public static void main(String[] args) {
		FastJson fastJson = new FastJson();
		Student student = fastJson.transfer(Student.class);
		System.out.println("student"+student.getName());
	}

	private <T> T transfer(Class<T> tClass) {
		Student student = new Student("张三", 20, 5000, StudentStatus.IN_SCHOOL);
		String studentStr = JSON.toJSONString(student);
		System.out.println("studentJson" + studentStr);
		T student2 = JSON.parseObject(studentStr, tClass);
		System.out.println("student2" + student2);
		return student2;
	}
}
