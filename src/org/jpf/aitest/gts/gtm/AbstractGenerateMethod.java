/**
 * @author 吴平福 E-mail:wupf@asiainfo.com
 * @version 创建时间：2017年9月29日 上午11:30:51 类说明
 */

package org.jpf.aitest.gts.gtm;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jpf.aitest.GenerateConst;
import org.jpf.aitest.JpfMethodInfo;
import org.jpf.aitest.JpfUtInfo;
import org.jpf.aitest.JpfUtMethodInfo;
import org.jpf.aitest.RunResult;
import org.jpf.aitest.gts.gtm.genbydb.GenInfoFromDB;
import org.jpf.aitest.gts.gtm.genbyjavadoc.GenInfoFromJavaDoc;
import org.jpf.aitest.gts.gtm.genbylog.GenParamValueFromLogByDb;
import org.jpf.aitest.gts.gtm.genbylog.GenParamValueFromLogByFile;
import org.jpf.aitest.gts.gtm.genbynlps.GenInfoFromNLP;
import org.jpf.aitest.utils.GenerateUtil2;

/**
 * 
 */
public abstract class AbstractGenerateMethod {
	private static final Logger logger = LogManager.getLogger();

	/**
	 * 
	 */
	public AbstractGenerateMethod() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 * @category @author 吴平福
	 * @param strReturn
	 * @param sb        update 2017年9月29日
	 */
	public abstract String addMethodCaller(String strClassName, String strMethod, List MethodParam,
			JpfUtInfo cJpfUtInfo);

	/**
	 * 
	 * @category @author 吴平福
	 * @param strClass
	 * @param MethodParam
	 * @param cUtFileText
	 * @return update 2017年11月14日
	 */
	public abstract String addClassInstance(String strClassName, List MethodParam, JpfUtInfo cJpfUtInfo);

	/**
	 * 
	 * @category 增加额外的公共方法
	 * @author 吴平福
	 * @param cJpfUtInf update 2017年12月9日
	 */
	public abstract String addExtraMethod(String strClassName, String strPackageName);

	/**
	 * 
	 * @category 增加TRY
	 * @author 吴平福
	 * @return update 2017年12月9日
	 */
	public String addTry() {
		return "    try{\n";
	}

	/**
	 * 
	 * @category 增加CATCH
	 * @author 吴平福
	 * @param strClassName
	 * @return update 2017年12月9日
	 */
	public String addCatchException(List MethodExceptions) {
		StringBuffer sb = new StringBuffer();
		if (null != MethodExceptions) {
			for (int i = 0; i < MethodExceptions.size(); i++) {
				if (!MethodExceptions.get(i).toString().equalsIgnoreCase("Exception")) {
					sb.append("    }catch(").append(MethodExceptions.get(i).toString()).append(" ex){\n")
							.append("       WritePrivateException(ex,Thread.currentThread().getStackTrace(),false);\n")
							.append("       ex.printStackTrace();").append("    \n");
				}
			}
		}
		sb.append("    }catch(Exception ex){\n")
				.append("      WritePrivateException(ex,Thread.currentThread().getStackTrace(),true);\n")
				.append("      ex.printStackTrace();\n").append("      fail();\n").append("    }\n");
		return sb.toString();
	}

