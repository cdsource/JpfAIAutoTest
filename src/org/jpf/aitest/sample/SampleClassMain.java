/**
 * 
 */
package org.jpf.aitest.sample;

import org.jpf.aitest.GenerateInputParam;
import org.jpf.aitest.GenerateMain;

/**
 * @author Administrator
 *
 */
public class SampleClassMain {

	/**
	 * @category @author 吴平福
	 * @param args update 2017年11月20日
	 */

	public static void main(String[] args) {

		String strFileName = "D:\\jworkspaces\\JpfUnitTest2\\src\\org\\jpf\\aitest\\sample\\SampleClass.java";

		GenerateInputParam.bNeedTimeOut = true;
		GenerateInputParam.FileNameFilter = "";

		new GenerateMain(1, strFileName);
	}

}
