/** 
* @author 吴平福 
* E-mail:wupf@aliyun.com 
* @version 创建时间：2018年2月27日 上午9:53:58 
* 类说明 
*/

package org.jpf.utils.classes;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jpf.aut.base.JpfUtInfo;


/**
 * 
 */
public class AddImport {

	/**
	 * 1 类中类，com.asiainfo.inter.server.csf.pojo.SInterRoamIn;
	 */
	private AddImport() {
	}

	private static final Logger logger = LogManager.getLogger();

	// 已经自行实例化
	private static final AddImport Instance = new AddImport();

	// 静态工厂方法
	public static AddImport getInstance() {
		return Instance;
	}

	/**
	 * 
	 * @category 查找引用类:如果带有.的类型，是完整类型，直接import;简单类型只需要在当前类下查找；如果在其它包下，IMPORT里面应该包含了。 
	 * @author 吴平福
	 * @param strInterfaceName
	 * @return update 2018年2月26日
	 */
	public void addImportForList(final String strParamType, JpfUtInfo cJpfUtInfo) {
		try {
			int i = strParamType.indexOf("<");
			String strNewParamType = strParamType;
			if (i > 0) {
				strNewParamType = strNewParamType.substring(i + 1, strNewParamType.length()).trim();
			}
			i = strNewParamType.lastIndexOf(">");
			if (i > 0) {
				strNewParamType = strNewParamType.substring(0, i).trim();
			}
			i = strNewParamType.lastIndexOf("[");
			if (i > 0) {
				strNewParamType = strNewParamType.substring(0, i).trim();
			}


			//logger.debug("strParamType=" + strNewParamType.trim());
			if (strNewParamType.indexOf(".") >0) 
			{
				cJpfUtInfo.addImport(strNewParamType);
				return;
			}

			if (strNewParamType.indexOf(".") == -1) {
				strNewParamType = cJpfUtInfo.getCurrentJavaFilePackage() + "." + strNewParamType;
				//logger.debug("strParamType=" + strNewParamType.trim());
				String strJavaFileName = FindClassInfoUtil.getInstance().findJavaFile(strNewParamType);
				
				if (null != strJavaFileName) {
					cJpfUtInfo.addImport(strNewParamType);
				} 
			}

		} catch (Exception ex) {
			// TODO: handle exception
			logger.error(strParamType);
			ex.printStackTrace();
			logger.error(ex);
		}

		return ;
	}

	public static void main(String[] args) {
		String strParamType = "Set<String>";

		AddImport cAddImport = new AddImport();
		cAddImport.addImportForList(strParamType, null);

		int i = strParamType.indexOf("<");
		if (i > 0) {
			strParamType = strParamType.substring(i + 1, strParamType.length()).trim();
		}
		i = strParamType.lastIndexOf(">");
		if (i > 0) {
			strParamType = strParamType.substring(0, i);
		}
		System.out.println(strParamType.trim());
	}
}
