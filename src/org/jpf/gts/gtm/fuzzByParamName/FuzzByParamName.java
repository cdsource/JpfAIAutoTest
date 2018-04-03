/** 
* @author 吴平福 
* E-mail:wupf@asiainfo.com 
* @version 创建时间：2017年12月6日 下午2:08:59 
* 类说明 
*/ 

package org.jpf.gts.gtm.fuzzByParamName;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jpf.gts.dbs.DbServer;
import org.jpf.gts.gtm.fuzzByParamType.IFuzz;
import org.jpf.unittests.generateuts.ParamInitBody;

import com.asiainfo.utils.dbsql.AiDBUtil;

/**
 * 
 */
public class FuzzByParamName implements IFuzz {
    private static final Logger logger = LogManager.getLogger();
    

    /**
     * 
     */
    public FuzzByParamName() {
        // TODO Auto-generated constructor stub
    }
    /* (non-Javadoc)
     * @see org.jpf.unittests.generateuts.fuzze.IFuzze#getFuzze(org.jpf.unittests.generateuts.ParamInitBody)
     */
    @Override
    public ArrayList<String> getFuzzeForNull(ParamInitBody cParamInitBody) {
    	ArrayList<String> mList=new ArrayList<String>();
        logger.debug("strParamName="+cParamInitBody.getParamVariable());
        //mList.add("    int "+strParamName+" =  1;");
        
        try
        {
        	Connection conn=DbServer.getInstance().getConn();
        	ResultSet rs=AiDBUtil.execSqlQuery(conn, "select * from sql_row_value where col_name like '%"+cParamInitBody.getParamVariable()+"%'");
        	while (rs.next())
        	{
        		if (cParamInitBody.getParamType().equalsIgnoreCase("String"))
        		{
        			mList.add("    "+cParamInitBody.getParamType()+" " +cParamInitBody.getParamVariable()+" = \""+rs.getString("col_value")+"\"");
        		}else
        		{
        			mList.add("    "+cParamInitBody.getParamType()+" " +cParamInitBody.getParamVariable()+" = "+rs.getString("col_value"));
        		}
        	}
        }catch(Exception ex)
        {
        	logger.error(ex);
        	ex.printStackTrace();
        }
        
        return mList;
    }

}
