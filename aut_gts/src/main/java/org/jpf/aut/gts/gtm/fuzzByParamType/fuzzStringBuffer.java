/**
 * copyrigth by wupf@asiainfo.com
 * 2018年8月19日
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
public class fuzzStringBuffer implements IFuzz{

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
    public ArrayList<String> getFuzzeForNull(MethodParamBody cParamInitBody) {
        mList.clear();
        //logger.debug("strParamName="+cParamInitBody.getParamVariable());
        //mList.add("    int "+strParamName+" =  1;");
        if (cParamInitBody.isArray())
        {
            mList.add("     "+cParamInitBody.getParamType()+" "+cParamInitBody.getParamVariable()+" = new StringBuffer[2];");
            mList.add("     "+cParamInitBody.getParamType()+" "+cParamInitBody.getParamVariable()+" = {\"a\",\"b\"};");
        }else
        {
        	StringBuffer sb=new StringBuffer("aa");
        	
            mList.add("     "+cParamInitBody.getParamType()+" "+cParamInitBody.getParamVariable()+" = new "+cParamInitBody.getParamType()+"(\"abc123\");");
            mList.add("     "+cParamInitBody.getParamType()+" "+cParamInitBody.getParamVariable()+" =  null;");
            mList.add("     "+cParamInitBody.getParamType()+" "+cParamInitBody.getParamVariable()+" =  new "+cParamInitBody.getParamType()+"(\"\");");
            mList.add("     "+cParamInitBody.getParamType()+" "+cParamInitBody.getParamVariable()+" =  new "+cParamInitBody.getParamType()+"(\"123\");");
            mList.add("     "+cParamInitBody.getParamType()+" "+cParamInitBody.getParamVariable()+" =  new "+cParamInitBody.getParamType()+"(\"中文\"）;");
            mList.add("     "+cParamInitBody.getParamType()+" "+cParamInitBody.getParamVariable()+" =  new "+cParamInitBody.getParamType()+"(\"中文123abc\"）;");
            mList.add("     "+cParamInitBody.getParamType()+" "+cParamInitBody.getParamVariable()+" =  new "+cParamInitBody.getParamType()+"(\"!@#$%^&*\"）;");
            //mList.add("    String "+strParamName+" =  \"abc\n\";");
            //mList.add("    String "+strParamName+" =  \"abc\r\";");
            mList.add("     "+cParamInitBody.getParamType()+" "+cParamInitBody.getParamVariable()+" =  new "+cParamInitBody.getParamType()+"(\"abc\0def\"）;");
            mList.add("     "+cParamInitBody.getParamType()+" "+cParamInitBody.getParamVariable()+" =  new "+cParamInitBody.getParamType()+"(\"abc\tdef\");");
            mList.add("     "+cParamInitBody.getParamType()+" "+cParamInitBody.getParamVariable()+" =  new "+cParamInitBody.getParamType()+"(\"abc\\\"def\\\"\");");
            mList.add("     "+cParamInitBody.getParamType()+" "+cParamInitBody.getParamVariable()+" =  new "+cParamInitBody.getParamType()+"(\"abc\'def'\");");
            mList.add("     "+cParamInitBody.getParamType()+" "+cParamInitBody.getParamVariable()+" =  new "+cParamInitBody.getParamType()+"(\"abc,deF,,'\");");
            mList.add("     "+cParamInitBody.getParamType()+" "+cParamInitBody.getParamVariable()+" =  new "+cParamInitBody.getParamType()+"(\"abc\u1234\");");
            mList.add("     "+cParamInitBody.getParamType()+" "+cParamInitBody.getParamVariable()+" =  new "+cParamInitBody.getParamType()+"(\"abc\1344\");");
        }

        return mList;
    }

    /*
     \n 换行 (0x0a) \r  回车 (0x0d) \f  换页符(0x0c) \b  退格 (0x08) \0  空字符 (0x20) \s  字符串 \t  制表符 \"  双引号 \'  单引号 \\  反斜杠  
      */
}
