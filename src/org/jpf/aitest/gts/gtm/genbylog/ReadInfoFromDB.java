/**
 * 
 */
package org.jpf.aitest.gts.gtm.genbylog;

import java.sql.Connection;
import java.sql.ResultSet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jpf.aitest.JpfMethodInfo;
import org.jpf.aitest.JpfUtMethodInfo;
import org.jpf.aitest.ParamInitBody;
import org.jpf.aitest.dbs.DbServer;

import com.asiainfo.utils.dbsql.AiDBUtil;

/**
 * @author Administrator
 *
 */
public class ReadInfoFromDB {

	private static final Logger logger = LogManager.getLogger();

	/**
	 * 
	 */
	private ReadInfoFromDB() {

	}

	// 已经自行实例化
	private static final ReadInfoFromDB Instance = new ReadInfoFromDB();

	// 静态工厂方法
	public static ReadInfoFromDB getInstance() {
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
	public boolean initParamByDB(JpfMethodInfo cJpfMethodInfo, JpfUtMethodInfo cJpfUtMethodInfo) {
		try {
			StringBuffer sb = new StringBuffer();
			String strValue = "";
			
			boolean isAllFind = true;
			for (int i = 0; i < cJpfMethodInfo.getMethodParam().size(); i++) {
				ParamInitBody cParamInitBody = new ParamInitBody(cJpfMethodInfo.getMethodParam().get(i).toString());
				logger.debug(cJpfMethodInfo.getClassName() + "." + cJpfMethodInfo.getMethodName());
				strValue = getFromDB(cJpfMethodInfo.getClassName() + "." + cJpfMethodInfo.getMethodName(),
						cParamInitBody.getParamVariable());
				if (null == strValue || strValue.isEmpty()) {
					isAllFind = false;
					break;
				} else {
					sb.append("    ").append(cParamInitBody.getParamType()).append(" ")
							.append(cParamInitBody.getParamVariable()).append(" =  ");

					switch (cParamInitBody.getParamType()) {
					case "String":
						sb.append("\"").append(strValue).append("\"").append(";\n");
						break;
					case "int":
						sb.append(strValue).append(";\n");
						break;
					default:
						sb.append("(").append(cParamInitBody.getParamType()).append(")").append(strValue).append(";\n");
					}

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
	 * @param strMethodName
	 * @param strVariableName
	 * @return
	 */
	private String getFromDB(String strMethodName, String strVariableName) {
		try {
			strVariableName = strVariableName.trim();
			Connection conn = DbServer.getInstance().getConn();
			String strSql = "SELECT t1.col_name,t1.col_value FROM sql_row_value t1,sql_info t2 where t1.sql_info_id=t2.id and t2.exe_method='"
					+ strMethodName + "' and t1.col_name='" + strVariableName + "';";
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
	public static void main(String[] args)
	{
		
		ReadInfoFromDB.getInstance().getFromDB(null, null);
	}

}
