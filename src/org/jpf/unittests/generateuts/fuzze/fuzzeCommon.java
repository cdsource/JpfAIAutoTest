/** 
* @author 吴平福 
* E-mail:wupf@asiainfo.com 
* @version 创建时间：2017年11月20日 上午11:58:36 
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
public class fuzzeCommon implements IFuzze {
    private static final Logger logger = LogManager.getLogger();
    /**
     * 
     */
    public fuzzeCommon() {
        // TODO Auto-generated constructor stub
    }

    /* (non-Javadoc)
     * @see org.jpf.unittests.generateuts.fuzze.IFuzze#getFuzze(org.jpf.unittests.generateuts.ParamInitBody)
     */
    @Override
    public ArrayList<String> getFuzze(ParamInitBody cParamInitBody) {
        // TODO Auto-generated method stub
       ArrayList<String> mList=new ArrayList<String>();
        
        logger.debug("strParamName="+cParamInitBody.getParamVariable());
        if (cParamInitBody.isArray())
        {
            mList.add("    "+cParamInitBody.getParamType()+" " +cParamInitBody.getParamVariable()+" =  new "+cParamInitBody.getParamType()+"{};\n");
            
        }else
        {
            mList.add("    "+cParamInitBody.getParamType()+" "+cParamInitBody.getParamVariable()+" =  null;\n");

        }
        return mList;
    }

}
