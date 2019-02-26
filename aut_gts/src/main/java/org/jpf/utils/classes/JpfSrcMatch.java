/** 
* copyrigth by 吴平福
* @author 吴平福 
* E-mail:wupf@aliyun.com 
* @version 创建时间：2017年9月30日 下午3:49:28 
* 类说明 
*/ 
package org.jpf.utils.classes;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jpf.utils.MatchUtil;

/**
 * @author wupf@aliyun.com
 *
 */
public class JpfSrcMatch {
	private static final Logger logger = LogManager.getLogger();
	/**
	 * 
	 */
	public JpfSrcMatch() {
		// TODO Auto-generated constructor stub

	}

	/**
	 * 
	 * @author wupf@aliyun.com
	 * @param strPomFilePath
	 * @param vFiles
	 * 2018年8月27日
	 */
	public void removeNotMatch(String strPomFilePath, Vector<String> vFiles) {
		// get filter from pom.xml
		List<String> mList = new ArrayList();

		logger.info(vFiles.size());
		for (int i = 0; i < vFiles.size(); i++) {
			for (int j = 0; j < mList.size(); j++) {
				if (MatchUtil.matchSrcFile(vFiles.get(i), mList.get(j) )) {
					vFiles.remove(i);
					i--;
				}
			}
		}
		logger.info(vFiles.size());
	}

	/**
	 * @author wupf@aliyun.com
	 * @param args 2018年8月27日
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			JpfSrcMatch cJpfSrcMatch = new JpfSrcMatch();
			Vector<String> vFiles = new Vector<String>();
			cJpfSrcMatch.removeNotMatch("", vFiles);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
