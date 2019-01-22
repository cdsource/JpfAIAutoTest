/**
 * copyrigth by wupf@asiainfo.com
 * 2018年4月19日
 */
package org.jpf.aut.logs.plugins;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import org.jpf.aut.utils.DbServer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jpf.utils.ios.AiFileUtil;

/**
 * @author wupf@asiainfo.com
 *
 */
public class SelfTestLogToDb {

	private static final Logger logger = LogManager.getLogger();

	private long lFindCount = 0;
	private final String LogFileType = ".log";

	Map<String, String> map = new HashMap<>();

	/**
	 * 
	 * @param strInputLogFilePath
	 */
	public SelfTestLogToDb(String strInputLogFilePath) {
		// TODO Auto-generated constructor stub
		// 2018-01-26 19:02:05,529 DEBUG [java.sql.PreparedStatement] - {pstm-100001}
		// 北京移动电商
		long start = System.currentTimeMillis();

		try {

			Vector<String> vXmlFiles = new Vector<>();
			AiFileUtil.getFiles(HandleLogInputParam.SELF_LOG_FILEPATH, vXmlFiles, LogFileType);
			for (int j = 0; j < vXmlFiles.size(); j++) {
				String strXmlFileName = vXmlFiles.get(j);
				logger.info(strXmlFileName);
				HandleLogFile(strXmlFileName);
			}
			map.forEach((k, v) -> {
				System.out.println("Item : " + k + " Count : " + v);
				saveToDb(v, k);
			});
			updateToDb();
			logger.info("ParamValueFromSelfLog length:" + map.size());
		} catch (Exception ex) {
			// TODO: handle exception
			logger.error(ex);
			ex.printStackTrace();
		}
		logger.info("lFindCount " + lFindCount);
		logger.info("ExcuteTime " + (System.currentTimeMillis() - start) + "ms");
	}

	/**
	 * @category @author 吴平福
	 * @param args update 2018年1月26日
	 */

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		new SelfTestLogToDb(HandleLogInputParam.SELF_LOG_FILEPATH);

	}

	private void saveToDb(String strSql, String strMethodName) {
		Connection conn = null;
		try {
			conn = DbServer.getInstance().getConn();
			// logger.info(conn == null);
			String strSql_SQL_INFO = "insert into selflog_info(LOG_STR,EXE_METHOD) values(?,?) ";

			PreparedStatement psmt1 = conn.prepareStatement(strSql_SQL_INFO);
			psmt1.setString(1, strSql);
			psmt1.setString(2, strMethodName);
			psmt1.executeUpdate();

			if (conn.getAutoCommit() == false) {
				conn.commit();
			}
		} catch (Exception ex) {
			// TODO: handle exception
			ex.printStackTrace();
			logger.error(ex);
		}
	}
	private void updateToDb() {
		Connection conn = null;
		try {
			conn = DbServer.getInstance().getConn();
			// logger.info(conn == null);
			String strSql_SQL_INFO = "update log_info t1,selflog_info t2  set t1.EXE_METHOD= t2.EXE_METHOD where t1.LOG_STR=t2.LOG_STR;";

			PreparedStatement psmt1 = conn.prepareStatement(strSql_SQL_INFO);
			psmt1.executeUpdate();

			if (conn.getAutoCommit() == false) {
				conn.commit();
			}
		} catch (Exception ex) {
			// TODO: handle exception
			ex.printStackTrace();
			logger.error(ex);
		}
	}
	/**
	 * 
	 * @author wupf@asiainfo.com
	 * @param strFileName
	 * @param iFileCount
	 * @throws Exception 2018年4月17日
	 */
	public void HandleLogFile(String strFileName) throws Exception {

		logger.info(strFileName);
		if (null == strFileName || 0 == strFileName.trim().length()) {
			return;
		}
		File f = new File(strFileName);
		if (!f.exists()) {

			return;
		}
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(f), "UTF-8"))) {

			String line;

			String strMethodName = "";
			String strSql = "";

			while ((line = reader.readLine()) != null) {

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

		}

	}

}
