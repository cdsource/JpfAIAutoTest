/**
 * @author 吴平福 E-mail:wupf@asiainfo.com
 * @version 创建时间：2017年9月29日 上午11:27:47 类说明
 */

package org.jpf.gts.gtm;


import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jpf.unittests.generateuts.JpfUtInfo;
import org.jpf.unittests.generateuts.utils.GenerateUtil2;



/**
 * 
 */
public class GenerateMethodPrivate extends AbstractGenerateMethod {
    private static final Logger logger = LogManager.getLogger();
    /**
     * 
     */
    public GenerateMethodPrivate() {
        // TODO Auto-generated constructor stub
        
    }

    public String addClassInstance(String strClass, List MethodParam, JpfUtInfo cJpfUtInfo) {

        // sb.append(" ").append(strClass).append(" fixture = new ").append(strClass)
        // .append("();\n");
        StringBuffer sb = new StringBuffer();
        sb.append(cJpfUtInfo.getUtMinConstructor());
        sb.append("    ").append("Class testClass = fixture.getClass();\n");

        // 函数参数
        sb.append("    ").append("Class[] typeParams = new Class[] { ");
        for (int i = 0; i < MethodParam.size(); i++) {
            String strParamType = MethodParam.get(i).toString().trim();
            if (strParamType.startsWith("final")) {
                strParamType = strParamType.substring(5, strParamType.length()).trim();
            }

            strParamType = GenerateUtil2.replaceAngleBrackets(strParamType);

            strParamType = strParamType.substring(0, strParamType.indexOf(" ")).trim();

            sb.append(strParamType).append(".class ,");
        }
        if (MethodParam.size() > 0) {
            sb.deleteCharAt(sb.length() - 1);
        }
        sb.append(" }").append(";\n");

        StringBuffer sbParamInstance = new StringBuffer();
        for (int i = 0; i < MethodParam.size(); i++) {
            // System.out.println(MethodParam.get(i));
            String strParamType = MethodParam.get(i).toString().trim();

            strParamType = GenerateUtil2.RemoveFinal(strParamType);
            String strParamName = strParamType.substring(strParamType.indexOf(" ")).trim();
            sbParamInstance.append(strParamName).append(",");

        }
        if (MethodParam.size() > 0) {
            sbParamInstance.deleteCharAt(sbParamInstance.length() - 1);
        }
        sb.append("    ").append("Object objParams[] = { ").append(sbParamInstance).append(" };\n");
        
        logger.debug(sb.toString());
        return sb.toString();

    }

    /**
     * 
     * @category @author 吴平福
     * @param strReturn
     * @param sb update 2017年9月29日
     */
    public String addMethodReturn(String strMethod, String strReturn, List MethodParam,JpfUtInfo cJpfUtInfo ) {
        StringBuffer sb=new StringBuffer();
        sb.append("    ").append("Method method = testClass.getDeclaredMethod(\"").append(strMethod)
                .append("\", typeParams);\n");
        sb.append("    ").append("method.setAccessible(true);\n");
        if (!strReturn.equalsIgnoreCase("void")) {
            sb.append("    ").append(strReturn).append(" result = ").append("(").append(strReturn).append(")");
        }
        return sb.toString();


    }

    /*
     * (non-Javadoc)
     * 
     * @see org.jpf.unittests.generateuts.GenerateMethod#addMethodCaller(java.lang.String,
     * java.lang.StringBuffer)
     */
    @Override
    public String addMethodCaller(String strClass, String strMethod, List MethodParam, JpfUtInfo cJpfUtInfo) {
        // TODO Auto-generated method stub
        return "    method.invoke(fixture, objParams);\n";

    }

    public void addMethodParamInput(List MethodParam, StringBuffer sb) {

    }

    /* (non-Javadoc)
     * @see org.jpf.unittests.generateuts.GenerateMethod#addExtraMethod(org.jpf.unittests.generateuts.JpfUtInfo)
     */
    @Override
    public String addExtraMethod(String strClassName, String strPackageName) {
        // TODO Auto-generated method stub
        return "";
    }

}
