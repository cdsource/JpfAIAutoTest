/**
 * copyrigth by wupf@
 * 2018年11月28日
 */
package org.jpf.aut.utils;

/**
 * @author wupf@aliyun.com
 *
 */
public class JpfUtil {

	/**
	 * 
	 */
	private JpfUtil() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 * @author wupf@aliyun.com
	 * @param strPath1
	 * @param strPath2
	 * @return
	 * 2018年8月21日
	 */
	public static String JoinPath(String strPath1,String strPath2)
	{
		if (strPath1.endsWith( java.io.File.separator))
		{
			return strPath1+strPath2;
		}else
		{
			return strPath1+ java.io.File.separator+strPath2;
		}
	}
}
