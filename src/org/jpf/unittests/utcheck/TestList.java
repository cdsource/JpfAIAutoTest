/**
 * 
 */
package org.jpf.unittests.utcheck;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * @author wupf@asiainfo.com
 *
 */
/**
 * 
 * ArrayList和LinkedeList的二分查找查找时间比较
 *
 */
public class TestList {

	public static final int N = 50000;
	public static List values;
	static {
		// 定义一个数组
		Integer vals[] = new Integer[N];
		Random r = new Random();
		int currval = 0;
		for (int i = 0; i < N; i++) {
			vals[i] = currval;
			// 获得随机数，这样就可以确保是数组是升序
			currval += r.nextInt(100) + i;
		}
		// 返回一个受指定数组支持的固定大小的列表
		values = Arrays.asList(vals);
	}

	static long timeList(List lst) {
		// 获得当前时间
		long start = System.currentTimeMillis();
		Collections.sort(values);
		for (int i = 0; i < N; i++) {
			// 二分查找，获得index,此方法要求list为升序，如果没有对列表进行排序，则结果是不确定的
			int index = Collections.binarySearch(lst, values.get(i));
			// 判断找出来的位置是否为对应的位置，
			if (index != i) {
				System.out.println("**出错了**");
			}
		}
		// 算出所花时间
		return System.currentTimeMillis() - start;
	}

	public static void main(String args[]) {
		System.out.println("ArrayList消耗时间：" + timeList(new ArrayList(values)));
		System.out.println("LinkedList消耗时间：" + timeList(new LinkedList(values)));
//  第一次运行
//  ArrayList消耗时间：47
//  LinkedList消耗时间：74781
//  第二次运行
//  ArrayList消耗时间：32
//  LinkedList消耗时间：72687
		// 时间不是固定的，但是不管你运行多少次，都可以看得出两者时间比
	}
}
