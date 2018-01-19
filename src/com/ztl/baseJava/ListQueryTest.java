package com.ztl.baseJava;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

public class ListQueryTest {

	public static void main(String[] args) {
		Random random = new Random();
		LinkedList<Integer> linkedList = new LinkedList<Integer>();
		for (int i = 0; i < 100000; i++) {
			linkedList.add(i);
		}
		ArrayList<Integer> arrayList = new ArrayList<Integer>();
		for (int i = 0; i < 100000; i++) {
			arrayList.add(i);
		}
		long start = System.currentTimeMillis();
		for (int i = 0; i < 100000; i++) {
			int j = random.nextInt(i + 1);
			int k = linkedList.get(j);
		}
		long end = System.currentTimeMillis();
		System.out.println(end - start);
		for (int i = 0; i < 100000; i++) {
			int j = random.nextInt(i + 1);
			int k = arrayList.get(j);
		}
		System.out.println(System.currentTimeMillis() - end);
	}
}