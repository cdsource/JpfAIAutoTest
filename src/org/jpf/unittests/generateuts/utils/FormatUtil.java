/** 
* @author 吴平福 
* E-mail:wupf@asiainfo.com 
* @version 创建时间：2017年12月11日 下午10:37:43 
* 类说明 
*/ 

package org.jpf.unittests.generateuts.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.ibatis.annotations.Param;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jpf.unittests.generateuts.ParamInitBody;

import com.asiainfo.template.pojo.ActivityPo;

/**
 * 
 */
public class FormatUtil {
    private static final Logger logger = LogManager.getLogger();
    /**
     * 
     * @category 去掉数组定义的多余空格和注释
     * @author 吴平福
     * @param strParamType
     * @return update 2017年10月1日
     */
    public static String formatParam(String strParamType) {
        strParamType = strParamType.replaceAll(" {2,}", " ");
        int iPos = strParamType.indexOf("[");
        if (iPos > 0) {
            strParamType =
                    strParamType.substring(0, iPos).trim() + strParamType.substring(iPos, strParamType.length()).trim();
        }
        iPos = strParamType.indexOf("]");
        if (iPos > 0) {
            strParamType =
                    strParamType.substring(0, iPos).trim() + strParamType.substring(iPos, strParamType.length()).trim();
        }

        strParamType= strParamType.replaceAll("/\\*(.*?)\\*/", "").replaceAll("\n", "").replaceAll("\\.\\.\\.", "[]");
        
        // String strParamType="@Param(\"record\") ActivityPo record";
        strParamType=strParamType.replaceAll("@Param(.*?) ", "").trim();
        
        return strParamType;
    }
    /**
     * 
     * @category 格式化参数
     * @author 吴平福
     * @param cParamInitBody
     * @param strParamName update 2017年11月19日
     */
    public static void formatToParamBody(ParamInitBody cParamInitBody, String strParamName) {
        strParamName = formatParam(strParamName).trim();

        int iPos = strParamName.lastIndexOf(" ");
        cParamInitBody.setParamType(strParamName.substring(0, iPos));
        cParamInitBody.setParamVariable(strParamName.substring(iPos, strParamName.length()));
        cParamInitBody.setArray(isParamArray(strParamName));
    }
    
    /**
     * 
     * @category 判断是否有数组
     * @author 吴平福
     * @param strParamType update 2017年9月30日
     */
    public static boolean isParamArray(String strParamType) {
        strParamType = strParamType.trim();
        // String [] aStrings;
        String regEx = "\\[\\s{0,}\\]";
        // logger.info(strParamType);
        Pattern pattern = Pattern.compile(regEx);
        Matcher matcher = pattern.matcher(strParamType);

        if (matcher.find()) {
            // Vector<String[]>
            regEx = "\\<\\s{0,}.*\\[\\s{0,}\\]s{0,}\\>";
            pattern = Pattern.compile(regEx);
            matcher = pattern.matcher(strParamType);
            if (!matcher.find()) {
                return true;
            }
        }

        regEx = "\\.\\.\\.";
        pattern = Pattern.compile(regEx);
        matcher = pattern.matcher(strParamType);
        if (matcher.find()) {
            return true;
        }
        return false;
    }
    
    public static void main(String[] args)
    {
        String strParamType="@Param(\"record\") ActivityPo record";
        //strParamType.replaceAll("/\\*(.*?)\\*/", "");
        strParamType = strParamType.replaceAll("@Param(.*?) ", " ");
        System.out.println(strParamType);
    }
}
