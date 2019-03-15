package org.jpf.utils.classes;


import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.jpf.aut.base.GenerateInputParam;
import org.jpf.aut.base.RunResult;
import org.jpf.aut.common.consts.AutConst;

/**
 * 
 * @author wupf@asiainfo.com
 *
 */
public class ClassUtil {
  private static final Logger logger = LogManager.getLogger();

  /**
   * 读取源代码获取类名
   * 
   * @param filePath java源文件路径
   * @param simpleClassName 和简单的类名进行对比，避免再往下读取
   * @return string
   */
  public static String getClassName(String filePath, String simpleClassName) {
    final String regex = "(class (.*?)\\{)|(interfase (.*?)\\{)";
    String curLine;
    BufferedReader reader;
    try {
      reader = new BufferedReader(new FileReader(filePath));
      while ((curLine = reader.readLine()) != null) {
        Matcher matcher =
            Pattern.compile(regex, Pattern.DOTALL | Pattern.MULTILINE).matcher(curLine);
        if (matcher.find()) {
          String selector = matcher.group();
          String[] strs = selector.split(" ");
          for (String str : strs) {
            if (str.contains(simpleClassName)) {
              return str;
            }
          }
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  /**
   * 
   * @category:
   * @Title: getClassName
   * @author:wupf@asiainfo.com
   * @date:2019年2月11日
   * @param sourceFileName
   * @return
   */
  public static String getClassName(String sourceFileName) {
    String strExistMain = "";
    try {

      CompilationUnit cCompilationUnit = ParseJavaByJdt.getInstance()
          .parseJavaSourceFile17(sourceFileName, GenerateInputParam.JAVA_ENCODE);

      List types = cCompilationUnit.types();
      if (types.size() == 0) {
        logger.warn("type=null:" + sourceFileName);
        return "";
      }
      TypeDeclaration typeDec = (TypeDeclaration) types.get(0);
      logger.info("classname=" + typeDec.getName());

      if (typeDec.getModifiers() == AutConst.CLASS_TYPE_ABSTRACT) {
        // abstract class
        logger.info("抽象类不能生产单元测试：" + sourceFileName);
        RunResult.AbstractFileCount++;
        return "";
      }
      if (typeDec.isInterface()) {
        logger.info("接口类单元测试：" + sourceFileName);
        RunResult.InterfaceFileCount++;
        return "";
      }

      strExistMain =
          cCompilationUnit.getPackage().getName().toString() + "." + typeDec.getName().toString();

    } catch (Exception ex) {
      logger.error(ex);
      ex.printStackTrace();
    }
    return strExistMain;
  }

  /**
   * 
   * @category: 从类名获取包名
   * @Title: getPackageFromClass
   * @author:wupf@asiainfo.com
   * @date:2019年1月14日
   * @param strClassName
   * @return
   */
  public static String getPackageFromClass(String strClassName) {
    return strClassName.substring(0, strClassName.lastIndexOf("."));
  }

  public static String LineSeparator = System.getProperty("line.separator");
}
