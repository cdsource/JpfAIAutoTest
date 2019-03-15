/**
 * copyrigth by wupf@ 2019年3月4日
 */
package org.jpf.utils.classes.accessmethods;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author wupf@asiainfo.com
 *
 */
public class SampleAccessMethod {
  private static final Logger logger = LogManager.getLogger();

  /**
   * 
   */
  public SampleAccessMethod() {
    // TODO Auto-generated constructor stub
  }

  /**
   * @category:
   * @Title: main
   * @author:wupf@asiainfo.com
   * @date:2019年3月4日
   * @param args
   */
  public static void main(String[] args) {
    // TODO Auto-generated method stub

  }

  private void add(int i, int j) {
    int f = i + j;
    logger.info(f);
  }

  private int add2(int i, int j) {
    int f = i + j;
    logger.info(f);
    return f;
  }

  protected int add(int i, int j, int m) {
    int f = i + j + m;
    logger.info(f);
    return f;
  }

}