	/**
	 * 
	 * @category doGenerateMethod
	 * @author 吴平福
	 * @param cMethodInfo
	 * @param cUtFileText
	 * @return update 2017年11月13日
	 */
	public void doGenerateMethod(JpfMethodInfo cMethodInfo, JpfUtInfo cJpfUtInfo) {

		// 调用方法
		StringBuffer sbCallMethod = new StringBuffer();
		sbCallMethod.append(addMethodCaller(cMethodInfo.getClassName(), cMethodInfo.getMethodName(),
				cMethodInfo.getMethodParam(), cJpfUtInfo));
		sbCallMethod.append(GenerateUtil2.addMethodParam2Method(cMethodInfo.getModifiers(),
				cMethodInfo.getMethodParam(), cJpfUtInfo));

		// 方法参数初始化
		if (cMethodInfo.getMethodParam().size() == 0) {
			// 方法无参数
			JpfUtMethodInfo cJpfUtMethodInfo = new JpfUtMethodInfo();
			cJpfUtInfo.setGenType("W");
			cJpfUtMethodInfo.setMethodJavaDoc(GenerateUtil2.addMethodJavaDoc(cMethodInfo, cJpfUtInfo));
			cJpfUtMethodInfo.setMethodDeclare(addMethodDeclare(cMethodInfo.getMethodName(), cJpfUtInfo));
			cJpfUtMethodInfo.setMethodTry(addTry());
			cJpfUtMethodInfo.setClassConstructor(
					addClassInstance(cMethodInfo.getClassName(), cMethodInfo.getMethodParam(), cJpfUtInfo));
			cJpfUtMethodInfo.setMethodReturn(addMethodReturn(cMethodInfo.getMethodName(), cMethodInfo.getStrReturn(),
					cMethodInfo.getMethodParam(), cJpfUtInfo));
			cJpfUtMethodInfo.setMethodCaller(sbCallMethod.toString());
			cJpfUtMethodInfo.setMethodAssert(addMethodAssert(cMethodInfo.getClassName(), cMethodInfo.getMethodName(),
					cMethodInfo.getStrReturn(), cJpfUtInfo));
			cJpfUtMethodInfo.setMethodCatch(addCatchException(cMethodInfo.getMethodExceptions()));
			logger.trace(cJpfUtMethodInfo.toString());
			cJpfUtInfo.getListUtMethodInfos().add(cJpfUtMethodInfo);
		} else {

			// 方法有参数
			// 根据JAVADOC产生
			generateByJavaDoc(cJpfUtInfo, cMethodInfo, sbCallMethod);
			// 随机产生
			generateRandom(cJpfUtInfo, cMethodInfo, sbCallMethod);

			// 根据日志产生
			generateByLog(cJpfUtInfo, cMethodInfo, sbCallMethod);
			// 根据NLP产生
			generateByNLP(cJpfUtInfo, cMethodInfo, sbCallMethod);

			// 根据数据库来源
			generateByDB(cJpfUtInfo, cMethodInfo, sbCallMethod);
			
			generateByLog2(cJpfUtInfo, cMethodInfo, sbCallMethod);
		}

	}

	/**
	 * 
	 * @param cJpfUtInfo
	 * @param cJpfMethodInfo
	 * @param sbCallMethod
	 */
	private void generateByJavaDoc(JpfUtInfo cJpfUtInfo, JpfMethodInfo cJpfMethodInfo, StringBuffer sbCallMethod) {
		// 根据JAVADOC产生
		if (cJpfMethodInfo.getStrJavaDoc() != null && cJpfMethodInfo.getStrJavaDoc().trim().length() > 0) {
			cJpfUtInfo.setGenType("Doc");
			logger.debug("init param by javadoc");

			logger.debug(cJpfMethodInfo.getStrJavaDoc());
			JpfUtMethodInfo cJpfUtMethodInfo = new JpfUtMethodInfo();
			if (GenInfoFromJavaDoc.getInstance().initParamByJavaDoc(cJpfMethodInfo, cJpfUtMethodInfo)) {
				cJpfUtMethodInfo.setMethodJavaDoc(GenerateUtil2.addMethodJavaDoc(cJpfMethodInfo, cJpfUtInfo));
				cJpfUtMethodInfo.setMethodDeclare(addMethodDeclare(cJpfMethodInfo.getMethodName(), cJpfUtInfo));
				cJpfUtMethodInfo.setMethodTry(addTry());
				cJpfUtMethodInfo.setClassConstructor(
						addClassInstance(cJpfMethodInfo.getClassName(), cJpfMethodInfo.getMethodParam(), cJpfUtInfo));
				cJpfUtMethodInfo.setMethodReturn(addMethodReturn(cJpfMethodInfo.getMethodName(),
						cJpfMethodInfo.getStrReturn(), cJpfMethodInfo.getMethodParam(), cJpfUtInfo));
				cJpfUtMethodInfo.setMethodCaller(sbCallMethod.toString());
				cJpfUtMethodInfo.setMethodCatch(addCatchException(cJpfMethodInfo.getMethodExceptions()));
				logger.trace(cJpfUtMethodInfo.toString());
				cJpfUtInfo.getListUtMethodInfos().add(cJpfUtMethodInfo);
			}
		}
	}

