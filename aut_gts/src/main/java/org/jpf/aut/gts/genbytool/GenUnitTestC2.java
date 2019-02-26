/**
 * copyrigth by wupf@asiainfo.com 2018年5月8日
 */
package org.jpf.aut.gts.genbytool;

import java.io.File;
import java.util.Vector;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jpf.aut.base.GenerateInputParam;
import org.jpf.aut.base.RunResult;
import org.jpf.aut.common.consts.AutConst;
import org.jpf.aut.utils.AutUtil;
import org.jpf.utils.ios.AiFileUtil;
import org.jpf.utils.ios.AiOsUtil;
import org.jpf.utils.mavens.JpfMvnUtil;
import org.jpf.utils.process.ProcessUtil;

/**
 * @author wupf@asiainfo.com
 *
 */
public class GenUnitTestC2 {
  private static final Logger logger = LogManager.getLogger();


  class generateThread extends Thread {
    String strCmd = "";
    String strJavaFileName = "";

    public generateThread(final String inCmd, String strJavaFileName) {
      this.strCmd = inCmd;
      this.strJavaFileName = strJavaFileName;
    }

    @Override
    public void run() {
      long start = System.currentTimeMillis();
      try {
        // logger.info("strCmd:" + strCmd);
        ProcessUtil.runexec(null, strCmd);
      } catch (Exception e) {
        e.printStackTrace();
      } finally {
        RunResult.addFinishFileCount();
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
        + java.io.File.separator + "classes" + AutConst.getJarConn();

    File f = new File(strPomFilePath + java.io.File.separator + "lib");
    if (f.exists() && f.isDirectory()) {
      for (String strFileName : f.list()) {
        if (!strFileName.endsWith(GenerateInputParam.WORK_JAR)) {
          strProjectCp += strPomFilePath + java.io.File.separator + "lib" + java.io.File.separator
              + strFileName + AutConst.getJarConn();
        }
        // check length
        if (strProjectCp.length() > 500) {
          break;
        }
      }
    }
    if (strProjectCp.endsWith(AutConst.getJarConn())) {
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
        + java.io.File.separator + "classes" + AutConst.getJarConn();

    File f = new File(strPomFilePath + java.io.File.separator + "lib");
    if (f.exists() && f.isDirectory()) {
      for (String strFileName : f.list()) {
        if (!strFileName.endsWith(GenerateInputParam.WORK_JAR) && strFileName.endsWith(".jar")) {
          strProjectCp += strPomFilePath + java.io.File.separator + "lib" + java.io.File.separator
              + strFileName + AutConst.getJarConn();
        }
      }
    }
    if (strProjectCp.endsWith(AutConst.getJarConn())) {
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
  public GenUnitTestC2() {}

  public void doWork(String strJavaSrcPath, String strPomFilePath) {

    try {

      String strProjectCp = getCpFilePath(strPomFilePath);
      String strCmd = "";
      if (AiOsUtil.getInstance().isWindows()) {
        // strClassPath = "java -jar
        // D:\\jworkspaces\\JpfUnitTest2\\evosuite-master-1.0.7-SNAPSHOT.jar ";
        strCmd =
            "java -Xms512m -Xmx512m -classpath D:\\jworkspaces\\JpfUnitTest2\\aitest-client-1.3.jar";
      } else {
        strCmd = "java -Xms512m -Xmx512m -classpath $HOME/aitest/aitest-master-1.0.6.jar ";
      }
      // String strClassPath = "java -jar $HOME/aitest/aitest-gen_1.3.jar "
      strCmd += " org.jpf.aut.wupf  ";

      if (!AiFileUtil.isDirectory(strPomFilePath)) {
        logger.info("file does not exist :" + strPomFilePath);
        return;
      }

      if (!AiFileUtil.isDirectory(strPomFilePath + java.io.File.separator + "lib")) {
        logger.info("file does not exist :" + strPomFilePath + java.io.File.separator + "lib");
        return;
      }

      generateThread cgenerateThread = new generateThread(strCmd, "");
      cgenerateThread.start();

      while (Thread.currentThread().activeCount() > 1) {
        logger.debug("activeCount() =" + Thread.currentThread().activeCount());
        Thread.sleep(5000);
      }

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
      GenUnitTestC2 cGenUnitTestC5 = new GenUnitTestC2();
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
