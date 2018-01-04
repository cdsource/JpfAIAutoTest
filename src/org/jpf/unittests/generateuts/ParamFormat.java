/** 
* @author 吴平福 
* E-mail:wupf@asiainfo.com 
* @version 创建时间：2017年11月19日 下午1:13:48 
* 类说明 
*/ 

package org.jpf.unittests.generateuts;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 
 */
public class ParamFormat {
    private static final Logger logger = LogManager.getLogger();
    /**
     * 
     */
    public ParamFormat() {
        // TODO Auto-generated constructor stub
    }

    /**
     * 
     * @category  去掉注释
     * @author 吴平福 
     * @param strParam
     * update 2017年11月8日
     */
    public static String removeDesc(String strParam)
    {
        return strParam.replaceAll("/\\*(.*?)\\*/", "").replaceAll("\n", "");
    }
    
    public static void formatParam(/*in*/ /**/ String /* */strParam)
    {
        System.out.println(strParam);
        //  /* a*/ final aa
        String saa=strParam.replaceAll("/\\*(.*?)\\*/", "");
        saa=saa.replaceAll("\n", "");
        System.out.println(saa);
        String REGEX="/\\*(.*)?\\*/";
        Pattern p = Pattern.compile(REGEX);
        Matcher m = p.matcher(strParam); // 获取 matcher 对象
        int count = 0;
  
        while(m.find()) {
          count++;
          System.out.println("Match number "+count);
          System.out.println("start(): "+m.start());
          System.out.println("end(): "+m.end());
       }
        
        strParam= "string keywod = \"abc\"; string value = \"test\";";
        REGEX="string (?<x>[^=]*?) *= *(?<y>[^;]*?);";
         p = Pattern.compile(REGEX);
         m = p.matcher(strParam); // 获取 matcher 对象
         count = 0;
  
        while(m.find()) {
          count++;
          System.out.println("Match number "+count);
          System.out.println("start(): "+m.start());
          System.out.println("end(): "+m.end());
          System.out.println(strParam.substring(m.start(),m.end()));
       }
    }
    /**
     * @category 
     * @author 吴平福 
     * @param args
     * update 2017年11月19日
     */

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        formatParam("/*in*/ /**/final /**/ String \n strParam");
    }

}
