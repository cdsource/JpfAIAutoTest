/**
 * @author 吴平福 E-mail:wupf@asiainfo.com
 * @version 创建时间：2017年11月13日 上午9:23:23 类说明
 */

package org.jpf.aitest;


import org.jpf.aitest.utils.FormatUtil;

/**
 * 
 */
public class ParamInitBody {

    private final String FINAL_KEY="final";
    /**
     * 
     */
    public ParamInitBody(String strParamName) {
        // TODO Auto-generated constructor stub
        strParamName = FormatUtil.formatParam(strParamName).trim();
        if (strParamName.startsWith(FINAL_KEY))
        {
            ParamMoidfy=FINAL_KEY;
            strParamName=strParamName.substring(FINAL_KEY.length()+1,strParamName.length());
            
        }
        int iPos = strParamName.lastIndexOf(" ");
        setParamType(strParamName.substring(0, iPos));
        setParamVariable(strParamName.substring(iPos, strParamName.length()));
        ParamRealType=ParamType;
        iPos = ParamType.indexOf("<");
        if (iPos > 0) {
            ParamRealType = ParamType.substring(0, iPos);
            ParamHideType = ParamType.substring(iPos, ParamType.length());
            ParamHideType = ParamHideType.replaceAll("<", "").replaceAll(">", "").trim();
        }
        setArray(FormatUtil.isParamArray(strParamName));

    }

    private String ParamMoidfy;
    private String ParamRealType;
    private String ParamHideType;
    private String ParamType;

    private String ParamVariable;
    private String ParamValue;
    private boolean isArray = false;
    // 参数前初始化，如数据库使用classforname
    private String ParamPreInit;

    
    private String currentPackage = "";

    
    /**
     * @return the paramMoidfy
     */
    public String getParamMoidfy() {
        return ParamMoidfy;
    }

    /**
     * @param paramMoidfy the paramMoidfy to set
     */
    public void setParamMoidfy(String paramMoidfy) {
        ParamMoidfy = paramMoidfy;
    }

    /**
     * @return the paramRealType
     */
    public String getParamRealType() {
        return ParamRealType;
    }

    /**
     * @param paramRealType the paramRealType to set
     */
    public void setParamRealType(String paramRealType) {
        ParamRealType = paramRealType;
    }

    /**
     * @return the paramHideType
     */
    public String getParamHideType() {
        return ParamHideType;
    }

    /**
     * @param paramHideType the paramHideType to set
     */
    public void setParamHideType(String paramHideType) {
        ParamHideType = paramHideType;
    }

    /**
     * @return the currentPackage
     */
    public String getCurrentPackage() {
        return currentPackage;
    }

    /**
     * @param currentPackage the currentPackage to set
     */
    public void setCurrentPackage(String currentPackage) {
        this.currentPackage = currentPackage;
    }

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
        if (isArray) {
            ParamType = getParamType().trim();
            if (!ParamType.endsWith("[]")) {
                ParamType += "[]";
            }
            ParamVariable = ParamVariable.trim().replaceAll("\\[.*\\]", "");
        }
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
        ParamType = removeDesc(paramType.trim());

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
        /*
        paramVariable = paramVariable.trim();
        if (paramVariable.startsWith(ParamType)) {
            paramVariable = paramVariable.substring(ParamType.length(), paramVariable.length());
        }
        */
        ParamVariable = paramVariable.trim();
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

    /**
     * 
     * @category 去掉注释
     * @author 吴平福
     * @param strParam update 2017年11月8日
     */
    public String removeDesc(String strParam) {
        return strParam.replaceAll("/\\*(.*?)\\*/", "").replaceAll("\n", "");
    }

    public static void main(String[] args) {
        //ParamInitBody cParamInitBody = new ParamInitBody("ArrayList<MonitorMapItem> /**/serviceSql");
        ParamInitBody cParamInitBody = new ParamInitBody(" answer2017Record answer2017Record");
        System.out.println(cParamInitBody.getParamMoidfy());
        System.out.println(cParamInitBody.getParamType());
        System.out.println(cParamInitBody.getParamVariable());
        System.out.println(cParamInitBody.getParamRealType());
        System.out.println(cParamInitBody.getParamHideType());
        System.out.println(cParamInitBody.isArray);
        System.out.println("game over");
    }
}
