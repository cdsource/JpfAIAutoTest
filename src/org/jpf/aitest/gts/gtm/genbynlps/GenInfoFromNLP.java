/**
 * 
 */
package org.jpf.aitest.gts.gtm.genbynlps;

import java.sql.Connection;
import java.sql.ResultSet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jpf.aitest.JpfMethodInfo;
import org.jpf.aitest.JpfUtMethodInfo;
import org.jpf.aitest.dbs.DbServer;
import org.jpf.aitest.gts.gtm.GenerateMethodUtil;
import org.jpf.aitest.gts.gtm.MethodParamBody;

import com.asiainfo.utils.dbsql.AiDBUtil;

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
				strValue = getFromNLP(cParamInitBody);
					/*
					sb.append("    ").append(cParamInitBody.getParamType()).append(" ")
							.append(cParamInitBody.getParamVariable()).append(" =  ").append(strValue).append(";\n");
							*/
					isAllFind=GenerateMethodUtil.initParamValue(sb, cParamInitBody, strValue);
					if (!isAllFind)
					{
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
	private String getFromNLP(MethodParamBody cParamInitBody) {
		try {
			String strVariableName = formatVariableName(cParamInitBody.getParamVariable());
			Connection conn = DbServer.getInstance().getConn();
			String strSql = "SELECT * FROM aicode.sql_row_value s where  LOCATE('" + strVariableName + "',col_name)>0";
			ResultSet rs = AiDBUtil.ExecSqlQuery(conn, strSql);
			if (rs.next()) {
				return rs.getString("col_value");
			}
			strSql = "SELECT * FROM aicode.sql_row_value s where LOCATE(col_name,'" + strVariableName + "')>0";
			rs = AiDBUtil.ExecSqlQuery(conn, strSql);
			if (rs.next()) {
				return rs.getString("col_value");
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return "";
	}

}
