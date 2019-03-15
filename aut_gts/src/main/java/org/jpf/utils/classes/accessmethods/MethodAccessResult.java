/**
 * copyrigth by wupf@aliyun.com 2018年8月25日
 */
package org.jpf.utils.classes.accessmethods;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author wupf@aliyun.com
 *
 */
public class MethodAccessResult {
  private static final Logger logger = LogManager.getLogger();

  /**
   * 
   */
  private MethodAccessResult() {
    // TODO Auto-generated constructor stub
  }

  private static MethodAccessResult instance = new MethodAccessResult();

  public static MethodAccessResult getInstance() {
    return instance;
  }

  // 私有方法数量
  private int PrivateMethodCount = 0;

  private int StaticPrivateMethodCount = 0;

  private int UnknownJavaFileCount = 0;
  private int EnumFileCount = 0;
  private int AbstractFileCount = 0;
  private int InterfaceFileCount = 0;
  private int AnnotationTypeFileCount = 0;

  private int JavaFileCount = 0;
  private int HasPrivateFileCount = 0;
  private int AllMethodCount = 0;

  private Map<String, String> mapFile = new HashMap<>();

  /**
   * 
   * @category:
   * @Title: addFile
   * @author:wupf@asiainfo.com
   * @date:2019年3月3日
   * @param strFileName
   */
  public void addFile(String strFileName) {
    mapFile.put(strFileName, strFileName);
  }

  /**
   * 
   * @category:
   * @Title: addHasPrivateFileCount
   * @author:wupf@asiainfo.com
   * @date:2019年3月3日
   */
  public synchronized void addHasPrivateFileCount() {
    HasPrivateFileCount++;
  }

  /**
   * 
   * @category:
   * @Title: addPrivateMethodCount
   * @author:wupf@asiainfo.com
   * @date:2019年3月3日
   */
  public synchronized void addPrivateMethodCount() {
    PrivateMethodCount++;
  }

  /**
   * 
   * @category:
   * @Title: addStaticPrivateMethodCount
   * @author:wupf@asiainfo.com
   * @date:2019年3月3日
   */
  public synchronized void addStaticPrivateMethodCount() {
    StaticPrivateMethodCount++;
  }

  public synchronized void addAllMethodCount(int iCount) {
    AllMethodCount += iCount;
  }

  public synchronized void addUnknownJavaFileCount() {
    UnknownJavaFileCount++;
  }

  public synchronized void addEnumFileCount() {
    EnumFileCount++;
  }

  public synchronized void addAbstractFileCount() {
    AbstractFileCount++;
  }

  public synchronized void addInterfaceFileCount() {
    InterfaceFileCount++;
  }

  public synchronized void addAnnotationTypeFileCount() {
    AnnotationTypeFileCount++;
  }

  public synchronized void setJavaFileCount(int iJavaFileCount) {
    JavaFileCount = iJavaFileCount;
  }

  /**
   * 
   * @category:
   * @Title: printResult
   * @author:wupf@asiainfo.com
   * @date:2019年3月3日
   */
  public void printResult() {

    logger.info("JavaFileCount: " + JavaFileCount);
    logger.info("UnknownJavaFileCount: " + UnknownJavaFileCount);
    logger.info("EnumFileCount: " + EnumFileCount);
    logger.info("AbstractFileCount: " + AbstractFileCount);
    logger.info("InterfaceFileCount: " + InterfaceFileCount);
    logger.info("AnnotationTypeFileCount: " + AnnotationTypeFileCount);
    logger.info("HasPrivateFileCount: " + HasPrivateFileCount);
    logger.info("PrivateMethodCount: " + PrivateMethodCount);
    logger.info("StaticPrivateMethodCount: " + StaticPrivateMethodCount);
    logger.info("AllMethodCount: " + AllMethodCount);

  }
}
