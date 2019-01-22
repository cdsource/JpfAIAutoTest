/**
 * copyrigth by wupf@aliyun.com
 * 2018年8月10日
 */
package org.jpf.algorithm;

/**
 * @category KMP算法
 * @author wupf@aliyun.com
 *
 */
public class Kmp {

	/**
	 * 
	 */
	public Kmp(String s,String t) {
		// TODO Auto-generated constructor stub
		char[] ss = s.trim().toCharArray();
		char[] tt = t.trim().toCharArray();
		System.out.println(KMP_Index(ss, tt)); // KMP匹配字符串
	}

	public static void main(String[] args) {
		String s = "abbabbbbcab"; // 主串
		String t = "      bbcac"; // 模式串
		Kmp cKmp=new Kmp(s,t);
	}
 
	/**
	 * 获得字符串的next函数值
	 * 
	 * @param t
	 *            字符串
	 * @return next函数值
	 */
	public static int[] next(char[] t) {
		int[] next = new int[t.length];
		next[0] = -1;
		int i = 0;
		int j = -1;
		while (i < t.length - 1) {
			if (j == -1 || t[i] == t[j]) {
				i++;
				j++;
				if (t[i] != t[j]) {
					next[i] = j;
				} else {
					next[i] = next[j];
				}
			} else {
				j = next[j];
			}
		}
		return next;
	}
 
	/**
	 * KMP匹配字符串
	 * 
	 * @param s
	 *            主串
	 * @param t
	 *            模式串
	 * @return 若匹配成功，返回下标，否则返回-1
	 */
	public static int KMP_Index(char[] s, char[] t) {
		int[] next = next(t);
		int i = 0;
		int j = 0;
		while (i <= s.length - 1 && j <= t.length - 1) {
			if (j == -1 || s[i] == t[j]) {
				i++;
				j++;
			} else {
				j = next[j];
			}
		}
		if (j < t.length) {
			return -1;
		} else
			return i - t.length; // 返回模式串在主串中的头下标
	}
}
