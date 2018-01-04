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
import org.apache.logging.log4j.core.appender.rolling.SizeBasedTriggeringPolicy;
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
            String strNewType=cParamInitBody.getParamType().replaceAll("List", "ArrayList").replaceAll("\\?", "");
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
