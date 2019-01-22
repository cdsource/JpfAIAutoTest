/**
 * copyrigth by 吴平福
 * 
 * @author 吴平福 E-mail:wupf
 * @version 创建时间：2017年9月30日 下午3:49:28 类说明
 */
package org.jpf.aut.common.consts;

import org.jpf.utils.ios.AiOsUtil;

/**
 * @author wupf
 *
 */
public class AiTestConst {

  // LINUX
  public static String getJarConn() {
    if (AiOsUtil.getInstance().isWindows()) {
      return ";";
    }
    return ":";

  }

  public static final String AITEST_PATH = "aitest_tests";

  public static final String GENERATE_AUTHOR = "wupf@asiainfo.com";

  public static final int Max_CaseCount_PerMethod = 5;

  // 抽象类，不能生成单元测试
  public final static int CLASS_TYPE_ABSTRACT = 1025;

  public final static String MAIN_SRC =
      "src" + java.io.File.separator + "main" + java.io.File.separator + "java";

  public final static String CONSTRUCTOR_VAL_PREFIX = "cc_";

  public final static String AITEST_HC_TYPE = "_WUTest";

  public final static String AITEST_HC_Suffix = AITEST_HC_TYPE + ".java";

  public final static String AITEST_RUN_TYPE = "*_WUTest,*_WGTest,*_GTest";

  public final static String[] AITEST_ALL_SUFFIXS = {"_WUTest.java", "_WGTest.java", "_GTest.java"};

  public final static String[] AUT_TEST_RESULT_SUFFIXS =
      {"_WUTest.xml", "_WGTest.xml", "_GTest.xml"};

  /**
   * 
   * 
   * @Title: checkAutoTestFile
   * 
   * @Description: TODO
   * 
   * @author:wupf@
   * 
   * @param strFileName
   * @return
   */
  public static boolean checkAutoTestFile(final String strFileName) {
    for (int i = 0; i < AITEST_ALL_SUFFIXS.length; i++) {
      if (strFileName.endsWith(AITEST_ALL_SUFFIXS[i])) {
        return true;
      }
    }
    return false;
  }

  /**
   * 
   * 
   * @Title: isAutTestResult
   * 
   * @Description: TODO
   * 
   * @author:wupf@
   * 
   * @param strXMLFileName
   * @return
   */
  public static boolean isAutTestResult(String strXMLFileName) {
    for (int i = 0; i < AUT_TEST_RESULT_SUFFIXS.length; i++) {
      if (strXMLFileName.endsWith(AUT_TEST_RESULT_SUFFIXS[i])) {
        return true;
      }
    }
    return false;
  }
}
