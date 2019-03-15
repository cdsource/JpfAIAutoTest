/**
 * @author 吴平福 E-mail:wupf@asiainfo.com
 * @version 创建时间：2018年2月28日 上午11:34:06 类说明
 */

package org.jpf.aut.utils;


import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jpf.algorithm.Descartes;
import org.jpf.aut.base.GenerateInputParam;
import org.jpf.aut.base.JpfMethodInfo;
import org.jpf.aut.base.JpfUtInfo;
import org.jpf.aut.base.RunResult;
import org.jpf.aut.common.consts.AutConst;
import org.jpf.aut.gts.gtm.MethodParamBody;
import org.jpf.aut.gts.gtm.fuzzByParamType.fuzzBigDecimal;
import org.jpf.aut.gts.gtm.fuzzByParamType.fuzzBoolean;
import org.jpf.aut.gts.gtm.fuzzByParamType.fuzzCalendar;
import org.jpf.aut.gts.gtm.fuzzByParamType.fuzzCharacter;
import org.jpf.aut.gts.gtm.fuzzByParamType.fuzzClass;
import org.jpf.aut.gts.gtm.fuzzByParamType.fuzzCollection;
import org.jpf.aut.gts.gtm.fuzzByParamType.fuzzCommon;
import org.jpf.aut.gts.gtm.fuzzByParamType.fuzzDate;
import org.jpf.aut.gts.gtm.fuzzByParamType.fuzzFile;
import org.jpf.aut.gts.gtm.fuzzByParamType.fuzzHttpServletRequest;
import org.jpf.aut.gts.gtm.fuzzByParamType.fuzzInt;
import org.jpf.aut.gts.gtm.fuzzByParamType.fuzzInteger;
import org.jpf.aut.gts.gtm.fuzzByParamType.fuzzIterator;
import org.jpf.aut.gts.gtm.fuzzByParamType.fuzzList;
import org.jpf.aut.gts.gtm.fuzzByParamType.fuzzLocalDateTime;
import org.jpf.aut.gts.gtm.fuzzByParamType.fuzzLong;
import org.jpf.aut.gts.gtm.fuzzByParamType.fuzzMap;
import org.jpf.aut.gts.gtm.fuzzByParamType.fuzzObjectType;
import org.jpf.aut.gts.gtm.fuzzByParamType.fuzzSet;
import org.jpf.aut.gts.gtm.fuzzByParamType.fuzzString;
import org.jpf.aut.gts.gtm.fuzzByParamType.fuzzStringBuffer;
import org.jpf.aut.gts.gtm.fuzzByParamType.fuzzTimestamp;
import org.jpf.aut.gts.gtm.fuzzByParamType.fuzzbyte;
import org.jpf.aut.gts.gtm.fuzzByParamType.fuzzchar;
import org.jpf.aut.gts.gtm.fuzzByParamType.fuzzdouble;
import org.jpf.aut.gts.gtm.fuzzByParamType.fuzzfloat;
import org.jpf.aut.gts.gtm.fuzzByParamType.fuzzshort;
import org.jpf.utils.JpfDateTimeUtil;
import org.jpf.utils.classes.AddImport;
import org.jpf.utils.classes.FormatUtil;



/**
 * 
 */
public class GenerateUtil2 {

