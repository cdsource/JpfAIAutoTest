/**
 * copyrigth by wupf@asiainfo.com 2018年7月17日
 */
package org.jpf.aut.utils;

import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.Vector;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jpf.aut.base.GenerateInputParam;
import org.jpf.aut.common.consts.AutConst;
import org.jpf.utils.MatchUtil;
import org.jpf.utils.ios.JpfOsUtil;

/**
 * @author wupf@asiainfo.com
 *
 */
public class AutUtil {
  private static final Logger logger = LogManager.getLogger();

  /**
   * 
   */
  private AutUtil() {
    // TODO Auto-generated constructor stub
  }

  /**
   * 
   * @author wupf@asiainfo.com
   * @param v 2018年7月17日
   */
  public static void removeNotSourceFile(Vector<String> v) {
    String[] testExcludes = GenerateInputParam.test_exclusions.split(",");

    for (int i = 0; i < v.size(); i++) {
      String strJavaFile = v.get(i);
      if (strJavaFile.indexOf(AutConst.MAIN_SRC) == -1) {
        // logger.warn("maybe not source file:" + strJavaFile);
        v.remove(i);
        i--;
        continue;
      }
      if (strJavaFile.indexOf(java.io.File.separator + "test" + java.io.File.separator) > 0) {
        // logger.warn("maybe not source file:" + strJavaFile);
        v.remove(i);
        i--;
        continue;
      }
      if (strJavaFile.indexOf(java.io.File.separator + "target") > 0) {
        // logger.warn("maybe not source file:" + strJavaFile);
        v.remove(i);
        i--;
        continue;
      }
      if (strJavaFile.indexOf(java.io.File.separator + "resources") > 0) {
        // logger.warn("maybe not source file:" + strJavaFile);
        v.remove(i);
        i--;
        continue;
      }

      if (strJavaFile.indexOf("$") > 0) {
        // logger.warn("bad java file:" + strJavaFile);
        v.remove(i);
        i--;
        continue;
      }
      if (strJavaFile
          .indexOf(java.io.File.separator + AutConst.AITEST_PATH + java.io.File.separator) > 0) {
        // logger.warn("maybe not source file:" + strJavaFile);
        v.remove(i);
        i--;
        continue;
      }
      if (testExcludes.length > 0) {
        for (int j = 0; j < testExcludes.length; j++) {
          if (testExcludes[j] != null && testExcludes[j].trim().length() > 0) {
            if (MatchUtil.matchSrcFile(strJavaFile, testExcludes[j].trim())) {
              logger.warn("maybe not source file:" + strJavaFile + " " + testExcludes[j]);
              v.remove(i);
              i--;
              break;
            }
          }
        }
      }

    }
  }



  /**
   * 
   * @category:
   * @Title: getGENERATE_CMD
   * @author:wupf@
   * @return
   */
  public static String getGENERATE_CMD() {
    if (!JpfOsUtil.getInstance().isWindows()) {
      int iCore = 1;
      try {
        String[] cmd =
            new String[] {"/bin/sh", "-c", "cat /proc/cpuinfo |grep 'processor'|tail -1"};
        Process process = Runtime.getRuntime().exec(cmd);

        InputStreamReader ir = new InputStreamReader(process.getInputStream());
        LineNumberReader input = new LineNumberReader(ir);

        String line;

        while ((line = input.readLine()) != null) {
          if (line.trim().startsWith("processor")) {
            logger.debug(line);
            int iPos = line.indexOf(":");
            if (iPos > 0) {
              iCore = Integer.parseInt(line.substring(iPos, line.length()));
              if (iCore < 2) {
                iCore = 1;
              } else {
                iCore = iCore - 1;
              }

            }
            continue;
          }
        }
        process.waitFor();
        if (process.exitValue() != 0) {
          logger.error(" Failed to generate test, exit value is " + process.exitValue());
          // return false;
        }

      } catch (Exception e) {
        e.printStackTrace();

      }
      return "-DmemoryInMB=" + iCore * 1000 + " -Dcores=" + iCore;
    } else {
      return "-DmemoryInMB=2000 -Dcores=2";
    }
  }



}
