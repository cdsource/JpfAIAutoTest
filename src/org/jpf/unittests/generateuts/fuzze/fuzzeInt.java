/**
 * @author 吴平福 E-mail:wupf@asiainfo.com
 * @version 创建时间：2017年9月29日 下午3:51:58 类说明
 */

package org.jpf.unittests.generateuts.fuzze;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jpf.unittests.generateuts.ParamInitBody;

/**
 * 1. Integer.MAX_VALUE; 2 Integer.MIN_VALUE 3. char
 */
public class fuzzeInt {
    private static final Logger logger = LogManager.getLogger();
    public static ArrayList<String> getFuzze(ParamInitBody cParamInitBody) {
        ArrayList<String> mList=new ArrayList<String>();
        
        logger.debug("strParamName="+cParamInitBody.getParamVariable());
        if (cParamInitBody.isArray())
        {
            mList.add("    "+cParamInitBody.getParamType()+" " +cParamInitBody.getParamVariable()+" =  new int[]{1,2};\n");
            mList.add("    "+cParamInitBody.getParamType()+" " +cParamInitBody.getParamVariable()+" =  new int[]{-11,-22};\n");
            mList.add("    "+cParamInitBody.getParamType()+" " +cParamInitBody.getParamVariable()+" =  new int[]{0,0};\n");
            mList.add("    "+cParamInitBody.getParamType()+" " +cParamInitBody.getParamVariable()+" =  new int[]{Integer.MAX_VALUE,Integer.MIN_VALUE};\n");
            mList.add("    "+cParamInitBody.getParamType()+" " +cParamInitBody.getParamVariable()+" =  new int[]{Integer.MIN_VALUE,Integer.MAX_VALUE};\n");
        }else
        {
            mList.add("    "+cParamInitBody.getParamType()+" "+cParamInitBody.getParamVariable()+" =  Integer.MAX_VALUE;\n");
            mList.add("    "+cParamInitBody.getParamType()+" "+cParamInitBody.getParamVariable()+" =  Integer.MIN_VALUE;\n");
            mList.add("    "+cParamInitBody.getParamType()+" "+cParamInitBody.getParamVariable()+" =  0;\n");
            mList.add("    char "+cParamInitBody.getParamVariable()+" =  'a';\n");

        }
        return mList;
    }
}
