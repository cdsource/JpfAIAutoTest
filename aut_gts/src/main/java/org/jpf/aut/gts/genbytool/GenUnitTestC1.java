/**
 * copyrigth by wupf@asiainfo.com 2018年5月8日
 */
package org.jpf.aut.gts.genbytool;

import java.io.File;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.Vector;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jpf.aut.base.GenerateInputParam;
import org.jpf.aut.base.RunResult;
import org.jpf.aut.common.consts.AiTestConst;
import org.jpf.aut.utils.AutUtil;
import org.jpf.utils.ios.AiFileUtil;
import org.jpf.utils.ios.AiOsUtil;
import org.jpf.utils.ios.ProcessUtil;
import org.jpf.utils.mavens.JpfMvnUtil;

/**
 * @author wupf@asiainfo.com
 *
 */
public class GenUnitTestC1 {
  private static final Logger logger = LogManager.getLogger();
  // private volatile int iFileCount = 0;
  // private volatile int iRunFileCount = 0;
  // private int iFileSucc = 0;

  private synchronized void addFinishFileCount() {
    RunResult.GenFileCount++;
  }

  class generateThread extends Thread {
    String strCmd = "";
    String strJavaFileName = "";

    public generateThread(final String inCmd, String strJavaFileName) {
      this.strCmd = inCmd;
      this.strJavaFileName = strJavaFileName;
    }

    public void run2() {
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
        if (!process.waitFor(2, TimeUnit.MINUTES)) {
          System.out.println("generate timeout " + strJavaFileName);
          // process.destroy();
          process.destroyForcibly();
        }

      } catch (Exception e) {
        e.printStackTrace();

      } finally {
        addFinishFileCount();
        logger.info("generate finish:" + strJavaFileName);
      }
      logger.info("ExcuteTime " + (System.currentTimeMillis() - start) + "ms");
    }

    public void run() {
      long start = System.currentTimeMillis();
      try {
        // logger.info("strCmd:" + strCmd);
        ProcessUtil.runexec(null, strCmd);
      } catch (Exception e) {
        e.printStackTrace();
      } finally {
        addFinishFileCount();
      }
      logger.info(
          strJavaFileName + " finish ExcuteTime " + (System.currentTimeMillis() - start) + "ms");
    }

    public void run3() {
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
          if (line.trim().startsWith("[INFO] Going to start job for:")) {
            System.out.println(line);
            continue;
          }
          if (line.trim().startsWith("[WARNING]")) {
            System.out.println(line);
            continue;
          }
          if (line.trim().startsWith("[INFO] Scanning for projects...")) {
            System.out.println(line);
            continue;
          }
          if (line.trim().startsWith("[INFO] Completed job. Left")) {
            System.out.println(line);
            continue;
          }
          if (line.trim().startsWith("[INFO] Basedir:")) {
            System.out.println(line);
            continue;
          }
          if (line.trim().startsWith("[INFO] Going to generate tests")) {
            System.out.println(line);
            continue;
          }
          if (line.trim().startsWith("[INFO] New test suites:")) {
            System.out.println(line);
            continue;
          }
          if (line.trim().startsWith("[Progress:")) {
            // System.out.println(line);
            continue;
          }
          if (line.trim().startsWith("* Minimizing test suite:")) {
            System.out.println(line);
            continue;
          }
          if (line.trim().startsWith("* Search finished after")) {
            System.out.println(line);
            continue;
          }
          if (line.trim().startsWith("* Setting up search algorithm for whole suite generation")) {
            System.out.println("Setting up search algorithm...");
            continue;
          }
          // System.out.println(line);
        }
        process.waitFor();

