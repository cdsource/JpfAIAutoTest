/**
 * 
 */
package org.jpf.aitest.gts.genbytool;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Vector;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.asiainfo.utils.ios.AiFileUtil;

/**
 * @author Administrator
 *
 */
public class GenByEvoClean {
	private static final Logger logger = LogManager.getLogger();

	/**
	 * 
	 */
	public GenByEvoClean(String strPrjPath) {
        long start = System.currentTimeMillis();
		doWork(strPrjPath);
        logger.info("ExcuteTime " + (System.currentTimeMillis() - start) + "ms");
	}
	
	/**
	 * 
	 * @param strPrjPath
	 */
	private void doWork(String strPrjPath) {
		try {
			delTmpPath(strPrjPath + "\\.evosuite");
			Vector<String> vFiles = new Vector<String>();
			
			AiFileUtil.getFiles(strPrjPath, vFiles, ".java");

			for (int i = 0; i < vFiles.size(); i++) {
				String strFileName = vFiles.get(i).trim();
				//logger.debug(strFileName);
				if (strFileName.endsWith("_scaffolding.java")) {
					AiFileUtil.delFile(strFileName);
					continue;
				}
				if (strFileName.endsWith("_ESTest.java")) {
					changeUtFile(strFileName);
					AiFileUtil.delFile(strFileName);
					continue;
				}
				if (strFileName.indexOf(".evosuite")>0) {
					AiFileUtil.delFile(strFileName);
					delTmpPath(AiFileUtil.getFilePath(strFileName));
					continue;
				}
				if (strFileName.indexOf(".evosuite-tests")>0) {
					AiFileUtil.delFile(strFileName);
					delTmpPath(AiFileUtil.getFilePath(strFileName));
					continue;
				}

			}

			delTmpPath(strPrjPath + "\\evosuite-report");
			delTmpPath(strPrjPath + "\\evosuite-tests");

		} catch (Exception ex) {
			logger.error(ex);
			ex.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @param strUTFileName
	 */
	private void changeUtFile(String strUTFileName) {
		
		logger.info(strUTFileName);
		if (null == strUTFileName || 0 == strUTFileName.trim().length()) {
			return;
		}
		File f = new File(strUTFileName);
		StringBuilder sb = new StringBuilder();

		FileInputStream in;
		BufferedReader br = null;
		try {
			in = new FileInputStream(f);
			br = new BufferedReader(new InputStreamReader(in, "UTF-8"));

			String line =null;
			String line_separator=System.getProperty("line.separator");
			while ((line= br.readLine())!= null) {
				if (line.endsWith("by EvoSuite")) {
					line = line.replace("by EvoSuite", "by wupf@asiainfo.com");
					sb.append(line).append(line_separator);
					continue;
				}
				if (line.startsWith("import org.evosuite")) {
					continue;
				}
				if (line.startsWith("@RunWith(EvoRunner.class)")) {
					continue;
				}
				/*
				 import static org.evosuite.shaded.org.mockito.Mockito.*;
import static org.evosuite.runtime.MockitoExtension.*;
import static org.evosuite.runtime.EvoAssertions.*;
import com.jpf.aitest.ViolatedAssumptionAnswer;
import static com.jpf.aitest.EvoAssertions.*;
				 * */

				if (line.startsWith("import static org.evosuite.runtime.EvoAssertions.*;")) {
					sb.append("import static com.jpf.aitest.EvoAssertions.*;").append(line_separator);
					continue;
				}
				if (line.startsWith("public class")) {
					line = line.replace("_ESTest", "Test");
					int iPos = line.indexOf("extends");
					if (iPos > 0) {
						line = line.substring(0, iPos - 1).trim()+" {";
					}
					sb.append(line).append(line_separator);
					continue;
				}
				sb.append(line).append(line_separator);

			}
			AiFileUtil.saveFile(strUTFileName.replace("_ESTest.java", "Test.java"), sb);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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

	private void delTmpPath(String strDelPath) {
		try {
			logger.debug(strDelPath);
			if (strDelPath.indexOf("evosuite") > 0) {
				AiFileUtil.delDirWithFiles(strDelPath);
			}
		} catch (Exception ex) {

		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		if (1==args.length)
		{
			new GenByEvoClean(args[0]);
		}else
		{
			 logger.info("error input!");
		}
	}

}
