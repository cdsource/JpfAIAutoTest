/**
 * @author 吴平福 E-mail:wupf@asiainfo.com
 * @version 创建时间：2018年2月4日 下午8:33:21 类说明
 */

package org.jpf.aitest.plugins.logs;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jpf.aitest.GenerateInputParam;
import org.jpf.aitest.plugins.springs.logs.HandleLogInputParam;

import com.asiainfo.utils.ios.AiFileUtil;

/**
 * 
 */
public class ParamValueFromSelfLog {

	private static final Logger logger = LogManager.getLogger();

	// 已经自行实例化
	private static final ParamValueFromSelfLog Instance = new ParamValueFromSelfLog();

	// 静态工厂方法
	public static ParamValueFromSelfLog getInstance() {
		return Instance;
	}

	private HashMap<String, String> map;

	/**
	 * 
	 */
	private ParamValueFromSelfLog() {

	}

	public static void main(String[] args) {
		try {
			logger.info(ParamValueFromSelfLog.getInstance()
					.findValueFromLog("com.asiainfo.ebiz.alipay.service.AlipayAccountManager.queryAccountLog"));
			// ParamValueFromSelfLog.getInstance().toString();
		} catch (Exception ex) {
			// TODO: handle exception
			ex.printStackTrace();
		}
	}

	public String toString() {
		Set<String> key = map.keySet();
		for (Iterator it = key.iterator(); it.hasNext();) {
			String s = (String) it.next();
			System.out.println(s);
			System.out.println(map.get(s));
		}
		return "";
	}

	private void init() throws Exception {
		map = new HashMap<>();
		Vector<String> vXmlFiles = new Vector<>();
		AiFileUtil.getFiles(HandleLogInputParam.SELF_LOG_FILEPATH, vXmlFiles, ".log");
		for (int j = 0; j < vXmlFiles.size(); j++) {
			String strXmlFileName = vXmlFiles.get(j);
			logger.info(strXmlFileName);
			BufferedReader reader = null;
			try {
				File f = new File(strXmlFileName);
				reader = new BufferedReader(new InputStreamReader(new FileInputStream(f), "UTF-8"));
				String line;
				String strSql = "";
				String strMethodName = "";

				while ((line = reader.readLine()) != null) {
					// datetime
					int iPos = line.trim().indexOf(HandleLogInputParam.KEY_SelfTest_Method);
					if (iPos >= 0) {
						strMethodName = line
								.substring(iPos + HandleLogInputParam.KEY_SelfTest_Method.length(), line.length()).trim();
						if ((line = reader.readLine()) != null) {
							strSql = line.trim();
						}
						map.put(strMethodName, strSql);
					}
				}

			} catch (Exception ex) {
				// TODO: handle exception
				ex.printStackTrace();
			} finally {
				if (null != reader) {
					reader.close();
				}
			}
		}
		logger.info("ParamValueFromSelfLog length:" + map.size());
	}

	public String findValueFromLog(String strMethodName) {
		String strSql = "";
		try {

			if (map == null) {
				init();
			}
			strSql = map.get(strMethodName);

			if (null == strSql) {
				logger.warn("not find value from self log:" + strMethodName);
			} else {
				logger.debug("find sql from selflog" + strMethodName);
			}
		} catch (Exception ex) {
			// TODO: handle exception
			ex.printStackTrace();
		}
		return strSql;
	}

}
