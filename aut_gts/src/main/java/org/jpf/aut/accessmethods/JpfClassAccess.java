/**
 * copyrigth by wupf@asiainfo.com
 * 2018年8月24日
 */
package org.jpf.aut.accessmethods;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.jpf.aut.base.GenerateInputParam;
import org.jpf.aut.formats.FormatCode;
import org.jpf.utils.classes.ParseJavaSourceFile;
import org.jpf.utils.ios.AiFileUtil;
import org.jpf.utils.ios.WuFileUtil;
import org.jpf.utils.mavens.JpfMvnUtil;



/**
 * @author wupf@asiainfo.com
 *
 */
public class JpfClassAccess {
	private static final Logger logger = LogManager.getLogger();

	/**
	 * 
	 */
	public JpfClassAccess(String strPomFilePath) {
		// TODO Auto-generated constructor stub
		try {
			// copy src/main/java

			String oldJavaPath = JpfMvnUtil.getSrcPath(strPomFilePath);
			String newJavaPath = JpfMvnUtil.getSrcTmpPath(strPomFilePath);
			WuFileUtil.copyDir(oldJavaPath, newJavaPath);

			Vector<String > vFile=new Vector<>();
			
			WuFileUtil.getFiles(oldJavaPath, vFile, ".java");
			
			for(int i=0;i<vFile.size();i++)
			{
				logger.info("Checking file:"+vFile.get(i));
				ChangeMethodAccess(vFile.get(i));
				//ChangeMethodAccess("D:\\jworkspaces\\JpfUnitTest2\\src\\org\\aitest\\sample\\SampleAcces.java");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		MethodAccessResult.printResult();
	}

	/**
	 * 
	 * @author wupf@asiainfo.com
	 * @param strFileName
	 * @param listMethodName 2018年8月21日
	 */
	public void ChangeMethodAccess(String strFileName) {
		try {
			// format code
			String strJavaFileText = AiFileUtil.getFileTxt(strFileName, GenerateInputParam.JAVA_ENCODE);

			strJavaFileText = FormatCode.format(strJavaFileText);
			AiFileUtil.saveFile(strFileName, strJavaFileText, GenerateInputParam.JAVA_ENCODE);

			String[] JavaFileTexts = strJavaFileText.split(System.getProperty("line.separator"));
			logger.info("Line count:" + JavaFileTexts.length);

			// get method pos
			CompilationUnit cu = ParseJavaSourceFile.getInstance().parseJavaSourceFile18(strFileName,"GBK");

			List types = cu.types();
			TypeDeclaration typeDec = (TypeDeclaration) types.get(0);
			logger.info("classname=" + typeDec.getName());

			// show methods
			MethodDeclaration methodDec[] = typeDec.getMethods();
			Map<String, String> mapMethodBody = new HashMap<String, String>();
			for (MethodDeclaration method : methodDec) {
				SimpleName methodName = method.getName();
				logger.info("methodName:" + methodName);
				if (method.getModifiers() == 2) {
					Block mBlock = method.getBody();
					// logger.info(mBlock.getStartPosition());
					// logger.info(mBlock.getLength());
					int iMethodStartRow = cu.getLineNumber(mBlock.getStartPosition());
					logger.info("start line:" + iMethodStartRow);
					logger.info("start line:" + JavaFileTexts[iMethodStartRow - 1]);
					JavaFileTexts[iMethodStartRow - 1] = JavaFileTexts[iMethodStartRow - 1].replaceFirst("private",
							"protected");
					MethodAccessResult.addPrivateMethodCount();
					logger.info("start line:" + JavaFileTexts[iMethodStartRow - 1]);
					logger.info("end line:" + cu.getLineNumber(mBlock.getStartPosition() + mBlock.getLength()));

				}
			}
			// rewrite java file
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
	 * @param args 2018年8月24日
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		if (1 == args.length) {
			JpfClassAccess cJpfClassAccess = new JpfClassAccess(args[0]);
		} else {
			logger.warn("error input param");
		}
	}

}
