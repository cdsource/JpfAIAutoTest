/** 
* @author 吴平福 
* E-mail:wupf@asiainfo.com 
* @version 创建时间：2017年11月20日 下午12:01:40 
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
public class fuzzeConnection implements IFuzze {
    private static final Logger logger = LogManager.getLogger();
    ArrayList<String> mList=new ArrayList<String>();
    /**
     * 
     */
    public fuzzeConnection() {
        // TODO Auto-generated constructor stub
    }

    /* (non-Javadoc)
     * @see org.jpf.unittests.generateuts.fuzze.IFuzze#getFuzze(org.jpf.unittests.generateuts.ParamInitBody)
     */
    @Override
    public ArrayList<String> getFuzzeForNull(ParamInitBody cParamInitBody) {
        mList.clear();
        
        logger.debug("strParamName="+cParamInitBody.getParamVariable());
        if (cParamInitBody.isArray())
        {
            mList.add("Class.forName(\"com.mysql.jdbc.Driver\");\n    "+cParamInitBody.getParamType()+" " +cParamInitBody.getParamVariable()+" =  new boolean[]{true,true};\n");
         }else
        {
            mList.add("Class.forName(\"com.mysql.jdbc.Driver\");\n    "+cParamInitBody.getParamType()+" "+cParamInitBody.getParamVariable()+" =  null;\n");
             
        }
        return mList;
    }

}
