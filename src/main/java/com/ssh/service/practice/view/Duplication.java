package com.ssh.service.practice.view;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;

public class Duplication {

	public static void main(String[] args) {
		Duplication duplication = new Duplication();
		List list = new ArrayList();
		for (int i = 0;i<10000;i++) {
			list.add("ssh-"+i);
		}
		for (int i = 0;i<10000;i++) {
			list.add("ssh-"+i);
		}
		System.out.println("list.size"+list.size());
		long time = System.currentTimeMillis();
		List test1 = duplication.test1(list);
		long time1 = System.currentTimeMillis();
		System.out.println("test1.size = "+test1.size()+",time = "+ (time1 - time));

		System.out.println("list.size"+list.size());
		long time2 = System.currentTimeMillis();
		List test2 = duplication.test2(list);
		long time3 = System.currentTimeMillis();
		System.out.println("test2.size = "+test2.size()+",time = "+ (time3 - time2));

		duplication.test3();
		//HashSet和LinkedHashSet 当数据较大时(10000)LinkedHashSet去重比较快
	}

	private List test1(List data){
		HashSet set = new HashSet();
		set.addAll(data);
		List list = new ArrayList();
		list.addAll(set);
		return list;
	}

	private List test2(List data) {
		LinkedHashSet set = new LinkedHashSet();
		set.addAll(data);
		List list = new ArrayList();
		list.addAll(set);
		return list;
	}

	private void test3(){
		HashSet hashSet = new HashSet();
		LinkedHashSet linkedHashSet = new LinkedHashSet();
		List list = new ArrayList();
		for (int i = 0;i<10;i++) {
			hashSet.add("ssh-" + i);
			linkedHashSet.add("ssh-" + i);
			list.add("ssh-" + i);
		}

		System.out.println("hashSet"+hashSet);
		System.out.println("linkedHashSet"+linkedHashSet);
		System.out.println("list"+list);
	}
}
