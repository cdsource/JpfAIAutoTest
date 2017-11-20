/**
 * @author 吴平福 E-mail:wupf@asiainfo.com
 * @version 创建时间：2017年9月29日 上午11:30:51 类说明
 */

package org.jpf.unittests.generateuts;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jpf.unittests.generateuts.fuzze.fuzzBoolean;
import org.jpf.unittests.generateuts.fuzze.fuzzeCommon;
import org.jpf.unittests.generateuts.fuzze.fuzzeConnection;
import org.jpf.unittests.generateuts.fuzze.fuzzeInt;
import org.jpf.unittests.generateuts.fuzze.fuzzeString;
import org.jpf.unittests.generateuts.fuzze.fuzzebyte;
import org.jpf.unittests.generateuts.fuzze.fuzzechar;
import org.jpf.unittests.generateuts.fuzze.fuzzedouble;
import org.jpf.unittests.generateuts.fuzze.fuzzefloat;
import org.jpf.unittests.generateuts.fuzze.fuzzelong;
import org.jpf.unittests.generateuts.fuzze.fuzzeshort;
import org.jpf.unittests.generateuts.utils.Descartes;
import org.jpf.unittests.generateuts.utils.GenerateUtils;



/**
 * 
 */
public abstract class GenerateMethod {
    private static final Logger logger = LogManager.getLogger();

    /**
     * 
     */
    public GenerateMethod() {
        // TODO Auto-generated constructor stub
    }

    /**
     * 
     * @category @author 吴平福
     * @param strReturn
     * @param sb update 2017年9月29日
     */
    public abstract StringBuffer addMethodCaller(String strClass, String strMethod, List MethodParam,
            UtFileText cUtFileText);

    /**
     * 
     * @category @author 吴平福
     * @param strClass
     * @param MethodParam
     * @param cUtFileText
     * @return update 2017年11月14日
     */
    public abstract StringBuffer addClassInstance(String strClass, List MethodParam, UtFileText cUtFileText);


    /**
     * 
     * @category doGenerateMethod
     * @author 吴平福
     * @param cMethodInfo
     * @param cUtFileText
     * @return update 2017年11月13日
     */
    public StringBuffer doGenerateMethod(MethodInfo cMethodInfo, UtFileText cUtFileText) {

        StringBuffer sb = new StringBuffer();
        // 注释
        StringBuffer sbJavaDoc = new StringBuffer();
        sbJavaDoc.append(GenerateUtils.addMethodJavaDoc(cMethodInfo.getModifiers(), cMethodInfo.getStrReturn(),
                cMethodInfo.getMethodName(), cMethodInfo.getMethodParam(), cUtFileText));
        // 方法声明
        // StringBuffer sbMethodName = new StringBuffer();
        // sbMethodName.append(addMethodDeclare(cMethodInfo.getMethodName(), cUtFileText));


        // 类声明
        StringBuffer sbClassName = new StringBuffer();
        sbClassName.append(addClassInstance(cMethodInfo.getClassName(), cMethodInfo.getMethodParam(), cUtFileText));

        // 获取返回值
        StringBuffer sbReturnName = new StringBuffer();
        sbReturnName.append(addMethodReturn(cMethodInfo.getMethodName(), cMethodInfo.getStrReturn(),
                cMethodInfo.getMethodParam(), cUtFileText));

        // 调用方法
        StringBuffer sbCallMethod = new StringBuffer();
        sbCallMethod.append(addMethodCaller(cMethodInfo.getClassName(), cMethodInfo.getMethodName(),
                cMethodInfo.getMethodParam(), cUtFileText));
        sbCallMethod.append(GenerateUtils.addMethodParam2Method(cMethodInfo.getModifiers(),
                cMethodInfo.getMethodParam(), cUtFileText));

        // 判断
        StringBuffer sbAssert = new StringBuffer();
        sbAssert.append(addMethodAssert(cMethodInfo.getStrReturn(), cUtFileText));


        // 方法参数初始化
        ArrayList<String> cParamInitBody = addMethodParamInit2(cMethodInfo.getMethodParam(), cUtFileText);

        for (int i = 0; i < cParamInitBody.size(); i++) {
            sb.append(sbJavaDoc).append(addMethodDeclare(cMethodInfo.getMethodName(), cUtFileText))
                    .append(cParamInitBody.get(i)).append(sbClassName).append(sbReturnName).append(sbCallMethod)
                    .append(sbAssert);
            logger.trace(sb.length());
        }

        cParamInitBody.clear();
        return sb;

    }



