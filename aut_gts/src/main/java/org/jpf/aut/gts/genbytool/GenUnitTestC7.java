/**
 * copyrigth by wupf@asiainfo.com 2018年5月8日
 */
package org.jpf.aut.gts.genbytool;

import java.io.File;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.Vector;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jpf.aut.base.RunResult;
import org.jpf.aut.common.consts.AiTestConst;
import org.jpf.aut.utils.AutUtil;
import org.jpf.utils.ios.AiFileUtil;
import org.jpf.utils.ios.AiOsUtil;
import org.jpf.utils.mavens.JpfMvnUtil;

/**
 * @author wupf@asiainfo.com
 *
 */
public class GenUnitTestC7 {
  private static final Logger logger = LogManager.getLogger();
  // private volatile int iFileCount = 0;
  // private volatile int iRunFileCount = 0;
  // private int iFileSucc = 0;

  private synchronized void addFinishFileCount() {
    RunResult.GenFileCount++;
  }


  public void run(String strCmd) {
    long start = System.currentTimeMillis();
    try {
      logger.info("strCmd" + strCmd);

      Process process;
      if (AiOsUtil.getInstance().isWindows()) {
        process = Runtime.getRuntime().exec(strCmd);
      } else {
        String[] cmd = new String[] {"/bin/sh", "-c", strCmd};
        process = Runtime.getRuntime().exec(cmd);
      }

      InputStreamReader ir = new InputStreamReader(process.getInputStream());
      LineNumberReader input = new LineNumberReader(ir);

      String line;

      while ((line = input.readLine()) != null) {
        // System.out.println(System.currentTimeMillis() - start);
        /*
         * if ((System.currentTimeMillis() - start) > 200000) {
         * System.out.println("thread timeout: " + strJavaFileName); process.destroy(); break; }
         */

        if (line.trim().startsWith("[Progress:")) {
          // System.out.println(line);
          continue;
        }

        logger.info(line);
      }
      process.waitFor();

      if (process.exitValue() != 0) {
        System.out.println(" Failed to generate test, exit value is " + process.exitValue());
        // return false;
      }
    } catch (Exception e) {
      e.printStackTrace();

    } finally {
      addFinishFileCount();
    }
    System.out.println(" finish ExcuteTime " + (System.currentTimeMillis() - start) + "ms");
  }

  public String getProjectCp(String strPomFilePath) {
    String strProjectCp = " -projectCP " + strPomFilePath + java.io.File.separator + "target"
        + java.io.File.separator + "classes" + AiTestConst.getJarConn();
    File f = new File(strPomFilePath + java.io.File.separator + "lib");
    if (f.exists() && f.isDirectory()) {
      for (String strFileName : f.list()) {

        strProjectCp += strPomFilePath + java.io.File.separator + "lib" + java.io.File.separator
            + strFileName + AiTestConst.getJarConn();
      }
    }
    if (strProjectCp.endsWith(AiTestConst.getJarConn())) {
      strProjectCp = strProjectCp.substring(0, strProjectCp.length() - 1);
    }

    // System.out.println(strProjectCp);

    return strProjectCp;
  }

  public String getPackageFromClass(String strClassName) {
    return strClassName.substring(0, strClassName.lastIndexOf("."));
  }

