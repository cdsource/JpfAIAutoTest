/** 
* @author 吴平福 
* E-mail:wupf@asiainfo.com 
* @version 创建时间：2018年1月30日 下午1:19:54 
* 类说明 
*/ 

package org.jpf.aut.logs.plugins;

import java.util.Vector;

/**
 * 
 */
public class LogCaseInfo {



    private String CaseTime;
    private String MethodName;
    private String Parameters;
    private String Types;
    
    private Vector<LogCaseUnit> vUnit=new Vector<>();
     
    public Vector<LogCaseUnit> getvUnit() {
		return vUnit;
	}

	public void setvUnit(Vector<LogCaseUnit> vUnit) {
		this.vUnit = vUnit;
	}

	public String getParameters() {
		return Parameters;
	}

	public void setParameters(String parameters) {
		Parameters = parameters;
	}

	public String getTypes() {
		return Types;
	}

	public void setTypes(String types) {
		Types = types;
	}

	/**
     * 
     */
    public LogCaseInfo() {
        // TODO Auto-generated constructor stub
    }

    /**
     * @return the caseTime
     */
    public String getCaseTime() {
        return CaseTime;
    }
    /**
     * @param caseTime the caseTime to set
     */
    public void setCaseTime(String caseTime) {
        CaseTime = caseTime;
    }


    
    /**
     * @return the methodName
     */
    public String getMethodName() {
        return MethodName;
    }

    /**
     * @param methodName the methodName to set
     */
    public void setMethodName(String methodName) {
        MethodName = methodName;
    }
    

}
