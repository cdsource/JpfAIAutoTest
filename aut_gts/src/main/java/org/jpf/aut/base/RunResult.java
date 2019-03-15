/**
 * @author 吴平福 E-mail:wupf@aliyun.com
 * @version 创建时间：2018年1月31日 下午10:48:36 类说明
 */

package org.jpf.aut.base;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 
 */
public class RunResult {
  private static final Logger logger = LogManager.getLogger();

  /**
   * 
   */
  private RunResult() {

  }

  public static int TotalJavaSrcFileCount = 0;

  public static int iMethodCount = 1;

  // 抽象类文件数量
  public static int AbstractFileCount = 0;
  // 接口类文件数量
  public static int InterfaceFileCount = 0;
  // 枚举类，暂时不支持
  public static int EnumFileCount = 0;
  // 产生的单元测试数量
  private static int GenFileCount = 0;
  // 可以产生单元测试的源文件数量
  public static int CanGenFileCount = 0;

  public static int iExistUtFileCount = 0;
  public static int iErrorFileCount = 0;

  public static int iPrivateMethodCount = 0;
  public static int iPublicMethodCount = 0;

  public static int iNoParamMethodCount = 0;

  public static int iGenericTypeCount = 0;

  public static int iFail_TestFile = 0;
  public static int iFail_TargetFile = 0;

  // 生成的空单元测试文件数量
  public static int GenEmptyUtFileCount = 0;

  // lvr产生的文件数量
  public static int GenFileCountByLVR = 0;
  // 排除的类
  private static int ExclusionsFileCount = 0;
  // 未知的类
  private static int UnknownFileCount = 0;

  /**
   * 
   * @author wupf@aliyun.com 2018年7月5日
   */
  public static void printResult() {
    logger.info("TotalJavaSrcFileCount:" + RunResult.TotalJavaSrcFileCount);
    logger.info("generate files:" + RunResult.GenFileCount);
    logger.info("abstract files:" + RunResult.AbstractFileCount);
    logger.info("interface files:" + RunResult.InterfaceFileCount);
    logger.info("EnumFileCount:" + RunResult.EnumFileCount);
    logger.info("exist ut files:" + RunResult.iExistUtFileCount);
    logger.info("Exception files:" + RunResult.iErrorFileCount);
    logger.info("Fail_TestFile:" + RunResult.iFail_TestFile);
    logger.info("Fail_TargetFile:" + RunResult.iFail_TargetFile);
    logger.info("GenericTypeCount:" + RunResult.iGenericTypeCount);
    logger.info("GenEmptyUtFileCount:" + RunResult.GenEmptyUtFileCount);
    logger.info("GenFileCountByLVR:" + RunResult.GenFileCountByLVR);
    logger.info("ExclusionsFileCount:" + RunResult.ExclusionsFileCount);
    logger.info("UnknownFileCount:" + RunResult.UnknownFileCount);

    logger.info("CommentRowCount:" + RunResult.iCommentCountRow);
    logger.info("CommentMethodCount:" + RunResult.CommentMethodCount);
    logger.info("CommentFileCount:" + RunResult.iCommentCountFile);

  }

  /**
   * 
   * @category:
   * @Title: printGenerateResult
   * @author:wupf@asiainfo.com
   * @date:2019年2月27日
   */
  public static void printGenerateResult() {
    logger.info("TotalJavaSrcFileCount:" + RunResult.TotalJavaSrcFileCount);
    logger.info("generate files:" + RunResult.GenFileCount);
    logger.info("abstract files:" + RunResult.AbstractFileCount);
    logger.info("interface files:" + RunResult.InterfaceFileCount);
    logger.info("EnumFileCount:" + RunResult.EnumFileCount);
    logger.info("exist ut files:" + RunResult.iExistUtFileCount);
    logger.info("Exception files:" + RunResult.iErrorFileCount);
    logger.info("Fail_TestFile:" + RunResult.iFail_TestFile);
    logger.info("Fail_TargetFile:" + RunResult.iFail_TargetFile);
    logger.info("GenericTypeCount:" + RunResult.iGenericTypeCount);
    logger.info("GenEmptyUtFileCount:" + RunResult.GenEmptyUtFileCount);
    logger.info("GenFileCountByLVR:" + RunResult.GenFileCountByLVR);
    logger.info("ExclusionsFileCount:" + RunResult.ExclusionsFileCount);
    logger.info("UnknownFileCount:" + RunResult.UnknownFileCount);

    logger.info("CommentRowCount:" + RunResult.iCommentCountRow);
    logger.info("CommentMethodCount:" + RunResult.CommentMethodCount);
    logger.info("CommentFileCount:" + RunResult.iCommentCountFile);

  }

  public static void reset() {
    TotalJavaSrcFileCount = 0;
    GenFileCount = 0;
    AbstractFileCount = 0;
    InterfaceFileCount = 0;
    EnumFileCount = 0;
    iExistUtFileCount = 0;
    iErrorFileCount = 0;
    iFail_TestFile = 0;
    iFail_TargetFile = 0;
    iGenericTypeCount = 0;
    GenEmptyUtFileCount = 0;
    GenFileCountByLVR = 0;
    ExclusionsFileCount = 0;
    UnknownFileCount = 0;
    iCommentCountRow = 0;
    CommentMethodCount = 0;
    iCommentCountFile = 0;
  }

  private static int iCommentCountRow = 0;

  private static int iCommentCountFile = 0;

  private static int CommentMethodCount = 0;

  public static int getCommentMethodCount() {
    return CommentMethodCount;
  }

  public static synchronized void addCommentMethodCount() {
    CommentMethodCount++;
  }



  public static int getiCommentCountRow() {
    return iCommentCountRow;
  }

  public static synchronized void addCommentCountRow() {
    iCommentCountRow++;
  }

  public static int getCommentCountFile() {
    return iCommentCountFile;
  }

  public static synchronized void addCommentCountFile() {
    iCommentCountFile++;
  }

  public static synchronized void addFinishFileCount() {
    GenFileCount++;
  }

  public static int getGenFileCount() {
    return GenFileCount;
  }

  public static synchronized void addExclusionsFileCount() {
    ExclusionsFileCount++;
  }

  public static int getExclusionsFileCount() {
    return ExclusionsFileCount;
  }

  public static synchronized void addUnknownFileCount() {
    UnknownFileCount++;
  }

}
