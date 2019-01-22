/**
 * @author 吴平福 E-mail:wupf@asiainfo.com
 * @version 创建时间：2018年2月4日 下午4:27:00 类说明
 */

package org.jpf.aut.gts.gtm.genbylog;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.jpf.aut.base.JpfMethodInfo;
import org.jpf.aut.base.JpfUtInfo;
import org.jpf.aut.gts.gtm.MethodParamBody;
import org.jpf.aut.utils.DbServer;
import org.jpf.aut.utils.GenerateUtil2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 
 */
public class GenParamValueFromLogByDb {

	private static final Logger logger = LogManager.getLogger();

	private int iMaxCaseCount = 3;

	// 已经自行实例化
	private static final GenParamValueFromLogByDb Instance = new GenParamValueFromLogByDb();

	List<RunLogInfo> mList = new ArrayList<>();

	String[] initValues = new String[iMaxCaseCount];

	// 静态工厂方法
	public static GenParamValueFromLogByDb getInstance() {
		return Instance;
	}

	private StringBuffer add(MethodParamBody cParamInitBody, String strValue) {
		StringBuffer sBuffer = new StringBuffer();
		switch (cParamInitBody.getParamType()) {
		case "int":
			sBuffer.append("  ").append(cParamInitBody.getParamVariable()).append("=").append(strValue).append(";\n");

			break;
		case "long":
			if (null != strValue) {
				sBuffer.append("  ").append(cParamInitBody.getParamVariable()).append("=").append(strValue)
						.append("L;\n");
			} else {
				sBuffer.append("  ").append(cParamInitBody.getParamVariable()).append("=").append(1L).append(";\n");
			}
			break;
		case "String":
			if (null != strValue) {
				sBuffer.append("  ").append(cParamInitBody.getParamVariable()).append("=\"").append(strValue)
						.append("\";\n");
			} else {
				sBuffer.append("  ").append(cParamInitBody.getParamVariable()).append("=\"").append("abc")
						.append("\";\n");
			}
			break;

		default:

			break;

		}
		return sBuffer;
	}

	/**
	 * 
	 * @param strFullClassName
	 * @param cMethodInfo
	 * @return
	 */
	private String genParamValue(MethodParamBody cParamInitBody) {
		int iFindCount = 0;
		for (int i = 0; i < mList.size(); i++) {
			RunLogInfo cRunLogInfo = mList.get(i);
			if (cRunLogInfo.getName().equalsIgnoreCase(cParamInitBody.getParamVariable())) {
				initValues[iFindCount] = initValues[iFindCount] + add(cParamInitBody, cRunLogInfo.getValue());
				iFindCount++;
				i--;
			}

		}
		return "";
	}

	private void doClean() {
		mList.clear();

		for (int j = 0; j < initValues.length - 1; j++) {
			initValues[j] = "";
		}
	}

	/**
	 * 
	 * @param cJpfUtInfo
	 * @param strFullClassName
	 * @param cMethodInfo
	 * @return
	 */
	public String[] genParamValue(JpfUtInfo cJpfUtInfo, String strFullClassName, JpfMethodInfo cJpfMethodInfo) {
		logger.info(strFullClassName);

		String strSql = "select * from sql_row_value t1 where LOG_INFO_ID in"
				+ "(select id from (select id from log_info where EXE_METHOD= ?   order by EXE_DATE desc limit "
				+ iMaxCaseCount + ")b) order by LOG_INFO_ID;";

		doClean();

		Connection conn = null;
		try {
			conn = DbServer.getInstance().getConn();
			PreparedStatement psmt1 = conn.prepareStatement(strSql);
			psmt1.setString(1, strFullClassName);

			ResultSet rs = psmt1.executeQuery();
			int id = 0;
			while (rs.next()) {
				RunLogInfo cRunLogInfo = new RunLogInfo();
				cRunLogInfo.setId(rs.getInt("LOG_INFO_ID"));
				cRunLogInfo.setName(rs.getString("COL_NAME"));
				cRunLogInfo.setValue(rs.getString("COL_value"));
				mList.add(cRunLogInfo);
			}

			for (int i = 0; i < cJpfMethodInfo.getMethodParam().size(); i++) {
				logger.debug(cJpfMethodInfo.getMethodParam().get(i).toString());
				MethodParamBody cParamInitBody = new MethodParamBody(cJpfMethodInfo.getMethodParam().get(i).toString());
				logger.debug(cParamInitBody.getParamType());

					genParamValue(cParamInitBody);

			}

		} catch (
		Exception ex) {
			// TODO: handle exception
			ex.printStackTrace();
		}
		logger.debug(initValues);
		return initValues;
	}


}
