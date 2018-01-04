/**
 * @author 吴平福 E-mail:wupf@asiainfo.com
 * @version 创建时间：2017年9月29日 下午10:14:00 类说明
 */

package org.jpf.unittests.generateuts.utils;


import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jpf.unittests.generateuts.JpfMethodInfo;
import org.jpf.unittests.generateuts.JpfUtInfo;
import org.jpf.unittests.generateuts.ParamInitBody;
import org.jpf.unittests.generateuts.fuzze.fuzzBoolean;
import org.jpf.unittests.generateuts.fuzze.fuzzCalendar;
import org.jpf.unittests.generateuts.fuzze.fuzzClass;
import org.jpf.unittests.generateuts.fuzze.fuzzCollection;
import org.jpf.unittests.generateuts.fuzze.fuzzCommon;
import org.jpf.unittests.generateuts.fuzze.fuzzConnection;
import org.jpf.unittests.generateuts.fuzze.fuzzDate;
import org.jpf.unittests.generateuts.fuzze.fuzzFile;
import org.jpf.unittests.generateuts.fuzze.fuzzHttpServletRequest;
import org.jpf.unittests.generateuts.fuzze.fuzzInteger;
import org.jpf.unittests.generateuts.fuzze.fuzzIterator;
import org.jpf.unittests.generateuts.fuzze.fuzzList;
import org.jpf.unittests.generateuts.fuzze.fuzzLong;
import org.jpf.unittests.generateuts.fuzze.fuzzMap;
import org.jpf.unittests.generateuts.fuzze.fuzzSet;
import org.jpf.unittests.generateuts.fuzze.fuzzTimestamp;
import org.jpf.unittests.generateuts.fuzze.fuzzbyte;
import org.jpf.unittests.generateuts.fuzze.fuzzchar;
import org.jpf.unittests.generateuts.fuzze.fuzzdouble;
import org.jpf.unittests.generateuts.fuzze.fuzzeInt;
import org.jpf.unittests.generateuts.fuzze.fuzzeString;
import org.jpf.unittests.generateuts.fuzze.fuzzelong;
import org.jpf.unittests.generateuts.fuzze.fuzzeshort;
import org.jpf.unittests.generateuts.fuzze.fuzzfloat;

import com.asiainfo.utils.AiDateTimeUtil;



/**
 * 
 */
public class GenerateUtil {
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
    /*
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

*/
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

    /**
     * 
     * @category 增加方法的参数的初始化
     * @author 吴平福
     * @param MethodParam
     * @param sb update 2017年9月29日
     */
    public static ArrayList<String> addMethodParamInit2(List MethodParam, JpfUtInfo cJpfUtInfo, int MaxCond) {

        ArrayList<ArrayList<String>> listAll = new ArrayList<ArrayList<String>>();
        for (int i = 0; i < MethodParam.size(); i++) {
            // System.out.println(MethodParam.get(i));
            ParamInitBody cParamInitBody = new ParamInitBody();
            FormatUtil.formatToParamBody(cParamInitBody, MethodParam.get(i).toString());

            switch (cParamInitBody.getParamType()) {

                case "boolean":
                case "final boolean":
                case "boolean[]":
                    listAll.add(new fuzzBoolean().getFuzzeForNull(cParamInitBody));
                    break;

                case "byte":
                case "final byte":
                case "byte[]":
                    listAll.add(new fuzzbyte().getFuzzeForNull(cParamInitBody));
                    break;

                case "Calendar":
                    listAll.add(new fuzzCalendar().getFuzzeForNull(cParamInitBody));
                    break;

                case "Class":
                    listAll.add(new fuzzClass().getFuzzeForNull(cParamInitBody));
                    break;

                case "char":
                case "final char":
                case "char[]":
                    listAll.add(new fuzzchar().getFuzzeForNull(cParamInitBody));
                    break;

                case "Collection":
                    listAll.add(new fuzzCollection().getFuzzeForNull(cParamInitBody));
                    break;

                case "Date":
                    listAll.add(new fuzzDate().getFuzzeForNull(cParamInitBody));
                    cJpfUtInfo.addImport("import java.util.Date;");
                    break;

                case "double":
                case "final double":
                case "double[]":
                    listAll.add(new fuzzdouble().getFuzzeForNull(cParamInitBody));
                    break;

                case "float":
                case "final float":
                case "float[]":
                    listAll.add(new fuzzfloat().getFuzzeForNull(cParamInitBody));
                    break;

                case "File":
                    listAll.add(new fuzzFile().getFuzzeForNull(cParamInitBody));
                    break;

                case "int":
                case "final int":
                case "int[]":
                    listAll.add(new fuzzeInt().getFuzzeForNull(cParamInitBody));
                    break;

                case "Integer":
                case "Integer[]":
                    listAll.add(new fuzzInteger().getFuzzeForNull(cParamInitBody));
                    break;

                case "Iterator":
                    listAll.add(new fuzzIterator().getFuzzeForNull(cParamInitBody));
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

                case "Timestamp":
                    listAll.add(new fuzzTimestamp().getFuzzeForNull(cParamInitBody));
                    break;

                case "Connection":
                    listAll.add(new fuzzConnection().getFuzzeForNull(cParamInitBody));
                    break;

                case "Map":
                    listAll.add(new fuzzMap().getFuzzeForNull(cParamInitBody));
                    cJpfUtInfo.addImport("import java.util.HashMap;");
                    cJpfUtInfo.addImport("import java.util.Map;");
                    break;

                case "Set":
                    listAll.add(new fuzzSet().getFuzzeForNull(cParamInitBody));
                    cJpfUtInfo.addImport("import java.util.HashSet;");
                    cJpfUtInfo.addImport("import java.util.Set;");
                    break;

                case "HttpServletRequest":
                case "HttpServletResponse":
                    cJpfUtInfo.addImport("import org.easymock.EasyMock;");
                    listAll.add(new fuzzHttpServletRequest().getFuzzeForNull(cParamInitBody));
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
                    } else if (cParamInitBody.getParamType().startsWith("Set")) {
                        listAll.add(new fuzzSet().getFuzzeForNull(cParamInitBody));
                        cJpfUtInfo.addImport("import java.util.Set;");
                        cJpfUtInfo.addImport("import java.util.HashSet;");
                    } else {
                        listAll.add(new fuzzCommon().getFuzzeForNew(cParamInitBody, cJpfUtInfo));
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

        while (listParamInitBody.size() > MaxCond) {
            listParamInitBody.remove(listParamInitBody.size() - 1);
        }
        logger.debug("after delete: listParamInitBody.size()=" + listParamInitBody.size());
        return listParamInitBody;
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
        // System.out.println(isParamArray(aString));
        aString = "String args[ ]";
        System.out.println(aString);
        // System.out.println(isParamArray(aString));
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

