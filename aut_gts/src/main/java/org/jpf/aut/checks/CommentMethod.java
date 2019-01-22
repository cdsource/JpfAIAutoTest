/**
 * copyrigth by wupf@asiainfo.com
 * 2018年8月21日
 */
package org.jpf.aut.checks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jpf.aut.base.GenerateInputParam;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.jpf.utils.classes.ParseJavaSourceFile;
import org.jpf.utils.ios.AiFileUtil;
import org.jpf.aut.base.RunResult;
import org.jpf.aut.formats.FormatCode;


/**
 * @author wupf@asiainfo.com
 *
 */
public class CommentMethod {
	private static final Logger logger = LogManager.getLogger();

	/**
	 * 
	 */
	public CommentMethod() {
		// TODO Auto-generated constructor stub
		List<String> mList = new ArrayList<String>();
		mList.add("test_add_R3");
		mList.add("test_add_R4");
		String strFileName = "D:\\jworkspaces\\JpfUnitTest2\\test\\org\\aitest\\sample\\SampleNLPWTest.java";
		doCommentMethod(strFileName, mList);
	}

	/**
	 * 
	 * @author wupf@asiainfo.com
	 * @param strJavaTestFileName
	 * @param RowNum              2018年8月21日
	 */
	public static void commentRow(String strJavaTestFileName, int RowNum) {
		try {
			String strJavaFileText = AiFileUtil.getFileTxt(strJavaTestFileName, GenerateInputParam.JAVA_ENCODE);
			if (strJavaFileText.indexOf("wupf@asiainfo.com") == -1) {
				logger.warn("not in control test file,check it yourself:" + strJavaTestFileName);
				return;
			}
			String[] JavaFileTexts = strJavaFileText.split(System.getProperty("line.separator"));
			logger.debug(JavaFileTexts[RowNum - 1]);
			JavaFileTexts[RowNum - 1] = "//" + JavaFileTexts[RowNum - 1];
			strJavaFileText = "";
			for (int i = 0; i < JavaFileTexts.length; i++) {
				strJavaFileText += JavaFileTexts[i] + System.getProperty("line.separator");
			}
			// strJavaFileText = FormatCode.format(strJavaFileText);
			AiFileUtil.saveFile(strJavaTestFileName, strJavaFileText, GenerateInputParam.JAVA_ENCODE);
		} catch (Exception ex) {
			ex.printStackTrace();
			try {
				AiFileUtil.delFile(strJavaTestFileName);
			} catch (Exception ex2) {
			}
		}

	}

	/**
	 * 
	 * @author wupf@asiainfo.com
	 * @param strJavaTestFileName
	 * @param listRowNum          2018年8月22日
	 */
	public static void commentRow(String strJavaTestFileName, Map<Integer, String> mapRowNum) {
		try {

			if (!AiFileUtil.FileExist(strJavaTestFileName)) {
				return;
			}

			String strJavaFileText = AiFileUtil.getFileTxt(strJavaTestFileName, GenerateInputParam.JAVA_ENCODE);
			if (strJavaFileText.indexOf("wupf@asiainfo.com") == -1) {
				logger.warn("not in control test file,check it yourself:" + strJavaTestFileName);
				return;
			}
			RunResult.addiCommentCountFile();
			String[] JavaFileTexts = strJavaFileText.split(System.getProperty("line.separator"));

			for (Map.Entry<Integer, String> entry : mapRowNum.entrySet()) {
				//logger.debug(entry.getKey() + ":" + entry.getValue());
				int lRowNum = entry.getKey();
				String strErrInfo = entry.getValue();
				logger.debug(lRowNum + ": " + JavaFileTexts[lRowNum - 1]);
				/*
				if (strErrInfo.equalsIgnoreCase("java.lang.AssertionError")) {
					if (JavaFileTexts[lRowNum - 1].indexOf("assertNull") > 0) {
						JavaFileTexts[lRowNum - 1] = JavaFileTexts[lRowNum - 1].replaceAll("assertNull",
								"assertNotNull");
					} else if (JavaFileTexts[lRowNum - 1].indexOf("assertFalse") > 0) {
						JavaFileTexts[lRowNum - 1] = JavaFileTexts[lRowNum - 1].replaceAll("assertFalse", "assertTrue");
					} else if (JavaFileTexts[lRowNum - 1].indexOf("assertTrue") > 0) {
						JavaFileTexts[lRowNum - 1] = JavaFileTexts[lRowNum - 1].replaceAll("assertTrue", "assertFalse");
					}else if (JavaFileTexts[lRowNum - 1].indexOf("assertEquals")>0) {
						JavaFileTexts[lRowNum - 1] = JavaFileTexts[lRowNum - 1].replaceAll("assertEquals", "assertNotEquals");
					} else {
						// 注释
						if (!JavaFileTexts[lRowNum - 1].trim().startsWith("//")) {
							JavaFileTexts[lRowNum - 1] = "//" + JavaFileTexts[lRowNum - 1];
						}
					}
				} else if (strErrInfo.equalsIgnoreCase("org.junit.ComparisonFailure")) {
					// 修改
					if (JavaFileTexts[lRowNum - 1].indexOf("assertEquals") > 0) {
						JavaFileTexts[lRowNum - 1] = JavaFileTexts[lRowNum - 1].replaceAll("assertEquals",
								"assertNotEquals");
					} else if (JavaFileTexts[lRowNum - 1].indexOf("assertNotEquals") > 0) {
						JavaFileTexts[lRowNum - 1] = JavaFileTexts[lRowNum - 1].replaceAll("assertNotEquals",
								"assertEquals");
					}
				}
				*/	
				// 注释
				if (!JavaFileTexts[lRowNum - 1].trim().startsWith("//")) {
					JavaFileTexts[lRowNum - 1] = "//" + JavaFileTexts[lRowNum - 1];
				}
				//logger.debug(lRowNum + ": " + JavaFileTexts[lRowNum - 1]);

				RunResult.addiCommentCountRow();

			}

			strJavaFileText = "";
			for (int i = 0; i < JavaFileTexts.length; i++) {
				strJavaFileText += JavaFileTexts[i] + System.getProperty("line.separator");
			}
			// strJavaFileText = FormatCode.format(strJavaFileText);
			AiFileUtil.saveFile(strJavaTestFileName, strJavaFileText, GenerateInputParam.JAVA_ENCODE);
		} catch (Exception ex) {
			ex.printStackTrace();
			try {
				AiFileUtil.delFile(strJavaTestFileName);
			} catch (Exception ex2) {
			}
		}

	}

