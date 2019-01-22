/**
 * copyrigth by wupf@asiainfo.com
 * 2018年8月10日
 */
package com.asiainfo.aitest.procgeneratecase.common;

import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

/**
 * @author wupf@asiainfo.com
 *
 */
public class SPProcessSample
{

    public SPProcessSample(DBInfoObject dbConfig)
    {
        db = new DBConn();
        dbConn = null;
        errMsg = null;
        durationFormat = NumberFormat.getInstance(Locale.US);
        if(dbConfig == null)
        {
            dbConfig = new DBInfoObject(dbConfig);
        } else
        {
            dbConfig.setDriverName(dbConfig.getDriverName());
            dbConfig.setUrl(dbConfig.getUrl());
            dbConfig.setUserName(dbConfig.getUserName());
            dbConfig.setPassword(dbConfig.getPassword());
        }
        isResultSetNeeded = false;
        db.init(dbConfig);
    }

    public SPProcessSample(String driver, String url, String user, String pwd)
    {
        db = new DBConn();
        dbConn = null;
        errMsg = null;
        durationFormat = NumberFormat.getInstance(Locale.US);
        if(dbConfig == null)
        {
            dbConfig = new DBInfoObject(driver, url, user, pwd);
        } else
        {
            dbConfig.setDriverName(driver);
            dbConfig.setUrl(url);
            dbConfig.setUserName(user);
            dbConfig.setPassword(pwd);
        }
        isResultSetNeeded = false;
        db.init(dbConfig);
    }

    public StoredProcedureInfo getSPParmList(String dbType, String routineSchema, String routineName)
        throws Exception
    {
        String strSQL = null;
        if(Java_constants.DBTYPE_DB2.equals(dbType))
        {
            strSQL = "select a.routineSchema,a.routineName,b.routineType,a.parmName,a.rowType,a.typeName,a.length,a.scale from SYSCAT.ROUTINEPARMS a,SYSCAT.ROUTINES b ";
            strSQL = strSQL + " where a.routineSchema = b.routineSchema and a.routineName = b.routineName and a.routineName='" + routineName.toUpperCase() + "' and a.routineschema='" + routineSchema.toUpperCase() + "' order by a.ORDINAL asc";
        } else
        if(Java_constants.DBTYPE_ORACLE.equals(dbType))
        {
            strSQL = " SELECT '' AS routineSchema,               ";
            strSQL = strSQL + "        a.OBJECT_NAME as routineName,     ";
            strSQL = strSQL + "        decode(b.OBJECT_TYPE,'PROCEDURE','P','FUNCTION','F') AS routineType, ";
            strSQL = strSQL + "        a.ARGUMENT_NAME as parmName, ";
            strSQL = strSQL + "        a.DATA_TYPE as typeName,     ";
            strSQL = strSQL + "        decode(a.IN_OUT,'IN','P','OUT','O') as rowType,";
            strSQL = strSQL + "        a.DATA_LENGTH as length,             ";
            strSQL = strSQL + "        a.DATA_SCALE as scale                ";
            strSQL = strSQL + " FROM USER_ARGUMENTS a ,USER_OBJECTS b WHERE a.OBJECT_NAME=b.OBJECT_NAME AND upper(a.OBJECT_NAME)='" + routineName.toUpperCase() + "' ";
            strSQL = strSQL + " order by a.position asc ";
        } else
        if(Java_constants.DBTYPE_SQLSERVER2000.equals(dbType))
        {
            strSQL = " select 'routineSchema'       = b.name,";
            strSQL = strSQL + "        'routineName'         = c.name,";
            strSQL = strSQL + "        'routineType'         = case when c.type ='P' then 'P' else 'F' end,";
            strSQL = strSQL + "        'parmName'            = a.name,";
            strSQL = strSQL + "        'rowType'             = case when a.isoutparam =1 then 'O' else 'P' end,";
            strSQL = strSQL + "        'typeName'            = type_name(a.xusertype),";
            strSQL = strSQL + "        'length'              = a.length,";
            strSQL = strSQL + "        'scale'               = OdbcScale(a.xtype,a.xscale)";
            strSQL = strSQL + " from syscolumns a , sysobjects c , sysusers b where upper(c.name) = '" + routineName.toUpperCase() + "' and c.uid = b.uid and a.id = c.id ";
            strSQL = strSQL + " order by a.colid asc ";
        }
        Statement stmt = null;
        ResultSet dbRslt = null;
        ArrayList pmVOList = new ArrayList();
        StoredProcedureInfo spInfo = null;
        int maxRetries = 3;
        int retryCount = 0;
        boolean retry = true;
        boolean set = false;
        spInfo = new StoredProcedureInfo();
        if(routineSchema != null)
            spInfo.setRoutineSchema(routineSchema.toUpperCase());
        else
            spInfo.setRoutineSchema("");
        if(routineName != null)
        {
            spInfo.setRoutineName(routineName);
        } else
        {
            spInfo.setRoutineName("");
            return spInfo;
        }
        while(retry && retryCount <= maxRetries) 
        {
            retry = false;
            try
            {
                dbConn = db.getConnection();
                if(dbConn == null)
                    break;
                stmt = dbConn.createStatement();
                boolean bResults = stmt.execute(strSQL);
                dbRslt = stmt.getResultSet();
                if(bResults)
                {
                    ParmInfo pmInfo;
                    for(; dbRslt != null && dbRslt.next(); pmVOList.add(pmInfo))
                    {
                        pmInfo = new ParmInfo();
                        if(!set)
                        {
                            spInfo.setRoutineSchema(dbRslt.getString("routineSchema"));
                            spInfo.setRoutineName(dbRslt.getString("routineName"));
                            spInfo.setRoutineType(dbRslt.getString("routineType"));
                            set = true;
                        }
                        pmInfo.setParmName(dbRslt.getString("parmName"));
                        pmInfo.setRowType(dbRslt.getString("rowType"));
                        pmInfo.setTypeName(dbRslt.getString("typeName"));
                        pmInfo.setLength(dbRslt.getInt("length"));
                        pmInfo.setScale(dbRslt.getInt("scale"));
                    }

                } else
                {
                    pmVOList = null;
                }
                spInfo.setParmList(pmVOList);
            }
            catch(Exception e)
            {
                e.printStackTrace();
                theThrowable = e;
                errMsg = new String(e.getMessage());
            }
            finally
            {
                if(dbRslt != null)
                    dbRslt.close();
                if(stmt != null)
                    stmt.close();
            }
        }
        return spInfo;
    }