	/**
	 * 
	 * @param cJpfUtInfo
	 * @param cJpfMethodInfo
	 * @param sbCallMethod
	 */
	private void generateRandom(JpfUtInfo cJpfUtInfo, JpfMethodInfo cJpfMethodInfo, StringBuffer sbCallMethod) {
		// 随机产生
		cJpfUtInfo.setGenType("R");
		ArrayList<String> cParamInitBody = GenerateUtil2.addMethodParamInit2(cJpfMethodInfo.getMethodParam(),
				cJpfUtInfo, GenerateConst.Max_CaseCount_PerMethod);
		// logger.debug("cParamInitBody.size()=" + cParamInitBody.size());

		for (int i = 0; i < cParamInitBody.size(); i++) {

			JpfUtMethodInfo cJpfUtMethodInfo = new JpfUtMethodInfo();
			cJpfUtMethodInfo.setMethodJavaDoc(GenerateUtil2.addMethodJavaDoc(cJpfMethodInfo, cJpfUtInfo));
			cJpfUtMethodInfo.setMethodDeclare(addMethodDeclare(cJpfMethodInfo.getMethodName(), cJpfUtInfo));
			cJpfUtMethodInfo.setMethodTry(addTry());
			cJpfUtMethodInfo.setClassConstructor(
					addClassInstance(cJpfMethodInfo.getClassName(), cJpfMethodInfo.getMethodParam(), cJpfUtInfo));
			cJpfUtMethodInfo.setMethodParam(cParamInitBody.get(i));
			cJpfUtMethodInfo.setMethodReturn(addMethodReturn(cJpfMethodInfo.getMethodName(),
					cJpfMethodInfo.getStrReturn(), cJpfMethodInfo.getMethodParam(), cJpfUtInfo));
			cJpfUtMethodInfo.setMethodCaller(sbCallMethod.toString());
			cJpfUtMethodInfo.setMethodAssert(addMethodAssert(cJpfMethodInfo.getClassName(),
					cJpfMethodInfo.getMethodName(), cJpfMethodInfo.getStrReturn(), cJpfUtInfo));
			cJpfUtMethodInfo.setMethodCatch(addCatchException(cJpfMethodInfo.getMethodExceptions()));
			logger.trace(cJpfUtMethodInfo.toString());
			cJpfUtInfo.getListUtMethodInfos().add(cJpfUtMethodInfo);
		}
		cParamInitBody.clear();
	}

	/**
	 * 
	 * @param cJpfUtInfo
	 * @param cJpfMethodInfo
	 * @return
	 */
	private String getMethodName(JpfUtInfo cJpfUtInfo, JpfMethodInfo cJpfMethodInfo) {
		return cJpfUtInfo.getSourcePackage() + "." + cJpfMethodInfo.getClassName() + "."
				+ cJpfMethodInfo.getMethodName();
	}

