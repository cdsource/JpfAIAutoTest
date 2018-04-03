/**
 * @author 吴平福 E-mail:wupf@asiainfo.com
 * @version 创建时间：2018年2月6日 下午1:31:09 类说明
 */

package org.jpf.unittests.generateuts.genbylog;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jpf.gts.dbs.DbServer;
import org.jpf.unittests.generateuts.GenerateInputParam;

import com.asiainfo.utils.dbsql.AiDBUtil;


/**
 * 
 */
public class HandleLogFromDebugToDb {

    private static final Logger logger = LogManager.getLogger();

    private long lFindCount = 0;
    private long lRowCount=0;

    /**
     * 
     */
    public HandleLogFromDebugToDb(String strInputLogFilePath) {
        // TODO Auto-generated constructor stub
        // 2018-01-26 19:02:05,529 DEBUG [java.sql.PreparedStatement] - {pstm-100001} 北京移动电商
        long start = System.currentTimeMillis();
        try {
            new HandleLogFromDebug(GenerateInputParam.RUN_LOG_SOURCE_FILEPATH);
            saveToDb(ParamValueFromDebugLog.getInstance().getAllData());

        } catch (Exception ex) {
            // TODO: handle exception
            logger.error(ex);
            ex.printStackTrace();
        }
        logger.info("lFindCount " + lFindCount);
        logger.info("lRowCount " + lRowCount);
        logger.info("ExcuteTime " + (System.currentTimeMillis() - start) + "ms");
    }


    /**
     * @category @author 吴平福
     * @param args update 2018年1月26日
     */

    public static void main(String[] args) {
        // TODO Auto-generated method stub

        new HandleLogFromDebugToDb(GenerateInputParam.RUN_LOG_SOURCE_FILEPATH);

    }

    private void saveToDb(HashMap <String, LogCaseInfo> map)
    {
        Connection conn=null;
        try {
            conn=DbServer.getInstance().getConn();
            logger.info(conn==null);
            String strSql_SQL_INFO="insert into sql_info(SQL_STR,EXE_DATE) values(?,?) ";
            String strSql_SQL_ROW_VALUE="insert into sql_row_value(SQL_INFO_ID,COL_NAME,COL_VALUE) values(?,?,?) ";
            String strSql_Query="select ID from sql_info where sql_str=? and exe_date=?";
            
            Set<String> key = map.keySet();
            long iSQL_ID=0L;
            for (Iterator it = key.iterator(); it.hasNext();) {
                String strKeySql = (String) it.next();
                System.out.println(strKeySql);
                LogCaseInfo cLogCaseInfo=map.get(strKeySql);
                //logger.debug(strSql);
                PreparedStatement psmt1=conn.prepareStatement(strSql_SQL_INFO);
                psmt1.setString(1, strKeySql);
                psmt1.setString(2, cLogCaseInfo.getCaseTime());
                psmt1.executeUpdate();
                
                PreparedStatement psmt3 = conn.prepareStatement(strSql_Query);
                psmt3.setString(1, strKeySql);
                psmt3.setString(2, cLogCaseInfo.getCaseTime());
                
                // create table
                ResultSet rs=psmt3.executeQuery();
                if (rs.next())
                {
                    iSQL_ID=rs.getLong("ID");
                }
                
                Set<String> key2 = cLogCaseInfo.getParams().keySet();
                for (Iterator it2 = key2.iterator(); it2.hasNext();) {
                    String strColName = (String) it2.next();
                    PreparedStatement psmt2=conn.prepareStatement(strSql_SQL_ROW_VALUE);
                    psmt2.setLong(1, iSQL_ID);
                    psmt2.setString(2, strColName);
                    psmt2.setString(3, cLogCaseInfo.getParams().get(strColName) );
                    psmt2.executeUpdate();
                    lRowCount++;
                }
                lFindCount++;
            }
            if (conn.getAutoCommit()==false)
            {
            	conn.commit();
            }
        } catch (Exception ex) {
            // TODO: handle exception
            ex.printStackTrace();
            logger.error(ex);
        }finally {
            AiDBUtil.doClear(conn);
        }
    }

}
