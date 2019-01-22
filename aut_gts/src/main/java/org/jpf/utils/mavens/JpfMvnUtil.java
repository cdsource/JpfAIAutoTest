/**
 * copyrigth by wupf@aliyun.com
 * 2018年5月8日
 */
package org.jpf.utils.mavens;

import java.io.File;

import org.jpf.aut.common.consts.AiTestConst;
import org.jpf.aut.utils.JpfUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jpf.utils.ios.AiFileUtil;
import org.jpf.utils.ios.AiOsUtil;

/**
 * @author wupf@aliyun.com
 *
 */
public class JpfMvnUtil {

	private static final Logger logger = LogManager.getLogger();

	/**
	 * 
	 */
	private JpfMvnUtil() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @category 通过MVN命令获取工程依赖的库文件
	 * @author wupf@aliyun.com
	 * @param strPomFilePath
	 * @return 2018年5月9日
	 */
	public static boolean doMvnDepency(String strPomFilePath) {

		if (!AiFileUtil.FileExist(strPomFilePath + "\\pom.xml")) {
			logger.warn("not find pom.xml in " + strPomFilePath);
			return false;
		}
		try {
			String strCmd = "cmd /c " + strPomFilePath.substring(0, 2) + " && cd " + strPomFilePath
					+ "  &&  mvn dependency:copy-dependencies -DoutputDirectory=lib -DincludeScope=compile";
			AiOsUtil.getInstance().runExecNoResult(strCmd);
			return true;
		} catch (Exception ex) {
			logger.error(ex);
		}
		return false;
	}

	/**
	 * 
	 * @author wupf@aliyun.com
	 * @param strPomFilePath
	 * @return 2018年6月21日
	 */
	public static String getClassDependPathForMaven(String strPomFilePath) {
		String strClassPath = strPomFilePath + java.io.File.separator + "target" + java.io.File.separator + "classes"
				+ AiTestConst.getJarConn();

		// JpfMvnUtil.doMvnDepency(strPomFilePath);
		File f = new File(strPomFilePath +java.io.File.separator + "lib");
		if (f.exists() && f.isDirectory()) {
			for (String strFileName : f.list()) {

				strClassPath += strPomFilePath + java.io.File.separator + "lib" + java.io.File.separator + strFileName
						+ AiTestConst.getJarConn();
			}
		}
		logger.trace(strClassPath);
		if (strClassPath.endsWith(AiTestConst.getJarConn())) {
			strClassPath = strClassPath.substring(0, strClassPath.length() - 1);
		}
		return strClassPath;
	}
	
	public static String getClassPathForMaven(String strPomFilePath) {

		return JpfUtil.JoinPath(strPomFilePath, "target"+java.io.File.separator+"classes");
	}
	/**
	 * 
	 * @author wupf@aliyun.com
	 * @param strPomFilePath
	 * @return
	 * 2018年8月22日
	 */
	public static String getSureFireReportsPath(String strPomFilePath)
	{
		return JpfUtil.JoinPath(strPomFilePath, "target"+java.io.File.separator+"surefire-reports");
	}
	
	public static String getSrcPath(String strPomFilePath)
	{
		return JpfUtil.JoinPath(strPomFilePath, "src"+java.io.File.separator+"main"+java.io.File.separator+"java");
	}
	
	public static String getSrcTmpPath(String strPomFilePath)
	{
		return JpfUtil.JoinPath(strPomFilePath, "src"+java.io.File.separator+"main"+java.io.File.separator+"java_wupf");
	}
	
	public static String getTestPath(String strPomFilePath)
	{
		return JpfUtil.JoinPath(strPomFilePath, "src"+java.io.File.separator+"test"+java.io.File.separator+"java");
	}
	
	public static String getLibPath(String strPomFilePath)
	{
		return JpfUtil.JoinPath(strPomFilePath, "lib");
	}
}
