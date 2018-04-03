/**
 * 
 */
package org.jpf.gts.samples;

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
	public int add(int i,int j)
	{
		return i+j;
	}
	/**
	 * 
	 * @param i "1"
	 * @param j 2
	 * @return 3
	 * 
	 */
	public int add(String i,int j)
	{
		return j;
	}
	/**
	 * 
	 * @param i "1"
	 * @param j  "2"
	 * @return "3"
	 * 
	 */
	public String add(String i,String j)
	{
		return j;
	}
}
