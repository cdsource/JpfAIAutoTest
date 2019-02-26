/**
 * copyrigth by wupf@asiainfo.com 2018年7月31日
 */
package org.jpf.utils.classes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.jpf.aut.base.RunResult;
import org.jpf.aut.checks.CheckUtils;
import org.jpf.utils.ios.AiFileUtil;
import org.jpf.utils.ios.WuFileUtil;


/**
 * @author wupf@asiainfo.com
 *
 */
public class AIUtRemoveSameMethod {
  private static final Logger logger = LogManager.getLogger();

  private String strPomPath = "";

  /**
   * 
   */
  public AIUtRemoveSameMethod(String strPomPath) {

    this.strPomPath = strPomPath;
  }

  /**
   * 
   * @author wupf@asiainfo.com
   * @param strPomPath 2018年8月16日
   */
  public void doWork() {
    try {
      if (WuFileUtil.isDirectory(strPomPath)) {
        Vector<String> vFiles = new Vector<String>();
        AiFileUtil.getFiles(strPomPath, vFiles, ".java");
        // remove test file
        CheckUtils.checkForSource(vFiles);

        RunResult.TotalJavaSrcFileCount = vFiles.size();
        logger.info(vFiles.size());
      } else if (WuFileUtil.FileExist(strPomPath)) {
        removeSameMethod(strPomPath);
      } else {
        logger.warn("Error input param:" + strPomPath);
      }
    } catch (Exception ex) {
      logger.error(ex);
      ex.printStackTrace();
    } finally {
      logger.info("handle files " + RunResult.TotalJavaSrcFileCount);
    }

  }

  /**
   * 
   * @author wupf@asiainfo.com
   * @param strUtFileName
   * @return 2018年8月16日
   */
  public int removeSameMethod(String strUtFileName) {
    try {

      // format

      CompilationUnit cCompilationUnit =
          ParseJavaByJdt.getInstance().parseJavaSourceFile18(strUtFileName, "GBK");

      List types = cCompilationUnit.types();
      if (types.size() == 0) {
        logger.warn("type=null:" + strUtFileName);
        return -2;
      }

      TypeDeclaration typeDec = (TypeDeclaration) types.get(0);
      logger.info("classname=" + typeDec.getName());

      // show methods
      MethodDeclaration methodDec[] = typeDec.getMethods();
      Map<String, String> mapMethodBody = new HashMap<String, String>();
      for (MethodDeclaration method : methodDec) {
        SimpleName methodName = method.getName();
        logger.info(methodName);
        logger.info(method.getBody());
        String methodBody = method.getBody().toString();
        if (methodBody.trim().length() == 0) {
          continue;
        }
        if (mapMethodBody.containsKey(methodBody)) {
          logger.info(method.getBody().getStartPosition());
          logger.info(method.getBody().getLength());
        } else {
          mapMethodBody.put(methodBody, strUtFileName);
        }
      }
    } catch (Exception ex) {
      logger.error(ex);
      ex.printStackTrace();
    }
    return 0;
  }

  /**
   * @author wupf@asiainfo.com
   * @param args 2018年7月31日
   */

  public static void main(String[] args) {


    // D:\jworkspaces\JpfUnitTest2\test\java\org\aitest\sample\SampleClassTest.java
    if (args.length == 1) {
      AIUtRemoveSameMethod cJpfUtRemoveSameMethod = new AIUtRemoveSameMethod(args[0]);
      cJpfUtRemoveSameMethod.doWork();

    } else {
      logger.warn("error input param");
    }
    logger.info("Game over");

    // aa("aa");
  }

}
