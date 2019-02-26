/**
 * copyrigth by wupf@asiainfo.com
 * 2018年8月16日
 */
package org.jpf.aut.gts.genbytool;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Vector;

import org.jpf.aut.base.GenerateInputParam;
import org.jpf.aut.common.consts.AutConst;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.jpf.utils.classes.ParseJavaByJdt;
import org.jpf.utils.mavens.JpfMvnUtil;

import org.jpf.utils.ios.AiFileUtil;

/**
 * @author wupf@asiainfo.com
 *
 */
public class JptUTSourceModify {

	/**
	 * 
	 */
	public JptUTSourceModify() {
		// TODO Auto-generated constructor stub
	}


	private static final Logger logger = LogManager.getLogger();

	int iModifyFileCount = 0;

	/**
	 * @author wupf@asiainfo.com
	 * @param args 2018年6月20日
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		if (args.length == 1) {
			JptUTSourceModify cJptUTSourceModify = new JptUTSourceModify();
			cJptUTSourceModify.doWork(args[0]);
		} else {
			logger.warn("Error Input");
		}
	}

	/**
	 * 
	 * @category 复制文件目录
	 * @author 吴平福
	 * @param src
	 * @param des
	 * @throws Exception update 2017年6月18日
	 */
	public void copyDir(String src, String des) throws Exception {
		try {
			logger.trace("src=" + src);
			//logger.debug("des=" + des);
			File file1 = new File(src);
			File[] fs = file1.listFiles();
			File file2 = new File(des);
			if (!file2.exists()) {
				file2.mkdirs();
			}
			if (!file1.exists()) {
				return;
			}
			for (File f : fs) {
				if (f.isFile()) {
					// fileCopy(f.getPath(),des+"\\"+f.getName()); //调用文件拷贝的方法
					AiFileUtil.copyFile(des + java.io.File.separator + f.getName(), f.getPath());
				} else if (f.isDirectory()) {
					copyDir(f.getPath(), des + java.io.File.separator + f.getName());
				}
			}
		} catch (Exception ex) {
			// TODO: handle exception
			logger.error("src=" + src);
			logger.error("des=" + des);
			throw ex;
		}

	}

	/**
	 * 
	 * @param strPrjPath
	 */
	public void doWork(String strPrjPath) {
		long start = System.currentTimeMillis();

		try {

			// copy tests
			//String newJavaTestsPath = strPomFilePath + AiTestConst.FileSep + AiTestConst.TEST_SRC;
			String newJavaTestsPath = JpfMvnUtil.getTestPath(strPrjPath);
			logger.debug(newJavaTestsPath);

			String oldJavaTestsPath = strPrjPath + java.io.File.separator + "evosuite-tests";
			// logger.debug(oldJavaTestsPath);
			copyDir(oldJavaTestsPath, newJavaTestsPath);

			oldJavaTestsPath = strPrjPath + java.io.File.separator + AutConst.AITEST_PATH;
			copyDir(oldJavaTestsPath, newJavaTestsPath);

			delTmpPath(strPrjPath + java.io.File.separator + "evosuite-report");
			delTmpPath(strPrjPath + java.io.File.separator + "evosuite-tests");
			delTmpPath(strPrjPath + java.io.File.separator + ".evosuite");
			//delTmpPath(strPrjPath + AiTestConst.FileSep + AiTestConst.AITEST_PATH);

			Vector<String> vFiles = new Vector<String>();

			AiFileUtil.getFiles(newJavaTestsPath, vFiles, ".java");
			logger.info("find java files count:"+vFiles.size());
			for (int i = 0; i < vFiles.size(); i++) {
				String strFileName = vFiles.get(i).trim();
				String strNewFileName = strFileName;
				// logger.debug(strFileName);
				if (strFileName.endsWith("_scaffolding.java")) {
					AiFileUtil.delFile(strFileName);
					continue;
				}
				if (strFileName.endsWith("TestAll.java")) {
					AiFileUtil.delFile(strFileName);
					continue;
				}

				if (strFileName.indexOf(".evosuite") > 0) {
					AiFileUtil.delFile(strFileName);
					delTmpPath(AiFileUtil.getFilePath(strFileName));
					continue;
				}
				if (strFileName.indexOf(".evosuite-tests") > 0) {
					AiFileUtil.delFile(strFileName);
					delTmpPath(AiFileUtil.getFilePath(strFileName));
					continue;
				}

				if (strFileName.endsWith("_ESTest.java")) {
					strNewFileName = strFileName.replaceAll("_ESTest.java", "Test.java");
					AiFileUtil.Rename(strFileName, strNewFileName);
					//continue;
				}

				if (strNewFileName.endsWith("Test.java")) {
					changeUtFile(strNewFileName);
					delNoMethodUtFile(strNewFileName);
					continue;
				}

			}

		} catch (Exception ex) {
			logger.error(ex);
			ex.printStackTrace();
		}
		logger.info("ExcuteTime " + (System.currentTimeMillis() - start) + "ms");
		logger.info("Modify Files: " + iModifyFileCount);
	}

