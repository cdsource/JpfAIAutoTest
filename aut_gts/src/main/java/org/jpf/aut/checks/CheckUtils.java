/**
 * copyrigth by wupf@asiainfo.com
 * 2018年8月16日
 */
package org.jpf.aut.checks;

import java.util.Vector;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jdt.core.dom.AbstractTypeDeclaration;
import org.eclipse.jdt.core.dom.EnumDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.jpf.aut.base.RunResult;
import org.jpf.aut.common.consts.AutConst;


/**
 * @author wupf@asiainfo.com
 *
 */
public class CheckUtils {
	private static final Logger logger = LogManager.getLogger();
	/**
	 * 
	 */
	public CheckUtils() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 * @author wupf@asiainfo.com
	 * @param strFileName
	 * @return
	 * 2018年8月16日
	 */
	public static boolean IsSource(String strFileName)
	{
		if (strFileName.indexOf("test") >= 0) {
			
			return false;
		}

		if (strFileName.indexOf("target") >= 0) {
			return false;
		}
		return true;
	}
	
	/**
	 * 
	 * @author wupf@asiainfo.com
	 * @param vFiles
	 * 2018年8月16日
	 */
	public static void checkForSource(Vector<String> vFiles)
	{
		for (int i = vFiles.size() - 1; i >= 0; i--) {
			if (!IsSource(vFiles.get(i))) {
				vFiles.remove(i);
				continue;
			}

		}
	}
	
	/**
	 * 
	 * @author wupf@asiainfo.com
	 * @param cAbstractTypeDeclaration
	 * @return
	 * 2018年8月16日
	 */
	public static int IsForUt(AbstractTypeDeclaration cAbstractTypeDeclaration)
	{
		if (cAbstractTypeDeclaration instanceof EnumDeclaration) {
			logger.info("不支持枚举类生产单元测试：" + cAbstractTypeDeclaration.getName());
			RunResult.EnumFileCount++;
			return -1;
		}

		if (!(cAbstractTypeDeclaration instanceof TypeDeclaration)) {
			return -3;
		}

		TypeDeclaration typeDec = (TypeDeclaration)cAbstractTypeDeclaration;

		if (typeDec.getModifiers() == AutConst.CLASS_TYPE_ABSTRACT) {
			logger.info("抽象类不能生产单元测试：" + cAbstractTypeDeclaration.getName());
			RunResult.AbstractFileCount++;
			return -4;
		}
		if (typeDec.isInterface()) {
			logger.info("接口类单元测试：" + cAbstractTypeDeclaration.getName());
			RunResult.InterfaceFileCount++;
			return -5;
		}
		return 0;
	}
}
