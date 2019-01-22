/** 
* @author 吴平福 
* E-mail:wupf@asiainfo.com 
* @version 创建时间：2018年3月2日 下午9:58:49 
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
public class fuzzCharacter  implements IFuzz{

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
            
            mList.add("    Character[] "+cParamInitBody.getParamVariable().replaceAll("\\[.*\\]", "")+" =  new Character[10];\n");
        }else
        {
            mList.add("    Character  " + cParamInitBody.getParamVariable() + " =  Character.MAX_VALUE;\n");
            mList.add("    Character  " + cParamInitBody.getParamVariable() + " =  Character.MIN_VALUE;\n");
            mList.add("    Character  " + cParamInitBody.getParamVariable() + " = 'a';\n");
            mList.add("    Character  " + cParamInitBody.getParamVariable() + " = 'aZ';\n");
            mList.add("    Character  " + cParamInitBody.getParamVariable() + " = '\u039A';\n");
        }

        return mList;
    }
    

}
