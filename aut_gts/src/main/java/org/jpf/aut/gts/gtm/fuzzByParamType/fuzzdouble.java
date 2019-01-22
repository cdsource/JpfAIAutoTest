/** 
* @author 吴平福 
* E-mail:wupf@asiainfo.com 
* @version 创建时间：2017年11月13日 下午3:23:47 
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
public class fuzzdouble implements IFuzz {

    private static final Logger logger = LogManager.getLogger();
    public  ArrayList<String> getFuzzeForNull(MethodParamBody cParamInitBody) {
        ArrayList<String> mList=new ArrayList<String>();
        logger.debug("strParamName="+cParamInitBody.getParamVariable());
        if (cParamInitBody.isArray())
        {
            mList.add("    "+cParamInitBody.getParamType()+" " +cParamInitBody.getParamVariable()+" =  new double[]{1d,2d};\n");
            mList.add("    "+cParamInitBody.getParamType()+" " +cParamInitBody.getParamVariable()+" =  new double[]{-11d,-22d};\n");
            mList.add("    "+cParamInitBody.getParamType()+" " +cParamInitBody.getParamVariable()+" =  new double[]{0d,0d};\n");
            mList.add("    "+cParamInitBody.getParamType()+" " +cParamInitBody.getParamVariable()+" =  new double[]{Double.MAX_VALUE,Double.MIN_VALUE};\n");
            mList.add("    "+cParamInitBody.getParamType()+" " +cParamInitBody.getParamVariable()+" =  new double[]{Double.MIN_VALUE,Double.MAX_VALUE};\n");
        }else
        {
            mList.add("    "+cParamInitBody.getParamType()+" "+cParamInitBody.getParamVariable()+" =  Double.MAX_VALUE;\n");
            mList.add("    "+cParamInitBody.getParamType()+" "+cParamInitBody.getParamVariable()+" =  Double.MIN_VALUE;\n");
            mList.add("    "+cParamInitBody.getParamType()+" "+cParamInitBody.getParamVariable()+" =  0d;\n");
            mList.add("    "+cParamInitBody.getParamType()+" "+cParamInitBody.getParamVariable()+" =  -1d;\n");

        }
        return mList;
    }

}
