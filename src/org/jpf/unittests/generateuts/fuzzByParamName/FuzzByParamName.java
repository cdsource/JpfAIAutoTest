/** 
* @author 吴平福 
* E-mail:wupf@asiainfo.com 
* @version 创建时间：2017年12月6日 下午2:08:59 
* 类说明 
*/ 

package org.jpf.unittests.generateuts.fuzzByParamName;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jpf.unittests.generateuts.ParamInitBody;
import org.jpf.unittests.generateuts.fuzzByParamType.IFuzze;

/**
 * 
 */
public class FuzzByParamName implements IFuzze {
    private static final Logger logger = LogManager.getLogger();
    
    private ArrayList<String> mList=new ArrayList<String>();
    /**
     * 
     */
    public FuzzByParamName() {
        // TODO Auto-generated constructor stub
    }
    /* (non-Javadoc)
     * @see org.jpf.unittests.generateuts.fuzze.IFuzze#getFuzze(org.jpf.unittests.generateuts.ParamInitBody)
     */
    @Override
    public ArrayList<String> getFuzzeForNull(ParamInitBody cParamInitBody) {
        mList.clear();
        logger.debug("strParamName="+cParamInitBody.getParamVariable());
        //mList.add("    int "+strParamName+" =  1;");
        if (cParamInitBody.getParamVariable().indexOf("mail")>=0)
        {
            mList.add("    String "+cParamInitBody.getParamVariable()+" =  \"abc@123\";\n");
            mList.add("    String "+cParamInitBody.getParamVariable()+" =  \"abc\1@344\";\n");
        }
        if (cParamInitBody.getParamVariable().indexOf("phone")>=0)
        {
            mList.add("    String "+cParamInitBody.getParamVariable()+" =  18086848011\n");
            mList.add("    String "+cParamInitBody.getParamVariable()+" =  18086848012;\n");
        }
        return mList;
    }

}