	/**
	 * 
	 * @param strUTFileName
	 */
	protected void changeUtFile(final String strUTFileName) {

		logger.debug(strUTFileName);

		if (null == strUTFileName || 0 == strUTFileName.trim().length()) {
			return;
		}
		File f = new File(strUTFileName);
		StringBuilder sb = new StringBuilder();

		FileInputStream in;
		BufferedReader br = null;
		boolean bDelete = false;
		try {
			in = new FileInputStream(f);
			br = new BufferedReader(new InputStreamReader(in, "UTF-8"));

			String line = null;
			String line_separator = System.getProperty("line.separator");
			while ((line = br.readLine()) != null) {

				// EvoSuite did not generate any tests
				if (line.indexOf("EvoSuite did not generate any tests") > 0) {
					bDelete = true;
					break;
				}
				if (line.endsWith("by EvoSuite")) {
					sb.append(line.replace("by EvoSuite", "by wupf@asiainfo.com")).append(line_separator);
					continue;
				}
				/*
				 * if (line.startsWith("import org.evosuite")) { continue; }
				 */
				if (line.trim().startsWith("@RunWith(EvoRunner.class)")) {
					continue;
				}
				if (line.indexOf("org.evosuite.runtime.EvoRunner;") >= 0) {
					continue;
				}
				if (line.indexOf("org.evosuite.runtime.EvoRunnerParameters;") >= 0) {
					continue;
				}
				if (line.trim().equalsIgnoreCase("import org.junit.runner.RunWith;")) {
					continue;
				}
				//

				// AiTest AiTest AiTestSystem.setCurrentTimeMillis((-420L));
				// adcloud-common/common/src/test/java/com/asiainfo/util/DateConvertUtilsTest.java
				if (line.indexOf("org.evosuite.runtime.System;") >= 0) {
					sb.append(line.replaceAll("org.evosuite.runtime.System;", "org.jpf.aitest.runtime.AiTestSystem;"))
							.append(line_separator);
					continue;
				}
				if (line.indexOf("org.evosuite") >= 0) {
					sb.append(line.replaceAll("org.evosuite", "org.jpf.aitest")).append(line_separator);
					continue;
				}

				if (line.indexOf(" System.setCurrentTimeMillis") >= 0) {
					sb.append(line.replaceAll(" System.setCurrentTimeMillis", " AiTestSystem.setCurrentTimeMillis"))
							.append(line_separator);
					continue;
				}
				/*
				 * if (line.indexOf("@Test(timeout = 4000)") >= 0) {
				 * sb.append(line.replaceAll("@Test(timeout = 4000)", " @Test(timeout = 1000)"))
				 * .append(line_separator); continue; }
				 */
				if (line.indexOf("CodePro") >= 0) {
					sb.append(line.replaceAll("CodePro", " wupf@asiainfo.com")).append(line_separator);
					continue;
				}
				/*
				if (line.trim().equalsIgnoreCase("@Test")) {
					sb.append(line.replaceAll("@Test", " @Test(timeout=1000)")).append(line_separator);
					continue;
				}
				*/
				if (line.trim().startsWith("@Test")) {
					sb.append("    @Test(timeout=1000)").append(line_separator);
					continue;
				}
				
				if (line.startsWith("public class")) {
					line = line.replace("_ESTest", "Test");
					int iPos = line.indexOf("extends");
					if (iPos > 0) {
						line = line.substring(0, iPos - 1).trim() + " {";
					}
					sb.append(line).append(line_separator);
					continue;
				}
				sb.append(line).append(line_separator);

			}
			
			
			iModifyFileCount++;

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
		try {
			if (bDelete) {
				AiFileUtil.delFile(strUTFileName);
			} else {
				AiFileUtil.saveFile(strUTFileName, sb);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * @category 删除没有源代码的测试文件
	 * @author wupf@asiainfo.com
	 * @param strUTFileName
	 * @return
	 * 2018年6月21日
	 */
	protected boolean delNoSourceUtFile(final String strUTFileName)
	{
		
		return false;
	}
	
	/**
	 * @category 查找到项目根节点
	 * @author wupf@asiainfo.com
	 * @param strUTFileName
	 * @return
	 * 2018年6月21日
	 */
	protected String findParent(final String strUTFileName)
	{
		return "";
	}
	/**
	 * @category 删除没有方法的类
	 * @author wupf@asiainfo.com
	 * @param strUTFileName
	 * 2018年6月21日
	 */
	protected void delNoMethodUtFile(final String strUTFileName) {

		try {
			if (!AiFileUtil.FileExist(strUTFileName)) {
				return;
			}
			CompilationUnit cCompilationUnit = ParseJavaByJdt.getInstance().parseJavaSourceFile17(strUTFileName,GenerateInputParam.JAVA_ENCODE);

			List types = cCompilationUnit.types();
			if (types.size() == 0) {
				logger.warn("type=null:" + strUTFileName);
				return;
			}
			TypeDeclaration typeDec = (TypeDeclaration) types.get(0);

			if (typeDec.getModifiers() == AutConst.CLASS_TYPE_ABSTRACT) {
				// abstract class
				logger.info("抽象类不能生产单元测试：" + strUTFileName);
				return;
			}
			if (typeDec.isInterface()) {
				logger.info("接口类单元测试：" + strUTFileName);
				return;
			}

			// show methods
			MethodDeclaration methodDec[] = typeDec.getMethods();
			logger.debug(strUTFileName + ":" + methodDec.length);
			if (methodDec.length == 0) {
				AiFileUtil.delFile(strUTFileName);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	protected void delTmpPath(String strDelPath) {
		try {
			logger.trace(strDelPath);
			AiFileUtil.delDirWithFiles(strDelPath);

		} catch (Exception ex) {

		}
	}
}