	/**
	 * 
	 * @author wupf@asiainfo.com
	 * @param strJavaTestFileName
	 * @param listRowNum          2018年8月22日
	 */
	public static void commentRow(String strJavaTestFileName, List<Integer> listRowNum) {
		try {

			if (!AiFileUtil.FileExist(strJavaTestFileName)) {
				return;
			}

			String strJavaFileText = AiFileUtil.getFileTxt(strJavaTestFileName, GenerateInputParam.JAVA_ENCODE);
			if (strJavaFileText.indexOf("wupf@asiainfo.com") == -1) {
				logger.warn("not in control test file,check it yourself:" + strJavaTestFileName);
				return;
			}
			RunResult.addiCommentCountFile();
			String[] JavaFileTexts = strJavaFileText.split(System.getProperty("line.separator"));

			for (int i = 0; i < listRowNum.size(); i++) {
				int lRowNum = listRowNum.get(i);
				logger.debug(lRowNum + ": " + JavaFileTexts[lRowNum - 1]);
				if (!JavaFileTexts[lRowNum - 1].trim().startsWith("//")) {
					JavaFileTexts[lRowNum - 1] = "//" + JavaFileTexts[lRowNum - 1];

				}
				RunResult.addiCommentCountRow();
			}

			strJavaFileText = "";
			for (int i = 0; i < JavaFileTexts.length; i++) {
				strJavaFileText += JavaFileTexts[i] + System.getProperty("line.separator");
			}
			// strJavaFileText = FormatCode.format(strJavaFileText);
			AiFileUtil.saveFile(strJavaTestFileName, strJavaFileText, GenerateInputParam.JAVA_ENCODE);
		} catch (Exception ex) {
			ex.printStackTrace();
			try {
				AiFileUtil.delFile(strJavaTestFileName);
			} catch (Exception ex2) {
			}
		}

	}

	/**
	 * 
	 * @author wupf@asiainfo.com
	 * @param strFileName
	 * @param listMethodName 2018年8月21日
	 */
	public void doCommentMethod(String strFileName, List<String> listMethodName) {
		try {
			// format code
			String strJavaFileText = AiFileUtil.getFileTxt(strFileName, GenerateInputParam.JAVA_ENCODE);
			if (strJavaFileText.indexOf("wupf@asiainfo.com") == -1) {
				logger.warn("not in control test file,check it yourself:" + strFileName);
				return;
			}
			strJavaFileText = FormatCode.format(strJavaFileText);
			AiFileUtil.saveFile(strFileName, strJavaFileText, GenerateInputParam.JAVA_ENCODE);

			String[] JavaFileTexts = strJavaFileText.split(System.getProperty("line.separator"));
			logger.info(JavaFileTexts.length);
			for (int i = 0; i < listMethodName.size(); i++) {
				String strMethodName = listMethodName.get(i);
				logger.info(strMethodName);

				// get method pos
				CompilationUnit cu = ParseJavaSourceFile.getInstance().parseJavaSourceFile18(strFileName, "GBK");

				List types = cu.types();
				TypeDeclaration typeDec = (TypeDeclaration) types.get(0);
				logger.info("classname=" + typeDec.getName());

				// show methods
				MethodDeclaration methodDec[] = typeDec.getMethods();
				Map<String, String> mapMethodBody = new HashMap<String, String>();
				for (MethodDeclaration method : methodDec) {
					SimpleName methodName = method.getName();
					logger.info(methodName);
					if (methodName.toString().equalsIgnoreCase(strMethodName)) {
						Block mBlock = method.getBody();
						// logger.info(mBlock.getStartPosition());
						// logger.info(mBlock.getLength());
						logger.info(cu.getLineNumber(mBlock.getStartPosition()));
						logger.info(cu.getLineNumber(mBlock.getStartPosition() + mBlock.getLength()));

						// strJavaFileText = FormatCode.format(strJavaFileText);
						for (int iNum = cu.getLineNumber(mBlock.getStartPosition()); iNum < cu
								.getLineNumber(mBlock.getStartPosition() + mBlock.getLength()) - 1; iNum++) {
							if (!JavaFileTexts[iNum].trim().startsWith("//")) {
								JavaFileTexts[iNum] = "//" + JavaFileTexts[iNum];
							}
						}
					}
				}
				// rewrite java file
			}
			strJavaFileText = "";
			for (int i = 0; i < JavaFileTexts.length; i++) {
				strJavaFileText += JavaFileTexts[i] + System.getProperty("line.separator");
			}
			strJavaFileText = FormatCode.format(strJavaFileText);
			AiFileUtil.saveFile(strFileName, strJavaFileText, GenerateInputParam.JAVA_ENCODE);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * @author wupf@asiainfo.com
	 * @param args 2018年8月21日
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		CommentMethod cCommentMethod = new CommentMethod();
	}

}
