/**
 * wupf@aliyun.com
 */
package org.jpf.aut.runtime;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jpf.utils.AiDateTimeUtil;
import org.jpf.utils.ios.AiFileUtil;

public class WriteException {
  private static final Logger logger = LogManager.getLogger();
  public static final String[] Check_Exceptions =
      {"java.lang.ArrayIndexOutOfBoundsException", "java.lang.StringIndexOutOfBoundsException",
          "java.io.FileNotFoundException", "java.sql.SQLException"};

  /**
   * 
   * @category:
   * @Title: WritePrivateException
   * @author:wupf@asiainfo.com
   * @date:2019年2月14日
   * @param ex
   * @param cStackTraceElement
   * @param isLimitException
   */
  public static void WritePrivateException(Exception ex, StackTraceElement[] cStackTraceElement,
      boolean isLimitException) {
    StringBuilder sb = new StringBuilder();
    sb.append(AiDateTimeUtil.getCurrDateTime()).append("\t");
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    ex.printStackTrace(new PrintStream(baos));
    String[] exceptions = baos.toString().split("\n");

    for (String Check_Exception : Check_Exceptions) {
      if (exceptions[0].indexOf(Check_Exception) >= 0) {
        sb.append(formatStringForCvs(exceptions[0])).append("\t");
        // logger.warn(exceptions[0]);
      }
    }
    String strTestClassName = cStackTraceElement[1].getClassName();
    sb.append(strTestClassName);
    int iPos = strTestClassName.indexOf("Test");
    strTestClassName = strTestClassName.substring(0, iPos);
    String strTestMethodName = cStackTraceElement[1].getMethodName();
    sb.append(".").append(strTestMethodName).append("\t");
    iPos = strTestMethodName.indexOf("test_");
    if (iPos >= 0) {
      strTestMethodName = strTestMethodName.substring(5, strTestMethodName.length());
    }
    iPos = strTestMethodName.lastIndexOf("_");
    if (iPos > 0) {
      strTestMethodName = strTestMethodName.substring(0, iPos);
    }
    sb.append(strTestClassName + "." + strTestMethodName).append("\t");
    // System.out.println( strTestClassName+"."+strTestMethodName);
    for (String exception : exceptions) {
      // System.out.println(exception);

      if (exception.indexOf(strTestClassName + "." + strTestMethodName) > 0) {
        sb.append(formatStringForCvs(exception)).append("\t");
        // logger.warn(exception);
        break;
      }
    }
    sb.append(isLimitException).append("\t");
    // 输出
    // 第一行
    // 关键行
    // 参数
    // cThread.g
    try {
      // logger.info(sb);
      sb.append("\n");
      AiFileUtil.appendToCsv(strTestClassName + ".junitlog.csv", sb);
      sb.delete(0, sb.length());
    } catch (Exception cException) {
      logger.error(cException);
    }
    // System.out.println(cStackTraceElement[1].getClassName());
    // System.out.println(cStackTraceElement[1].getMethodName());
  }

  /**
   * 
   * @category:
   * @Title: formatStringForCvs
   * @author:wupf@asiainfo.com
   * @date:2019年2月14日
   * @param strInput
   * @return
   */
  private static String formatStringForCvs(String strInput) {
    return strInput.replace("\n", "").replaceAll("\r", "").replaceAll("\t", "");
  }
}
