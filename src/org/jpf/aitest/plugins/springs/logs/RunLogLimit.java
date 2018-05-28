/**
 * copyrigth by wupf@asiainfo.com
 * 2018年4月23日
 */
package org.jpf.aitest.plugins.springs.logs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jpf.aitest.dbs.DbServer;

/**
 * @author wupf@asiainfo.com
 *
 */
public class RunLogLimit {
	private static final Logger logger = LogManager.getLogger();
	private final int MaxCount = 10;

	/**
	 * 
	 */
	public RunLogLimit() {
		long start = System.currentTimeMillis();
		Connection conn = null;
		try {
			conn = DbServer.getInstance().getConn();
			// logger.info(conn == null);
			String SQL_INFO_QUERY = "select LOG_STR,max(id) id from log_info group by LOG_STR;";
			String SQL_INFO_DEL = "truncate table log_info_tmp";
			String SQL_ROW_VALUE_INSERT = "insert into log_info_tmp select * from log_info where log_str=? order by exe_date desc limit "
					+ MaxCount;

			PreparedStatement psmt2 = null;
			PreparedStatement psmt1 = conn.prepareStatement(SQL_INFO_DEL);
			psmt1.executeUpdate();

			psmt1 = conn.prepareStatement(SQL_INFO_QUERY);

			ResultSet keys = psmt1.executeQuery();
			while (keys.next()) {
				logger.info(keys.getString("LOG_STR"));
				psmt2 = conn.prepareStatement(SQL_ROW_VALUE_INSERT);
				psmt2.setString(1, keys.getString("LOG_STR"));
				psmt2.executeUpdate();
			}
			psmt2 = conn.prepareStatement("truncate table log_info");
			psmt2.executeUpdate();
			
			psmt2 = conn.prepareStatement("insert into log_info select * from log_info_tmp");
			psmt2.executeUpdate();
			
			if (conn.getAutoCommit() == false) {
				conn.commit();
			}
		} catch (Exception ex) {
			// TODO: handle exception
			ex.printStackTrace();
			logger.error(ex);
		}
		logger.info("ExcuteTime " + (System.currentTimeMillis() - start) + "ms");
	}

	public static void main(String[] args) {
		new RunLogLimit();

	}
}