	/**
	 * 
	 * @param cJpfUtInfo
	 * @param cJpfMethodInfo
	 * @param sbCallMethod
	 */
	private void generateByLog2(JpfUtInfo cJpfUtInfo, JpfMethodInfo cJpfMethodInfo, StringBuffer sbCallMethod) {
		// 根据日志产生
		logger.debug(cJpfUtInfo.getSourcePackage());

		String strParamValues = GenParamValueFromLogByFile.getInstance().genParamValue(cJpfUtInfo,
				getMethodName(cJpfUtInfo, cJpfMethodInfo), cJpfMethodInfo);
		if (null != strParamValues && strParamValues.trim().length() > 0) {
			cJpfUtInfo.setGenType("Log2");
			JpfUtMethodInfo cJpfUtMethodInfo = new JpfUtMethodInfo();
			cJpfUtMethodInfo.setMethodJavaDoc(GenerateUtil2.addMethodJavaDoc(cJpfMethodInfo, cJpfUtInfo));
			cJpfUtMethodInfo.setMethodDeclare(addMethodDeclare(cJpfMethodInfo.getMethodName(), cJpfUtInfo));
			cJpfUtMethodInfo.setMethodTry(addTry());
			cJpfUtMethodInfo.setClassConstructor(
					addClassInstance(cJpfMethodInfo.getClassName(), cJpfMethodInfo.getMethodParam(), cJpfUtInfo));
			cJpfUtMethodInfo.setMethodParam(strParamValues);
			cJpfUtMethodInfo.setMethodReturn(addMethodReturn(cJpfMethodInfo.getMethodName(),
					cJpfMethodInfo.getStrReturn(), cJpfMethodInfo.getMethodParam(), cJpfUtInfo));
			cJpfUtMethodInfo.setMethodCaller(sbCallMethod.toString());
			cJpfUtMethodInfo.setMethodAssert(addMethodAssert(cJpfMethodInfo.getClassName(),
					cJpfMethodInfo.getMethodName(), cJpfMethodInfo.getStrReturn(), cJpfUtInfo));
			cJpfUtMethodInfo.setMethodCatch(addCatchException(cJpfMethodInfo.getMethodExceptions()));
			logger.trace(cJpfUtMethodInfo.toString());
			cJpfUtInfo.getListUtMethodInfos().add(cJpfUtMethodInfo);
		}

	}

	/**
	 * 
	 * @param cJpfUtInfo
	 * @param cJpfMethodInfo
	 * @param sbCallMethod
	 */
	private void generateByLog(JpfUtInfo cJpfUtInfo, JpfMethodInfo cJpfMethodInfo, StringBuffer sbCallMethod) {
		// 根据日志产生
		logger.debug(cJpfUtInfo.getSourcePackage());

		String[] strParamValues = GenParamValueFromLogByDb.getInstance().genParamValue(cJpfUtInfo,
				getMethodName(cJpfUtInfo, cJpfMethodInfo), cJpfMethodInfo);
		for (int i = 0; i < strParamValues.length; i++) {
			if (null != strParamValues[i] && strParamValues[i].trim().length() > 0) {
				cJpfUtInfo.setGenType("Log");
				JpfUtMethodInfo cJpfUtMethodInfo = new JpfUtMethodInfo();
				cJpfUtMethodInfo.setMethodJavaDoc(GenerateUtil2.addMethodJavaDoc(cJpfMethodInfo, cJpfUtInfo));
				cJpfUtMethodInfo.setMethodDeclare(addMethodDeclare(cJpfMethodInfo.getMethodName(), cJpfUtInfo));
				cJpfUtMethodInfo.setMethodTry(addTry());
				cJpfUtMethodInfo.setClassConstructor(
						addClassInstance(cJpfMethodInfo.getClassName(), cJpfMethodInfo.getMethodParam(), cJpfUtInfo));
				cJpfUtMethodInfo.setMethodParam(strParamValues[i]);
				cJpfUtMethodInfo.setMethodReturn(addMethodReturn(cJpfMethodInfo.getMethodName(),
						cJpfMethodInfo.getStrReturn(), cJpfMethodInfo.getMethodParam(), cJpfUtInfo));
				cJpfUtMethodInfo.setMethodCaller(sbCallMethod.toString());
				cJpfUtMethodInfo.setMethodAssert(addMethodAssert(cJpfMethodInfo.getClassName(),
						cJpfMethodInfo.getMethodName(), cJpfMethodInfo.getStrReturn(), cJpfUtInfo));
				cJpfUtMethodInfo.setMethodCatch(addCatchException(cJpfMethodInfo.getMethodExceptions()));
				logger.trace(cJpfUtMethodInfo.toString());
				cJpfUtInfo.getListUtMethodInfos().add(cJpfUtMethodInfo);
			}
		}
	}

