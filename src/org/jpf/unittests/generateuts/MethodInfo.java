/** 
* @author 吴平福 
* E-mail:wupf@asiainfo.com 
* @version 创建时间：2017年9月30日 下午3:49:28 
* 类说明 
*/ 

package org.jpf.unittests.generateuts;

import java.util.List;

/**
 * 
 */
public class MethodInfo {

    /**
     * 
     */
    public MethodInfo() {
        // TODO Auto-generated constructor stub
    }
    private int Modifiers;
    private String ClassName;
    private String MethodName;
    private List MethodParam;
    private String strReturn;
    private String strJavaDoc;
    /**
     * @return the strJavaDoc
     */
    public String getStrJavaDoc() {
        return strJavaDoc;
    }
    /**
     * @param strJavaDoc the strJavaDoc to set
     */
    public void setStrJavaDoc(String strJavaDoc) {
        this.strJavaDoc = strJavaDoc;
    }
    /**
     * @return the modifiers
     */
    public int getModifiers() {
        return Modifiers;
    }
    /**
     * @param modifiers the modifiers to set
     */
    public void setModifiers(int modifiers) {
        Modifiers = modifiers;
    }

    /**
     * @return the className
     */
    public String getClassName() {
        return ClassName;
    }
    /**
     * @param className the className to set
     */
    public void setClassName(String className) {
        ClassName = className;
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
    /**
     * @return the methodParam
     */
    public List getMethodParam() {
        return MethodParam;
    }
    /**
     * @param methodParam the methodParam to set
     */
    public void setMethodParam(List methodParam) {
        MethodParam = methodParam;
    }
    /**
     * @return the strReturn
     */
    public String getStrReturn() {
        return strReturn;
    }
    /**
     * @param strReturn the strReturn to set
     */
    public void setStrReturn(String strReturn) {
        this.strReturn = strReturn;
    }
 
}
