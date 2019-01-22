/** 
* @author 吴平福 
* E-mail:wupf@asiainfo.com 
* @version 创建时间：2017年12月11日 下午12:12:28 
* 类说明 
*/ 

package org.jpf.aut.gts.gtm.fuzzByParamType;

import java.util.ArrayList;

import org.jpf.aut.gts.gtm.MethodParamBody;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 
 */
public class fuzzMap  implements IFuzz{
    private static final Logger logger = LogManager.getLogger();
    
    public  ArrayList<String> getFuzzeForNull(MethodParamBody cParamInitBody) {
        ArrayList<String> mList=new ArrayList<String>();
        logger.debug("strParamName="+cParamInitBody.getParamVariable());
        
        if (cParamInitBody.isArray())
        {
            mList.add("    "+cParamInitBody.getParamType()+" " +cParamInitBody.getParamVariable()+" =  new HashMap[]{1,2};\n");

        }else
        {
            mList.add("    "+cParamInitBody.getParamType()+" "+cParamInitBody.getParamVariable()+" =  new "+cParamInitBody.getParamType().replaceAll("Map", "HashMap")+"();\n");
        }
        return mList;
    }

}
