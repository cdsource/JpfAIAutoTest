/**
 * copyrigth by wupf@aliyun.com
 * 2018年8月20日
 */
package org.jpf.aut.checks;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jpf.aut.base.GenerateInputParam;
import org.jpf.utils.ios.JpfFileUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * @author wupf@aliyun.com
 *
 */
public class CompileErrClassName {
	private static final Logger logger = LogManager.getLogger();
	//final String Err_ClassName = "class [.*]  is public, should be declared in a file named [.*].java";
	final static String Err_ClassName = "class .* is public, should be declared in a file named .*.java";
	/**
	 * 
	 */
	private CompileErrClassName() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @author wupf@aliyun.com
	 * @param args
	 * 2018年8月20日
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		CompileErrClassName.checkIn("", "class JschUtilTest is public, should be declared in a file named JschUtilTest.java");
	}

	public static void checkIn(String strFileName, String strErrorInfo) {
		// class JschUtilTest is public, should be declared in a file named
		// JschUtilTest.java
		Pattern p = Pattern.compile(Err_ClassName);
		Matcher m = p.matcher(strErrorInfo);
		if (m.find()) {
			// modify class name
			logger.info("find");
			int iPos=strErrorInfo.indexOf("class ");
			int iPos2=strErrorInfo.indexOf(" is public");
			logger.info(strErrorInfo.substring(iPos+6, iPos2).trim());
			logger.info(strFileName);
		}else
		{
			logger.info("not find");
		}
	}

	public void doAction(final String strFileName) {
		logger.debug(strFileName);

		if (null == strFileName || 0 == strFileName.trim().length()) {
			return;
		}
		File f = new File(strFileName);
		StringBuilder sb = new StringBuilder();

		FileInputStream in;
		BufferedReader br = null;
		try {
			in = new FileInputStream(f);
			br = new BufferedReader(new InputStreamReader(in, GenerateInputParam.JAVA_ENCODE));

			String line = null;
			String line_separator = System.getProperty("line.separator");
			while ((line = br.readLine()) != null) {

				if (line.trim().startsWith("public class")) {
					line = line.replace("_ESTest", "Test");
					sb.append(line).append(line_separator);
					continue;
				}
				sb.append(line).append(line_separator);

			}
			JpfFileUtil.saveFile(strFileName, sb);
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
