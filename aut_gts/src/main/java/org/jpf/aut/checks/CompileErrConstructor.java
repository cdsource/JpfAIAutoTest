/**
 * copyrigth by wupf@aliyun.com
 * 2018年8月20日
 */
package org.jpf.aut.checks;

/**
 * @author wupf@aliyun.com
 *
 */
public class CompileErrConstructor {

	/**
	 * 
	 */
	public CompileErrConstructor() {
		// TODO Auto-generated constructor stub
	}
	public boolean checkIn(String strErrMsg)
	{
		if (strErrMsg.startsWith("no suitable constructor found for")) {
			return true;
		}
		return false;
	}
	
	public void doAction(StringBuffer sb,String line)
	{
		sb.append(line.substring(0, line.indexOf("=") + 1)).append(" null;");
	}
}
