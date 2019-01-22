/** 
* @author 吴平福 
* E-mail:wupf@asiainfo.com 
* @version 创建时间：2017年11月20日 上午10:27:16 
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
public class fuzzBoolean implements IFuzz {

    private static final Logger logger = LogManager.getLogger();
    public  ArrayList<String> getFuzzeForNull(MethodParamBody cParamInitBody) {
        ArrayList<String> mList=new ArrayList<String>();
        
        //logger.debug("strParamName="+cParamInitBody.getParamVariable());
        if (cParamInitBody.isArray())
        {
            mList.add("    "+cParamInitBody.getParamType()+" " +cParamInitBody.getParamVariable()+" =  new boolean[]{true,true};");
            mList.add("    "+cParamInitBody.getParamType()+" " +cParamInitBody.getParamVariable()+" =  new boolean[]{false,false};");
        }else
        {
            mList.add("    "+cParamInitBody.getParamType()+" "+cParamInitBody.getParamVariable()+" =  true;");
            mList.add("    "+cParamInitBody.getParamType()+" "+cParamInitBody.getParamVariable()+" =  false;");
            
        }
        return mList;
    }

}
