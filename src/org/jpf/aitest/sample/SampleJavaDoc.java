/**
 * 
 */
package org.jpf.aitest.sample;

/**
 * @author wupf@asiainfo.com
 *
 */
public class SampleJavaDoc {

	/**
	 * 
	 */
	public SampleJavaDoc() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 * @param i 1
	 * @param j 2
	 * @return 3
	 * 
	 */
	public int add(int i, int j) {
		return i + j;
	}

	/**
	 * 
	 * @param i "1"
	 * @param j 2
	 * @return 3
	 * 
	 */
	public int add(String i, int j) {
		return j;
	}

	/**
	 * 
	 * @param i "1"
	 * @param j "2"
	 * @return "3"
	 * 
	 */
	public String add(String i, String j) {
		return j;
	}

	public String add(String i, String j, int ACCOUNTPAGEQUERYLOGID) {
		return j;
	}

	public String add_nlp(String i, String j, int PAGEQUERYLOGID) {
		return j;
	}

	public void timeout() {
		try {
			//项目经理要求这里运行缓慢，好让客户给钱优化
			Thread.sleep(2000);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