	/**
	 * @category 根据数据库产生
	 * @param cJpfUtInfo
	 * @param cJpfMethodInfo
	 */
	private void generateByDB(JpfUtInfo cJpfUtInfo, JpfMethodInfo cJpfMethodInfo, StringBuffer sbCallMethod) {
		// 根据数据库产生

		JpfUtMethodInfo cJpfUtMethodInfo = new JpfUtMethodInfo();
		if (GenInfoFromDB.getInstance().initParamByDB(cJpfMethodInfo, cJpfUtMethodInfo)) {
			cJpfUtInfo.setGenType("DB");

			cJpfUtMethodInfo.setMethodJavaDoc(GenerateUtil2.addMethodJavaDoc(cJpfMethodInfo, cJpfUtInfo));
			cJpfUtMethodInfo.setMethodDeclare(addMethodDeclare(cJpfMethodInfo.getMethodName(), cJpfUtInfo));
			cJpfUtMethodInfo.setMethodTry(addTry());
			cJpfUtMethodInfo.setClassConstructor(
					addClassInstance(cJpfMethodInfo.getClassName(), cJpfMethodInfo.getMethodParam(), cJpfUtInfo));

			cJpfUtMethodInfo.setMethodReturn(addMethodReturn(cJpfMethodInfo.getMethodName(),
					cJpfMethodInfo.getStrReturn(), cJpfMethodInfo.getMethodParam(), cJpfUtInfo));
			cJpfUtMethodInfo.setMethodCaller(sbCallMethod.toString());
			cJpfUtMethodInfo.setMethodAssert(addMethodAssert(cJpfMethodInfo.getClassName(),
					cJpfMethodInfo.getMethodName(), cJpfMethodInfo.getStrReturn(), cJpfUtInfo));
			cJpfUtMethodInfo.setMethodCatch(addCatchException(cJpfMethodInfo.getMethodExceptions()));
			logger.trace(cJpfUtMethodInfo.toString());
			cJpfUtInfo.getListUtMethodInfos().add(cJpfUtMethodInfo);
		}
	}

	/**
	 * @category 根据NLP产生
	 * @param cJpfUtInfo
	 * @param cJpfMethodInfo
	 */
	private void generateByNLP(JpfUtInfo cJpfUtInfo, JpfMethodInfo cJpfMethodInfo, StringBuffer sbCallMethod) {
		// 根据NLP产生

		JpfUtMethodInfo cJpfUtMethodInfo = new JpfUtMethodInfo();
		if (GenInfoFromNLP.getInstance().initParamByNLP(cJpfMethodInfo, cJpfUtMethodInfo)) {
			cJpfUtInfo.setGenType("NLP");

			cJpfUtMethodInfo.setMethodJavaDoc(GenerateUtil2.addMethodJavaDoc(cJpfMethodInfo, cJpfUtInfo));
			cJpfUtMethodInfo.setMethodDeclare(addMethodDeclare(cJpfMethodInfo.getMethodName(), cJpfUtInfo));
			cJpfUtMethodInfo.setMethodTry(addTry());
			cJpfUtMethodInfo.setClassConstructor(
					addClassInstance(cJpfMethodInfo.getClassName(), cJpfMethodInfo.getMethodParam(), cJpfUtInfo));

			cJpfUtMethodInfo.setMethodReturn(addMethodReturn(cJpfMethodInfo.getMethodName(),
					cJpfMethodInfo.getStrReturn(), cJpfMethodInfo.getMethodParam(), cJpfUtInfo));
			cJpfUtMethodInfo.setMethodCaller(sbCallMethod.toString());
			cJpfUtMethodInfo.setMethodAssert(addMethodAssert(cJpfMethodInfo.getClassName(),
					cJpfMethodInfo.getMethodName(), cJpfMethodInfo.getStrReturn(), cJpfUtInfo));
			cJpfUtMethodInfo.setMethodCatch(addCatchException(cJpfMethodInfo.getMethodExceptions()));
			logger.trace(cJpfUtMethodInfo.toString());
			cJpfUtInfo.getListUtMethodInfos().add(cJpfUtMethodInfo);
		}
	}

