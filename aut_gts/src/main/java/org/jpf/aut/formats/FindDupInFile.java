/**
 * copyrigth by wupf@ 2019年1月18日
 */
package org.jpf.aut.formats;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.jpf.aut.base.GenerateInputParam;
import org.jpf.utils.classes.ParseJavaSourceFile;
import org.jpf.utils.ios.WuFileUtil;

/**
 * @author wupf@asiainfo.com
 *
 */
public class FindDupInFile {
  private static final Logger logger = LogManager.getLogger();

  /**
   * 
   */
  public FindDupInFile() {
    // TODO Auto-generated constructor stub
  }

  public void doWork(String strJavaFileName) {
    try {
      if (!WuFileUtil.isFile(strJavaFileName)) {
        logger.info("Not a file:" + strJavaFileName);
        return;
      }
      if (!strJavaFileName.endsWith(".java")) {
        logger.info("Not a java file:" + strJavaFileName);
        return;
      }

      FormatCode.formatJavaCode(strJavaFileName);

      CompilationUnit cu = ParseJavaSourceFile.getInstance().parseJavaSourceFile18(strJavaFileName,
          GenerateInputParam.JAVA_ENCODE);

      List types = cu.types();
      TypeDeclaration typeDec = (TypeDeclaration) types.get(0);
      logger.info("classname=" + typeDec.getName());

      // show methods
      MethodDeclaration methodDec[] = typeDec.getMethods();
      Map<String, String> mapMethodBody = new HashMap<String, String>();
      for (MethodDeclaration method : methodDec) {
        SimpleName methodName = method.getName();
        logger.info("methodName:" + methodName);
        logger.info("method.getModifiers()=" + method.getModifiers());
        Block mBlock = method.getBody();
        // logger.info(mBlock.getStartPosition());
        // logger.info(mBlock.getLength());
        int iMethodStartRow = cu.getLineNumber(mBlock.getStartPosition());
        logger.info("start line:" + iMethodStartRow);
        logger.info("end line:" + cu.getLineNumber(mBlock.getStartPosition() + mBlock.getLength()));
        logger.info(mBlock.toString());


      }
    } catch (Exception e) {
      // TODO: handle exception
      e.printStackTrace();
    }
  }

  /**
   * @category:
   * @Title: main
   * @author:wupf@asiainfo.com
   * @date:2019年1月18日
   * @param args
   */
  public static void main(String[] args) {
    // TODO Auto-generated method stub
    if (1 == args.length) {
      FindDupInFile cFindDupInFile = new FindDupInFile();
      cFindDupInFile.doWork(args[0]);
    } else {
      FindDupInFile cFindDupInFile = new FindDupInFile();
      cFindDupInFile.doWork(
          "F:\\prj_code\\cuc\\20190114\\kc_search_appsvc_msa-feature-002\\src\\main\\java\\com\\unicom\\kc\\search\\app\\util\\DateUtil.java");
      cFindDupInFile.doWork(
          "F:\\prj_code\\cuc\\20190114\\kc_search_appsvc_msa-feature-002\\src\\test\\java\\com\\unicom\\kc\\search\\app\\util\\DateUtil_WETest.java");
      cFindDupInFile.doWork(
          "F:\\prj_code\\cuc\\20190114\\kc_search_appsvc_msa-feature-002\\src\\test\\java\\com\\unicom\\kc\\search\\app\\util\\DateUtil_WGTest.java");
      cFindDupInFile.doWork(
          "F:\\prj_code\\cuc\\20190114\\kc_search_appsvc_msa-feature-002\\src\\test\\java\\com\\unicom\\kc\\search\\app\\util\\DateUtil_WUTest.java");

      logger.warn("Error input Params");
    }
  }

}