    /**
     * 
     * @category @author 吴平福
     * @param strReturn
     * @param sb update 2017年9月29日
     */
    public StringBuffer addMethodAssert(String strReturn, UtFileText cUtFileText) {

        StringBuffer sb = new StringBuffer();
        strReturn = strReturn.trim();

        if (strReturn.equalsIgnoreCase("void")) {
            sb.append("   //请在这里增加检查点:比如 assertEquals(true, result);").append("\n");
        } else if (strReturn.equalsIgnoreCase("boolean")) {
            sb.append("    assertEquals(true, result);").append("\n");
        } else if (strReturn.equalsIgnoreCase("String")) {
            sb.append("    assertEquals(\"a\", result);").append("\n");
        } else if (strReturn.startsWith("Long")) {
            sb.append("    assertEquals(Long.valueOf(\"1\"), result);").append("\n");
        } else if (strReturn.equalsIgnoreCase("Integer")) {
            sb.append("    assertEquals(Integer.valueOf(1), result);").append("\n");
        } else if (strReturn.equalsIgnoreCase("int")) {
            sb.append("    assertEquals(1, result);").append("\n");
        } else if (strReturn.equalsIgnoreCase("double")) {
            sb.append("    ").append(strReturn).append(" dwupf=new Double(123.456);").append("\n");
            sb.append("    assertEquals(dwupf, result);").append("\n");
        } else if (strReturn.equalsIgnoreCase("Character")) {
            sb.append("    Character ch = 'a';\n");
            sb.append("    assertEquals(ch, result);").append("\n");
        } else {
            sb.append("    assertEquals(1, result);").append("\n");
        }

        sb.append("  }\n");
        return sb;
    }

    /**
     * 
     * @category @author 吴平福
     * @param strReturn
     * @param sb update 2017年9月29日
     */
    public StringBuffer addMethodReturn(String strMethod, String strReturn, List MethodParam, UtFileText cUtFileText) {
        StringBuffer sb = new StringBuffer();
        if (!strReturn.equalsIgnoreCase("void")) {
            sb.append("    ").append(strReturn).append("  result=");
        }
        return sb;
    }



    /**
     * 
     * @category @author 吴平福
     * @param Modifiers
     * @param strClass
     * @param strMethod
     * @param MethodParam
     * @param strReturn
     * @param sb update 2017年9月29日
     */
    private StringBuffer addMethodDeclare(String strMethodName, UtFileText cUtFileText) {
        StringBuffer sb = new StringBuffer();
        sb.append("  public void test").append(strMethodName).append("_").append(GenerateUnitTests.iMethodCount++)
                .append("() throws Exception\n").append("  {").append("\n");
        return sb;
    }

    /**
     * 
     * @category 增加方法的参数的初始化
     * @author 吴平福
     * @param MethodParam
     * @param sb update 2017年9月29日
     */
    public ArrayList<String> addMethodParamInit2(List MethodParam, UtFileText cUtFileText) {

        ArrayList<ArrayList<String>> listAll = new ArrayList<ArrayList<String>>();
        for (int i = 0; i < MethodParam.size(); i++) {
            // System.out.println(MethodParam.get(i));
            ParamInitBody cParamInitBody = new ParamInitBody();
            GenerateUtils.formatToParamBody(cParamInitBody, MethodParam.get(i).toString());

            switch (cParamInitBody.getParamType()) {

                case "boolean":
                case "final boolean":
                case "boolean[]":
                    listAll.add(new fuzzBoolean().getFuzze(cParamInitBody));
                    break;

                case "byte":
                case "final byte":
                case "byte[]":
                    listAll.add(new fuzzebyte().getFuzze(cParamInitBody));
                    break;

                case "char":
                case "final char":
                case "char[]":
                    listAll.add(new fuzzechar().getFuzze(cParamInitBody));
                    break;

                case "double":
                case "final double":
                case "double[]":
                    listAll.add(new fuzzedouble().getFuzze(cParamInitBody));
                    break;

                case "float":
                case "final float":
                case "float[]":
                    listAll.add(new fuzzefloat().getFuzze(cParamInitBody));
                    break;

                case "int":
                case "final int":
                case "int[]":
                    listAll.add(new fuzzeInt().getFuzze(cParamInitBody));
                    break;

                case "long":
                case "final long":
                case "long[]":
                    listAll.add(new fuzzelong().getFuzze(cParamInitBody));
                    break;

                case "short":
                case "final short":
                case "short[]":
                    listAll.add(new fuzzeshort().getFuzze(cParamInitBody));
                    break;
                    
                case "String":
                case "final String":
                case "String[]":
                    listAll.add(new fuzzeString().getFuzze(cParamInitBody));
                    break;

                case "Connection":
                    listAll.add(new fuzzeConnection().getFuzze(cParamInitBody));
                    break;

                default:
                    listAll.add(new fuzzeCommon().getFuzze(cParamInitBody));
                    break;
            }
        }

        logger.debug("listAll.size()=" + listAll.size());
        ArrayList<String> listParamInitBody = new ArrayList<String>();

        new Descartes().run(listAll, listParamInitBody, 0, "");

        for (int j = 0; j < listAll.size(); j++) {
            listAll.get(j).clear();
        }
        int i = 1;
        for (String s : listParamInitBody) {
            logger.trace(i++ + ":" + s);
        }

        logger.debug("listParamInitBody.size()=" + listParamInitBody.size());

        while (listParamInitBody.size() > GenerateConst.Max_CaseCount_PerMethod) {
            listParamInitBody.remove(listParamInitBody.size()-1);
        }
        logger.debug("after delete: listParamInitBody.size()=" + listParamInitBody.size());
        return listParamInitBody;
    }



}
