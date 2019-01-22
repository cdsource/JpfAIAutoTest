/**
 * copyrigth by wupf@asiainfo.com
 * 2018年8月10日
 */
package com.asiainfo.aitest.procgeneratecase.common;

/**
 * @author wupf@asiainfo.com
 *
 */
public class DBInfoObject {

    public DBInfoObject(String driverName, String url, String userName, String password)
    {
        this.driverName = null;
        this.url = null;
        this.userName = null;
        this.password = null;
        dbType = null;
        this.driverName = driverName;
        this.url = url;
        this.userName = userName;
        this.password = password;
        if(driverName.toUpperCase().indexOf(Java_constants.DBTYPE_DB2) >= 0)
            dbType = Java_constants.DBTYPE_DB2;
        else
        if(driverName.toUpperCase().indexOf(Java_constants.DBTYPE_ORACLE) >= 0)
            dbType = Java_constants.DBTYPE_ORACLE;
        else
        if(driverName.toUpperCase().indexOf(Java_constants.DBTYPE_SQLSERVER2000) >= 0)
            dbType = Java_constants.DBTYPE_SQLSERVER2000;
        else
            dbType = "";
    }

    public DBInfoObject(DBInfoObject dbInfo)
    {
        driverName = null;
        url = null;
        userName = null;
        password = null;
        dbType = null;
        driverName = dbInfo.getDriverName();
        url = dbInfo.getUrl();
        userName = dbInfo.getUserName();
        password = dbInfo.getPassword();
    }

    public String getDbType()
    {
        if(dbType.length() == 0)
            if(driverName.toUpperCase().indexOf(Java_constants.DBTYPE_DB2.toUpperCase()) >= 0)
                dbType = Java_constants.DBTYPE_DB2;
            else
            if(driverName.toUpperCase().indexOf(Java_constants.DBTYPE_ORACLE.toUpperCase()) >= 0)
                dbType = Java_constants.DBTYPE_ORACLE;
            else
            if(driverName.toUpperCase().indexOf(Java_constants.DBTYPE_SQLSERVER2000.toUpperCase()) >= 0)
                dbType = Java_constants.DBTYPE_SQLSERVER2000;
            else
                dbType = "";
        return dbType;
    }

    public void setDriverName(String str)
    {
        driverName = str;
    }

    public void setUrl(String str)
    {
        url = str;
    }

    public void setUserName(String str)
    {
        userName = str;
    }

    public void setPassword(String str)
    {
        password = str;
    }

    public String getDriverName()
    {
        return driverName;
    }

    public String getUrl()
    {
        return url;
    }

    public String getUserName()
    {
        return userName;
    }

    public String getPassword()
    {
        return password;
    }

    private String driverName;
    private String url;
    private String userName;
    private String password;
    private String dbType;

}
