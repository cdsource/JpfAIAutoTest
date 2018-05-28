/**
 * copyrigth by wupf@asiainfo.com
 * 2018年4月18日
 */
package org.jpf.aitest.plugins.springs.logs;

/**
 * @author wupf@asiainfo.com
 *
 */
public class LogParamInfo {

	private String ParamValue;
	private String ParamType;
	
	public String getParamValue() {
		return ParamValue;
	}
	public void setParamValue(String paramValue) {
		ParamValue = paramValue;
	}
	public String getParamType() {
		return ParamType;
	}
	public void setParamType(String paramType) {
		ParamType = paramType;
	}
	
	public String toString()
	{
		return ParamValue+":"+ParamType;
	}
	
	
}
