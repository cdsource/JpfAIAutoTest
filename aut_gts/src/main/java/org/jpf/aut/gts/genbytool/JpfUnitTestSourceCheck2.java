/**
 * copyrigth by wupf@
 * 2018年11月29日
 */
package org.jpf.aut.gts.genbytool;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Vector;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.jpf.aut.base.GenerateInputParam;
import org.jpf.aut.base.RunResult;
import org.jpf.aut.common.consts.AutConst;
import org.jpf.utils.classes.ParseJavaByJdt;
import org.jpf.utils.ios.JpfFileUtil;
import org.jpf.utils.ios.JpfFileUtil;
import org.jpf.utils.mavens.JpfMvnUtil;

/**
 * @author wupf@asiainfo.com
 *
 */
public class JpfUnitTestSourceCheck2 {

	private static final Logger logger = LogManager.getLogger();

	int iFileCount = 0;

	/**
	 * 
	 */
	public JpfUnitTestSourceCheck2() {
		// TODO Auto-generated constructor stub

	}

	/**
	 * @author wupf@asiainfo.com
	 * @param args 2018年6月20日
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		if (args.length == 1) {
			JpfUnitTestSourceCheck2 cJpfUnitTestSourceCheck = new JpfUnitTestSourceCheck2();
			cJpfUnitTestSourceCheck.doWork(args[0]);
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
			// logger.debug("des=" + des);
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
					JpfFileUtil.copyFile(des + java.io.File.separator + f.getName(), f.getPath());
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
	 * @param strPomFilePath
	 */
	public void doWork(String strPomFilePath) {
		long start = System.currentTimeMillis();

		try {

			// copy tests
			// String newJavaTestsPath = strPomFilePath + AiTestConst.FileSep +
			// AiTestConst.TEST_SRC;
			String newJavaTestsPath = JpfMvnUtil.getTestPath(strPomFilePath);
			logger.debug(newJavaTestsPath);

			String oldJavaTestsPath = strPomFilePath + java.io.File.separator +AutConst.AITEST_PATH;
			// logger.debug(oldJavaTestsPath);
			copyDir(oldJavaTestsPath, newJavaTestsPath);

			JpfFileUtil.delDirWithFiles(strPomFilePath + java.io.File.separator + "evosuite-report");
			JpfFileUtil.delDirWithFiles(strPomFilePath + java.io.File.separator + "evosuite-tests");
			JpfFileUtil.delDirWithFiles(strPomFilePath + java.io.File.separator + ".evosuite");
			// delTmpPath(strPrjPath + AiTestConst.FileSep + AiTestConst.AITEST_PATH);

			Vector<String> vFiles = new Vector<String>();

			JpfFileUtil.getFiles(newJavaTestsPath, vFiles, ".java");
			logger.info("find java files count:" + vFiles.size());
			logger.info(AutConst.AITEST_HC_Suffix+".java");
			for (int i = 0; i < vFiles.size(); i++) {
				String strFileName = vFiles.get(i).trim();
				String strNewFileName = strFileName;
				logger.info(strFileName);
				if (strFileName.endsWith("_scaffolding.java")) {
					JpfFileUtil.delFile(strFileName);
					continue;
				}
				if (strFileName.endsWith("TestAll.java")) {
					JpfFileUtil.delFile(strFileName);
					continue;
				}

				if (strFileName.indexOf(".evosuite") > 0) {
					JpfFileUtil.delFile(strFileName);
					JpfFileUtil.delDirWithFiles(JpfFileUtil.getFilePath(strFileName));
					continue;
				}
				if (strFileName.indexOf(".evosuite-tests") > 0) {
					JpfFileUtil.delFile(strFileName);
					JpfFileUtil.delDirWithFiles(JpfFileUtil.getFilePath(strFileName));
					continue;
				}
				
				/*
				if (strFileName.endsWith("_ESTest.java")) {
					strNewFileName = strFileName.replaceAll("_ESTest.java", AiTestConst.AITEST_HC_Suffix);
					JpfFileUtil.Rename(strFileName, strNewFileName);
				}
		        */
				
				if (AutConst.checkAutoTestFile(strNewFileName)) {
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
		logger.info("Excute Files: " + iFileCount);
	}


	/**
	 * 
	 * @param strUTFileName
	 */
	protected void changeUtFile(final String strUTFileName) {

		logger.info(strUTFileName);

		if (null == strUTFileName || 0 == strUTFileName.trim().length()) {
			return;
		}
		
		if (AutConst.checkAutoTestFile(strUTFileName)==false)
		{
			return;
		}
		File f = new File(strUTFileName);
		StringBuilder sb = new StringBuilder();

		FileInputStream in;
		BufferedReader br = null;
		boolean bDelete = false;
		try {
			in = new FileInputStream(f);
			br = new BufferedReader(new InputStreamReader(in, GenerateInputParam.JAVA_ENCODE));

			String line = null;
			String line_separator = System.getProperty("line.separator");
			boolean isFirstRow=true;
			while ((line = br.readLine()) != null) {
				if (isFirstRow)
				{
					if (line.trim().startsWith("package "))
					{
						sb.append("/*").append(line_separator)
						  .append(" this file is write by wupf@").append(line_separator)
						  .append(" */").append(line_separator);
					}
					sb.append(line).append(line_separator);
					isFirstRow=false;
					continue;
				}
				// EvoSuite did not generate any tests
				if (line.indexOf("EvoSuite did not generate any tests") > 0) {
					bDelete = true;
					RunResult.GenEmptyUtFileCount++;
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
				/*
				if (line.trim().equalsIgnoreCase("import org.junit.runner.RunWith;")) {
					continue;
				}
				*/
				if (line.trim().startsWith("////")) {
					continue;
				}
				//

				// AiTest AiTest AiTestSystem.setCurrentTimeMillis((-420L));
				// adcloud-common/common/src/test/java/com/asiainfo/util/DateConvertUtilsTest.java
				if (line.indexOf("org.evosuite.runtime.System") >= 0) {
					sb.append(line.replaceAll("org.evosuite.runtime.System", "org.jpf.aut.runtime.System"))
							.append(line_separator);
					continue;
				}
				if (line.indexOf("org.evosuite") >= 0) {
					sb.append(line.replaceAll("org.evosuite", "org.jpf.aut")).append(line_separator);
					continue;
				}
				if (line.indexOf("org.jpf.runtime") >= 0) {
					sb.append(line.replaceAll("org.jpf.runtime", "org.jpf.aut.runtime"))
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
				 * if (line.trim().equalsIgnoreCase("@Test")) {
				 * sb.append(line.replaceAll("@Test",
				 * " @Test(timeout=1000)")).append(line_separator); continue; }
				 */
				if (line.trim().startsWith("@Test")) {
					sb.append("    @Test(timeout=1000)").append(line_separator);
					continue;
				}

				if (line.startsWith("public class")) {
					if (line.indexOf(AutConst.AITEST_HC_TYPE) > 0) {
						//line = line.replace("_ESTest", AiTestConst.AITEST_HC_TYPE);
						int iPos = line.indexOf("extends");
						if (iPos > 0) {
							line = line.substring(0, iPos - 1).trim() + " {";
						}
					}
					sb.append(line).append(line_separator);

					continue;
				}
				sb.append(line).append(line_separator);

			}

			iFileCount++;

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
				JpfFileUtil.delFile(strUTFileName);
			} else {
				JpfFileUtil.saveFile(strUTFileName, sb.toString(), GenerateInputParam.JAVA_ENCODE);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * @category 删除没有源代码的测试文件
	 * @author wupf@asiainfo.com
	 * @param strUTFileName
	 * @return 2018年6月21日
	 */
	protected boolean delNoSourceUtFile(final String strUTFileName) {

		return false;
	}

	/**
	 * @category 查找到项目根节点
	 * @author wupf@asiainfo.com
	 * @param strUTFileName
	 * @return 2018年6月21日
	 */
	protected String findParent(final String strUTFileName) {
		return "";
	}

	/**
	 * @category 删除没有方法的类
	 * @author wupf@asiainfo.com
	 * @param strUTFileName 2018年6月21日
	 */
	protected void delNoMethodUtFile(final String strUTFileName) {

		try {
			if (!JpfFileUtil.FileExist(strUTFileName)) {
				return;
			}
			CompilationUnit cCompilationUnit = ParseJavaByJdt.getInstance().parseJavaSourceFile18(strUTFileName,
					GenerateInputParam.JAVA_ENCODE);

			List types = cCompilationUnit.types();
			if (types.size() == 0) {
				logger.warn("type=null:" + strUTFileName);
				return;
			}
			TypeDeclaration typeDec = (TypeDeclaration) types.get(0);

			if (typeDec.getModifiers() == AutConst.CLASS_TYPE_ABSTRACT) {
				// abstract class
					return;
			}
			if (typeDec.isInterface()) {
				return;
			}

			// show methods
			MethodDeclaration methodDec[] = typeDec.getMethods();
			// logger.debug(strUTFileName + ":" + methodDec.length);
			if (methodDec.length == 0) {
				JpfFileUtil.delFile(strUTFileName);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}




}

