/**
 * @author 吴平福 E-mail:wupf@asiainfo.com
 * @version 创建时间：2017年9月29日 下午10:14:00 类说明
 */

package org.jpf.unittests.generateuts.utils;


import java.lang.reflect.Method;
import java.util.List;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.jpf.unittests.generateuts.JpfMethodInfo;
import org.jpf.unittests.generateuts.JpfUtInfo;
import org.jpf.unittests.generateuts.ParamInitBody;

import com.asiainfo.utils.AiDateTimeUtil;
import com.asiainfo.utils.ios.AiFileUtil;



/**
 * 
 */
public class GenerateUtil{
    private static final Logger logger = LogManager.getLogger();



    /**
     * 
     * @category 替换最后一个查找的关键字
     * @author 吴平福
     * @param strInput
     * @param regEx
     * @param replacement
     * @return update 2017年10月5日
     */
    private static String replaceLast(String strInput, String regEx, String replacement) {
        int iPos = strInput.lastIndexOf(regEx);
        if (iPos >= 0) {
            String tmpStr = strInput.substring(0, iPos) + replacement;
            if (iPos + regEx.length() < strInput.length()) {
                tmpStr += strInput.substring(iPos + regEx.length(), strInput.length());
            }
            return tmpStr;
        }
        return strInput;

    }


    /**
     * 
     * @category @author 吴平福
     * @param strParam
     * @param strParamType
     * @return update 2017年11月9日
     */
    private static String addListInit(String strParam, String strParamType) {
        String strtmp = "    " + strParam + " =new " + strParamType + "();";
        strtmp = replaceLast(strtmp, "List", "ArrayList");
        return strtmp;
    }

    /**
     * 
     * @category @author 吴平福
     * @param strParam
     * @param strParamType
     * @return update 2017年11月9日
     */
    private static String addBufferedImageInit(String strParam, String strParamType) {
        return "    " + strParam + " = ImageIO.read(new File(\"a\"));";
    }

    /**
     * 
     * @category @author 吴平福
     * @param strParam
     * @param strParamType
     * @return update 2017年11月9日
     */
    private static String addFileInit(String strParam, String strParamType) {
        return "    " + strParam + " =new File(\"aa\");";

    }

    /**
     * 
     * @category @author 吴平福
     * @param strParam
     * @param strParamType
     * @return update 2017年11月9日
     */
    private static String addIntegerInit(String strParam, String strParamType) {
        // Integer typeCode =new Integer();
        return "    " + strParam + " =new Integer(1);";
    }


    /**
     * 
     * @category @author 吴平福
     * @param strParam
     * @param strParamType
     * @return update 2017年11月9日
     */
    private static String addMapInit(String strParam, String strParamType) {
        String strtmp = "    " + strParam + " =new " + strParamType + "();";
        strtmp = replaceLast(strtmp, "Map", "HashMap");

        return strtmp;
    }

    private static String addLongInit(String strParam, String strParamType) {
        // Long a=1L;
        return "    " + strParam + " =1L ;";

    }

    private static String addOutputStreamInit(String strParam, String strParamType) {
        String strtmp = "    " + strParam + " =new FileOutputStream(\"aa\");";
        return strtmp;
    }



    /**
     * 
     * @category 初始化数组
     * @author 吴平福
     * @param cParamInitBody
     * @param strParamName
     * @param strParamType
     * @param cUtFileText update 2017年11月17日
     */
    public static void addArrayParamInit(/* IN */ParamInitBody cParamInitBody, JpfUtInfo cJpfUtInfo) {
        // 数组
        cParamInitBody.setArray(true);
        if (cParamInitBody.getParamType().startsWith("String")) {
            cParamInitBody.setParamValue("new String[]{\"aa\",\"bb\"}");
        } else if (cParamInitBody.getParamType().startsWith("int")) {
            cParamInitBody.setParamValue("new int[]{1,2}");
        } else {

            cParamInitBody.setParamValue(cParamInitBody.getParamType().replaceAll("\\[\\]", "\\[2\\]"));

        }
    }



