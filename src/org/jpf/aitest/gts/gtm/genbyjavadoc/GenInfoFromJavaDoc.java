/** 
* @author 吴平福 
* E-mail:wupf@asiainfo.com 
* @version 创建时间：2017年11月17日 下午12:00:55 
* 类说明 
*/

package org.jpf.aitest.gts.gtm.genbyjavadoc;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jpf.aitest.JpfMethodInfo;
import org.jpf.aitest.JpfUtMethodInfo;
import org.jpf.aitest.gts.gtm.MethodParamBody;

/**
 * 
 */
public class GenInfoFromJavaDoc {
	private static final Logger logger = LogManager.getLogger();

	/**
	 * 
	 */
	private GenInfoFromJavaDoc() {

	}

	// 已经自行实例化
	private static final GenInfoFromJavaDoc Instance = new GenInfoFromJavaDoc();

	// 静态工厂方法
	public static GenInfoFromJavaDoc getInstance() {
		return Instance;
	}

	/**
	 * 
	 * @category 根据声明产生初始化值
	 * @author 吴平福
	 * @param cMethodInfo
	 * @param cJpfUtInfo
	 * @return update 2018年1月23日
	 */
	public boolean initParamByJavaDoc(JpfMethodInfo cMethodInfo, JpfUtMethodInfo cJpfUtMethodInfo) {
		boolean isAllFind=false;
		try {
			StringBuffer sb = new StringBuffer();
			String[] strJavaDocs = cMethodInfo.getStrJavaDoc().split("\n");
			int iFindCount = 0;

			for (int i = 0; i < cMethodInfo.getMethodParam().size(); i++) {
				MethodParamBody cParamInitBody = new MethodParamBody(cMethodInfo.getMethodParam().get(i).toString());

				for (int j = 0; j < strJavaDocs.length; j++) {
					strJavaDocs[j] = strJavaDocs[j].trim();
					String strKey = "* @param " + cParamInitBody.getParamVariable();
					if (strJavaDocs[j].startsWith(strKey)) {
						sb.append("    ").append(cParamInitBody.getParamType()).append(" ")
								.append(cParamInitBody.getParamVariable()).append(" =  ")
								.append(strJavaDocs[j].substring(strKey.length() + 1, strJavaDocs[j].length()).trim())
								.append(";\n");

						iFindCount++;
					}
				}
			}
			logger.debug(sb);
			if (iFindCount == cMethodInfo.getMethodParam().size() && cMethodInfo.getMethodParam().size() > 0) {
				cJpfUtMethodInfo.setMethodParam(sb.toString());
			}else
			{
				return false;
			}
			String strKeyReturn= "* @return";
			for (int j = 0; j < strJavaDocs.length; j++) {
				strJavaDocs[j]=strJavaDocs[j].trim();
				if (strJavaDocs[j].startsWith(strKeyReturn)) {
					if (strJavaDocs[j].length()>strKeyReturn.length() + 1)
					{
						String strReturn=strJavaDocs[j].substring(strKeyReturn.length() + 1, strJavaDocs[j].length()).trim();
						if (strReturn!=null && strReturn.length()>0)
						{
							cJpfUtMethodInfo.setMethodAssert("    assertEquals(result,"+strReturn+");");
							 isAllFind=true;
						}
					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		}
		return isAllFind;
	}

}
