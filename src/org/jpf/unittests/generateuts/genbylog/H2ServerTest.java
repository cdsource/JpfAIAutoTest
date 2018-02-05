/**
 * @author 吴平福 E-mail:wupf@asiainfo.com
 * @version 创建时间：2018年2月6日 下午1:47:06 类说明
 */

package org.jpf.unittests.generateuts.genbylog;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.naming.spi.DirStateFactory.Result;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class H2ServerTest {

    private static final Logger logger = LogManager.getLogger();
    // 已经自行实例化
    private static final H2ServerTest Instance = new H2ServerTest();

    // 静态工厂方法
    public static H2ServerTest getInstance() {
        return Instance;
    }

    public static void main(String[] args) {
        try {
            getInstance().getConn();
            getInstance().getConn();
            getInstance().getConn();
            getInstance().getConn();
        } catch (Exception ex) {
            // TODO: handle exception
            ex.printStackTrace();
        }

    }


    private Connection conn = null;

    public Connection getConn() throws Exception {
        if (null == conn) {
            Class.forName("org.h2.Driver");
            conn = DriverManager.getConnection("jdbc:h2:./h2db/jfptest", "sa", "wupflove");
            init();

        }
        return conn;
    }

    public void init() {
        try {

            Statement stat = conn.createStatement();

            // create table
            stat.execute("CREATE TABLE IF NOT EXISTS  sql_info (" + "  SQL_STR varchar(2000) NOT NULL,"
                    + "  EXE_DATE varchar(45) NOT NULL," + "  EXE_METHOD varchar(300) DEFAULT NULL,GUESS_CLASS varchar(300) DEFAULT NULL,"
                    + "  ID bigint primary key auto_increment)  ");

            stat.execute("CREATE TABLE  IF NOT EXISTS sql_row_value (" + "  ID bigint primary key auto_increment,"
                    + "  SQL_INFO_ID int  NOT NULL," + "  COL_NAME varchar(80) NOT NULL,"
                    + "  COL_VALUE varchar(1024) NOT NULL) ");
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
