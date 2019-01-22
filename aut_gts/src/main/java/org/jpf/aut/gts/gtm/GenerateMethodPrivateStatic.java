/**
 * copyrigth by wupf@asiainfo.com
 * 2018年6月4日
 */
package org.jpf.aut.gts.gtm;

import java.util.List;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jpf.aut.base.JpfUtInfo;
import org.jpf.aut.gts.gtm.MethodParamBody;
import org.jpf.aut.utils.GenerateUtil2;

/**
 * @author wupf@asiainfo.com
 *
 */
public class GenerateMethodPrivateStatic extends AbstractGenerateMethod {

    private static final Logger logger = LogManager.getLogger();
	/**
	 * 
	 */
	public GenerateMethodPrivateStatic() {
		// TODO Auto-generated constructor stub
	}

	/*
	 Method method1=Class.forName("T").getDeclaredMethod("me1", new Class[]{String.class,Integer.class});
			method1.setAccessible(true);
			method1.invoke(T.class, "test static private",1296699761);
	 * */
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
        	if ("boolean".equalsIgnoreCase(strReturn))
        	{
        		strReturn="Boolean";
        	}
            sb.append("    ").append(strReturn).append(" wupf_result = ").append("(").append(strReturn).append(")");
        }
        //logger.debug(sb);
        return sb.toString();


    }
    
	@Override
	public String addMethodCaller(String strClassName, String strMethod, List MethodParam, JpfUtInfo cJpfUtInfo) {
        return "    method.invoke(wupf_fixture, objParams);\n";
	}

	
    public String addClassInstance(String strClass, List MethodParam, JpfUtInfo cJpfUtInfo) {

        StringBuffer sb = new StringBuffer();
        sb.append(cJpfUtInfo.getUtMinConstructor());
        sb.append("    ").append("Class testClass = wupf_fixture.getClass();\n");

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
            
            MethodParamBody cParamInitBody = new MethodParamBody(MethodParam.get(i).toString());
            
            logger.debug(cParamInitBody.getParamVariable());
            sbParamInstance.append(cParamInitBody.getParamVariable()).append(",");

        }
        if (MethodParam.size() > 0) {
            sbParamInstance.deleteCharAt(sbParamInstance.length() - 1);
        }
        sb.append("    ").append("Object objParams[] = { ").append(sbParamInstance).append(" };\n");
        
        //logger.debug(sb.toString());
        return sb.toString();

    }
	@Override
	public String addExtraMethod(String strClassName, String strPackageName) {
		// TODO Auto-generated method stub
		return "";
	}

	@Override
	public void addExtraImport(JpfUtInfo cJpfUtInfo) {
		// TODO Auto-generated method stub
		cJpfUtInfo.addImport("import java.lang.reflect.Method;");
	}

}
