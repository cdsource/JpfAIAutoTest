/**
 * 
 */
package org.jpf.aut.gts.gtm.genbynlps;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.jpf.aut.utils.DbServer;
import org.jpf.utils.dbsql.JpfDBUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jpf.aut.base.JpfMethodInfo;
import org.jpf.aut.base.JpfUtMethodInfo;
import org.jpf.aut.gts.gtm.GenerateMethodUtil;
import org.jpf.aut.gts.gtm.MethodParamBody;


/**
 * @author Administrator
 *
 */
public class GenInfoFromNLP {

	private static final Logger logger = LogManager.getLogger();

	/**
	 * 
	 */
	private GenInfoFromNLP() {

	}

	// 已经自行实例化
	private static final GenInfoFromNLP Instance = new GenInfoFromNLP();

	// 静态工厂方法
	public static GenInfoFromNLP getInstance() {
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
	public boolean initParamByNLP(JpfMethodInfo cMethodInfo, JpfUtMethodInfo cJpfUtMethodInfo) {
		try {
			StringBuffer sb = new StringBuffer();
			String strValue = "";
			boolean isAllFind = true;
			for (int i = 0; i < cMethodInfo.getMethodParam().size(); i++) {
				MethodParamBody cParamInitBody = new MethodParamBody(cMethodInfo.getMethodParam().get(i).toString());
				//logger.debug(cParamInitBody.getParamType() + " " + cParamInitBody.getParamVariable());
				strValue = getFromNLP(cParamInitBody);
				isAllFind = GenerateMethodUtil.initParamValue(sb, cParamInitBody, strValue);
				if (!isAllFind) {
					break;
				}

			}

			//logger.debug(sb);
			if (isAllFind) {
				cJpfUtMethodInfo.setMethodParam(sb.toString());
			}
			return isAllFind;
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		}
	}

	/**
	 * 
	 * @category 根据声明产生初始化值
	 * @author 吴平福
	 * @param cMethodInfo
	 * @param cJpfUtInfo
	 * @return update 2018年1月23日
	 */
	public boolean initParamByNLPStrict(JpfMethodInfo cMethodInfo, JpfUtMethodInfo cJpfUtMethodInfo) {
		try {
			StringBuffer sb = new StringBuffer();
			String strValue = "";
			boolean isAllFind = true;
			for (int i = 0; i < cMethodInfo.getMethodParam().size(); i++) {
				MethodParamBody cParamInitBody = new MethodParamBody(cMethodInfo.getMethodParam().get(i).toString());
				logger.debug(cParamInitBody.getParamType() + " " + cParamInitBody.getParamVariable());
				strValue = getFromNLPStrict(cParamInitBody);
				isAllFind = GenerateMethodUtil.initParamValue(sb, cParamInitBody, strValue);
				if (!isAllFind) {
					break;
				}

			}

			logger.debug(sb);
			if (isAllFind) {
				cJpfUtMethodInfo.setMethodParam(sb.toString());
			}
			return isAllFind;
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		}
	}
	/**
	 * 
	 * @param strVariableName
	 * @return
	 */
	private String formatVariableName(String strVariableName) {
		strVariableName = strVariableName.replaceAll("_", "");
		strVariableName = strVariableName.replaceAll("-", "");
		if (strVariableName.startsWith("str")) {
			strVariableName = strVariableName.substring(3, strVariableName.length());
		}
		strVariableName = strVariableName.toUpperCase();
		return strVariableName;
	}
	/**
	 * 
	 * @author wupf@asiainfo.com
	 * @param cParamInitBody
	 * @return
	 */
	private String getFromNLPStrict(MethodParamBody cParamInitBody) {
		try {
			String strVariableName = formatVariableName(cParamInitBody.getParamVariable());
			Connection conn = DbServer.getInstance().getConn();
			String strSql = "SELECT * FROM sql_row_value s where  left(col_name," + strVariableName.length() + ")=\""
					+ strVariableName + "\" OR right(col_name, " + strVariableName.length() + ")=\"" + strVariableName
					+ "\" and col_type=\""+cParamInitBody.getParamType()+"\"";
			ResultSet rs = JpfDBUtil.ExecSqlQuery(conn, strSql);
			if (rs.next()) {
				return rs.getString("col_value");
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return "";
	}
	/**
	 * 
	 * @author wupf@asiainfo.com
	 * @param cParamInitBody
	 * @return
	 */
	private String getFromNLP(MethodParamBody cParamInitBody) {
		try {
			String strVariableName = formatVariableName(cParamInitBody.getParamVariable());
			Connection conn = DbServer.getInstance().getConn();
			String strSql = "SELECT * FROM sql_row_value s where  left(col_name," + strVariableName.length() + ")=\""
					+ strVariableName + "\" OR right(col_name, " + strVariableName.length() + ")=\"" + strVariableName
					+ "\"";
			ResultSet rs = JpfDBUtil.ExecSqlQuery(conn, strSql);
			if (rs.next()) {
				return rs.getString("col_value");
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return "";
	}


	public List<String> initParamByNLPs(JpfMethodInfo cMethodInfo) {
		List<String> mList = new ArrayList<String>();
		try {

			String strSql = "select ";
			for (int i = 0; i < cMethodInfo.getMethodParam().size(); i++) {
				strSql += "t" + i + ".col_value col_value" + i + ",";
			}
			if (strSql.endsWith(",")) {
				strSql = strSql.substring(0, strSql.length() - 1);
			}
			strSql += " from";

			logger.info(strSql);

			for (int i = 0; i < cMethodInfo.getMethodParam().size(); i++) {
				MethodParamBody cParamInitBody = new MethodParamBody(cMethodInfo.getMethodParam().get(i).toString());
				logger.debug(cParamInitBody.getParamType() + " " + cParamInitBody.getParamVariable());
				String strVariableName = formatVariableName(cParamInitBody.getParamVariable());
				strSql += "(SELECT * FROM sql_row_value s where  left(col_name," + strVariableName.length() + ")=\""
						+ strVariableName + "\" OR right(col_name, " + strVariableName.length() + ")=\""
						+ strVariableName + "\")t" + i + ",";
			}
			if (strSql.endsWith(",")) {
				strSql = strSql.substring(0, strSql.length() - 1);
			}
			logger.info(strSql);
			if (cMethodInfo.getMethodParam().size() > 1) {
				strSql += " where 1=1";
				for (int i = 1; i < cMethodInfo.getMethodParam().size(); i++) {
					strSql += " and t0.log_info_id=t" + i + ".log_info_id ";
				}
			}
			logger.info(strSql);

			Connection conn = DbServer.getInstance().getConn();
			ResultSet rs = JpfDBUtil.ExecSqlQuery(conn, strSql);
			while (rs.next()) {
				StringBuffer sb=new StringBuffer();
				for (int i = 0; i < cMethodInfo.getMethodParam().size(); i++) {
					MethodParamBody cParamInitBody = new MethodParamBody(cMethodInfo.getMethodParam().get(i).toString());
					logger.debug(cParamInitBody.getParamType() + " " + cParamInitBody.getParamVariable());
					
					GenerateMethodUtil.initParamValue(sb, cParamInitBody, rs.getString("col_value"+i));

				}
				logger.debug(sb);
				mList.add(sb.toString());	
				sb.setLength(0);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return mList;
	}
}
