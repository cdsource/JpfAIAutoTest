/**
 * copyrigth by wupf@ 2019年2月25日
 */
package org.jpf.utils.classes;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.ImportDeclaration;

/**
 * @author wupf@asiainfo.com
 *
 */
public class ParseJavaImports {

  private static final Logger logger = LogManager.getLogger();


  // 已经自行实例化
  private static final ParseJavaImports Instance = new ParseJavaImports();

  // 静态工厂方法
  public static ParseJavaImports getInstance() {
    return Instance;
  }

  /**
   * @category:
   * @Title: main
   * @author:wupf@asiainfo.com
   * @date:2019年2月25日
   * @param args
   */
  public static void main(String[] args) {
    // TODO Auto-generated method stub

  }

  /**
   * 
   * @category:
   * @Title: checkLVR
   * @author:wupf@asiainfo.com
   * @date:2019年2月25日
   * @param cu
   * @return
   */
  public boolean checkAutowired(CompilationUnit cu) {
    List importDeclarations = cu.imports();
    for (Object object : importDeclarations) {
      ImportDeclaration importDec = (ImportDeclaration) object;
      // logger.info(importDec.getName());
      // slogger.info(importDec);
      // logger.info(importDec.isOnDemand());
      // logger.info(importDec.isStatic());
      if (importDec.toString().trim()
          .endsWith("org.springframework.beans.factory.annotation.Autowired;")) {
        return true;
      }
    }
    return false;
  }
}
