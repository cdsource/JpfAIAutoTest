/**
 * copyrigth by wupf@asiainfo.com
 */
package org.jpf.aut.gts.gtm;


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
 
                sBuffer.append("  int ").append(cParamInitBody.getParamVariable()).append("=")
                .append(strValue).append(";\n");
           
            break;
        case "long":
  
                sBuffer.append("  long ").append(cParamInitBody.getParamVariable()).append("=")
                .append(strValue).append("L;\n");

            break;
        case "String":
                sBuffer.append("  String ").append(cParamInitBody.getParamVariable()).append("=\"")
                .append(strValue).append("\";\n");

            break;
            
        default:
            isFindType = false;
            break;
        }
        return isFindType;

    }

}
