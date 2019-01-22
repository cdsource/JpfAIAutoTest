/**
 * copyrigth by wupf@aliyun.com
 * 2018年8月20日
 */
package org.jpf.aut.checks;

/**
 * @author wupf@aliyun.com
 *
 */
public class CompileErrPackageErr {

	/**
	 * 
	 */
	private CompileErrPackageErr() {
		// TODO Auto-generated constructor stub
	}

	public boolean checkIn(String strErrMsg)
	{
		if ("package org.aitest.runtime does not exist".equalsIgnoreCase(strErrMsg)) {
			return true;
		}
		return false;
	}
	
	public void doAction(StringBuffer sb,String line)
	{
		sb.append(line.replaceAll("org.aitest", "org.jpf.aitest"));
	}
}
