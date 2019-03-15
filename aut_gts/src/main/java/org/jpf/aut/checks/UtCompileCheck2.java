/**
 * copyrigth by wupf@aliyun.com 2018年6月2日
 */
package org.jpf.aut.checks;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jpf.utils.ios.JpfFileUtil;
import org.jpf.utils.ios.JpfOsUtil;

/**
 * @author wupf@aliyun.com
 *
 */
public class UtCompileCheck2 {
  private static final Logger logger = LogManager.getLogger();

  boolean bOK = false;

  Map<String, String> mapModifyTestFile = new HashMap<>();
  Map<String, String> mapErrs = new HashMap<>();

  /**
   * 
   * @param strStartPath
   */
  public UtCompileCheck2(final String strStartPath) {
    // TODO Auto-generated constructor stub
    long start = System.currentTimeMillis();
    try {
      int iTryCount = 0;

      while (!bOK) {
        iTryCount++;
        bOK = true;
        runexec(strStartPath);
        logger.info("iTryCount=" + iTryCount);
        logger.info("iModifyTestFileCount=" + mapModifyTestFile.size());
        // bOK = true;
      }

      logger.info("iTryCount=" + iTryCount);
      logger.info("iDeleteTestFileCount=" + mapModifyTestFile.size());

    } catch (Exception ex) {
      logger.error(ex);
    }
    logger.info("ExcuteTime " + (System.currentTimeMillis() - start) + "ms");
    printResult();

  }

  public void printResult() {
    for (Map.Entry<String, String> entry : mapModifyTestFile.entrySet()) {

      logger.info("Key = " + entry.getKey());

    }

    for (Map.Entry<String, String> entry : mapErrs.entrySet()) {

      logger.info("Key = " + entry.getKey());

    }
  }

  /**
   * 
   * @author wupf@aliyun.com
   * @param strTestFileName 2018年8月14日
   */
  private void delTestFile(String strTestFileName) {
    try {
      // logger.info(strTestFileName);
      if (strTestFileName.endsWith("TestAll.java")) {
        JpfFileUtil.delFile(strTestFileName);
        logger.info("delete test file:" + strTestFileName);
      }
    } catch (Exception ex) {
      logger.error(ex);
    }
  }

