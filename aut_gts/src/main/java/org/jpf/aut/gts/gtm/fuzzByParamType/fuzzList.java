/** 
* @author 吴平福 
* E-mail:wupf@asiainfo.com 
* @version 创建时间：2017年12月11日 下午2:28:43 
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
public class fuzzList  implements IFuzz{
    private static final Logger logger = LogManager.getLogger();
    
    public  ArrayList<String> getFuzzeForNull(MethodParamBody cParamInitBody) {
        ArrayList<String> mList=new ArrayList<String>();
        logger.debug("strParamName="+cParamInitBody.getParamVariable());
        
        if (cParamInitBody.isArray())
        {
        }else
        {
             String strNewType=cParamInitBody.getParamType().replaceFirst("List", "ArrayList").replaceAll("\\?", "");
            mList.add("    "+cParamInitBody.getParamType()+" "+cParamInitBody.getParamVariable()+" =  new "+strNewType+"();\n");
        }
        return mList;
    }
    public static void main(String[] args)
    {
        String strNewType="List<?> a";
         strNewType=strNewType.replaceAll("List", "ArrayList").replaceAll("\\?", "");
         System.out.println(strNewType);
    }
}
