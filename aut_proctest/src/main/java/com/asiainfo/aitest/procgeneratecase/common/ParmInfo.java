/**
 * copyrigth by wupf@asiainfo.com
 * 2018年8月10日
 */
package com.asiainfo.aitest.procgeneratecase.common;

import java.util.Hashtable;

/**
 * @author wupf@asiainfo.com
 *
 */
public class ParmInfo {

    public ParmInfo()
    {
        initTypeMap();
    }

    public ParmInfo(String parmName, String rowType, String typeName, Object parmValue)
    {
        initTypeMap();
        this.parmName = parmName;
        this.rowType = rowType;
        this.typeName = typeName;
        this.parmValue = parmValue;
    }

    public ParmInfo(String parmName, String rowType, String typeName)
    {
        initTypeMap();
        this.parmName = parmName;
        this.rowType = rowType;
        this.typeName = typeName;
    }

    private void initTypeMap()
    {
        typeMap = new Hashtable();
        typeMap.put("BIGINT", new Integer(-5));
        typeMap.put("NUMBER", new Integer(2));
        typeMap.put("NUMERIC", new Integer(2));
        typeMap.put("INTEGER", new Integer(4));
        typeMap.put("INT", new Integer(4));
        typeMap.put("SMALLINT", new Integer(5));
        typeMap.put("DECIMAL", new Integer(3));
        typeMap.put("REAL", new Integer(7));
        typeMap.put("DOUBLE", new Integer(8));
        typeMap.put("BINARY", new Integer(-2));
        typeMap.put("VARBINARY", new Integer(-3));
        typeMap.put("BOOLEAN", new Integer(12));
        typeMap.put("CHAR", new Integer(1));
        typeMap.put("NCHAR", new Integer(12));
        typeMap.put("VARCHAR", new Integer(12));
        typeMap.put("NVARCHAR", new Integer(12));
        typeMap.put("VARCHAR2", new Integer(12));
        typeMap.put("CHARACTER", new Integer(1));
        typeMap.put("TEXT", new Integer(-1));
        typeMap.put("LONG VARCHAR", new Integer(-1));
        typeMap.put("BLOB", new Integer(2004));
        typeMap.put("CLOB", new Integer(2005));
        typeMap.put("DATE", new Integer(91));
        typeMap.put("TIME", new Integer(92));
        typeMap.put("TIMESTAMP", new Integer(93));
        typeMap.put("REFERENCE", new Integer(2006));
        typeMap.put("DATALINK", new Integer(2006));
        typeDefaultValue = new Hashtable();
        typeDefaultValue.put("SMALLINT", "new Integer(0)");
        typeDefaultValue.put("TINYINT", "new Integer(0)");
        typeDefaultValue.put("INT", "new Integer(0)");
        typeDefaultValue.put("BIGINT", "new Integer(0)");
        typeDefaultValue.put("INTEGER", "new Integer(0)");
        typeDefaultValue.put("NUMERIC", "new Integer(0)");
        typeDefaultValue.put("BOOLEAN", "true");
        typeDefaultValue.put("CHARACTER", "\"\"");
        typeDefaultValue.put("CHAR", "\"\"");
        typeDefaultValue.put("NCHAR", "\"\"");
        typeDefaultValue.put("VARCHAR", "\"\"");
        typeDefaultValue.put("NVARCHAR", "\"\"");
        typeDefaultValue.put("VARCHAR2", "\"\"");
        typeDefaultValue.put("TEXT", "\"\"");
        typeDefaultValue.put("BINARY", "null");
        typeDefaultValue.put("MONEY", "new Integer(0)");
        typeDefaultValue.put("SMALLMONEY", "new Integer(0)");
        typeDefaultValue.put("BLOB", "new Object()");
        typeDefaultValue.put("CLOB", "new Object()");
        typeDefaultValue.put("DATE", "new Date(0,0,1)");
        typeDefaultValue.put("DECIMAL", "new Float(0)");
        typeDefaultValue.put("NUMBER", "new Float(0)");
        typeDefaultValue.put("DOUBLE", "new Double(0)");
        typeDefaultValue.put("LONG VARCHAR", "\"\"");
        typeDefaultValue.put("DATALINK", "new Object()");
        typeDefaultValue.put("REAL", "new Float(0)");
        typeDefaultValue.put("REFERENCE", "new Object()");
        typeDefaultValue.put("TIME", "new Time(0,0,0)");
        typeDefaultValue.put("TIMESTAMP", "new Timestamp(0,0,0,0,0,0,0)");
    }

    public void setParmName(String parmName)
    {
        this.parmName = parmName;
    }

    public void setRowType(String rowType)
    {
        this.rowType = rowType;
    }

    public void setTypeName(String typeName)
    {
        this.typeName = typeName;
    }

    public void setLength(int length)
    {
        this.length = length;
    }

    public void setScale(int scale)
    {
        this.scale = scale;
    }

    public void setParmValue(Object value)
    {
        parmValue = value;
    }

    public String getParmName()
    {
        return parmName;
    }

    public String getDefaultValue()
    {
        return (String)typeDefaultValue.get(typeName.toUpperCase());
    }

    public String getRowType()
    {
        return rowType;
    }

    public String getTypeName()
    {
        return typeName;
    }

    public int getLength()
    {
        return length;
    }

    public int getScale()
    {
        return scale;
    }

    public Object getParmValue()
    {
        return parmValue;
    }

    public int getType()
    {
        Integer i = new Integer(0);
        i = (Integer)typeMap.get(typeName.toUpperCase());
        return i.intValue();
    }

    private String parmName;
    private String rowType;
    private String typeName;
    private Hashtable typeMap;
    private Hashtable typeDefaultValue;
    private Object parmValue;
    private int length;
    private int scale;

}
