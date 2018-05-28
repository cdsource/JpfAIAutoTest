/**
 * @author 吴平福 E-mail:wupf@asiainfo.com
 * @version 创建时间：2017年11月20日 下午2:09:25 类说明
 */

package org.jpf.aitest;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jpf.aitest.gts.gtf.AbstractGenerateTests;
import org.jpf.aitest.gts.gtf.forabstract.GTFForAbstract;
import org.jpf.aitest.gts.gtf.fornormal.GTFForNormal;
import org.jpf.aitest.plugins.gts.springs.gtf.GTFForDAO;

import com.asiainfo.utils.ios.AiFileUtil;

/**
 * 
 */
public class GenerateMain {
	private static final Logger logger = LogManager.getLogger();

	Pattern p = Pattern.compile(GenerateInputParam.FileNameFilter);

	/**
	 * 
	 * @param args
	 */
	public GenerateMain(String[] args) {
		Map<String, Object> mapArgs = new HashMap<>();
		int iFindIdx = -1;
		for (int i = 0; i < args.length; i++) {
			iFindIdx = args[i].indexOf('=');
			if (iFindIdx != -1) {
				mapArgs.put(args[i].substring(0, iFindIdx), args[i].substring(iFindIdx + 1));
			}
		}
		// 超时注解
		if (mapArgs.get("NeedTimeOut") != null) {
			GenerateInputParam.bNeedTimeOut = (boolean) mapArgs.get("NeedTimeOut");
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
			GenerateInputParam.FilePath_Find_Java_Source = (String) mapArgs.get("FilePath_Find_Java_Source");
		}

		if (mapArgs.get("SRC_PATH") != null) {
			GenerateInputParam.SRC_PATH = (String) mapArgs.get("SRC_PATH");
		} else {
			return;
		}

		GenerateInputParam.FilePath_Find_Java_Source += ";" + GenerateInputParam.SRC_PATH;

		int GenerateType = 0;
		if (mapArgs.get("GenerateType") != null) {
			GenerateType = (int) mapArgs.get("GenerateType");
		} else {
			return;
		}

		doWork(GenerateType, GenerateInputParam.SRC_PATH);

	}

	/**
	 * @category 构造函数
	 * @author wupf
	 * @param GenerateType 1：单元测试 ; 2：DAO_interface ;3：抽象类
	 * @param strInputFile
	 */
	public GenerateMain(int GenerateType, String strInputFile) {
		GenerateInputParam.bNeedTimeOut = true;
		GenerateInputParam.FileNameFilter = "";
		GenerateInputParam.SRC_PATH = strInputFile;
		doWork(GenerateType, GenerateInputParam.SRC_PATH);
	}

