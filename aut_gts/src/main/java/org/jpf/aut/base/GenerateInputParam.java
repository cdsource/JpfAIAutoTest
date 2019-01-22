/**
 * copyrigth by 吴平福
 * 
 * @author 吴平福 E-mail:wupf@aliyun.com
 * @version 创建时间：2017年9月30日 下午3:49:28 类说明
 */

package org.jpf.aut.base;

import org.jpf.aut.common.consts.AiTestConst;
import org.jpf.utils.ios.WuFileUtil;

/**
 * 
 */
public class GenerateInputParam {

  /**
   * 
   */
  private GenerateInputParam() {}

  // classpath 排除JAR文件
  public final static String ExClude_JAR = "aitest-";
  // 备份已经存在的文件目录 0：不操作，1：备份，移除
  public static int DelExistTestFiles = 0;

  public static String PROC_JVM_XMS = " -Xms512m";
  public static String PROC_JVM_XMX = " -Xmx512m";
  // 单元测试函数超时设置
  public static int UtTimeOut = 1000;

  // 搜索超时时间
  public static int budget = 30;

  // 单元测试函数体注释
  public static boolean bNeedMethodJavaDoc = false;

  // JAVA文件编码
  public static String JAVA_ENCODE = "UTF-8";

  // 对哪些文件生成测试过滤规则
  public static String FileNameFilter = ".*Manager.java";

  // 查找JAVA代码路径
  public static String FilePath_Find_Java_Source = "";

  public static String SRC_PATH = "";

  public static String getSRC_PATH() {
    return SRC_PATH;
  }

  public static void setSRC_PATH(String sRC_PATH) {
    SRC_PATH = sRC_PATH;
  }

  // 1 正常 2接口 3 抽象 4 高分支覆盖率
  public static int GenerateType = 0;

  private static String POM_PATH = "";

  public static String VERSION_INFO = "1.3.0";

  public static boolean FUN_OPEN_NLP = false;

  public static int THREAD_MAX = 4;

  public static String WORK_JAR = "";

  // ONEBRANCH, EVOSUITE, RANDOM, RANDOM_FIXED, ENTBUG, REGRESSION, MOSUITE, DSE, NOVELTY
  public static String Strategy = "EVOSUITE";

  // 4:max 5:AVG 6:min 0: default
  public static int REGRESSION_ANALYSIS_OBJECTDISTANCE = 0;

  public static String test_exclusions = "";

  /**
   * 
   * 
   * @Title: getLibPath
   * 
   * @Description: TODO
   * 
   * @author:wupf@
   * 
   * @return
   */
  public static String getLibPath() {
    return POM_PATH + java.io.File.separator + "lib" + java.io.File.separator;
  }

  public static String getPOM_PATH() {
    return POM_PATH + java.io.File.separator;
  }

  public static void setPOM_PATH(String pOM_PATH) throws Exception {
    if (WuFileUtil.FileExist(pOM_PATH)) {
      POM_PATH = pOM_PATH;
    } else {
      throw new Exception("POM FILE NOT EXIST:" + pOM_PATH);
    }
  }

  public static String getClassPath() {
    return POM_PATH + java.io.File.separator + "target" + java.io.File.separator + "classes"
        + java.io.File.separator;
  }


  public static String getAiTestPath() {
    return POM_PATH + java.io.File.separator + AiTestConst.AITEST_PATH;
  }

  public static String getSrcMainPathKey() {
    return "src" + java.io.File.separator + "main" + java.io.File.separator + "java";
  }

  public static String getSrcTestPathKey() {
    return "src" + java.io.File.separator + "test" + java.io.File.separator + "java";
  }

  public static String getSrcTestPath() {
    return POM_PATH + java.io.File.separator + getSrcTestPathKey();
  }

  public static String joinPath(String strPath1, String strPath2) {
    if (strPath1.endsWith(java.io.File.separator)) {
      return strPath1.substring(0, strPath1.length() - 1) + strPath2;
    }
    return strPath1 + strPath2;
  }

}
