/** 
* @author 吴平福 
* E-mail:wupf@asiainfo.com 
* @version 创建时间：2017年11月13日 下午3:33:28 
* 类说明 
*/ 

package org.jpf.unittests.generateuts.fuzzByParamType;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jpf.unittests.generateuts.ParamInitBody;

/**
 * 
 */
public class fuzzchar implements IFuzz{

    private static final Logger logger = LogManager.getLogger();

    
    /**
     * 
     * @category 获取Fuzz样本
     * @author 吴平福 
     * @param cParamInitBody
     * @return
     * update 2017年11月16日
     */
    public  ArrayList<String> getFuzzeForNull(ParamInitBody cParamInitBody) {
        ArrayList<String> mList=new ArrayList<String>();
        logger.debug("strParamName="+cParamInitBody.getParamVariable());
        //mList.add("    int "+strParamName+" =  1;");
        if (cParamInitBody.isArray())
        {
            
            mList.add("    char[] "+cParamInitBody.getParamVariable().replaceAll("\\[.*\\]", "")+" =  new char[10];\n");
        }else
        {
            mList.add("    char  " + cParamInitBody.getParamVariable() + " =  Character.MAX_VALUE;\n");
            mList.add("    char  " + cParamInitBody.getParamVariable() + " =  Character.MIN_VALUE;\n");
            mList.add("    char  " + cParamInitBody.getParamVariable() + " =  0;\n");
            mList.add("    char  " + cParamInitBody.getParamVariable() + " = 'a';\n");
        }

        return mList;
    }
    
    public static void main(String[] args)
    {
        String abc="abc[ ]";
        System.out.println(abc.replaceAll("\\[.*\\]", ""));
    }
}
