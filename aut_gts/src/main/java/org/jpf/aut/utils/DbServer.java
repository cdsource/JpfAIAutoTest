/**
 * @author 吴平福 E-mail:wupf@asiainfo.com
 * @version 创建时间：2018年2月6日 下午1:47:06 类说明
 */
package org.jpf.aut.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author Administrator
 *
 */
public class DbServer {


    private static final Logger logger = LogManager.getLogger();
    // 已经自行实例化
    private static final DbServer Instance = new DbServer();

    // 静态工厂方法
    public static DbServer getInstance() {
        return Instance;
        
    }

    public static void main(String[] args) {
        try {
            getInstance().getConn();
        } catch (Exception ex) {
            // TODO: handle exception
            ex.printStackTrace();
            logger.error(ex);
        }

    }

    private Connection conn = null;

    public Connection getConn() throws Exception {
        if (null == conn) {
			String driver = "com.mysql.jdbc.Driver";
			String dburl = "jdbc:mysql://127.0.0.1:3306/aitest";//
			String dbusr = "sonar";//
			String dbpwd = "wupflove";//
			/*
			String dburl = "jdbc:mysql://20.26.33.105:3306/aicode";//
			String dbusr = "aitest";//
			String dbpwd = "R00tadmin!";//
			*/
			logger.info("DB URL:" + driver);
			logger.info("DB URL:" + dburl);
			Class.forName(driver).newInstance();

			conn = DriverManager.getConnection(dburl, dbusr, dbpwd);
			
            init();

        }
        return conn;
    }

    public void init() {
        try {

            Statement stat = conn.createStatement();

            // create table
            stat.execute("CREATE TABLE IF NOT EXISTS log_info  (" + 
            		"  LOG_STR varchar(2000) NOT NULL," + 
            		"  EXE_DATE varchar(45) NOT NULL," + 
            		"  ID int(10) unsigned NOT NULL AUTO_INCREMENT," + 
            		"  STATE_DATE timestamp NULL DEFAULT CURRENT_TIMESTAMP," + 
            		"  log_DESC varchar(256) DEFAULT NULL," + 
            		"  EXE_METHOD varchar(256) DEFAULT NULL," + 
            		"  PRIMARY KEY (ID)," + 
            		"  KEY Index_2 (EXE_METHOD)" + 
            		") ENGINE=InnoDB  DEFAULT CHARSET=utf8;");

            stat.execute("CREATE TABLE IF NOT EXISTS  nlp_base_key (" + 
            		"  key_name varchar(32) NOT NULL," + 
            		"  source_name varchar(64) NOT NULL," + 
            		"  PRIMARY KEY (key_name)" + 
            		") ENGINE=InnoDB DEFAULT CHARSET=utf8;");
            
            stat.execute("CREATE TABLE IF NOT EXISTS selflog_info (" + 
            		"  LOG_STR varchar(2000) NOT NULL," + 
            		"  EXE_DATE varchar(45) DEFAULT NULL," + 
            		"  ID int(10) unsigned NOT NULL AUTO_INCREMENT," + 
            		"  STATE_DATE timestamp NULL DEFAULT CURRENT_TIMESTAMP," + 
            		"  log_DESC varchar(256) DEFAULT NULL," + 
            		"  EXE_METHOD varchar(256) NOT NULL," + 
            		"  LOG_INFO_ID int(10) unsigned DEFAULT NULL," + 
            		"  PRIMARY KEY (ID)," + 
            		"  KEY Index_2 (EXE_METHOD)" + 
            		") ENGINE=InnoDB  DEFAULT CHARSET=utf8");
            
            stat.execute("CREATE TABLE IF NOT EXISTS sql_info (" + 
            		"  SQL_STR varchar(2000) NOT NULL," + 
            		"  EXE_DATE varchar(45) NOT NULL," + 
            		"  EXE_METHOD varchar(300) DEFAULT NULL," + 
            		"  GUESS_CLASS varchar(300) DEFAULT NULL," + 
            		"  ID bigint(20) NOT NULL AUTO_INCREMENT," + 
            		"  STATE_DATE timestamp NULL DEFAULT CURRENT_TIMESTAMP," + 
            		"  PRIMARY KEY (ID)" + 
            		") ENGINE=InnoDB DEFAULT CHARSET=utf8");
            
            
            stat.execute("CREATE TABLE IF NOT EXISTS sql_row_value (" + 
            		"  ID int(10) unsigned NOT NULL AUTO_INCREMENT," + 
            		"  LOG_INFO_ID int(10) unsigned NOT NULL," + 
            		"  COL_NAME varchar(80) NOT NULL," + 
            		"  COL_VALUE mediumtext NOT NULL," + 
            		"  STATE_DATE timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP," + 
            		"  COL_TYPE varchar(45) DEFAULT NULL," + 
            		"  PRIMARY KEY (ID)," + 
            		"  KEY Index_2 (LOG_INFO_ID)," + 
            		"  KEY Index_3 (COL_NAME)" + 
            		") ENGINE=InnoDB  DEFAULT CHARSET=utf8");
            logger.info("create table succ");
            ResultSet rs=stat.executeQuery("select * from sql_info");
            while(rs.next())
            {
                System.out.println(rs.getString("SQL_STR"));
                System.out.println(rs.getString("EXE_DATE"));
            }
            stat.close();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
