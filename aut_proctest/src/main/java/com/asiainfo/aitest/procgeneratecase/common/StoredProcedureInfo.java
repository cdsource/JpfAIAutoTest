/**
 * copyrigth by wupf@asiainfo.com
 * 2018年8月10日
 */
package com.asiainfo.aitest.procgeneratecase.common;

import java.util.ArrayList;

/**
 * @author wupf@asiainfo.com
 *
 */
public class StoredProcedureInfo {

    public StoredProcedureInfo()
    {
        routineType = "P";
    }

    public StoredProcedureInfo(String type)
    {
        routineType = type;
    }

    public void setRoutineSchema(String routineSchema)
    {
        this.routineSchema = routineSchema;
    }

    public void setRoutineName(String routineName)
    {
        this.routineName = routineName;
    }

    public void setParmList(ArrayList list)
    {
        parmList = new ArrayList(list);
    }

    public String getRoutineSchema()
    {
        return routineSchema;
    }

    public String getRoutineName()
    {
        return routineName;
    }

    public String getSPName()
    {
        if(routineSchema == null || routineSchema.length() == 0)
            return routineName;
        else
            return routineSchema + "." + routineName;
    }

    public ArrayList getParmList()
    {
        return parmList;
    }

    public void setRowsReturnedParmId(int id)
    {
        rowsReturnedParmId = id;
    }

    public int getRowsReturnedParmId()
    {
        return rowsReturnedParmId;
    }

    public String getParmString()
    {
        StringBuffer parmString = new StringBuffer();
        String parm = new String();
        if(parmList != null)
        {
            for(int i = 0; i < parmList.size(); i++)
            {
                ParmInfo pmInfo = new ParmInfo();
                pmInfo = (ParmInfo)parmList.get(i);
                if("P".equals(pmInfo.getRowType()))
                    parmString.append("'" + pmInfo.getParmValue().toString() + "',");
                else
                    parmString.append("?,");
            }

            if(parmString.length() > 2)
                parm = parmString.substring(0, parmString.length() - 1);
        }
        return parm;
    }

    public String getRoutineType()
    {
        return routineType;
    }

    public void setRoutineType(String string)
    {
        routineType = string;
    }

    private String routineSchema;
    private String routineName;
    private String routineType;
    private ArrayList parmList;
    private int rowsReturnedParmId;


}
