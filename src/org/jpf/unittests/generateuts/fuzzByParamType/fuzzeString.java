/** 
* @author 吴平福 
* E-mail:wupf@asiainfo.com 
* @version 创建时间：2017年11月13日 下午3:16:29 
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
public class fuzzeString implements IFuzze{

    private static final Logger logger = LogManager.getLogger();
    
    private ArrayList<String> mList=new ArrayList<String>();


    /**
     * 
     * @category 获取Fuzz样本
     * @author 吴平福 
     * @param cParamInitBody
     * @return
     * update 2017年11月17日
     */
    public ArrayList<String> getFuzzeForNull(ParamInitBody cParamInitBody) {
        mList.clear();
        logger.debug("strParamName="+cParamInitBody.getParamVariable());
        //mList.add("    int "+strParamName+" =  1;");
        if (cParamInitBody.isArray())
        {
            mList.add("     "+cParamInitBody.getParamType()+" "+cParamInitBody.getParamVariable()+" = new String[2];");
        }else
        {
            mList.add("    String "+cParamInitBody.getParamVariable()+" =  \"abc123\";\n");
            mList.add("    String "+cParamInitBody.getParamVariable()+" =  \"123\";\n");
            mList.add("    String "+cParamInitBody.getParamVariable()+" =  \"中文\";\n");
            mList.add("    String "+cParamInitBody.getParamVariable()+" =  \"中文123abc\";\n");
            mList.add("    String "+cParamInitBody.getParamVariable()+" =  \"!@#$%^&*\";\n");
            //mList.add("    String "+strParamName+" =  \"abc\n\";");
            //mList.add("    String "+strParamName+" =  \"abc\r\";");
            mList.add("    String "+cParamInitBody.getParamVariable()+" =  \"abc\0def\";\n");
            mList.add("    String "+cParamInitBody.getParamVariable()+" =  \"abc\tdef\";\n");
            mList.add("    String "+cParamInitBody.getParamVariable()+" =  \"abc\\\"def\\\"\";\n");
            mList.add("    String "+cParamInitBody.getParamVariable()+" =  \"abc\'def'\";\n");
            mList.add("    String "+cParamInitBody.getParamVariable()+" =  \"abc,deF,,'\";\n");
            mList.add("    String "+cParamInitBody.getParamVariable()+" =  \"abc\u1234\";\n");
            mList.add("    String "+cParamInitBody.getParamVariable()+" =  \"abc\1344\";\n");
        }

        return mList;
    }

    /*
     \n 换行 (0x0a) \r  回车 (0x0d) \f  换页符(0x0c) \b  退格 (0x08) \0  空字符 (0x20) \s  字符串 \t  制表符 \"  双引号 \'  单引号 \\  反斜杠  
      */
}
