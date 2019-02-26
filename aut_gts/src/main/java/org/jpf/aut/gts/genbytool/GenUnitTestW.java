/**
 * @author 吴平福 E-mail:wupf@asiainfo.com
 * @version 创建时间：2017年11月20日 下午2:09:25 类说明
 */

package org.jpf.aut.gts.genbytool;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jpf.aut.base.GenerateInputParam;
import org.jpf.aut.base.RunResult;
import org.jpf.aut.gts.gtf.AbstractGenerateTests;
import org.jpf.aut.gts.gtf.forabstract.GTFForAbstract;
import org.jpf.aut.gts.gtf.fornormal.GTFForNormal;
import org.jpf.aut.gts.plugins.springs.gtf.ItForDao;
import org.jpf.aut.utils.AutUtil;
import org.jpf.utils.ios.AiFileUtil;

/**
 * 
 */
public class GenUnitTestW {
  private static final Logger logger = LogManager.getLogger();

  /**
   * 
   * @param args
   */
  public GenUnitTestW(String[] args) {
    Map<String, Object> mapArgs = new HashMap<>();
    int iFindIdx = -1;
    for (String arg : args) {
      iFindIdx = arg.indexOf('=');
      if (iFindIdx != -1) {
        mapArgs.put(arg.substring(0, iFindIdx), arg.substring(iFindIdx + 1));
      }
    }
    // 超时注解
    if (mapArgs.get("NeedTimeOut") != null) {
      GenerateInputParam.UtTimeOut = Integer.parseInt((String) mapArgs.get("NeedTimeOut"));
    }
    // 文件名称过滤
    if (mapArgs.get("FileNameFilter") != null) {
      GenerateInputParam.FileNameFilter = (String) mapArgs.get("FileNameFilter");
    }
    // JAVA文件编码
    if (mapArgs.get("JAVA_ENCODE") != null) {
      GenerateInputParam.JAVA_ENCODE = (String) mapArgs.get("JAVA_ENCODE");
    }
    // JAVA源文件查找范围
    if (mapArgs.get("FilePath_Find_Java_Source") != null) {
      GenerateInputParam.FilePath_Find_Java_Source =
          (String) mapArgs.get("FilePath_Find_Java_Source");
    }

    if (mapArgs.get("SRC_PATH") != null) {
      GenerateInputParam.SRC_PATH = (String) mapArgs.get("SRC_PATH");
    } else {
      return;
    }

    GenerateInputParam.FilePath_Find_Java_Source += ";" + GenerateInputParam.SRC_PATH;

    if (mapArgs.get("GenerateType") != null) {
      GenerateInputParam.GenerateType = Integer.parseInt((String) mapArgs.get("GenerateType"));
    } else {
      return;
    }

    doWork(GenerateInputParam.SRC_PATH);

  }

  /**
   * @category 构造函数
   * @author wupf
   * @param GenerateType 1：单元测试 ; 2：DAO_interface ;3：抽象类
   * @param strInputFile
   */
  public GenUnitTestW(int GenerateType, String strInputFile) {
    GenerateInputParam.UtTimeOut = 1000;
    GenerateInputParam.FileNameFilter = "";
    GenerateInputParam.SRC_PATH = strInputFile;
    GenerateInputParam.FilePath_Find_Java_Source = findMaxSearchPath(strInputFile);
    GenerateInputParam.GenerateType = GenerateType;
    doWork(GenerateInputParam.SRC_PATH);
  }

  /**
   * @category 查找最大的路径
   * @author wupf@asiainfo.com
   * @param strInputFilePath
   * @return 2018年8月6日
   */
  public String findMaxSearchPath(String strInputFilePath) {
    boolean isFind = false;
    if (!AiFileUtil.isDirectory(strInputFilePath)) {
      strInputFilePath = AiFileUtil.getFilePath(strInputFilePath);
    }

    while (!isFind) {
      if (strInputFilePath.endsWith("src")) {
        isFind = true;
        break;
      }
      if (AiFileUtil.FileExist(strInputFilePath + java.io.File.separator + "pom.xml")) {
        isFind = true;
        break;
      }

      strInputFilePath = AiFileUtil.getFilePath(strInputFilePath);

    }
    return strInputFilePath;
  }


  /**
   * 
   * @author wupf@asiainfo.com 2018年7月5日
   */
  public void printParam() {
    logger.info("bNeedTimeOut=" + GenerateInputParam.UtTimeOut);
    logger.info("FileNameFilter=" + GenerateInputParam.FileNameFilter);
    logger.info("JAVA_ENCODE=" + GenerateInputParam.JAVA_ENCODE);
    logger.info("SRC_PATH=" + GenerateInputParam.SRC_PATH);
    logger.info("FilePath_Find_Java_Source=" + GenerateInputParam.FilePath_Find_Java_Source);
    logger.info("GenerateType=" + GenerateInputParam.GenerateType);
  }



  /**
   * 
   * @author wupf@asiainfo.com
   * @param strInputFile 2018年7月5日
   */
  private void doWork(String strInputFile) {
    long start = System.currentTimeMillis();
    Pattern p = Pattern.compile(GenerateInputParam.FileNameFilter);
    strInputFile = strInputFile.trim();
    logger.info(strInputFile);

    printParam();

    AbstractGenerateTests cGenerateTests = null;

    switch (GenerateInputParam.GenerateType) {
      case 1:
        cGenerateTests = new GTFForNormal();
        break;
      case 2:
        cGenerateTests = new ItForDao();
        break;
      case 3:
        cGenerateTests = new GTFForAbstract();
        break;
      default:
        logger.info("not support Generate type");
        break;
    }

    if (null == cGenerateTests) {
      return;
    }
    try {
      if (AiFileUtil.isFile(strInputFile)) {
        if (cGenerateTests.doGenerateFile(strInputFile)) {
          RunResult.addFinishFileCount();
        }
        RunResult.TotalJavaSrcFileCount++;
      } else if (AiFileUtil.isDirectory(strInputFile)) {
        Vector<String> vFiles = new Vector<String>();
        AiFileUtil.getFiles(strInputFile, vFiles, ".java");
        // add filter
        AutUtil.removeNotSourceFile(vFiles);

        RunResult.TotalJavaSrcFileCount = vFiles.size();
        logger.info(vFiles.size());
        while (vFiles.size() > 0) {
          String strFileName = vFiles.get(vFiles.size() - 1);
          logger.info(strFileName);
          vFiles.remove(vFiles.size() - 1);
          if (strFileName.indexOf("test") >= 0) {
            RunResult.iFail_TestFile++;
            continue;
          }
          if (strFileName.indexOf("target") >= 0) {
            RunResult.iFail_TargetFile++;
            continue;
          }
          if (GenerateInputParam.FileNameFilter.length() > 0) {

            Matcher m = p.matcher(strFileName);
            if (!m.find()) {
              continue;
            }
          }
          cGenerateTests.doGenerateFile(strFileName);
        }

      }

    } catch (Exception ex) {
      ex.printStackTrace();
    } finally {
      RunResult.printResult();

    }
    logger.info("ExcuteTime " + (System.currentTimeMillis() - start) + "ms");
  }

  /**
   * @category @author 吴平福
   * @param args update 2017年11月20日
   */

  public static void main(String[] args) {
    // TODO Auto-generated method stub

    GenerateInputParam.FileNameFilter = "";

    if (1 == args.length) {
      new GenUnitTestW(1, args[0]);
    } else {
      new GenUnitTestW(args);
    }

  }

}
