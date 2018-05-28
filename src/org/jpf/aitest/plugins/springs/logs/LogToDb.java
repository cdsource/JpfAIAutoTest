/**
 * copyrigth by wupf@asiainfo.com
 * 2018年4月18日
 */
package org.jpf.aitest.plugins.springs.logs;

/**
 * @author 吴平福 E-mail:wupf@asiainfo.com
 * @version 创建时间：2018年2月6日 下午1:31:09 类说明
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jpf.aitest.dbs.DbServer;
import org.jpf.aitest.plugins.logs.LogCaseInfo;

import com.asiainfo.utils.dbsql.AiDBUtil;
import com.asiainfo.utils.ios.AiFileUtil;

/**
 * 
 */
public class LogToDb {

	private static final Logger logger = LogManager.getLogger();

	private long lFindCount = 0;
	private long lRowCount = 0;
	private final String LogFileType = ".log";

	Vector<LogCaseInfo> vCaseInfo = new Vector<>();

	/**
	 * 
	 */
	public LogToDb(String strInputLogFilePath) {
		// TODO Auto-generated constructor stub
		// 2018-01-26 19:02:05,529 DEBUG [java.sql.PreparedStatement] - {pstm-100001}
		// 北京移动电商
		long start = System.currentTimeMillis();
		long lSelectCount = 0;
		long lUpdateCount = 0;
		long lDeleteCount = 0;
		long lInsertCount = 0;
		long lUnknownCount = 0;
		try {

			Vector<String> vFiles = new Vector<String>();
			AiFileUtil.getFiles(strInputLogFilePath, vFiles, LogFileType);
			logger.info("find log file count=" + vFiles.size());
			for (int i = 0; i < vFiles.size(); i++) {
				HandleLogFile(vFiles.get(i));
			}
			logger.info(vCaseInfo.size());

			Map<String, LogParamInfo> map = new HashMap<>();
			for (int i = 0; i < vCaseInfo.size(); i++) {
				LogCaseInfo cLogCaseInfo = (LogCaseInfo) vCaseInfo.get(i);

				if (cLogCaseInfo.getMethodName().trim().toUpperCase().startsWith("INSERT")) {
					/*
					handleInsert(map, cLogCaseInfo);
					map.forEach((k, v) -> {
						logger.info(k + "  : " + v);
					});
					lRowCount += map.size();
					*/
					lInsertCount++;
				} else if (cLogCaseInfo.getMethodName().trim().toUpperCase().startsWith("SELECT")) {
					handleSelect(map, cLogCaseInfo);
					map.forEach((k, v) -> {
						logger.info(k + "  : " + v);
					});
					lRowCount = +map.size();
					lSelectCount++;
				} else if (cLogCaseInfo.getMethodName().trim().toUpperCase().startsWith("UPDATE")) {
					lUpdateCount++;
				} else if (cLogCaseInfo.getMethodName().trim().toUpperCase().startsWith("DELETE")) {
					lDeleteCount++;
				} else {
					logger.info(cLogCaseInfo.getMethodName().trim());
					lUnknownCount++;
				}
			}
		} catch (Exception ex) {
			// TODO: handle exception
			logger.error(ex);
			ex.printStackTrace();
		}
		logger.info("lFindCount " + lFindCount);
		logger.info("lRowCount " + lRowCount);
		logger.info("lSelectCount " + lSelectCount);
		logger.info("lUpdateCount " + lUpdateCount);
		logger.info("lDeleteCount " + lDeleteCount);
		logger.info("lInsertCount " + lInsertCount);
		logger.info("lUnknownCount " + lUnknownCount);
		logger.info("ExcuteTime " + (System.currentTimeMillis() - start) + "ms");
	}