	/**
	 * 
	 * @category @author 吴平福
	 * @param strReturn
	 * @param sb        update 2017年9月29日
	 */
	public String addMethodAssert(String strClassName, String strMethod, String strReturn, JpfUtInfo cJpfUtInfo) {

		StringBuffer sb = new StringBuffer();
		strReturn = strReturn.trim();

		if (strReturn.equalsIgnoreCase("void")) {
			sb.append("   //请在这里增加检查点:比如 assertEquals(true, result);").append("\n");
		} else if (strReturn.equalsIgnoreCase("boolean")) {
			sb.append("    assertEquals(true, result);").append("\n");
		} else if (strReturn.equalsIgnoreCase("String")) {
			sb.append("    assertEquals(\"a\", result);").append("\n");
		} else if (strReturn.startsWith("Long")) {
			sb.append("    assertEquals(Long.valueOf(\"1\"), result);").append("\n");
		} else if (strReturn.equalsIgnoreCase("Integer")) {
			sb.append("    assertEquals(Integer.valueOf(1), result);").append("\n");
		} else if (strReturn.equalsIgnoreCase("int")) {
			sb.append("    assertEquals(1, result);").append("\n");
		} else if (strReturn.equalsIgnoreCase("double")) {
			sb.append("    ").append(strReturn).append(" dwupf=new Double(123.456);").append("\n");
			sb.append("    assertEquals(dwupf, result);").append("\n");
		} else if (strReturn.equalsIgnoreCase("Character")) {
			sb.append("    Character ch = 'a';\n");
			sb.append("    assertEquals(ch, result);").append("\n");
		} else {
			sb.append("    assertNotNull(result);").append("\n");
		}
		// 联通POC临时
		sb.setLength(0);

		sb.append("   //请在这里增加检查点:比如 assertEquals(true, result);").append("\n").append("    assertTrue(true);")
				.append("\n");

		return sb.toString();
	}

	/**
	 * 
	 * @category @author 吴平福
	 * @param strReturn
	 * @param sb        update 2017年9月29日
	 */
	public String addMethodReturn(String strMethod, String strReturn, List MethodParam, JpfUtInfo cJpfUtInfo) {
		StringBuffer sb = new StringBuffer();
		if (!strReturn.equalsIgnoreCase("void")) {
			sb.append("    ").append(strReturn).append("  result=");
		}
		return sb.toString();
	}

	/**
	 * 
	 * @category @author 吴平福
	 * @param Modifiers
	 * @param strClass
	 * @param strMethod
	 * @param MethodParam
	 * @param strReturn
	 * @param sb          update 2017年9月29日
	 */
	private String addMethodDeclare(String strMethodName, JpfUtInfo cJpfUtInfo) {
		StringBuffer sb = new StringBuffer();
		sb.append("  public void test_").append(strMethodName).append("_").append(cJpfUtInfo.getGenType())
				.append(++RunResult.iMethodCount).append("() throws Exception\n").append("  {").append("\n");
		return sb.toString();
	}

}
