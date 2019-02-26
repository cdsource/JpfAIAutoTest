/**
 * copyrigth by wupf@asiainfo.com
 * 2018年6月14日
 */
package org.jpf.aut.checks;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.jpf.aut.common.consts.AutConst;
import org.jpf.aut.base.GenerateInputParam;
import org.jpf.aut.gts.gtm.MethodParamBody;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jdt.core.dom.AbstractTypeDeclaration;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.EnumDeclaration;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.jpf.utils.MatchUtil;
import org.jpf.utils.classes.ParseJavaByJdt;

import org.jpf.aut.base.RunResult;
import org.jpf.utils.ios.AiFileUtil;

/**
 * @author wupf@asiainfo.com
 *
 */
public class SourceCheck {

	private static final Logger logger = LogManager.getLogger();

	private StringBuffer sb = new StringBuffer();

	private Map<String, String> mapParams = new HashMap<String, String>();

	/**
	 * 
	 */
	public SourceCheck() {

	}

	/**
	 * 
	 * @author wupf@asiainfo.com
	 * @param sourceFileName
	 * @return 2018年6月21日
	 */
	public int doCheckUtExist(final String sourceFileName) {
		int iTestCount = 0;
		try {
			String strUtFileName = sourceFileName.replaceAll("main", "test");
			logger.debug(strUtFileName);
			String baseDirName = AiFileUtil.getFilePath(strUtFileName);
			File baseDir = new File(baseDirName); // 创建一个File对象
			String tempName = null;
			File tempFile;
			File[] files = baseDir.listFiles();
			if (files != null) {
				strUtFileName = AiFileUtil.getFileName(strUtFileName).replaceAll("\\.java", ".?Test[0-9]*\\.java");
				//logger.debug(strUtFileName);
				for (int i = 0; i < files.length; i++) {
					tempFile = files[i];
					if (tempFile.isFile()) {
						tempName = tempFile.getName();
						// logger.info(tempName);
						// logger.info(strUtFileName);
						if (MatchUtil.wildcardMatch2(strUtFileName, tempName)) {
							// 匹配成功，将文件名添加到结果集
							logger.info(tempFile.getAbsoluteFile());
							iTestCount++;
						}
					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return iTestCount;
	}

	/**
	 * 
	 * @author wupf@asiainfo.com
	 * @param sourceFileName 2018年6月14日
	 */
	private String doCheck(String sourceFileName) {
		String strExistMain = "";
		try {
			RunResult.iPrivateMethodCount = 0;
			RunResult.iPublicMethodCount = 0;
			RunResult.iNoParamMethodCount = 0;

			CompilationUnit cCompilationUnit = ParseJavaByJdt.getInstance().parseJavaSourceFile17(sourceFileName,GenerateInputParam.JAVA_ENCODE);


			
			List types = cCompilationUnit.types();
			if (types.size() == 0) {
				logger.warn("type=null:" + sourceFileName);
				return "type=null";
			}
			
			AbstractTypeDeclaration cAbstractTypeDeclaration= (AbstractTypeDeclaration)types.get(0);
			
			if (cAbstractTypeDeclaration instanceof EnumDeclaration)
			{
				logger.info("不支持枚举类生产单元测试：" + sourceFileName);
				RunResult.EnumFileCount++;
				return "";
			}
			
			if (!(cAbstractTypeDeclaration instanceof TypeDeclaration))
			{
				return "";
			}
			TypeDeclaration typeDec = (TypeDeclaration) types.get(0);
			logger.info("classname=" + typeDec.getName());
			//logger.debug("typeDec.getModifiers()=" + typeDec.getModifiers());

			if (typeDec.getModifiers() == AutConst.CLASS_TYPE_ABSTRACT) {
				// abstract class
				logger.info("抽象类不能生产单元测试：" + sourceFileName);
				RunResult.AbstractFileCount++;
				return "抽象类";
			}
			if (typeDec.isInterface()) {
				logger.info("接口类单元测试：" + sourceFileName);
				RunResult.InterfaceFileCount++;
				return "接口类";
			}

			// show methods
			MethodDeclaration methodDec[] = typeDec.getMethods();

			for (MethodDeclaration method : methodDec) {

				SimpleName methodName = method.getName();
				if (methodName.toString().equalsIgnoreCase("main")) {
					strExistMain = " main:" + cCompilationUnit.getLineNumber(method.getBody().getStartPosition()) + "--"
							+ cCompilationUnit.getLineNumber(method.getBody().getLength());
					// continue;
				}
				if (2 == method.getModifiers() || 10 == method.getModifiers()) {
					// private private static
					RunResult.iPrivateMethodCount++;
				} else {
					RunResult.iPublicMethodCount++;
				}

				Type returnType = method.getReturnType2();
				if (returnType == null) {
					// 构造函数
				} else {
					if (0 == method.parameters().size()) {
						RunResult.iNoParamMethodCount++;
					}

				}
				for (int i = 0; i < method.parameters().size(); i++) {
					MethodParamBody cParamInitBody = new MethodParamBody(method.parameters().get(i).toString());
					long iCount = 1;
					if (mapParams.get(cParamInitBody.getParamType()) != null) {
						iCount = Long.parseLong((String) mapParams.get(cParamInitBody.getParamType())) + 1;
					}
					mapParams.put(cParamInitBody.getParamType(), String.valueOf(iCount));
				}
			}

		} catch (Exception ex) {
			logger.error(ex);
			ex.printStackTrace();
		}
		return RunResult.iPrivateMethodCount + " " + RunResult.iPublicMethodCount + " " + RunResult.iNoParamMethodCount
				+ strExistMain;
	}

	/**
	 * 
	 * @author wupf@asiainfo.com
	 * @param strPomFath 2018年6月14日
	 */
	public void doWork(String strPomFath) {
		try {
			Vector<String> vFiles = new Vector<String>();
			AiFileUtil.getFiles(strPomFath, vFiles, ".java");
			// remove test file
			CheckUtils.checkForSource(vFiles);

			RunResult.TotalJavaSrcFileCount = vFiles.size();
			logger.info(vFiles.size());
			sb.append("文件名").append(" ").append("对应单元测试数量").append(" ").append("私有方法 公有方法 无参方法").append("\r\n");
			while (vFiles.size() > 0) {
				String strFileName = vFiles.get(vFiles.size() - 1);
				//logger.info(strFileName);

				// cGenerateTests.doGenerateFile(vFiles.get(vFiles.size() - 1));
				sb.append(strFileName).append(" ").append(doCheckUtExist(strFileName)).append(" ")
						.append(doCheck(strFileName)).append("\r\n");
				vFiles.remove(vFiles.size() - 1);
				logger.info("exist files count:" + vFiles.size());
			}

		} catch (Exception ex) {
			logger.error(ex);
			ex.printStackTrace();
		} finally {
			logger.info("handle files " + RunResult.TotalJavaSrcFileCount);
			logger.info("abstract files " + RunResult.AbstractFileCount);
			logger.info("interface files " + RunResult.InterfaceFileCount);
		}
		logger.info(sb.toString());
		for (String key : mapParams.keySet()) {
			System.out.println(key + ":" + mapParams.get(key));
		}
	}

	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		if (args.length == 1) {
			SourceCheck cSourceCheck = new SourceCheck();
			cSourceCheck.doWork(args[0]);
		}

		logger.info("ExcuteTime " + (System.currentTimeMillis() - start) + "ms");
	}

}
