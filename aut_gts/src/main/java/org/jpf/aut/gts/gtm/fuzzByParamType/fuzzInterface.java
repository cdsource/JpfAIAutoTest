/** 
* copyrigth by 吴平福
* @author 吴平福 
* E-mail:wupf@asiainfo.com 
* @version 创建时间：2017年9月30日 下午3:49:28 
* 类说明 
*/ 
package org.jpf.aut.gts.gtm.fuzzByParamType;

import java.util.ArrayList;

import org.jpf.aut.gts.gtm.MethodParamBody;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author wupf@asiainfo.com
 *
 */
public class fuzzInterface implements IFuzz{
    private static final Logger logger = LogManager.getLogger();
    
    public  ArrayList<String> getFuzzeForNull(MethodParamBody cParamInitBody) {
        ArrayList<String> mList=new ArrayList<String>();
        //logger.debug("strParamName="+cParamInitBody.getParamVariable());
        if (!cParamInitBody.isArray())
        {
        	String strVar="mock_"+ cParamInitBody.getParamType();
        	mList.add(" "+ cParamInitBody.getParamType()+" "+ strVar +" = EasyMock.createMock("+cParamInitBody.getParamType()+".class)\n"
        			+"  		// add mock object expectations here \n"
        			+"   		//EasyMock.expect(result.getErrorCount()).andReturn(2);\n"
        			+"     	EasyMock.replay("+strVar+");");

    		
            
        }
        return mList;
    }

}