	/**
	 * @category @author 吴平福
	 * @param args update 2018年1月26日
	 */

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		new LogToDb(HandleLogInputParam.RUN_LOG_SOURCE_FILEPATH);

	}

	private void saveToDb() {
		Connection conn = null;
		String strKeySql = null;
		LogCaseInfo cLogCaseInfo = null;
		try {
			conn = DbServer.getInstance().getConn();
			logger.info(conn == null);
			String strSql_SQL_INFO = "insert into sql_info(SQL_STR,EXE_DATE) values(?,?) ";
			String strSql_SQL_ROW_VALUE = "insert into sql_row_value(LOG_INFO_ID,COL_NAME,COL_VALUE) values(?,?,?) ";
			String strSql_Query = "select ID from sql_info where sql_str=? and exe_date=?";

			long iSQL_ID = 0L;
			PreparedStatement psmt1 = null;
			PreparedStatement psmt3 = null;
			ResultSet rs = null;
			for (int i = 0; i < vCaseInfo.size(); i++) {
				cLogCaseInfo = (LogCaseInfo) vCaseInfo.get(i);

				// logger.debug(strSql);
				psmt1 = conn.prepareStatement(strSql_SQL_INFO);
				psmt1.setString(1, strKeySql);
				psmt1.setString(2, cLogCaseInfo.getCaseTime());
				psmt1.executeUpdate();

				psmt3 = conn.prepareStatement(strSql_Query);
				psmt3.setString(1, strKeySql);
				psmt3.setString(2, cLogCaseInfo.getCaseTime());

				// create table
				rs = psmt3.executeQuery();
				if (rs.next()) {
					iSQL_ID = rs.getLong("ID");
				}

				/*
				 * Set<String> key2 = cLogCaseInfo.getParams().keySet(); for (Iterator it2 =
				 * key2.iterator(); it2.hasNext();) { String strColName = (String) it2.next();
				 * PreparedStatement psmt2 = conn.prepareStatement(strSql_SQL_ROW_VALUE);
				 * psmt2.setLong(1, iSQL_ID); psmt2.setString(2, strColName); psmt2.setString(3,
				 * cLogCaseInfo.getParams().get(strColName)); psmt2.executeUpdate();
				 * lRowCount++; }
				 */
				lFindCount++;
			}
			if (conn.getAutoCommit() == false) {
				conn.commit();
			}
		} catch (Exception ex) {
			// TODO: handle exception
			ex.printStackTrace();
			logger.warn(strKeySql);
			logger.warn(cLogCaseInfo.getCaseTime());
			logger.error(ex);
		} finally {
			AiDBUtil.doClear(conn);
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
		// InputStreamReader read = null;
		// BufferedReader reader = null;
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
			boolean isFindSql = false;

			String strDateTime = "";
			String strSql = "";
			String strValue = "";

			while ((line = reader.readLine()) != null) {

				if (line.indexOf(HandleLogInputParam.KEY_SQL) > 0) {
					int iPos = line.indexOf(HandleLogInputParam.KEY_SQL_STATEMENT);
					if (iPos > 0) {
						strDateTime = line.substring(0, 19);
						line = line.substring(iPos + HandleLogInputParam.KEY_SQL_STATEMENT.length() + 1, line.length())
								.trim();
						if (line.toUpperCase().endsWith(HandleLogInputParam.KEY_EXCLUDE_SQL)) {
							continue;
						}

						Matcher m = HandleLogInputParam.p.matcher(line);
						strSql = m.replaceAll(" ");
						// logger.debug("strSql="+strSql);
						lFindCount++;
						isFindSql = true;
					}
					iPos = line.indexOf(HandleLogInputParam.KEY_SQL_PARAM);
					if (iPos > 0 && isFindSql) {
						line = line.substring(iPos + HandleLogInputParam.KEY_SQL_PARAM.length() + 1, line.length());
						Matcher m = HandleLogInputParam.p.matcher(line);
						strValue = m.replaceAll(" ");
						strValue = strValue.replaceAll("\\[", "").replaceAll("\\]", "").trim();
						// logger.debug("strValue="+strValue);
						isFindSql = false;
						if (strValue.length() > 0) {
							LogCaseInfo cLogCaseInfo = new LogCaseInfo();
							cLogCaseInfo.setCaseTime(strDateTime);
							cLogCaseInfo.setMethodName(strSql);
							cLogCaseInfo.setParameters(strValue);
							vCaseInfo.add(cLogCaseInfo);
						}
						strDateTime = "";
						strSql = "";
						strValue = "";
					}
				}
				// sb.append(line).append("\r\n");

			}

		} catch (Exception ex) {
			// TODO: handle exception
			ex.printStackTrace();

		}

	}
	/**
	 * 
	 * @author wupf@asiainfo.com
	 * @param map
	 * @param cLogCaseInfo
	 * @return
	 * 2018年4月18日
	 */
	private boolean handleSelect(Map<String, LogParamInfo> map, LogCaseInfo cLogCaseInfo) {
		String strSql = cLogCaseInfo.getMethodName();
		logger.info(strSql);
		
		Pattern p = Pattern.compile("[A-Za-z][A-Z|a-z|0-9|\\_|\\-]{1,}\\s?[>=<]{1,2}\\s?\\?");
		Matcher m = p.matcher(strSql);
		List<String> mList=new ArrayList();
		while(m.find()){ 
			mList.add(m.group().replaceAll(">", "").replaceAll("<", "").replaceAll("=", "").replaceAll("\\?","").trim());
            //System.out.println(m.group());
        }
		logger.info("strMarks.length:" +mList.size());
		/*
		 * for (int i = 0; i < strMarks.length; i++) { logger.info(strMarks[i]); }
		 */
		// [2015060900001000190055105362, alipay]
		String strValue = cLogCaseInfo.getParameters();
		logger.debug(strValue);
		String[] strValues = strValue.split(", ");
		logger.debug(strValues.length);

		if (mList.size()!=strValues.length)
		{
			return false;
		}
		for (int i = 0; i < mList.size(); i++) {

				LogParamInfo cLogParamInfo = new LogParamInfo();
				cLogParamInfo.setParamValue(strValues[i].trim());
				map.put(mList.get(i) , cLogParamInfo);
		}

		return true;
	}
	/**
	 * 
	 * @author wupf@asiainfo.com
	 * @param map
	 * @param cLogCaseInfo
	 * @return
	 * 2018年4月18日
	 */
	private boolean handleInsert(Map<String, LogParamInfo> map, LogCaseInfo cLogCaseInfo) {
		String strSql = cLogCaseInfo.getMethodName();
		logger.info(strSql);
		int iPos = strSql.indexOf("(");
		String strColName = strSql.substring(iPos + 1, strSql.length());
		iPos = strColName.indexOf(")");
		strColName = strColName.substring(0, iPos);

		strColName = strColName.replaceAll("_", "");
		String[] strKeys = strColName.split(",");
		logger.info(strColName);
		logger.debug(strKeys.length);

		String strMark = strSql.substring(iPos + 1, strSql.length());
		iPos = strMark.indexOf("(");
		strMark = strMark.substring(iPos + 1, strMark.length());
		iPos = strMark.lastIndexOf(")");
		strMark = strMark.substring(0, iPos);
		logger.debug(strMark);
		// strMark=strMark.replaceAll(",.*?\\(.*?\\?.*?\\)", ",?");
		strMark = strMark.replaceAll(", *\\w+\\(.*?\\) *", ",?");
		// logger.info(strMark);
		String[] strMarks = strMark.split(",");
		logger.info("strMarks.length:" + strMarks.length);
		/*
		 * for (int i = 0; i < strMarks.length; i++) { logger.info(strMarks[i]); }
		 */
		// [2015060900001000190055105362, alipay]
		String strValue = cLogCaseInfo.getParameters();
		logger.debug(strValue);
		String[] strValues = strValue.split(", ");
		logger.debug(strValues.length);

		logger.debug(cLogCaseInfo.getTypes());
		int j = 0;
		for (int i = 0; i < strKeys.length; i++) {
			if (strMarks[i].trim().equalsIgnoreCase("?")) {
				LogParamInfo cLogParamInfo = new LogParamInfo();
				cLogParamInfo.setParamValue(strValues[j].trim());
				map.put(strKeys[i].trim(), cLogParamInfo);
				j++;
			}
		}
		if (j != strValues.length) {
			logger.error("error count:" + strSql);
			return false;
		}
		return true;
	}
}
