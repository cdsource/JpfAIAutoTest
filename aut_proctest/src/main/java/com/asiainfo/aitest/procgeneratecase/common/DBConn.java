/**
 * copyrigth by wupf@asiainfo.com
 * 2018年8月10日
 */
package com.asiainfo.aitest.procgeneratecase.common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author wupf@asiainfo.com
 *
 */
public class DBConn {

    public DBConn()
    {
    }

    public String init(DBInfoObject dbConfig)
    {
        this.dbConfig = dbConfig;
        String errMsg = new String();
        try
        {
            if(conn == null)
            {
                Class.forName(this.dbConfig.getDriverName());
                conn = DriverManager.getConnection(this.dbConfig.getUrl(), this.dbConfig.getUserName(), this.dbConfig.getPassword());
            }
        }
        catch(ClassNotFoundException e)
        {
            errMsg = "Class not found :" + e.getMessage();
        }
        catch(SQLException e)
        {
            errMsg = e.getMessage();
        }
        return errMsg;
    }

    public synchronized Connection getConnection()
    {
        if(conn != null)
            return conn;
        else
            return null;
    }

    public void close()
    {
        try
        {
            if(conn != null)
            {
                conn.close();
                conn = null;
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }

    private DBInfoObject dbConfig;
    private Connection conn;

}
