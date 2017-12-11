/** 
* @author 吴平福 
* E-mail:wupf@asiainfo.com 
* @version 创建时间：2017年12月11日 下午2:28:43 
* 类说明 
*/ 

package org.jpf.unittests.generateuts.fuzze;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jpf.unittests.generateuts.ParamInitBody;

/**
 * 
 */
public class fuzzList  implements IFuzze{
    private static final Logger logger = LogManager.getLogger();
    
    public  ArrayList<String> getFuzzeForNull(ParamInitBody cParamInitBody) {
        ArrayList<String> mList=new ArrayList<String>();
        logger.debug("strParamName="+cParamInitBody.getParamVariable());
        
        if (cParamInitBody.isArray())
        {
        }else
        {
            mList.add("    "+cParamInitBody.getParamType()+" "+cParamInitBody.getParamVariable()+" =  new "+cParamInitBody.getParamType().replaceAll("List", "ArrayList")+"();\n");
        }
        return mList;
    }

}
