/**
 * @author 吴平福 E-mail:wupf@asiainfo.com
 * @version 创建时间：2017年11月20日 下午2:09:25 类说明
 */

package org.jpf.unittests.generateuts;

import java.util.Vector;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jpf.unittests.generateuts.utils.AppParam;

import com.asiainfo.utils.ios.AiFileUtil;

/**
 * 
 */
public class GenerateMain {
    private static final Logger logger = LogManager.getLogger();

    private String strFileNameFilter = "DAO";

    /**
     * 
     */
    public GenerateMain(String strInputFile) {
        long start = System.currentTimeMillis();
        GenerateUnitTests cGenerateUnitTests = new GenerateUnitTests();
        try {
            // String strFileName="D:\\jworkspaces\\jpfapp\\src\\org\\jpf\\ci\\rpts\\SonarAvg.java";
            if (AiFileUtil.isFile(strInputFile)) {
                if (cGenerateUnitTests.doGenerateFile(strInputFile)) {
                    GenerateConst.iGenFileCount++;
                }
                GenerateConst.iTotalFileCount++;
            }
            if (AiFileUtil.isDirectory(strInputFile)) {

                Vector<String> vFiles = new Vector<String>();
                AiFileUtil.getFiles(strInputFile, vFiles, ".java");
                AppParam.vFilesAll = (Vector<String>) vFiles.clone();
                GenerateConst.iTotalFileCount = vFiles.size();


                while (vFiles.size() > 0) {
                    if (strFileNameFilter.length() > 0) {
                        if (vFiles.get(vFiles.size() - 1).endsWith(strFileNameFilter + ".java")) {
                            if (cGenerateUnitTests.doGenerateFile(vFiles.get(vFiles.size() - 1))) {
                                GenerateConst.iGenFileCount++;
                            }
                        }
                    } else {
                        if (cGenerateUnitTests.doGenerateFile(vFiles.get(vFiles.size() - 1))) {
                            GenerateConst.iGenFileCount++;
                        }
                    }
                    vFiles.remove(vFiles.size() - 1);
                }

            }
            // 1 分析代码，找出 1 package 2 import 3 方法名带参数

            // 2 生成单元测试
            // 2.1 package
            // 2.2 import
            // 2.2.1 增加单元测试相关import
            // 3 增加注释
            // 4 生成类名
            // 5 生成测试CASE
            // 6 增加其它

        } catch (Exception ex) {
            // TODO: handle exception
            ex.printStackTrace();
        }
        logger.info("处理文件个数 " + GenerateConst.iTotalFileCount);
        logger.info("生成文件个数 " + GenerateConst.iGenFileCount);
        logger.info("抽象类文件个数 " + GenerateConst.iAbstractFileCount);
        logger.info("接口类文件个数 " + GenerateConst.iInterfaceFileCount);
        logger.info("已存在单元测试文件个数 " + GenerateConst.iExistUtFileCount);
        logger.info("处理异常文件个数 " + GenerateConst.iErrorFileCount);
        logger.info("ExcuteTime " + (System.currentTimeMillis() - start) + "ms");
    }

    /**
     * @category @author 吴平福
     * @param args update 2017年11月20日
     */

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        String strFileName = "D:\\jworkspaces\\AI_CodeTest\\src\\com\\asiainfo\\utsample\\abc.java";
        // strFileName =
        // "D:\\jworkspaces\\jpfapp\\src\\org\\jpf\\unittests\\samples\\sampleclass.java";
        strFileName = "d:\\svn\\ecommerce-branch-20170912\\app-util\\src\\main\\java";
        // strFileName = "d:\\svn\\ecommerce-branch-20170912\\poffice-util\\src\\main\\java";
        // strFileName = "d:\\svn\\ecommerce-branch-20170912\\app-dao\\src\\main\\java";
        // 单列模式
        // strFileName =
        // "D:\\svn\\ecommerce-branch-20170912\\app-util\\src\\main\\java\\com\\asiainfo\\ebiz\\iposm\\util\\HiIposmConfig.java";
        // strFileName =
        // "D:\\svn\\ecommerce-branch-20170912\\app-util\\src\\main\\java\\com\\asiainfo\\ebiz\\util\\Des3New.java";
        // ControlServiceInterceptorTest
        // import *
        // strFileName="D:\\svn\\ecommerce-branch-20170912\\app-util\\src\\main\\java\\com\\asiainfo\\ebiz\\log\\EbizDBAppender.java";
        // strFileName = "D:\\jworkspaces\\AI_CodeTest\\src\\com\\asiainfo\\utsample\\def.java";
        // strFileName=
        // "D:\\svn\\ecommerce-branch-20170912\\app-util\\src\\main\\java\\com\\asiainfo\\ebiz\\util\\ControlServiceInterceptorTest.java";
        // strFileName="d:\\svn\\ecommerce-branch-20170912\\app-util\\src\\main\\java\\com\\asiainfo\\ebiz\\yyzf\\HiIposmResponse.java";
        strFileName = "D:\\svn\\ecommerce-branch-20170912\\app-dao\\src\\main\\java\\com\\asiainfo\\ebiz";
        // strFileName =
        // "F:\\svn\\svn10.1.195.110\\trunk\\code-res\\templateInteApi\\templateCommon\\src\\main\\java\\com";
        new GenerateMain(strFileName);
    }

}
