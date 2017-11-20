/** 
* @author 吴平福 
* E-mail:wupf@asiainfo.com 
* @version 创建时间：2017年11月13日 下午3:26:49 
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
public class fuzzefloat  implements IFuzze{

    private static final Logger logger = LogManager.getLogger();
    public  ArrayList<String> getFuzzeForNull(ParamInitBody cParamInitBody) {
        ArrayList<String> mList=new ArrayList<String>();
        logger.debug("strParamName="+cParamInitBody.getParamVariable());
        if (cParamInitBody.isArray())
        {
            mList.add("    "+cParamInitBody.getParamType()+" " +cParamInitBody.getParamVariable()+" =  new float[]{1f,2f};\n");
            mList.add("    "+cParamInitBody.getParamType()+" " +cParamInitBody.getParamVariable()+" =  new float[]{-11f,-22f};\n");
            mList.add("    "+cParamInitBody.getParamType()+" " +cParamInitBody.getParamVariable()+" =  new float[]{0f,0f};\n");
            mList.add("    "+cParamInitBody.getParamType()+" " +cParamInitBody.getParamVariable()+" =  new float[]{Float.MAX_VALUE,Float.MIN_VALUE};\n");
            mList.add("    "+cParamInitBody.getParamType()+" " +cParamInitBody.getParamVariable()+" =  new float[]{Float.MIN_VALUE,Float.MAX_VALUE};\n");
        }else
        {
            mList.add("    "+cParamInitBody.getParamType()+" "+cParamInitBody.getParamVariable()+" =  Float.MAX_VALUE;\n");
            mList.add("    "+cParamInitBody.getParamType()+" "+cParamInitBody.getParamVariable()+" =  Float.MIN_VALUE;\n");
            mList.add("    "+cParamInitBody.getParamType()+" "+cParamInitBody.getParamVariable()+" =  0f;\n");
            mList.add("    char "+cParamInitBody.getParamVariable()+" =  'a';\n");

        }
        return mList;
    }

}