    /**
     * 
     * @category 增加方法的参数的初始化
     * @author 吴平福
     * @param MethodParam
     * @param sb update 2017年9月29日
     */
    public static ParamInitBody[] addMethodParamInit(List MethodParam, JpfUtInfo cJpfUtInfo) {

        ParamInitBody[] cParamInitBodys = new ParamInitBody[MethodParam.size()];
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < MethodParam.size(); i++) {
            // System.out.println(MethodParam.get(i));
            cParamInitBodys[i] = new ParamInitBody();
            // formatToParamBody(cParamInitBodys[i], MethodParam.get(i).toString());
            if (cParamInitBodys[i].isArray()) {
                // 数组
                addArrayParamInit(cParamInitBodys[i], cJpfUtInfo);
            } else if (cParamInitBodys[i].getParamType().equalsIgnoreCase("int")
                    || cParamInitBodys[i].getParamType().equalsIgnoreCase("final int")) {
                cParamInitBodys[i].setParamValue("1");
            } else if (cParamInitBodys[i].getParamType().equalsIgnoreCase("boolean")) {
                // sb.append(" ").append(MethodParam.get(i)).append(" =true; ");
                cParamInitBodys[i].setParamValue("true");
            } else if (cParamInitBodys[i].getParamType().equalsIgnoreCase("connection")) {
                cParamInitBodys[i].setParamPreInit("    Class.forName(\"com.mysql.jdbc.Driver\");");
                cParamInitBodys[i].setParamValue("java.sql.DriverManager.getConnection(\"\", \"\", \"\");");
                cJpfUtInfo.addImport("import java.sql.Connection;");
            } else if (cParamInitBodys[i].getParamType().startsWith("List")) {
                sb.append(addListInit(MethodParam.get(i).toString(), cParamInitBodys[i].getParamType()));
            } else if (cParamInitBodys[i].getParamType().startsWith("Integer")) {
                sb.append(addIntegerInit(MethodParam.get(i).toString(), cParamInitBodys[i].getParamType()));
            } else if (cParamInitBodys[i].getParamType().startsWith("Map")) {
                sb.append(addMapInit(MethodParam.get(i).toString(), cParamInitBodys[i].getParamType()));

            } else if (cParamInitBodys[i].getParamType().startsWith("Long")) {
                //// Long sysParameterID =1L;
                sb.append(addLongInit(MethodParam.get(i).toString(), cParamInitBodys[i].getParamType()));
            } else if (cParamInitBodys[i].getParamType().startsWith("BufferedImage")) {
                sb.append(addBufferedImageInit(MethodParam.get(i).toString(), cParamInitBodys[i].getParamType()));
            } else if (cParamInitBodys[i].getParamType().startsWith("OutputStream")) {
                sb.append(addOutputStreamInit(MethodParam.get(i).toString(), cParamInitBodys[i].getParamType()));
            } else if (cParamInitBodys[i].getParamType().startsWith("File")) {
                sb.append(addFileInit(MethodParam.get(i).toString(), cParamInitBodys[i].getParamType()));
            } else if (cParamInitBodys[i].getParamType().equalsIgnoreCase("Element")) {
                sb.append("    ").append(MethodParam.get(i)).append(" = EasyMock.createMock(Element.class); ");
            } else if (cParamInitBodys[i].getParamType().equalsIgnoreCase("HttpMethod")) {
                sb.append("    ").append(MethodParam.get(i)).append(" = EasyMock.createMock(HttpMethod.class); ");
            } else {
                sb.append("    ").append(MethodParam.get(i)).append(" =new ");
                sb.append(cParamInitBodys[i].getParamType()).append("();");
            }
            // HttpMethod
            sb.append("\n");
        }
        return cParamInitBodys;
    }


    /**
     * 
     * @category 把方法的参数增加到方法中取消类型描述
     * @author 吴平福
     * @param MethodParam
     * @param sb update 2017年9月29日
     */
    public static StringBuilder addMethodParam2Method(int Modifiers, List MethodParam, JpfUtInfo cJpfUtInfo) {

        StringBuilder sb = new StringBuilder();
        if (Modifiers == 2) {
            return sb;
        }
        sb.append("(");
        for (int i = 0; i < MethodParam.size(); i++) {

            String strParamName = FormatUtil.formatParam(MethodParam.get(i).toString());
            strParamName = RemoveFinal(strParamName);
            strParamName = strParamName.substring(strParamName.indexOf(" ")).trim();
            int iPos = strParamName.indexOf("[");
            if (iPos > 0) {
                strParamName = strParamName.substring(0, iPos);
            }
            sb.append(strParamName).append(",");
        }

        if (MethodParam.size() > 0) {
            sb.deleteCharAt(sb.length() - 1);
        }
        sb.append(");\n");
        return sb;
    }

    /**
     * 
     * @category 增加方法的注释说明JAVADOC
     * @author 吴平福
     * @param strClass
     * @param strMethod
     * @param param
     * @param strReturn
     * @param sb update 2017年9月29日
     */
    public static String addMethodJavaDoc(JpfMethodInfo cJpfMethodInfo, JpfUtInfo cJpfUtInfo) {

        return addMethodJavaDoc(cJpfMethodInfo.getModifiers(), cJpfMethodInfo.getStrReturn(),
                cJpfMethodInfo.getMethodName(), cJpfMethodInfo.getMethodParam(), cJpfUtInfo);
    }

    public static String addMethodJavaDoc(int Modifiers, String StrReturn, String strMethodName, List MethodParam,
            JpfUtInfo cJpfUtInfo) {

        StringBuilder sb = new StringBuilder();
        sb.append("\n").append("  /**").append("\n");
        sb.append("  * Run the ").append(strMethodName).append(" method test.").append("\n");
        sb.append("  *").append("\n");
        sb.append("  * @throws Exception").append("\n");
        sb.append("  * ").append("\n");
        sb.append("  *  @generated By wupf@asiainfo.com at ").append(AiDateTimeUtil.getCurrDateTime()).append("\n");
        sb.append("  *  Modifier=").append(Modifiers).append("  ").append(StrReturn).append(" ").append(strMethodName);
        sb.append("(");
        for (int i = 0; i < MethodParam.size(); i++) {
            sb.append(MethodParam.get(i)).append(",");
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.append(")\n");

        sb.append("  */\n");
        sb.append("  @Test(timeout=1000)").append("\n");
        return sb.toString();
    }

    /**
     * 
     * @category 去掉参数中的尖括号和中间的内容
     * @author 吴平福
     * @param strParamType
     * @return update 2017年10月5日
     */
    public static String replaceAngleBrackets(String strParamType) {

        String regEx = "\\<.*\\>";
        Pattern pattern = Pattern.compile(regEx);
        Matcher matcher = pattern.matcher(strParamType.trim());

        if (matcher.find()) {
            strParamType = matcher.replaceAll("");
            System.out.println(strParamType);
            return strParamType;
        }
        return strParamType;
    }

    /**
     * 
     * @category 去除
     * @author 吴平福
     * @param strParamType
     * @return update 2017年10月5日
     */
    public static String RemoveFinal(String strParamType) {
        if (strParamType.startsWith("final")) {
            strParamType = strParamType.substring(5, strParamType.length()).trim();
        }
        return strParamType;
    }

    public static void main(String... args) {
        String aString = "String... args";
        aString = aString.replaceAll("\\.\\.\\.", "[]");
        System.out.println(aString);
        String regEx = "\\.\\.\\.";
        // logger.info(strParamType);
        Pattern pattern = Pattern.compile(regEx);
        Matcher matcher = pattern.matcher(aString);
        System.out.println(matcher.find());
        regEx = "\\<\\s{0,}.*\\[\\s{0,}\\]s{0,}\\>";
        pattern = Pattern.compile(regEx);
        matcher = pattern.matcher(aString);
        System.out.println(matcher.find());
        System.out.println(aString);
        //System.out.println(isParamArray(aString));
        aString = "String args[ ]";
        System.out.println(aString);
        //System.out.println(isParamArray(aString));
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
}

