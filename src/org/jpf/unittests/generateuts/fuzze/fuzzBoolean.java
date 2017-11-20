/** 
* @author 吴平福 
* E-mail:wupf@asiainfo.com 
* @version 创建时间：2017年11月20日 上午10:27:16 
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
public class fuzzBoolean implements IFuzze {

    private static final Logger logger = LogManager.getLogger();
    public  ArrayList<String> getFuzzeForNull(ParamInitBody cParamInitBody) {
        ArrayList<String> mList=new ArrayList<String>();
        
        logger.debug("strParamName="+cParamInitBody.getParamVariable());
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
