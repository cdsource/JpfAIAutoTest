/** 
* @author 吴平福 
* E-mail:wupf@asiainfo.com 
* @version 创建时间：2017年12月13日 下午11:03:33 
* 类说明 
*/ 

package org.jpf.aitest.gts.gtm.fuzzByParamType;

import java.sql.Timestamp;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jpf.aitest.gts.gtm.MethodParamBody;

/**
 * 
 */
public class fuzzTimestamp implements IFuzz {

    private static final Logger logger = LogManager.getLogger();
    public  ArrayList<String> getFuzzeForNull(MethodParamBody cParamInitBody) {
        ArrayList<String> mList=new ArrayList<String>();
        
        logger.debug("strParamName="+cParamInitBody.getParamVariable());
        if (cParamInitBody.isArray())
        {
        }else
        {
            mList.add("    "+cParamInitBody.getParamType()+" "+cParamInitBody.getParamVariable()+" =  new Timestamp(System.currentTimeMillis()); ");
            
        }
        return mList;
    }

}
