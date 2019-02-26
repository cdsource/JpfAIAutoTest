/**
 * copyrigth by wupf@ 2019年2月8日
 */
package org.jpf.utils.classes.accessmethods;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author wupf@asiainfo.com
 *
 */
public class ParseJavaMethod {
  private static final Logger logger = LogManager.getLogger();


  // 已经自行实例化
  private static final ParseJavaMethod Instance = new ParseJavaMethod();

  // 静态工厂方法
  public static ParseJavaMethod getInstance() {
    return Instance;
  }

  /**
   * 
   */
  private ParseJavaMethod() {
    // TODO Auto-generated constructor stub
  }

}