  /**
   * 
   * @author wupf@aliyun.com
   * @param strCmd 2018年7月2日
   */
  private void runShell(String strStartPath) {
    try {
      String[] cmd =
          new String[] {"/bin/sh", "-c", "cd " + strStartPath + "  ; mvn compile test-compile"};
      Process process = Runtime.getRuntime().exec(cmd);
      InputStreamReader ir = new InputStreamReader(process.getInputStream());
      LineNumberReader input = new LineNumberReader(ir);

      String line;
      Map<String, String> map = new HashMap<>();
      while ((line = input.readLine()) != null) {
        line = line.trim();
        // logger.debug(line);
        if (line.startsWith("[ERROR]")) {
          int j = line.indexOf(".java:[") + 5;
          if (j > 0 && line.indexOf(
              "src" + java.io.File.separator + "test" + java.io.File.separator + "java") > 0) {
            logger.debug(line);
            String strDelFileName = line.substring(8, j).trim();
            String strPos = line.substring(j + 2, line.length()).trim();
            j = strPos.indexOf(",");
            strPos = strPos.substring(0, j);
            logger.debug(strDelFileName);
            logger.debug(strPos);
            // delFile(strDelFileName);
            // TestAll.java

            bOK = false;
            if (map.containsKey(strDelFileName)) {
              String strValue = map.get(strDelFileName);
              // logger.debug(strValue);
              strValue += strPos + ",";
              map.put(strDelFileName, strValue);
            } else {
              map.put(strDelFileName, strPos + ",");
            }
          }
        }
        if (line.trim().equalsIgnoreCase("[INFO] BUILD SUCCESS")) {
          bOK = true;
          logger.info(line);
        }
      }
      process.waitFor();
      if (process.exitValue() != 0) {
        logger.error(" Failed to check test, exit value is " + process.exitValue());
      }
      for (Map.Entry<String, String> entry : map.entrySet()) {

        logger.info("Key = " + entry.getKey() + ", Value = " + entry.getValue());
        // changeUtFile(entry.getKey(), entry.getValue());
        mapModifyTestFile.put(entry.getKey(), entry.getValue());
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * 
   * @author wupf@aliyun.com
   * @param strCmd 2018年7月1日
   */
  private void runexec(String strStartPath) {
    if (JpfOsUtil.getInstance().isWindows()) {
      runCmd(strStartPath);
    } else {
      runShell(strStartPath);
    }
  }

  public String getErrorInfo(final String strInput) {
    // 程序包org.aitest.runtime不存在
    String strErrInfo = "";
    int iPos = strInput.indexOf("]");
    if (iPos > 0) {
      strErrInfo = strInput.substring(iPos + 1, strInput.length()).trim();
    }
    return strErrInfo;
  }

  /**
   * 
   * @author wupf@aliyun.com
   * @param strCmd 2018年7月1日
   */
  private void runCmd(String strStartPath) {
    try {
      logger.debug(strStartPath);
      String strCmd = "cmd /c " + strStartPath.substring(0, 2) + " && cd " + strStartPath
          + "  && mvn compile test-compile";
      logger.debug(strCmd);
      Process process = Runtime.getRuntime().exec(strCmd);
      InputStreamReader ir = new InputStreamReader(process.getInputStream());
      LineNumberReader input = new LineNumberReader(ir);

      String line;

      Map<String, Map<Long, String>> ErrMap = new HashMap();

      while ((line = input.readLine()) != null) {

        line = line.trim();
        if (line.startsWith("[ERROR]")) {
          logger.debug(line);
          int j = line.indexOf(".java:[") + 5;
          if (j > 0 && line.indexOf("/test/") > 0) {
            // logger.debug(line);
            String strDelFileName = line.substring(9, j).trim();
            String strPos = line.substring(j + 2, line.length()).trim();
            String strErrInfo = getErrorInfo(strPos);
            j = strPos.indexOf(",");
            strPos = strPos.substring(0, j);
            // logger.debug(strDelFileName);
            // logger.debug(strPos);
            // delFile(strDelFileName);
            // error code
            delTestFile(strDelFileName);

            bOK = false;
            if (ErrMap.containsKey(strDelFileName)) {
              Map detailMap = ErrMap.get(strDelFileName);
              if (detailMap == null) {
                detailMap = new HashMap<Long, String>();
                detailMap.put(Long.parseLong(strPos), strErrInfo);
              } else {
                if (!detailMap.containsKey(Long.parseLong(strPos))) {
                  detailMap.put(Long.parseLong(strPos), strErrInfo);
                }
              }
            } else {
              Map detailMap = new HashMap<Long, String>();
              detailMap.put(Long.parseLong(strPos), strErrInfo);
              ErrMap.put(strDelFileName, detailMap);
            }
          }
          if (line.trim().equalsIgnoreCase("[INFO] BUILD SUCCESS")) {
            bOK = true;
            logger.info(line);
          }

        }
      }
      process.waitFor();
      int iRetValue = process.exitValue();

      for (Map.Entry<String, Map<Long, String>> entry : ErrMap.entrySet()) {

        // logger.info("Key = " + entry.getKey() + ", Value = " + entry.getValue());
        // changeUtFile(entry.getKey(), entry.getValue());
        Map<Long, String> detailMap = entry.getValue();
        logger.info("Key = " + entry.getKey());
        for (Map.Entry<Long, String> entry2 : detailMap.entrySet()) {
          logger.info("Key = " + entry2.getKey() + ", Value = " + entry2.getValue());
          mapErrs.put(entry2.getValue(), "");
        }
        mapModifyTestFile.put(entry.getKey(), "");
        modifyUtFile(entry.getKey(), detailMap);
      }

    } catch (Exception e) {
      logger.error(e);

    }
  }

  /**
   * @param args
   */
  public static void main(String[] args) {
    // TODO Auto-generated method stub

    if (1 == args.length) {
      new UtCompileCheck2(args[0]);
    } else {
      logger.info("error input!");
    }
  }

  /**
   * 
   * @author wupf@aliyun.com
   * @param strUTFileName
   * @param strFileRowNums 2018年7月30日
   */
  private void modifyUtFile(final String strUTFileName, final Map<Long, String> mapErr) {

    logger.debug(strUTFileName);
    if (null == strUTFileName || 0 == strUTFileName.trim().length()) {
      return;
    }
    File f = new File(strUTFileName);
    StringBuilder sb = new StringBuilder();
    FileInputStream in;
    BufferedReader br = null;
    boolean bDelete = false;
    int iModifyCount = 0;
    try {
      in = new FileInputStream(f);
      br = new BufferedReader(new InputStreamReader(in, "UTF-8"));

      String line = null;
      String line_separator = System.getProperty("line.separator");
      long j = 0;
      for (j = 1; (line = br.readLine()) != null; j++) {
        if (mapErr.containsKey(j)) {
          String strErrMsg = mapErr.get(j);
          if ("package org.aitest.runtime does not exist".equalsIgnoreCase(strErrMsg)) {
            sb.append(line.replaceAll("org.aitest", "org.jpf.aitest"));
          } else if (strErrMsg.startsWith("no suitable constructor found for")) {
            sb.append(line.substring(0, line.indexOf("=") + 1)).append(" null;");
          } else if (strErrMsg.endsWith("is abstract; cannot be instantiated")) {
            sb.append(line.substring(0, line.indexOf("=") + 1)).append(" null;");
          } else if (strErrMsg.startsWith(
              "a type with the same simple name is already defined by the single-type-import of ")) {
            sb = sb.append("//").append(line);
          } else if (strErrMsg.endsWith("cannot be applied to given types;")) {
            sb.append(line.substring(0, line.indexOf("=") + 1)).append(" null;");
          } else if (strErrMsg.startsWith("cannot find symbol")) {
            sb.append("//").append(line);
          } else {
            // sb.append("//").append(line);
            logger.info(line);
            // delete Java test file
            bDelete = true;
          }
          iModifyCount++;
        } else {
          sb.append(line);
        }
        sb.append(line_separator);
      }

      JpfFileUtil.saveFile(strUTFileName, sb);
      logger.debug("file rowcount :" + j);
      logger.debug("ModifyCount:" + iModifyCount);
      if (mapErr.size() != iModifyCount) {
        logger.debug("ModifyCount:" + iModifyCount);
        logger.debug("mapErr.size():" + mapErr.size());
      }
      if (bDelete) {
        JpfFileUtil.delFile(strUTFileName);
      }
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } finally {
      try {
        if (br != null) {
          br.close();
        }
      } catch (Exception ex) {
      }
    }

  }


}