  /**
   * 
   */
  private GenerateUtil2() {}

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
   * @category 增加方法的参数的初始化
   * @author 吴平福
   * @param MethodParam
   * @param sb update 2017年9月29日
   */
  /*
   * public static ParamInitBody[] addMethodParamInit(List MethodParam, JpfUtInfo cJpfUtInfo) {
   * 
   * ParamInitBody[] cParamInitBodys = new ParamInitBody[MethodParam.size()]; StringBuffer sb = new
   * StringBuffer(); for (int i = 0; i < MethodParam.size(); i++) { //
   * System.out.println(MethodParam.get(i)); cParamInitBodys[i] = new ParamInitBody(); //
   * formatToParamBody(cParamInitBodys[i], MethodParam.get(i).toString()); if
   * (cParamInitBodys[i].isArray()) { // 数组 addArrayParamInit(cParamInitBodys[i], cJpfUtInfo); }
   * else if (cParamInitBodys[i].getParamType().equalsIgnoreCase("int") ||
   * cParamInitBodys[i].getParamType().equalsIgnoreCase("final int")) {
   * cParamInitBodys[i].setParamValue("1"); } else if
   * (cParamInitBodys[i].getParamType().equalsIgnoreCase("boolean")) { //
   * sb.append(" ").append(MethodParam.get(i)).append(" =true; ");
   * cParamInitBodys[i].setParamValue("true"); } else if
   * (cParamInitBodys[i].getParamType().equalsIgnoreCase("connection")) { cParamInitBodys[i].
   * setParamPreInit("    Class.forName(\"com.mysql.jdbc.Driver\");"); cParamInitBodys[i].
   * setParamValue("java.sql.DriverManager.getConnection(\"\", \"\", \"\");");
   * cJpfUtInfo.addImport("import java.sql.Connection;"); } else if
   * (cParamInitBodys[i].getParamType().startsWith("List")) {
   * sb.append(addListInit(MethodParam.get(i).toString(), cParamInitBodys[i].getParamType())); }
   * else if (cParamInitBodys[i].getParamType().startsWith("Integer")) {
   * sb.append(addIntegerInit(MethodParam.get(i).toString(), cParamInitBodys[i].getParamType())); }
   * else if (cParamInitBodys[i].getParamType().startsWith("Map")) {
   * sb.append(addMapInit(MethodParam.get(i).toString(), cParamInitBodys[i].getParamType()));
   * 
   * } else if (cParamInitBodys[i].getParamType().startsWith("Long")) { //// Long sysParameterID
   * =1L; sb.append(addLongInit(MethodParam.get(i).toString(), cParamInitBodys[i].getParamType()));
   * } else if (cParamInitBodys[i].getParamType().startsWith("BufferedImage")) {
   * sb.append(addBufferedImageInit(MethodParam.get(i).toString(),
   * cParamInitBodys[i].getParamType())); } else if
   * (cParamInitBodys[i].getParamType().startsWith("OutputStream")) {
   * sb.append(addOutputStreamInit(MethodParam.get(i).toString(),
   * cParamInitBodys[i].getParamType())); } else if
   * (cParamInitBodys[i].getParamType().startsWith("File")) {
   * sb.append(addFileInit(MethodParam.get(i).toString(), cParamInitBodys[i].getParamType())); }
   * else if (cParamInitBodys[i].getParamType().equalsIgnoreCase("Element")) {
   * sb.append("    ").append(MethodParam.get(i)). append(" = EasyMock.createMock(Element.class); "
   * ); } else if (cParamInitBodys[i].getParamType().equalsIgnoreCase("HttpMethod")) {
   * sb.append("    ").append(MethodParam.get(i)).
   * append(" = EasyMock.createMock(HttpMethod.class); "); } else {
   * sb.append("    ").append(MethodParam.get(i)).append(" =new ");
   * sb.append(cParamInitBodys[i].getParamType()).append("();"); } // HttpMethod sb.append("\n"); }
   * return cParamInitBodys; }
   * 
   */
  /**
   * 
   * @category 把方法的参数增加到方法中取消类型描述
   * @author 吴平福
   * @param MethodParam
   * @param sb update 2017年9月29日
   */
  public static StringBuilder addMethodParam2Method(int Modifiers, List MethodParam,
      JpfUtInfo cJpfUtInfo) {

    StringBuilder sb = new StringBuilder();
    // private && private static
    if (Modifiers == 2 || 10 == Modifiers) {
      return sb;
    }
    sb.append("(");
    for (int i = 0; i < MethodParam.size(); i++) {

      String strParamName = FormatUtil.formatParam(MethodParam.get(i).toString());
      int iPos = strParamName.lastIndexOf(" ");
      strParamName = strParamName.substring(iPos, strParamName.length()).trim();
      /*
       * strParamName = RemoveFinal(strParamName); strParamName =
       * strParamName.substring(strParamName.indexOf(" ")).trim(); int iPos =
       * strParamName.indexOf("["); if (iPos > 0) { strParamName = strParamName.substring(0, iPos);
       * }
       */
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

  /**
   * 
   * @author wupf@asiainfo.com
   * @param Modifiers
   * @param StrReturn
   * @param strMethodName
   * @param MethodParam
   * @param cJpfUtInfo
   * @return 2018年7月10日
   */
  public static String addMethodJavaDoc(int Modifiers, String StrReturn, String strMethodName,
      List MethodParam, JpfUtInfo cJpfUtInfo) {

    StringBuilder sb = new StringBuilder();

    // 需要注释
    if (GenerateInputParam.bNeedMethodJavaDoc) {
      sb.append("\n").append("  /**").append("\n").append("  * Run the ").append(strMethodName)
          .append(" method test.").append("\n").append("  *").append("\n")
          .append("  * @throws Exception").append("\n").append("  * ").append("\n")
          .append("  * @generated By wupf@asiainfo.com \n").append("  * @generated at ")
          .append(JpfDateTimeUtil.getCurrDateTime()).append("\n");
      sb.append("  *@see  Modifier=").append(Modifiers).append("  ").append(StrReturn).append(" ")
          .append(strMethodName);
      sb.append("(");
      for (int i = 0; i < MethodParam.size(); i++) {
        sb.append(MethodParam.get(i)).append(",");
      }
      sb.deleteCharAt(sb.length() - 1);
      sb.append(")\n");

      sb.append("  */\n");
    }
    if (GenerateInputParam.UtTimeOut >= 10) {
      sb.append("  @Test(timeout=").append(GenerateInputParam.UtTimeOut).append(")\n");
    }
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
   * @param cMethodParam
   * @param sb update 2017年9月29日
   */
  public static ArrayList<String> addMethodParamInit2(List cMethodParam, JpfUtInfo cJpfUtInfo,
      int MaxCond) {

    ArrayList<ArrayList<String>> listAll = new ArrayList<ArrayList<String>>();
    for (int i = 0; i < cMethodParam.size(); i++) {
      // logger.debug(cMethodParam.get(i));

      MethodParamBody cParamInitBody = new MethodParamBody(cMethodParam.get(i).toString());
      if (cMethodParam.get(i).toString().indexOf("<T") > 0
          || cMethodParam.get(i).toString().indexOf("<?") > 0) {
        logger.info(cMethodParam.get(i).toString());
        // 泛型
        logger.info(cParamInitBody.getParamType());
        RunResult.iGenericTypeCount++;
        return null;
      }
      if (1 == MaxCond) {
        cParamInitBody
            .setParamVariable(AutConst.CONSTRUCTOR_VAL_PREFIX + cParamInitBody.getParamVariable());
      }

      // logger.debug(cParamInitBody.getParamType());

      switch (cParamInitBody.getParamType()) {

        case "boolean":
        case "final boolean":
        case "boolean[]":
        case "Boolean":
        case "java.lang.Boolean":
          listAll.add(new fuzzBoolean().getFuzzeForNull(cParamInitBody));
          break;

        case "java.math.BigDecimal":
        case "BigDecimal":
          listAll.add(new fuzzBigDecimal().getFuzzeForNull(cParamInitBody));
          break;

        case "byte":
        case "final byte":
        case "byte[]":
        case "java.lang.Byte":
          listAll.add(new fuzzbyte().getFuzzeForNull(cParamInitBody));
          break;

        case "Calendar":
          listAll.add(new fuzzCalendar().getFuzzeForNull(cParamInitBody));
          break;

        case "Class":
          listAll.add(new fuzzClass().getFuzzeForNull(cParamInitBody));
          break;

        case "Character":
        case "java.lang.Character":
          listAll.add(new fuzzCharacter().getFuzzeForNull(cParamInitBody));
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
        case "Double":
          listAll.add(new fuzzdouble().getFuzzeForNull(cParamInitBody));
          break;

        case "float":
        case "final float":
        case "float[]":
        case "Float":
          listAll.add(new fuzzfloat().getFuzzeForNull(cParamInitBody));
          break;

        case "File":
          listAll.add(new fuzzFile().getFuzzeForNull(cParamInitBody));
          break;

        case "int":
        case "final int":
        case "int[]":
          listAll.add(new fuzzInt().getFuzzeForNull(cParamInitBody));
          break;

        case "Integer":
        case "Integer[]":
        case "java.lang.Integer":
          listAll.add(new fuzzInteger().getFuzzeForNull(cParamInitBody));
          break;

        case "Iterator":
          listAll.add(new fuzzIterator().getFuzzeForNull(cParamInitBody));
          break;

        case "LocalDateTime":
        case "LocalDateTime[]":
          listAll.add(new fuzzLocalDateTime().getFuzzeForNull(cParamInitBody));
          break;

        case "long":
          // case "final long":
        case "long[]":
        case "Long":
        case "java.lang.Long":
          listAll.add(new fuzzLong().getFuzzeForNull(cParamInitBody));
          break;

        case "short":
          // case "final short":
        case "short[]":
        case "Short":
        case "java.lang.Short":
          listAll.add(new fuzzshort().getFuzzeForNull(cParamInitBody));
          break;

        case "String":
          // case "final String":
        case "String[]":
        case "java.lang.String":
          listAll.add(new fuzzString().getFuzzeForNull(cParamInitBody));
          break;

        case "java.lang.StringBuffer":
          listAll.add(new fuzzStringBuffer().getFuzzeForNull(cParamInitBody));
          break;

        case "Timestamp":
          listAll.add(new fuzzTimestamp().getFuzzeForNull(cParamInitBody));
          break;

        case "ObjectType":
          listAll.add(new fuzzObjectType().getFuzzeForNull(cParamInitBody));
          break;

        case "HttpServletRequest":
        case "HttpServletResponse":
          cJpfUtInfo.addImport("import org.easymock.EasyMock;");
          listAll.add(new fuzzHttpServletRequest().getFuzzeForNull(cParamInitBody));
          break;
        case "Class<T>":
          /*
           * // 反射得到T的真实类型 ParameterizedType ptype = (ParameterizedType)
           * this.getClass().getGenericSuperclass();// 获取当前new的对象的泛型的父类的类型 this.clazz = (Class<T>)
           * ptype.getActualTypeArguments()[0]; // 获取第一个类型参数的真实类型model =
           * clazz.newInstance();实例化需要的时候添
           */
          break;

        default:
          if (cParamInitBody.getParamType().startsWith("List")) {
            listAll.add(new fuzzList().getFuzzeForNull(cParamInitBody));
            cJpfUtInfo.addImport("import java.util.List;");
            cJpfUtInfo.addImport("import java.util.ArrayList;");

            AddImport.getInstance().addImportForList(cParamInitBody.getParamType(), cJpfUtInfo);
          } else if (cParamInitBody.getParamType().startsWith("Set")
              || cParamInitBody.getParamType().startsWith("java.util.Set")) {
            listAll.add(new fuzzSet().getFuzzeForNull(cParamInitBody));
            cJpfUtInfo.addImport("import java.util.Set;");
            cJpfUtInfo.addImport("import java.util.HashSet;");
            AddImport.getInstance().addImportForList(cParamInitBody.getParamType(), cJpfUtInfo);
          } else if (cParamInitBody.getParamType().startsWith("Map")) {
            listAll.add(new fuzzMap().getFuzzeForNull(cParamInitBody));
            cJpfUtInfo.addImport("import java.util.HashMap;");
            cJpfUtInfo.addImport("import java.util.Map;");
          } else {
            // logger.info(cJpfUtInfo.getCurrentJavaFilePackage());
            cJpfUtInfo.setInitCountPerMethod(cJpfUtInfo.getInitCountPerMethod() + 1);

            if (cJpfUtInfo.getInitCountPerMethod() > 10) {
              logger.info(cJpfUtInfo.getInitCountPerMethod());
              logger.info(cJpfUtInfo.strCurrentFileName);
              for (int j = 0; j < cMethodParam.size(); j++) {
                logger.debug(cMethodParam.get(j));
              }
            }
            listAll.add(new fuzzCommon().getFuzzeForNew(cParamInitBody, cJpfUtInfo));
          }

          break;
      }
    }

    // logger.debug("listAll.size()=" + listAll.size());
    ArrayList<String> listParamInitBody = new ArrayList<String>();

    new Descartes().run(listAll, listParamInitBody, 0, "");

    for (int j = 0; j < listAll.size(); j++) {
      listAll.get(j).clear();
    }
    int i = 1;
    for (String s : listParamInitBody) {
      logger.trace(i++ + ":" + s);
    }

    // logger.debug("listParamInitBody.size()=" + listParamInitBody.size());

    while (listParamInitBody.size() > MaxCond) {
      listParamInitBody.remove(listParamInitBody.size() - 1);
    }
    // logger.debug(listParamInitBody.get(0));
    // logger.debug("after delete: listParamInitBody.size()=" +
    // listParamInitBody.size());
    return listParamInitBody;
  }

}
