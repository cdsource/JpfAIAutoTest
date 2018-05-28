/** 
* @author 吴平福 
* E-mail:wupf@asiainfo.com 
* @version 创建时间：2017年12月13日 下午11:48:11 
* 类说明 
*/ 

package org.jpf.aitest.gts.gtm.fuzzByParamType;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jpf.aitest.gts.gtm.MethodParamBody;

/**
 * 
 */
public class fuzzIterator   implements IFuzz {

    private static final Logger logger = LogManager.getLogger();
    
    public  ArrayList<String> getFuzzeForNull(MethodParamBody cParamInitBody) {
        ArrayList<String> mList=new ArrayList<String>();
        
        logger.debug("strParamName="+cParamInitBody.getParamVariable());
        if (cParamInitBody.isArray())
        {
        }else
        {
            mList.add("    List mlist= new ArrayList<>();\n     "+cParamInitBody.getParamType()+" "+cParamInitBody.getParamVariable()+" =   mlist.iterator();");
            
        }
        return mList;
    }

}
