/** 
* @author 吴平福 
* E-mail:wupf@asiainfo.com 
* @version 创建时间：2018年1月30日 下午1:19:54 
* 类说明 
*/ 

package org.jpf.unittests.generateuts.genbylog;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

/**
 * 
 */
public class LogCaseInfo {



    private String CaseTime;
    private String MethodName;
    private HashMap<String, String> params=new HashMap<>();
    
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
     * @return the params
     */
    public HashMap<String, String> getParams() {
        return params;
    }
    /**
     * @param params the params to set
     */
    public void setParams(HashMap<String, String> params) {
        this.params = params;
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
    
    public String toString()
    {
        StringBuffer sb=new StringBuffer();
        sb.append("CaseTime:").append(CaseTime).append("\n").append("MethodName:").append(MethodName).append("\n");
        Set<String> key = params.keySet();
        for (Iterator it = key.iterator(); it.hasNext();) {
            String s = (String) it.next();
            sb.append(s).append(":");
            sb.append(params.get(s)).append("\n");
        }
        return sb.toString();
    }
}
