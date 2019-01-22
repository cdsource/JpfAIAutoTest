/**
 * copyrigth by wupf@asiainfo.com
 * 2018年7月3日
 */
package org.jpf.aut.gts.gtm.fuzzByParamType;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jpf.aut.gts.gtm.MethodParamBody;

/**
 * @author wupf@asiainfo.com
 *
 */
public class fuzzBigDecimal implements IFuzz {

    private static final Logger logger = LogManager.getLogger();

	@Override
	public ArrayList<String> getFuzzeForNull(MethodParamBody cParamInitBody) {
        ArrayList<String> mList=new ArrayList<String>();
        
        logger.debug("strParamName="+cParamInitBody.getParamVariable());
        if (cParamInitBody.isArray())
        {
            mList.add("    "+cParamInitBody.getParamType()+" " +cParamInitBody.getParamVariable()+" =  new java.math.BigDecimal[]{0,1};");
            mList.add("    "+cParamInitBody.getParamType()+" " +cParamInitBody.getParamVariable()+" =  new java.math.BigDecimal[]{-1,1};");
        }else
        {
            mList.add("    "+cParamInitBody.getParamType()+" "+cParamInitBody.getParamVariable()+" =  new java.math.BigDecimal(1);");
            mList.add("    "+cParamInitBody.getParamType()+" "+cParamInitBody.getParamVariable()+" =  new java.math.BigDecimal(0);");
            mList.add("    "+cParamInitBody.getParamType()+" "+cParamInitBody.getParamVariable()+" =  new java.math.BigDecimal(-1);");
        }
        return mList;
	}



}
