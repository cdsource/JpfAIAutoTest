/**
 * copyrigth by wupf@asiainfo.com
 * 2018年4月18日
 */
package org.jpf.aut.logs.plugins;

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
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jpf.aut.utils.DbServer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jpf.utils.ios.AiFileUtil;

/**
 * 
 */
public class RunLogToDb {

	private static final Logger logger = LogManager.getLogger();

	private long lFindCount = 0;
	private long lRowCount = 0;
	private final String LogFileType = ".log";


	/**
	 * 
	 */
	public RunLogToDb(String strInputLogFilePath) {
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
			Vector<LogCaseInfo> vLogCase=new Vector<>();
			Vector<String> vFiles = new Vector<String>();
			AiFileUtil.getFiles(strInputLogFilePath, vFiles, LogFileType);
			logger.info("find log file count=" + vFiles.size());
			for (int i = 0; i < vFiles.size(); i++) {
				HandleLogFile(vFiles.get(i),vLogCase);
			}
			logger.info(vLogCase.size());
			
			for (int i = 0; i < vLogCase.size(); i++) {
				LogCaseInfo cLogCaseInfo = (LogCaseInfo) vLogCase.get(i);
				Map<String, LogCaseUnit> map = new HashMap<>();
				if (cLogCaseInfo.getMethodName().trim().toUpperCase().startsWith("INSERT")) {
					handleInsert(map, cLogCaseInfo);
					/*
					 * map.forEach((k, v) -> { logger.info(k + "  : " + v); });
					 */
					saveToDb(map, cLogCaseInfo);
					lRowCount += map.size();
					lInsertCount++;
				} else if (cLogCaseInfo.getMethodName().trim().toUpperCase().startsWith("SELECT")) {
					handleSelect(map, cLogCaseInfo);
					/*
					 * map.forEach((k, v) -> { logger.info(k + "  : " + v); });
					 */
					saveToDb(map, cLogCaseInfo);
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
			
			new RunLogLimit();
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

		new RunLogToDb(HandleLogInputParam.RUN_LOG_SOURCE_FILEPATH);

	}


	private void saveToDb(Map<String, LogCaseUnit> map, LogCaseInfo cLogCaseInfo) {
		Connection conn = null;
		try {
			conn = DbServer.getInstance().getConn();
			// logger.info(conn == null);
			String strSql_SQL_INFO = "insert into log_info(LOG_STR,EXE_DATE) values(?,?) ";
			String strSql_SQL_ROW_VALUE = "insert into sql_row_value(LOG_INFO_ID,COL_NAME,COL_VALUE) values(?,?,?) ";


			long iSQL_ID = 0L;

				// logger.debug(strSql);
				PreparedStatement psmt1 = conn.prepareStatement(strSql_SQL_INFO,
						PreparedStatement.RETURN_GENERATED_KEYS);
				psmt1.setString(1, cLogCaseInfo.getMethodName());
				psmt1.setString(2, cLogCaseInfo.getCaseTime());
				psmt1.executeUpdate();

				ResultSet keys = psmt1.getGeneratedKeys();
				if (keys.next()) {
					iSQL_ID = keys.getLong(1);
				}

			PreparedStatement psmt2 = conn.prepareStatement(strSql_SQL_ROW_VALUE);
			LogCaseUnit cLogCaseUnit = null;
			for (String key : map.keySet()) {
				cLogCaseUnit = map.get(key);
				psmt2.setLong(1, iSQL_ID);
				psmt2.setString(2, key);
				psmt2.setString(3, cLogCaseUnit.getUnitValue());
				psmt2.addBatch();
			}
			psmt2.executeBatch();

			if (conn.getAutoCommit() == false) {
				conn.commit();
			}
		} catch (Exception ex) {
			// TODO: handle exception
			ex.printStackTrace();
			logger.warn(cLogCaseInfo.getMethodName());
			logger.warn(cLogCaseInfo.getCaseTime());
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
	public void HandleLogFile(String strFileName,Vector<LogCaseInfo> vLogCase) throws Exception {
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
						strDateTime = line.substring(0, 23);
						line = line.substring(iPos + HandleLogInputParam.KEY_SQL_STATEMENT.length() + 1, line.length())
								.trim();
						if (line.toUpperCase().endsWith(HandleLogInputParam.KEY_EXCLUDE_SQL)) {
							continue;
						}

						Matcher m = HandleLogInputParam.p.matcher(line);
						strSql = m.replaceAll(" ");
						logger.debug("strSql="+strSql);
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
							//?
							//addToMap(strSql, cLogCaseInfo);
							vLogCase.add( cLogCaseInfo);
						}else
						{
							
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
	 * @param strSql
	 * @param cLogCaseInfo
	 * @return
	 * 2018年4月21日
	 */
	private boolean addToMap(String strSql, LogCaseInfo cLogCaseInfo,Map<String,LogCaseInfo> mapLogCase)
	{
		LogCaseInfo cLogCaseInfo2=null;
		for (String key : mapLogCase.keySet()) {  
			if (key.equalsIgnoreCase(strSql))
			{
				cLogCaseInfo2=mapLogCase.get(key);
				break;
			}
			//System.out.println("Key = " + key);  
		}
		//没有找到
		if (cLogCaseInfo2==null)
		{
			mapLogCase.put(strSql, cLogCaseInfo);
		}else
		{
			
		}
		return true;
	}
	/**
	 * 
	 * @author wupf@asiainfo.com
	 * @param map
	 * @param cLogCaseInfo
	 * @return 2018年4月18日
	 */
	private boolean handleSelect(Map<String, LogCaseUnit> map, LogCaseInfo cLogCaseInfo) {
		String strSql = cLogCaseInfo.getMethodName();
		logger.info(strSql);

		Pattern p = Pattern.compile("[A-Za-z][A-Z|a-z|0-9|\\_|\\-]{1,}\\s?[>=<]{1,2}\\s?\\?");
		Matcher m = p.matcher(strSql);
		List<String> mList = new ArrayList();
		while (m.find()) {
			mList.add(
					m.group().replaceAll(">", "").replaceAll("<", "").replaceAll("=", "").replaceAll("\\?", "").trim());
			// System.out.println(m.group());
		}
		logger.info("strMarks.length:" + mList.size());
		/*
		 * for (int i = 0; i < strMarks.length; i++) { logger.info(strMarks[i]); }
		 */
		// [2015060900001000190055105362, alipay]
		String strValue = cLogCaseInfo.getParameters();
		logger.debug(strValue);
		String[] strValues = strValue.split(", ");
		logger.debug(strValues.length);

		if (mList.size() != strValues.length) {
			return false;
		}
		for (int i = 0; i < mList.size(); i++) {

			LogCaseUnit cLogParamInfo = new LogCaseUnit();
			cLogParamInfo.setUnitValue(strValues[i].trim());
			map.put(mList.get(i), cLogParamInfo);
		}

		return true;
	}

	/**
	 * 
	 * @author wupf@asiainfo.com
	 * @param map
	 * @param cLogCaseInfo
	 * @return 2018年4月18日
	 */
	private boolean handleInsert(Map<String, LogCaseUnit> map, LogCaseInfo cLogCaseInfo) {
		String strSql = cLogCaseInfo.getMethodName();
		logger.debug(strSql);
		int iPos = strSql.indexOf("(");
		String strColName = strSql.substring(iPos + 1, strSql.length());
		iPos = strColName.indexOf(")");
		strColName = strColName.substring(0, iPos);

		strColName = strColName.replaceAll("_", "");
		String[] strKeys = strColName.split(",");
		// logger.debug(strColName);
		// logger.debug(strKeys.length);

		String strMark = strSql.substring(iPos + 1, strSql.length());
		iPos = strMark.indexOf("(");
		strMark = strMark.substring(iPos + 1, strMark.length());
		iPos = strMark.lastIndexOf(")");
		strMark = strMark.substring(0, iPos);
		// logger.debug(strMark);
		// strMark=strMark.replaceAll(",.*?\\(.*?\\?.*?\\)", ",?");
		strMark = strMark.replaceAll(", *\\w+\\(.*?\\) *", ",?");
		// logger.info(strMark);
		String[] strMarks = strMark.split(",");
		logger.trace("strMarks.length:" + strMarks.length);
		/*
		 * for (int i = 0; i < strMarks.length; i++) { logger.info(strMarks[i]); }
		 */
		// [2015060900001000190055105362, alipay]
		String strValue = cLogCaseInfo.getParameters();
		// logger.debug(strValue);
		String[] strValues = strValue.split(", ");
		// logger.debug(strValues.length);

		// logger.debug(cLogCaseInfo.getTypes());
		int j = 0;
		for (int i = 0; i < strKeys.length; i++) {
			if (strMarks[i].trim().equalsIgnoreCase("?")) {
				LogCaseUnit cLogParamInfo = new LogCaseUnit();
				cLogParamInfo.setUnitValue(strValues[j].trim());
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
