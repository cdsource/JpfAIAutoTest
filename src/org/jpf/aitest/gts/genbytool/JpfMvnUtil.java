/**
 * copyrigth by wupf@asiainfo.com
 * 2018年5月8日
 */
package org.jpf.aitest.gts.genbytool;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.asiainfo.utils.ios.AiFileUtil;
import com.asiainfo.utils.ios.AiOsUtil;

/**
 * @author wupf@asiainfo.com
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
	 * @author wupf@asiainfo.com
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
}
