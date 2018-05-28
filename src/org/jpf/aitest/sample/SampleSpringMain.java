/**
 * 
 */
package org.jpf.aitest.sample;


import org.jpf.aitest.GenerateInputParam;
import org.jpf.aitest.GenerateMain;

/**
 * @author wupf@asiainfo.com
 *
 */
public class SampleSpringMain {

	/**
	 * @category @author 吴平福
	 * @param args update 2017年11月20日
	 */

	public static void main(String[] args) {
		// 北京移动
		String strFileName = "D:\\svn\\ecommerce-branch-20170912\\app-dao\\src\\main\\java\\com\\asiainfo\\ebiz\\alipay\\service";
		GenerateInputParam.bNeedTimeOut = false;
		GenerateInputParam.FileNameFilter =  ".*Manager.java";
		GenerateInputParam.JAVA_ENCODE="UTF-8";
		new GenerateMain(2, strFileName);
	}

}
