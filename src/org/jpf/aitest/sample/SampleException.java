/**
 * 
 */
package org.jpf.aitest.sample;

import java.sql.SQLException;
import java.util.Vector;

/**
 * @category 演示越界
 * @author Administrator
 *
 */
public class SampleException {

	public void showOutArry()
	{
		String str1="abcd";
		String str2=str1.substring(0,5);
		
	}
	
	public void showOutMap()throws SQLException 
	{
		Vector<String> v=new Vector<String>();
		v.add("a");
		String str1=v.get(2);
	}

}
