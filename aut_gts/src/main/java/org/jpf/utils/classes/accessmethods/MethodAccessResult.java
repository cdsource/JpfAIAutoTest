/**
 * copyrigth by wupf@aliyun.com
 * 2018年8月25日
 */
package org.jpf.utils.classes.accessmethods;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author wupf@aliyun.com
 *
 */
public class MethodAccessResult {
	private static final Logger logger = LogManager.getLogger();
	/**
	 * 
	 */
	public MethodAccessResult() {
		// TODO Auto-generated constructor stub
	}
	
	private static int iPrivateMethodCount=0;
	
	private static Map<String,String> mapFile=new HashMap<>();
	
	
	public static void addFile(String strFileName) {
		mapFile.put(strFileName, strFileName);
	}
	
	public static int getPrivateMethodCount() {
		return iPrivateMethodCount;
	}

	public static void addPrivateMethodCount() {
		iPrivateMethodCount ++;
	}

	public  static void printResult()
	{
		logger.info("PrivateMethodCount " + iPrivateMethodCount);
	}
}