        if (process.exitValue() != 0) {
          logger.info(" Failed to generate test, exit value is " + process.exitValue());
          // return false;
        }
      } catch (Exception e) {
        e.printStackTrace();

      } finally {
        addFinishFileCount();
      }
      logger.info(
          strJavaFileName + " finish ExcuteTime " + (System.currentTimeMillis() - start) + "ms");
    }
  }

  /**
   * 
   * @author wupf@asiainfo.com
   * @param strPomFilePath
   * @return 2018年10月26日
   */
  public String getProjectCp(String strPomFilePath) {
    String strProjectCp = " -projectCP " + strPomFilePath + java.io.File.separator + "target"
        + java.io.File.separator + "classes" + AiTestConst.getJarConn();

    File f = new File(strPomFilePath + java.io.File.separator + "lib");
    if (f.exists() && f.isDirectory()) {
      for (String strFileName : f.list()) {
        if (!strFileName.endsWith(GenerateInputParam.WORK_JAR)) {
          strProjectCp += strPomFilePath + java.io.File.separator + "lib" + java.io.File.separator
              + strFileName + AiTestConst.getJarConn();
        }
        // check length
        if (strProjectCp.length() > 500) {
          break;
        }
      }
    }
    if (strProjectCp.endsWith(AiTestConst.getJarConn())) {
      strProjectCp = strProjectCp.substring(0, strProjectCp.length() - 1);
    }

    // System.out.println(strProjectCp);

    return strProjectCp;
  }

  /**
   * 
   * @author wupf@asiainfo.com
   * @param strPomFilePath CP_file_path
   * @return 2018年10月26日
   */
  public String getCpFilePath(String strPomFilePath) {
    String strProjectCp = strPomFilePath + java.io.File.separator + "target"
        + java.io.File.separator + "classes" + AiTestConst.getJarConn();

    File f = new File(strPomFilePath + java.io.File.separator + "lib");
    if (f.exists() && f.isDirectory()) {
      for (String strFileName : f.list()) {
        if (!strFileName.endsWith(GenerateInputParam.WORK_JAR) && strFileName.endsWith(".jar")) {
          strProjectCp += strPomFilePath + java.io.File.separator + "lib" + java.io.File.separator
              + strFileName + AiTestConst.getJarConn();
        }
      }
    }
    if (strProjectCp.endsWith(AiTestConst.getJarConn())) {
      strProjectCp = strProjectCp.substring(0, strProjectCp.length() - 1);
    }

    AiFileUtil.saveFile(strPomFilePath + java.io.File.separator + "aitest_path.txt", strProjectCp);
    if (logger.isDebugEnabled())
      logger.debug(strProjectCp);
    strProjectCp = " -DCP_file_path=" + strPomFilePath + java.io.File.separator + "aitest_path.txt";
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
      if (logger.isDebugEnabled())
        logger.debug("find source java files:" + v.size());
      AutUtil.removeNotSourceFile(v);
      if (logger.isDebugEnabled())
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
  public GenUnitTestC1() {}

  public void doWork(String strJavaSrcPath, String strPomFilePath) {

    try {

      String strProjectCp = getCpFilePath(strPomFilePath);
      String strClassPath = "";
      if (AiOsUtil.getInstance().isWindows()) {
        // strClassPath = "java -jar
        // D:\\jworkspaces\\JpfUnitTest2\\evosuite-master-1.0.7-SNAPSHOT.jar ";
        strClassPath =
            "java -Xms512m -Xmx512m -jar D:\\jworkspaces\\JpfUnitTest2\\aitest-master-1.0.6.jar ";
      } else {
        strClassPath = "java -Xms512m -Xmx512m -jar $HOME/aitest/aitest-master-1.0.6.jar ";
      }
      // String strClassPath = "java -jar $HOME/aitest/aitest-gen_1.3.jar "
      strClassPath += " -generateSuite -Dtest_dir=" + AiTestConst.AITEST_PATH + " -base_dir="
          + strPomFilePath.trim()
          + " -Dno_runtime_dependency=true -Dlog.level=error -Dseed_dir=aitest-seeds -Dsandbox=false -Dreport_dir=aitest-report -Djunit_suffix="
          + AiTestConst.AITEST_HC_TYPE + " -Dsearch_budget=30 ";

      if (!AiFileUtil.isDirectory(strPomFilePath)) {
        logger.info("file does not exist :" + strPomFilePath);
        return;
      }

      if (!AiFileUtil.isDirectory(strPomFilePath + java.io.File.separator + "lib")) {
        logger.info("file does not exist :" + strPomFilePath + java.io.File.separator + "lib");
        return;
      }
      // String strCLassPath = JpfMvnUtil.getClassPathForMaven(strPomFilePath);
      Vector<String> v = getJavaFiles(strJavaSrcPath, strPomFilePath);
      // GenByToolUtil cGenByToolUtil = new GenByToolUtil();
      RunResult.TotalJavaSrcFileCount = v.size();
      RunResult.CanGenFileCount = 0;
      RunResult.GenFileCount = 0;
      for (String strJavaFile : v) {

        // System.out.println("handle file:" + strJavaFile);

        String strClassName = AutUtil.getClassName(strJavaFile);
        if (strClassName.trim().length() == 0) {
          continue;
        }

        String strCmd = strClassPath + " -class " + strClassName;
        // strCmd += " -DPROJECT_PREFIX=" + getPackageFromClass(strClassName);
        strCmd += strProjectCp;
        // logger.debug("strCmd=" + strCmd);

        while (Thread.currentThread().activeCount() > GenerateInputParam.THREAD_MAX) {
          logger.info("activeCount() =" + Thread.currentThread().activeCount() + "  "
              + (RunResult.GenFileCount + "/" + RunResult.CanGenFileCount + "/"
                  + RunResult.TotalJavaSrcFileCount));
          Thread.sleep(3000);
        }
        logger.info("Start ClassName=" + strClassName);
        generateThread cgenerateThread = new generateThread(strCmd, strClassName);
        cgenerateThread.start();
        RunResult.CanGenFileCount++;

      }
      /*
       * while (Thread.currentThread().activeCount() > 1) { logger.debug("activeCount() =" +
       * Thread.currentThread().activeCount()); Thread.sleep(5000); }
       */
      while (RunResult.GenFileCount < RunResult.CanGenFileCount
          && Thread.currentThread().activeCount() > 1) {
        logger.info(
            "activeCount() =" + Thread.currentThread().activeCount() + " " + RunResult.GenFileCount
                + "/" + RunResult.CanGenFileCount + "/" + RunResult.TotalJavaSrcFileCount);
        Thread.sleep(3000);
      }

      logger.info("activeCount() =" + Thread.currentThread().activeCount());
      logger.info(RunResult.GenFileCount + "/" + RunResult.CanGenFileCount + "/"
          + RunResult.TotalJavaSrcFileCount);
      v.clear();
      doClear(strPomFilePath);
      // checkGenUtFiles(strPomFilePath);

      // doClear(strPomFilePath);

      // JpfUnitTestSourceCheck cJpfUnitTestSourceCheck = new
      // JpfUnitTestSourceCheck();
      // cJpfUnitTestSourceCheck.doWork(strPomFilePath);
    } catch (Exception ex) {
      ex.printStackTrace();
    }

  }

  /*
   * private void checkGenUtFiles(String strPomFilePath) { try { Vector<String> v = new
   * Vector<String>(); strPomFilePath = strPomFilePath + AiTestConst.FileSep +
   * AiTestConst.AITEST_PATH; AiFileUtil.getFiles(strPomFilePath, v, ".java"); for (int i = 0; i <
   * v.size(); i++) { if (!v.get(i).endsWith(AiTestConst.AITEST_HC_Suffix)) { v.remove(i); i--; } }
   * if (RunResult.GenFileCount != v.size()) { logger.info("utfile count warning:" +
   * RunResult.GenFileCount + "/" + v.size()); } v.clear(); } catch (Exception ex) {
   * 
   * } }
   */

  public static void main(String[] args) {
    long start = System.currentTimeMillis();
    logger.debug("start");
    if (2 == args.length) {
      GenUnitTestC1 cGenUnitTestC5 = new GenUnitTestC1();
      cGenUnitTestC5.doWork(args[0], args[1]);

    } else {
      logger.info("error input param");
    }
    logger.info("ExcuteTime " + (System.currentTimeMillis() - start) + "ms");
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
