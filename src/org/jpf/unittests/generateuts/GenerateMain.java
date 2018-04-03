/**
 * @author 吴平福 E-mail:wupf@asiainfo.com
 * @version 创建时间：2017年11月20日 下午2:09:25 类说明
 */

package org.jpf.unittests.generateuts;

import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jpf.gts.gtf.GTFForAbstract;
import org.jpf.gts.gtf.GTFForInterface;
import org.jpf.gts.gtf.GTFForNormal;
import org.jpf.gts.gtf.AbstractGenerateTests;

import com.asiainfo.utils.ios.AiFileUtil;

/**
 * 
 */
public class GenerateMain {
    private static final Logger logger = LogManager.getLogger();


    Pattern p = Pattern.compile(GenerateInputParam.FileNameFilter);

    /**
     * @category 构造函数
     * @author wupf
     * @param GenerateType 1：单元测试 ; 2：DAO_interface ;3：抽象类
     * @param strInputFile
     */
    public GenerateMain(int GenerateType, String strInputFile) {


        long start = System.currentTimeMillis();
        AbstractGenerateTests cGenerateTests = null;
        if (1 == GenerateType) {
            cGenerateTests = new GTFForNormal();
        } else if (2 == GenerateType) {
            cGenerateTests = new GTFForInterface();
        } else if (3 == GenerateType) {
            cGenerateTests = new GTFForAbstract();
        } else {
            logger.warn("not support Generate type");
            return;
        }

        try {
            // String strFileName="D:\\jworkspaces\\jpfapp\\src\\org\\jpf\\ci\\rpts\\SonarAvg.java";
            if (AiFileUtil.isFile(strInputFile)) {
                if (cGenerateTests.doGenerateFile(strInputFile)) {
                    RunResult.iGenFileCount++;
                }
                RunResult.iTotalFileCount++;
            }

            if (AiFileUtil.isDirectory(strInputFile)) {

                Vector<String> vFiles = new Vector<String>();
                AiFileUtil.getFiles(strInputFile, vFiles, ".java");
                RunResult.iTotalFileCount = vFiles.size();

                while (vFiles.size() > 0) {
                    if (GenerateInputParam.FileNameFilter.length() > 0) {
                        String strFileName = vFiles.get(vFiles.size() - 1);
                        logger.debug(strFileName);
                        Matcher m = p.matcher(strFileName);
                        if (m.find()) {
                            cGenerateTests.doGenerateFile(strFileName);
                        }
                    } else {
                        cGenerateTests.doGenerateFile(vFiles.get(vFiles.size() - 1));

                    }

                    vFiles.remove(vFiles.size() - 1);
                    logger.info("exist files count:" + vFiles.size());
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
        } finally {
            logger.info("处理文件个数 " + RunResult.iTotalFileCount);
            logger.info("生成测试文件个数 " + RunResult.iGenFileCount);
            logger.info("抽象类文件个数 " + RunResult.iAbstractFileCount);
            logger.info("接口类文件个数 " + RunResult.iInterfaceFileCount);
            logger.info("已存在单元测试文件个数 " + RunResult.iExistUtFileCount);
            logger.info("处理异常文件个数 " + RunResult.iErrorFileCount);
            logger.info("ExcuteTime " + (System.currentTimeMillis() - start) + "ms");
        }
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
        new GenerateMain(2, strFileName);
    }

}
