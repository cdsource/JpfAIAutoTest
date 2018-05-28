/**
 * copyrigth by wupf@asiainfo.com
 */
package org.jpf.aitest.gts.gtm;

/**
 * @author wupf@asiainfo.com
 *
 */
public class GenerateMethodUtil {

    public static boolean initParamValue(StringBuffer sBuffer,MethodParamBody cParamInitBody,String strValue)
    {
    	boolean isFindType=true;
    	if (null == strValue || strValue.isEmpty())
    	{
    		return false;
    	}
        switch (cParamInitBody.getParamType()) {
        case "int":
 
                sBuffer.append("  ").append(cParamInitBody.getParamVariable()).append("=")
                .append(strValue).append(";\n");
           
            break;
        case "long":
  
                sBuffer.append("  ").append(cParamInitBody.getParamVariable()).append("=")
                .append(strValue).append("L;\n");

            break;
        case "String":
                sBuffer.append("  ").append(cParamInitBody.getParamVariable()).append("=\"")
                .append(strValue).append("\";\n");

            break;
            
        default:
            isFindType = false;
            break;
        }
        return isFindType;

    }

}