    public void closeDB()
    {
        try
        {
            Connection dbConn = db.getConnection();
            if(dbConn != null)
                dbConn.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public int runSP(StoredProcedureInfo spInfo)
        throws Exception
    {
        StringBuffer strTemp = new StringBuffer();
        errMsg = null;
        int rows = 0;
        String strSQL = new String();
        if("P".equalsIgnoreCase(spInfo.getRoutineType()))
        {
            strTemp.append("{");
            strTemp.append("CALL ");
            strTemp.append(spInfo.getSPName());
            strTemp.append(" (");
        } else
        if("F".equalsIgnoreCase(spInfo.getRoutineType()))
            return runUDF(spInfo);
        String tmp = "";
        for(int i = 0; i < spInfo.getParmList().size(); i++)
            tmp = tmp + "?,";

        strTemp.append(tmp);
        if("P".equalsIgnoreCase(spInfo.getRoutineType()))
            strSQL = strTemp.substring(0, strTemp.length() - 1) + ")";
        strSQL = strSQL + "}";
        CallableStatement callStmt = null;
        ResultSet rs = null;
        int maxRetries = 3;
        int retryCount = 0;
        boolean retry = true;
        long currentTestStartTime = System.currentTimeMillis();
        while(retry && retryCount <= maxRetries) 
        {
            retry = false;
            outputParamPos = new ArrayList();
            try
            {
                dbConn = db.getConnection();
                callStmt = dbConn.prepareCall(strSQL);
                ArrayList pmList = new ArrayList(spInfo.getParmList());
                for(int i = 0; i < pmList.size(); i++)
                {
                    ParmInfo pmInfo = (ParmInfo)pmList.get(i);
                    StoredProcedureInfo spInfoTemp = new StoredProcedureInfo();
                    int j = i + 1;
                    if(pmInfo.getRowType().equals("P"))
                    {
                        if(pmInfo.getType() == 12 || pmInfo.getType() == 1)
                            callStmt.setString(j, (String)pmInfo.getParmValue());
                        else
                        if(pmInfo.getType() == 4 || pmInfo.getType() == -5 || pmInfo.getType() == 5 || pmInfo.getType() == 2)
                            callStmt.setInt(j, (new Integer(pmInfo.getParmValue().toString())).intValue());
                        else
                        if(pmInfo.getType() == 3 || pmInfo.getType() == 7)
                            callStmt.setFloat(j, (new Float((String)pmInfo.getParmValue())).floatValue());
                        else
                        if(pmInfo.getType() == 8)
                            callStmt.setDouble(j, (new Double((String)pmInfo.getParmValue())).doubleValue());
                        else
                        if(pmInfo.getType() == 91)
                            callStmt.setDate(j, Date.valueOf(pmInfo.getParmValue().toString()));
                        else
                        if(pmInfo.getType() == 92)
                            callStmt.setTime(j, Time.valueOf(pmInfo.getParmValue().toString()));
                        else
                        if(pmInfo.getType() == 93)
                            callStmt.setTimestamp(j, Timestamp.valueOf((String)pmInfo.getParmValue()));
                        else
                        if(pmInfo.getType() == 2004 || pmInfo.getType() == 2005)
                            callStmt.setBlob(j, (Blob)pmInfo.getParmValue());
                        else
                        if(pmInfo.getType() == 2006)
                            callStmt.setRef(j, (Ref)pmInfo.getParmValue());
                        else
                            callStmt.setObject(j, pmInfo.getParmValue());
                        continue;
                    }
                    if(pmInfo.getRowType().equals("O"))
                    {
                        outputParamPos.add(new Integer(i + 1));
                        callStmt.registerOutParameter(j, pmInfo.getType());
                        continue;
                    }
                    if(pmInfo.getRowType().equals("B"))
                    {
                        callStmt.registerOutParameter(j, pmInfo.getType());
                        continue;
                    }
                    rows = 0;
                    break;
                }

                durationTime = 0L;
                currentTestStartTime = System.currentTimeMillis();
                boolean bResults = callStmt.execute();
                durationTime = System.currentTimeMillis() - currentTestStartTime;
                outputValues = new ArrayList(outputParamPos.size());
                for(int i = 0; i < outputParamPos.size(); i++)
                    outputValues.add(callStmt.getObject((new Integer(outputParamPos.get(i).toString())).intValue()));

                if(spInfo.getRowsReturnedParmId() > 0)
                {
                    rows = callStmt.getInt(spInfo.getRowsReturnedParmId());
                } else
                {
                    rs = callStmt.getResultSet();
                    if(isResultSetNeeded)
                    {
                        resultSets = new ArrayList();
                        resultSets.add(rs);
                    }
                    if(rs != null)
                        while(rs.next()) 
                            rows++;
                    if(isResultSetNeeded)
                        for(; callStmt.getMoreResults() || callStmt.getUpdateCount() != -1; resultSets.add(rs))
                            rs = callStmt.getResultSet();

                }
            }
            catch(SQLException e)
            {
                errMsg = new String(e.getMessage());
                theThrowable = e;
                e.printStackTrace();
                durationTime = System.currentTimeMillis() - currentTestStartTime;
            }
            catch(Exception e)
            {
                errMsg = new String(e.toString());
                theThrowable = e;
                e.printStackTrace();
                durationTime = System.currentTimeMillis() - currentTestStartTime;
            }
            finally
            {
                if(callStmt != null)
                    callStmt.close();
            }
        }
        return rows;
    }

    public int runUDF(StoredProcedureInfo spInfo)
        throws Exception
    {
        StringBuffer strTemp = new StringBuffer();
        int rows = 0;
        String strSQL = new String();
        strTemp.append("select ");
        strTemp.append(spInfo.getSPName());
        strTemp.append(" (");
        String tmp = "";
        ArrayList pmList = new ArrayList(spInfo.getParmList());
        long currentTestStartTime = System.currentTimeMillis();
        for(int i = 0; i < pmList.size(); i++)
        {
            ParmInfo pmInfo = (ParmInfo)pmList.get(i);
            StoredProcedureInfo spInfoTemp = new StoredProcedureInfo();
            int j = i + 1;
            if(pmInfo.getType() == 12 || pmInfo.getType() == 1)
            {
                strTemp.append("'");
                strTemp.append((String)pmInfo.getParmValue());
                strTemp.append("',");
            } else
            if(pmInfo.getType() == 4 || pmInfo.getType() == -5 || pmInfo.getType() == 5)
            {
                strTemp.append(pmInfo.getParmValue().toString());
                strTemp.append(",");
            } else
            if(pmInfo.getType() == 3 || pmInfo.getType() == 7)
            {
                strTemp.append(pmInfo.getParmValue().toString());
                strTemp.append(",");
            } else
            if(pmInfo.getType() == 8)
            {
                strTemp.append(pmInfo.getParmValue().toString());
                strTemp.append(",");
            } else
            if(pmInfo.getType() == 2)
            {
                strTemp.append(pmInfo.getParmValue().toString());
                strTemp.append(",");
            } else
            if(pmInfo.getType() == 91)
            {
                strTemp.append(pmInfo.getParmValue().toString());
                strTemp.append(",");
            } else
            if(pmInfo.getType() == 92)
            {
                strTemp.append(pmInfo.getParmValue().toString());
                strTemp.append(",");
            } else
            if(pmInfo.getType() == 93)
            {
                strTemp.append(pmInfo.getParmValue().toString());
                strTemp.append(",");
            } else
            {
                strTemp.append("'");
                strTemp.append(pmInfo.getType());
                strTemp.append("',");
            }
        }

        strSQL = strTemp.substring(0, strTemp.length() - 1) + ")  as value from  sysibm.sysdummy1";
        Statement callStmt = null;
        ResultSet rs = null;
        int maxRetries = 3;
        int retryCount = 0;
        for(boolean retry = true; retry && retryCount <= maxRetries;)
        {
            retry = false;
            outputParamPos = new ArrayList();
            try
            {
                dbConn = db.getConnection();
                callStmt = dbConn.createStatement();
                durationTime = 0L;
                currentTestStartTime = System.currentTimeMillis();
                rs = callStmt.executeQuery(strSQL);
                durationTime = System.currentTimeMillis() - currentTestStartTime;
                if(rs.next())
                    returnedValue = rs.getObject("value");
                rs.close();
            }
            catch(SQLException e)
            {
                durationTime = System.currentTimeMillis() - currentTestStartTime;
                errMsg = e.getMessage();
                e.printStackTrace();
            }
            catch(Exception e)
            {
                durationTime = System.currentTimeMillis() - currentTestStartTime;
                errMsg = e.getMessage();
                e.printStackTrace();
            }
            finally
            {
                if(rs != null)
                    rs.close();
                if(callStmt != null)
                    callStmt.close();
            }
        }

        return 0;
    }

    public Object getReturnedObject(int parmIndex)
    {
        boolean found = false;
        for(int i = 0; i < outputParamPos.size();)
        {
            if(parmIndex == (new Integer(outputParamPos.get(i).toString())).intValue())
                found = true;
            break;
        }

        if(found)
            return outputValues.get(parmIndex);
        else
            return null;
    }

    public static StringBuffer replace(StringBuffer code, String src, String des)
    {
        for(int pos = 0; (pos = code.toString().indexOf(src)) >= 0;)
        {
            code = code.delete(pos, pos + src.length());
            code = code.insert(pos, des);
        }

        return code;
    }

    public String getErrMsg()
    {
        if(errMsg != null)
        {
            errMsg.replace('"', '\'');
            errMsg = replace(new StringBuffer(errMsg), "<", "&lt;").toString();
            errMsg = replace(new StringBuffer(errMsg), ">", "&gt;").toString();
        }
        return errMsg;
    }

    public Object getReturnedValue()
    {
        return returnedValue;
    }

    public Throwable getTheThrowable()
    {
        return theThrowable;
    }

    public void setTheThrowable(Throwable throwable)
    {
        theThrowable = throwable;
    }

    public String getDurationTime()
    {
        return durationFormat.format((double)durationTime / 1000D);
    }

    public boolean isResultSetNeeded()
    {
        return isResultSetNeeded;
    }

    public void setResultSetNeeded(boolean b)
    {
        isResultSetNeeded = b;
    }

    public ArrayList getResultSets()
    {
        if(isResultSetNeeded)
            return resultSets;
        else
            return null;
    }

    DBConn db;
    Connection dbConn;
    DBInfoObject dbConfig;
    private String errMsg;
    private Throwable theThrowable;
    private Object returnedValue;
    private boolean isResultSetNeeded;
    private ArrayList outputValues;
    private ArrayList resultSets;
    private ArrayList outputParamPos;
    private long durationTime;
    private NumberFormat durationFormat;
}
