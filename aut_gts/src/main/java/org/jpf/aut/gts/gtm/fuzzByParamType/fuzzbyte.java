/** 
* @author 吴平福 
* E-mail:wupf@asiainfo.com 
* @version 创建时间：2017年11月13日 下午3:31:20 
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
public class fuzzbyte implements IFuzz{

    private static final Logger logger = LogManager.getLogger();
    
    /**
     * 
     * @category 获取Fuzz样本
     * @author 吴平福 
     * @param cParamInitBody
     * @return
     * update 2017年11月16日
     */
    public  ArrayList<String> getFuzzeForNull(MethodParamBody cParamInitBody) {
        ArrayList<String> mList=new ArrayList<String>();
        logger.debug("strParamName="+cParamInitBody.getParamVariable());
        //mList.add("    int "+strParamName+" =  1;");
        if (cParamInitBody.isArray())
        {
            mList.add("    "+cParamInitBody.getParamType()+" " +cParamInitBody.getParamVariable()+" =  new byte[10];\n");
        }else
        {
            mList.add("     "+cParamInitBody.getParamType()+" "+cParamInitBody.getParamVariable()+" =  Byte.MAX_VALUE;\n");
            mList.add("     "+cParamInitBody.getParamType()+" "+cParamInitBody.getParamVariable()+" =  Byte.MIN_VALUE;\n");
            mList.add("     "+cParamInitBody.getParamType()+" "+cParamInitBody.getParamVariable()+" =  0;\n");
            mList.add("     "+cParamInitBody.getParamType()+" "+cParamInitBody.getParamVariable()+ " = 'a';\n");
        }

        return mList;
    }
}
