/**
 * @author 吴平福 E-mail:wupf@asiainfo.com
 * @version 创建时间：2017年12月8日 上午9:59:33 类说明
 */

package org.jpf.unittests.ibatis2x;

import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.jpf.unittests.generateuts.ClassInfoConst;
import org.jpf.unittests.generateuts.GenerateBaseMethods;
import org.jpf.unittests.generateuts.GenerateConst;
import org.jpf.unittests.generateuts.JpfMethodInfo;
import org.jpf.unittests.generateuts.ParamInitBody;
import org.jpf.unittests.generateuts.UtFileText;
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

import com.asiainfo.utils.ios.AiFileUtil;

/**
 * 
 */
public class GenerateMethodForInterface {
    private transient static final Logger logger = LogManager.getLogger();

    /**
     * 
     */
    public GenerateMethodForInterface() {
        // TODO Auto-generated constructor stub
    }

    public StringBuffer addClassInstance(String strClass, List MethodParam, UtFileText cUtFileText) {
        StringBuffer sb = new StringBuffer();
        sb.append(cUtFileText.getMinConstructor());
        return sb;
    }


    public void doGenerateMethod(JpfMethodInfo cMethodInfo, UtFileText cUtFileText, AppParam cAppParam) {
        StringBuffer sb = new StringBuffer();

        cUtFileText.addImport("import java.sql.SQLException;\n");
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
        // ArrayList<String> cParamInitBody = addMethodParamInit2(cMethodInfo.getMethodParam(),
        // cUtFileText);
        if (cMethodInfo.getMethodParam().size() == 0) {
            logger.warn(cMethodInfo.getClassName() + " " + cMethodInfo.getMethodName());
        }
        ArrayList<String> cParamInitBody = addMethodParamInit2(cMethodInfo.getMethodParam(), cUtFileText);

        for (int i = 0; i < cParamInitBody.size(); i++) {
            sb.append(sbJavaDoc).append(addMethodDeclare(cMethodInfo.getMethodName(), cUtFileText)).append("    try{\n")
                    .append(cParamInitBody.get(i)).append(sbClassName).append(sbReturnName).append(sbCallMethod)
                    .append(sbAssert)
                    .append(addCatch(cMethodInfo.getClassName() + "." + cMethodInfo.getMethodName()));
            logger.trace(sb.length());
        }
        /*
         * StringBuffer sbParamInitBody = getMethodInfo(cAppParam, cMethodInfo.getMethodParam());
         * sb.append(sbJavaDoc).append(addMethodDeclare(cMethodInfo.getMethodName(),
         * cUtFileText)).append(sbParamInitBody)
         * .append(sbClassName).append(sbReturnName).append(sbCallMethod).append(sbAssert)
         * .append(getMethodEnd(cMethodInfo.getClassName() + "." + cMethodInfo.getMethodName()));
         * logger.trace(sb.length());
         */
        cUtFileText.sbMethod.append(sb);
    }


    public StringBuffer addCatch(String strClassName) {
        StringBuffer sb = new StringBuffer();
        sb.append("    }catch(Exception ex){\n")
        .append("        ex.printStackTrace();\n")
        .append("      fail(\"").append(strClassName).append(" error! ").append("\");\n")
        .append("      }\n")
        .append("  }\n");
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

    public StringBuffer addMethodCaller(String strClass, String strMethod, List MethodParam, UtFileText cUtFileText) {
        // TODO Auto-generated method stub
        StringBuffer sb = new StringBuffer();
        sb.append("      fixture.").append(strMethod);
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
                    listAll.add(new fuzzeInt().getFuzze(cParamInitBody));
                    break;

                case "long":
                case "final long":
                case "long[]":
                    listAll.add(new fuzzelong().getFuzzeForNull(cParamInitBody));
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

                default:
                    listAll.add(new fuzzeCommon().getFuzzeForNew(cParamInitBody));
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

    /**
     * 
     * @category 获取类的方法
     * @author 吴平福
     * @param cAppParam
     * @param strClassName update 2017年12月8日
     */
    public StringBuffer getMethodInfo(AppParam cAppParam, List listParam) {
        StringBuffer sb = new StringBuffer();
        String strParam = "";
        if (listParam.size() == 0) {
            logger.warn("listParam.size()=" + listParam.size());
            return sb;
        }
        strParam = listParam.get(0).toString().trim();
        String strClassName = strParam.substring(0, strParam.indexOf(" "));
        String strVarName = strParam.substring(strParam.indexOf(" "), strParam.length());
        sb.append("    try{\n");
        sb.append("      ").append(strClassName).append(strVarName).append("= new ").append(strClassName)
                .append("();\n");
        String strJavaFileName = "";
        for (int i = 0; i < cAppParam.vFilesAll.size(); i++) {
            if (cAppParam.vFilesAll.get(i).endsWith(strClassName + ".java")) {
                strJavaFileName = cAppParam.vFilesAll.get(i);
                break;
            }
        }

        logger.info(strJavaFileName);

        try {
            String sourceString = AiFileUtil.getFileTxt(strJavaFileName, "GBK");
            logger.trace(sourceString);
            ASTParser astParser = ASTParser.newParser(AST.JLS8);
            astParser.setKind(ASTParser.K_COMPILATION_UNIT);
            astParser.setResolveBindings(true);

            astParser.setSource(sourceString.toCharArray());
            CompilationUnit cCompilationUnit = (CompilationUnit) astParser.createAST(null);


            List types = cCompilationUnit.types();
            if (types.size() == 0) {
                logger.warn("type=null:" + strJavaFileName);
                return sb;
            }
            TypeDeclaration typeDec = (TypeDeclaration) types.get(0);
            logger.info("classname=" + typeDec.getName());
            logger.debug("typeDec.getModifiers()=" + typeDec.getModifiers());
            if (typeDec.getModifiers() == ClassInfoConst.CLASS_TYPE_ABSTRACT) {
                // abstract class
                logger.info("抽象类不能生产单元测试：" + strJavaFileName);
                return sb;
            }
            if (typeDec.isInterface()) {
                logger.info("不处理非接口类单元测试：" + strJavaFileName);
                return sb;
            }

            // show methods
            MethodDeclaration methodDec[] = typeDec.getMethods();

            for (MethodDeclaration method : methodDec) {

                SimpleName methodName = method.getName();
                if (!methodName.toString().startsWith("set")) {
                    continue;
                }
                logger.debug(methodName.toString());
                // 1 public
                // 2 private
                // 9 public static
                // 10 private static
                sb.append("     ").append(strVarName).append(".").append(methodName.toString()).append("()")
                        .append(";\n");

                // List param = method.parameters();

            }
        } catch (Exception ex) {
            // TODO: handle exception
            ex.printStackTrace();
        }


        return sb;
    }

    private void getMethodInfo(String pkgName) {
        StringBuffer sb = new StringBuffer();
        try {
            Class clazz = Class.forName(pkgName);
            Method[] methods = clazz.getMethods();
            for (Method method : methods) {
                String methodName = method.getName();
                System.out.println("方法名称:" + methodName);

                Class<?>[] parameterTypes = method.getParameterTypes();
                for (Class<?> clas : parameterTypes) {
                    String parameterName = clas.getName();
                    System.out.println("参数名称:" + parameterName);
                }
                System.out.println("*****************************");
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
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
        sb.append("  public void test").append(strMethodName).append("_").append(GenerateConst.iMethodCount++)
                .append("() throws Exception\n").append("  {").append("\n");
        return sb;
    }
}
