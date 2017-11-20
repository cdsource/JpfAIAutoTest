/** 
* @author 吴平福 
* E-mail:wupf@asiainfo.com 
* @version 创建时间：2017年11月13日 上午9:23:23 
* 类说明 
*/ 

package org.jpf.unittests.generateuts;

/**
 * 
 */
public class ParamInitBody {

    /**
     * 
     */
    public ParamInitBody() {
        // TODO Auto-generated constructor stub
    }

    private String ParamType;
    private String ParamVariable;
    private String ParamValue;
    private boolean isArray=false;
    //参数前初始化，如数据库使用classforname
    private String ParamPreInit;
    /**
     * @return the paramPreInit
     */
    public String getParamPreInit() {
        return ParamPreInit;
    }
    /**
     * @param paramPreInit the paramPreInit to set
     */
    public void setParamPreInit(String paramPreInit) {
        ParamPreInit = paramPreInit;
    }
    /**
     * @return the isArray
     */
    public boolean isArray() {
        return isArray;
    }
    /**
     * @param isArray the isArray to set
     */
    public void setArray(boolean isArray) {
        this.isArray = isArray;
    }
    /**
     * @return the paramType
     */
    public String getParamType() {
        return ParamType;
    }
    /**
     * @param paramType the paramType to set
     */
    public void setParamType(String paramType) {
        ParamType = paramType.trim();
    }
    /**
     * @return the paramVariable
     */
    public String getParamVariable() {
        return ParamVariable;
    }
    /**
     * @param paramVariable the paramVariable to set
     */
    public void setParamVariable(String paramVariable) {
        paramVariable=paramVariable.trim();
        if (paramVariable.startsWith(ParamType))
        {
            paramVariable=paramVariable.substring(ParamType.length(), paramVariable.length());
        }
        ParamVariable = paramVariable;
    }
    /**
     * @return the paramValue
     */
    public String getParamValue() {
        return ParamValue;
    }
    /**
     * @param paramValue the paramValue to set
     */
    public void setParamValue(String paramValue) {
        ParamValue = paramValue.trim();
    }
}