	private void doWork(int GenerateType, String strInputFile) {
		strInputFile = strInputFile.trim();
		logger.info(strInputFile);

		logger.info("bNeedTimeOut=" + GenerateInputParam.bNeedTimeOut);
		logger.info("FileNameFilter=" + GenerateInputParam.FileNameFilter);
		logger.info("JAVA_ENCODE=" + GenerateInputParam.JAVA_ENCODE);
		logger.info("SRC_PATH=" + GenerateInputParam.SRC_PATH);
		logger.info("FilePath_Find_Java_Source=" + GenerateInputParam.FilePath_Find_Java_Source);

		logger.info("GenerateType=" + GenerateType);

		long start = System.currentTimeMillis();
		AbstractGenerateTests cGenerateTests = null;
		if (1 == GenerateType) {
			cGenerateTests = new GTFForNormal();
		} else if (2 == GenerateType) {
			cGenerateTests = new GTFForDAO();
		} else if (3 == GenerateType) {
			cGenerateTests = new GTFForAbstract();
		} else {
			logger.warn("not support Generate type");
			return;
		}

		try {
			// String
			// strFileName="D:\\jworkspaces\\jpfapp\\src\\org\\jpf\\ci\\rpts\\SonarAvg.java";
			if (AiFileUtil.isFile(strInputFile)) {
				if (cGenerateTests.doGenerateFile(strInputFile)) {
					RunResult.iGenFileCount++;
				}
				RunResult.iTotalFileCount++;
			} else if (AiFileUtil.isDirectory(strInputFile)) {
					Vector<String> vFiles = new Vector<String>();
					AiFileUtil.getFiles(strInputFile, vFiles, ".java");
					//remove test file
					for(int i=vFiles.size();i>0;i--)
					{
						if (vFiles.get(i).indexOf("test") == -1)
						   {
							   vFiles.remove(i);
						   }
					}

					   
					RunResult.iTotalFileCount = vFiles.size();
					logger.info(vFiles.size());
					while (vFiles.size() > 0) {
						String strFileName = vFiles.get(vFiles.size() - 1);
						logger.info(strFileName);
						if (GenerateInputParam.FileNameFilter.length() > 0) {

							Matcher m = p.matcher(strFileName);
							if (m.find()) {
								cGenerateTests.doGenerateFile(strFileName);
							}
						} else {
							if (strFileName.indexOf("test") == -1) {
								cGenerateTests.doGenerateFile(vFiles.get(vFiles.size() - 1));
							} else {
								logger.warn("not source code: " + strFileName);
							}
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
			logger.error(ex);
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
		// strFileName =
		// "d:\\svn\\ecommerce-branch-20170912\\poffice-util\\src\\main\\java";
		// strFileName = "d:\\svn\\ecommerce-branch-20170912\\app-dao\\src\\main\\java";
		// 单列模式
		// strFileName =
		// "D:\\svn\\ecommerce-branch-20170912\\app-util\\src\\main\\java\\com\\asiainfo\\ebiz\\iposm\\util\\HiIposmConfig.java";
		// strFileName =
		// "D:\\svn\\ecommerce-branch-20170912\\app-util\\src\\main\\java\\com\\asiainfo\\ebiz\\util\\Des3New.java";
		// ControlServiceInterceptorTest
		// import *
		// strFileName="D:\\svn\\ecommerce-branch-20170912\\app-util\\src\\main\\java\\com\\asiainfo\\ebiz\\log\\EbizDBAppender.java";
		// strFileName =
		// "D:\\jworkspaces\\AI_CodeTest\\src\\com\\asiainfo\\utsample\\def.java";
		// strFileName=
		// "D:\\svn\\ecommerce-branch-20170912\\app-util\\src\\main\\java\\com\\asiainfo\\ebiz\\util\\ControlServiceInterceptorTest.java";
		// strFileName="d:\\svn\\ecommerce-branch-20170912\\app-util\\src\\main\\java\\com\\asiainfo\\ebiz\\yyzf\\HiIposmResponse.java";

		// 根据日志生成
		strFileName = "D:\\svn\\ecommerce-branch-20170912\\app-dao\\src\\main\\java\\com\\asiainfo\\ebiz\\alipay\\service";

		// 根据注释生成
		GenerateInputParam.bNeedTimeOut = true;
		strFileName = "D:\\jworkspaces\\JpfUnitTest\\src\\org\\jpf\\unittests\\samples\\SampleJavaDoc.java";

		// 根据NLP生成
		strFileName = "D:\\jworkspaces\\JpfUnitTest\\src\\org\\jpf\\unittests\\samples\\SampleNLP.java";

		// 抓取运行异常
		strFileName = "D:\\jworkspaces\\JpfUnitTest\\src\\org\\jpf\\unittests\\samples\\SampleOutArray.java";

		//
		strFileName = "F:\\svn\\yellowstone-parent\\common";
		// 浙江移动
		GenerateInputParam.FileNameFilter = "";
		// strFileName="F:\\zj2\\order-center\\order_center\\aiorder-parent\\aiorder-common";
		// strFileName =
		// "F:\\svn\\svn10.1.195.110\\trunk\\code-res\\templateInteApi\\templateCommon\\src\\main\\java\\com";

		new GenerateMain(1, strFileName);
	}

}