  /**
   * 
   * @author wupf@asiainfo.com
   * @param strJavaSrcPath
   * @param strPomFilePath
   * @return 2018年7月26日
   */
  public Vector<String> getJavaFiles(String strJavaSrcPath, String strPomFilePath) {
    try {
      Vector<String> v = new Vector<String>();

      if (AiFileUtil.isFile(strJavaSrcPath)) {
        if (strJavaSrcPath.endsWith(".java")) {
          v.add(strJavaSrcPath);
        }
      } else if (AiFileUtil.isDirectory(strJavaSrcPath)) {
        // if (JpfMvnUtil.getSrcPath(strJavaSrcPath))
        if (strJavaSrcPath.equalsIgnoreCase(strPomFilePath)) {
          AiFileUtil.getFiles(JpfMvnUtil.getSrcPath(strJavaSrcPath), v, ".java");
        } else {
          AiFileUtil.getFiles(strJavaSrcPath, v, ".java");
        }
      }

      logger.debug("find source java files:" + v.size());
      AutUtil.removeNotSourceFile(v);
      logger.debug("checked source java files:" + v.size());
      return v;
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    return null;
  }

  /**
   * 
   */
  public GenUnitTestC7() {}

  public void doWork(String strJavaSrcPath, String strPomFilePath) {
    long start = System.currentTimeMillis();
    try {
      // strPomFilePath =
      // "E:\\prj_code\\zjcmc\\general-parent2\\general-dao\\general-dao-order";

      String strProjectCp = getProjectCp(strPomFilePath);
      String strClassPath = "";
      if (AiOsUtil.getInstance().isWindows()) {
        strClassPath = "java -jar  D:\\jworkspaces\\JpfUnitTest2\\aitest-gen_1.3.jar ";
      } else {
        strClassPath = "java -jar $HOME/aitest/aitest-gen_1.3.jar ";
      }
      // String strClassPath = "java -jar $HOME/aitest/aitest-gen_1.3.jar "
      // -Dsearch_budget=30



      if (!AiFileUtil.isDirectory(strPomFilePath)) {
        System.out.println("file does not exist :" + strPomFilePath);
        return;
      }

      if (!AiFileUtil.isDirectory(strPomFilePath + java.io.File.separator + "lib")) {
        System.out
            .println("file does not exist :" + strPomFilePath + java.io.File.separator + "lib");
        return;
      }

      // String strCLassPath = JpfMvnUtil.getClassPathForMaven(strPomFilePath);
      Vector<String> v = getJavaFiles(strJavaSrcPath, strPomFilePath);
      RunResult.TotalJavaSrcFileCount = v.size();

      String strCmd = strClassPath + "-continuous execute   -target "
          + JpfMvnUtil.getClassPathForMaven(strPomFilePath);
      strCmd += "  -Dtest_dir=" + AiTestConst.AITEST_PATH + " -base_dir=" + strPomFilePath.trim()
          + " -Dlog.level=error -Dctg_time_per_class=2 -Dctg_memory=2000 -Dctg_cores=3 -Dreport_dir=aitest-report -Djunit_suffix=_WUTest   ";
      // strCmd += " -DPROJECT_PREFIX=" + getPackageFromClass(strClassName);
      // strCmd += strProjectCp;
      // logger.debug("strCmd=" + strCmd);

      run(strCmd);


      System.out.println("ExcuteTime " + (System.currentTimeMillis() - start) + "ms");
      System.out.println(RunResult.GenFileCount + "/" + RunResult.CanGenFileCount + "/"
          + RunResult.TotalJavaSrcFileCount);
      v.clear();

      // checkGenUtFiles(strPomFilePath);

      // doClear(strPomFilePath);

      // JpfUnitTestSourceCheck cJpfUnitTestSourceCheck = new JpfUnitTestSourceCheck();
      // cJpfUnitTestSourceCheck.doWork(strPomFilePath);
    } catch (Exception ex) {
      ex.printStackTrace();
    }

  }

  private void checkGenUtFiles(String strPomFilePath) {
    try {
      Vector<String> v = new Vector<String>();
      strPomFilePath = strPomFilePath + java.io.File.separator + AiTestConst.AITEST_PATH;
      AiFileUtil.getFiles(strPomFilePath, v, ".java");
      for (int i = 0; i < v.size(); i++) {
        if (!v.get(i).endsWith("ESTest.java")) {
          v.remove(i);
          i--;
        }
      }
      if (RunResult.GenFileCount != v.size()) {
        logger.info("utfile count warning:" + RunResult.GenFileCount + "/" + v.size());
      }
      v.clear();
    } catch (Exception ex) {

    }
  }

  private final int THREAD_MAX = 12;

  public static void main(String[] args) {
    long start = System.currentTimeMillis();
    logger.debug("start");
    if (2 == args.length) {
      GenUnitTestC7 cGenUnitTestC5 = new GenUnitTestC7();
      cGenUnitTestC5.doWork(args[0], args[1]);
    } else {
      System.out.println("error input param");
    }
    System.out.println("ExcuteTime " + (System.currentTimeMillis() - start) + "ms");
  }

  /**
   * 
   * @author wupf@asiainfo.com
   * @param strPomFilePath 2018年8月28日
   */
  private void doClear(String strPomFilePath) {
    try {
      File file = new File(strPomFilePath);
      File[] files = file.listFiles();
      for (int i = 0; i < files.length; i++) {
        if (files[i].getName().startsWith("hs_err_EvoSuite_client_p")) {
          files[i].delete();
        }
      }
    } catch (Exception ex) {

    }
  }
}
