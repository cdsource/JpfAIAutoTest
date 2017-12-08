/**
 * @author 吴平福 E-mail:wupf@asiainfo.com
 * @version 创建时间：2017年9月29日 上午11:30:51 类说明
 */

package org.jpf.unittests.generateuts;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jdt.internal.compiler.codegen.CachedIndexEntry;
import org.jpf.unittests.generateuts.fuzze.fuzzBoolean;
import org.jpf.unittests.generateuts.fuzze.fuzzList;
import org.jpf.unittests.generateuts.fuzze.fuzzLong;
import org.jpf.unittests.generateuts.fuzze.fuzzMap;
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
    public abstract String addMethodCaller(String strClassName, String strMethod, List MethodParam,
            JpfUtInfo cJpfUtInfo);

    /**
     * 
     * @category @author 吴平福
     * @param strClass
     * @param MethodParam
     * @param cUtFileText
     * @return update 2017年11月14日
     */
    public abstract String addClassInstance(String strClassName, List MethodParam, JpfUtInfo cJpfUtInfo);

    /**
     * 
     * @category 增加额外的公共方法
     * @author 吴平福
     * @param cJpfUtInf update 2017年12月9日
     */
    public abstract void addExtraMethod(String strClassName, JpfUtInfo cJpfUtInfo);

    /**
     * 
     * @category 增加TRY
     * @author 吴平福
     * @return update 2017年12月9日
     */
    public String addTry() {
        return "    try{\n";
    }


    /**
     * 
     * @category 增加CATCH
     * @author 吴平福
     * @param strClassName
     * @return update 2017年12月9日
     */
    public String addCatch(String strClassName) {
        StringBuffer sb = new StringBuffer();
        sb.append("    }catch(Exception ex){\n").append("        ex.printStackTrace();\n").append("      }\n");
        return sb.toString();
    }

    /**
     * 
     * @category doGenerateMethod
     * @author 吴平福
     * @param cMethodInfo
     * @param cUtFileText
     * @return update 2017年11月13日
     */
    public void doGenerateMethod(JpfMethodInfo cMethodInfo, JpfUtInfo cJpfUtInfo) {

        StringBuffer sb = new StringBuffer();
        // 注释
        StringBuffer sbJavaDoc = new StringBuffer();
        sbJavaDoc.append(GenerateUtils.addMethodJavaDoc(cMethodInfo, cJpfUtInfo));
        // 方法声明
        // StringBuffer sbMethodName = new StringBuffer();
        // sbMethodName.append(addMethodDeclare(cMethodInfo.getMethodName(), cUtFileText));

        // 类声明
        StringBuffer sbClassName = new StringBuffer();
        sbClassName.append(addClassInstance(cMethodInfo.getClassName(), cMethodInfo.getMethodParam(), cJpfUtInfo));

        // 获取返回值
        StringBuffer sbReturnName = new StringBuffer();
        sbReturnName.append(addMethodReturn(cMethodInfo.getMethodName(), cMethodInfo.getStrReturn(),
                cMethodInfo.getMethodParam(), cJpfUtInfo));

        // 调用方法
        StringBuffer sbCallMethod = new StringBuffer();
        sbCallMethod.append(addMethodCaller(cMethodInfo.getClassName(), cMethodInfo.getMethodName(),
                cMethodInfo.getMethodParam(), cJpfUtInfo));
        sbCallMethod.append(GenerateUtils.addMethodParam2Method(cMethodInfo.getModifiers(),
                cMethodInfo.getMethodParam(), cJpfUtInfo));

        // 判断
        StringBuffer sbAssert = new StringBuffer();
        sbAssert.append(addMethodAssert(cMethodInfo.getStrReturn(), cJpfUtInfo));

        // 方法参数初始化
        if (cMethodInfo.getMethodParam().size() == 0) {
            // 方法无参数
            sb.append(sbJavaDoc).append(addMethodDeclare(cMethodInfo.getMethodName(), cJpfUtInfo)).append(addTry())
                    .append(sbClassName).append(sbReturnName).append(sbCallMethod).append(sbAssert)
                    .append(addCatch(cMethodInfo.getClassName()));
            logger.trace(sb);
            JpfUtMethodInfo cJpfUtMethodInfo = new JpfUtMethodInfo();
            cJpfUtMethodInfo.setMethodJavaDoc(sbJavaDoc.toString());
            cJpfUtMethodInfo.setMethodDeclare(addMethodDeclare(cMethodInfo.getMethodName(), cJpfUtInfo));
            cJpfUtMethodInfo.setMethodTry(addTry());

            cJpfUtMethodInfo.setMethodCaller(sbReturnName.toString() + sbCallMethod.toString());
            cJpfUtMethodInfo.setMethodAssert(sbAssert.toString());
            cJpfUtMethodInfo.setMethodCatch(addCatch(cMethodInfo.getClassName()));
            logger.trace(cJpfUtMethodInfo.toString());
            cJpfUtInfo.getListUtMethodInfos().add(cJpfUtMethodInfo);
        } else {
            // 方法有参数
            ArrayList<String> cParamInitBody = addMethodParamInit2(cMethodInfo.getMethodParam(), cJpfUtInfo);
            logger.debug("cParamInitBody.size()=" + cParamInitBody.size());
            for (int i = 0; i < cParamInitBody.size(); i++) {
                sb.append(sbJavaDoc).append(addMethodDeclare(cMethodInfo.getMethodName(), cJpfUtInfo)).append(addTry())
                        .append(cParamInitBody.get(i)).append(sbClassName).append(sbReturnName).append(sbCallMethod)
                        .append(sbAssert).append(addCatch(cMethodInfo.getClassName()));
                logger.trace(sb);
                JpfUtMethodInfo cJpfUtMethodInfo = new JpfUtMethodInfo();
                cJpfUtMethodInfo.setMethodJavaDoc(sbJavaDoc.toString());
                cJpfUtMethodInfo.setMethodDeclare(addMethodDeclare(cMethodInfo.getMethodName(), cJpfUtInfo));
                cJpfUtMethodInfo.setMethodTry(addTry());
                cJpfUtMethodInfo.setMethodParam(cParamInitBody.get(i));
                cJpfUtMethodInfo.setMethodCaller(sbReturnName.toString() + sbCallMethod.toString());
                cJpfUtMethodInfo.setMethodAssert(sbAssert.toString());
                cJpfUtMethodInfo.setMethodCatch(addCatch(cMethodInfo.getClassName()));
                logger.trace(cJpfUtMethodInfo.toString());
                cJpfUtInfo.getListUtMethodInfos().add(cJpfUtMethodInfo);
            }

            cParamInitBody.clear();
        }

    }



    /**
     * 
     * @category @author 吴平福
     * @param strReturn
     * @param sb update 2017年9月29日
     */
    public String addMethodAssert(String strReturn, JpfUtInfo cJpfUtInfo) {

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

        return sb.toString();
    }

    /**
     * 
     * @category @author 吴平福
     * @param strReturn
     * @param sb update 2017年9月29日
     */
    public String addMethodReturn(String strMethod, String strReturn, List MethodParam, JpfUtInfo cJpfUtInfo) {
        StringBuffer sb = new StringBuffer();
        if (!strReturn.equalsIgnoreCase("void")) {
            sb.append("    ").append(strReturn).append("  result=");
        }
        return sb.toString();
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
    private String addMethodDeclare(String strMethodName, JpfUtInfo cJpfUtInfo) {
        StringBuffer sb = new StringBuffer();
        sb.append("  public void test").append(strMethodName).append("_").append(GenerateConst.iMethodCount++)
                .append("() throws Exception\n").append("  {").append("\n");
        return sb.toString();
    }

    /**
     * 
     * @category 增加方法的参数的初始化
     * @author 吴平福
     * @param MethodParam
     * @param sb update 2017年9月29日
     */
    public ArrayList<String> addMethodParamInit2(List MethodParam, JpfUtInfo cJpfUtInfo) {

        ArrayList<ArrayList<String>> listAll = new ArrayList<ArrayList<String>>();
        for (int i = 0; i < MethodParam.size(); i++) {
            // System.out.println(MethodParam.get(i));
            ParamInitBody cParamInitBody = new ParamInitBody();
            GenerateUtils.formatToParamBody(cParamInitBody, MethodParam.get(i).toString());

            switch (cParamInitBody.getParamType()) {

                case "boolean":
                case "final boolean":
                case "boolean[]":
                    listAll.add(new fuzzBoolean().getFuzzeForNull(cParamInitBody));
                    break;

                case "byte":
                case "final byte":
                case "byte[]":
                    listAll.add(new fuzzebyte().getFuzzeForNull(cParamInitBody));
                    break;

                case "char":
                case "final char":
                case "char[]":
                    listAll.add(new fuzzechar().getFuzzeForNull(cParamInitBody));
                    break;

                case "double":
                case "final double":
                case "double[]":
                    listAll.add(new fuzzedouble().getFuzzeForNull(cParamInitBody));
                    break;

                case "float":
                case "final float":
                case "float[]":
                    listAll.add(new fuzzefloat().getFuzzeForNull(cParamInitBody));
                    break;

                case "int":
                case "final int":
                case "int[]":
                case "Interger":
                case "Interger[]":
                    listAll.add(new fuzzeInt().getFuzzeForNull(cParamInitBody));
                    break;

                case "long":
                case "final long":
                case "long[]":
                    listAll.add(new fuzzelong().getFuzzeForNull(cParamInitBody));
                    break;

                case "Long":
                    listAll.add(new fuzzLong().getFuzzeForNull(cParamInitBody));
                    break;

                case "short":
                case "final short":
                case "short[]":
                    listAll.add(new fuzzeshort().getFuzzeForNull(cParamInitBody));
                    break;

                case "String":
                case "final String":
                case "String[]":
                    listAll.add(new fuzzeString().getFuzzeForNull(cParamInitBody));
                    break;

                case "Connection":
                    listAll.add(new fuzzeConnection().getFuzzeForNull(cParamInitBody));
                    break;

                case "Map":
                    listAll.add(new fuzzMap().getFuzzeForNull(cParamInitBody));
                    cJpfUtInfo.addImport("import java.util.HashMap;");
                    cJpfUtInfo.addImport("import java.util.Map;");
                    break;

                default:
                    if (cParamInitBody.getParamType().startsWith("Map")
                            || cParamInitBody.getParamType().startsWith("Map")) {
                        listAll.add(new fuzzMap().getFuzzeForNull(cParamInitBody));
                        cJpfUtInfo.addImport("import java.util.HashMap;");
                        cJpfUtInfo.addImport("import java.util.Map;");
                    } else if (cParamInitBody.getParamType().startsWith("List")) {
                        listAll.add(new fuzzList().getFuzzeForNull(cParamInitBody));
                        cJpfUtInfo.addImport("import java.util.List;");
                        cJpfUtInfo.addImport("import java.util.ArrayList;");

                    } else {
                        listAll.add(new fuzzeCommon().getFuzzeForNew(cParamInitBody));
                    }

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
            listParamInitBody.remove(listParamInitBody.size() - 1);
        }
        logger.debug("after delete: listParamInitBody.size()=" + listParamInitBody.size());
        return listParamInitBody;
    }



}
