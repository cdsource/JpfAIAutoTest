/**
 * @author 吴平福 E-mail:wupf@asiainfo.com
 * @version 创建时间：2017年11月13日 下午3:32:19 类说明
 */

package org.jpf.aut.gts.gtm.fuzzByParamType;

import java.util.ArrayList;

import org.jpf.aut.gts.gtm.MethodParamBody;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 
 */
public class fuzzshort implements IFuzz {

    private static final Logger logger = LogManager.getLogger();

    public ArrayList<String> getFuzzeForNull(MethodParamBody cParamInitBody) {
        ArrayList<String> mList = new ArrayList<String>();


        //logger.debug("strParamName=" + cParamInitBody.getParamVariable());
        if (cParamInitBody.isArray()) {
            mList.add("    " + cParamInitBody.getParamType() + " " + cParamInitBody.getParamVariable()
                    + " =  new short[]{1,2};\n");
            mList.add("    " + cParamInitBody.getParamType() + " " + cParamInitBody.getParamVariable()
                    + " =  new short[]{-11,-22};\n");
            mList.add("    " + cParamInitBody.getParamType() + " " + cParamInitBody.getParamVariable()
                    + " =  new short[]{0,0};\n");
            mList.add("    " + cParamInitBody.getParamType() + " " + cParamInitBody.getParamVariable()
                    + " =  new short[]{Short.MAX_VALUE,Short.MIN_VALUE};\n");
            mList.add("    " + cParamInitBody.getParamType() + " " + cParamInitBody.getParamVariable()
                    + " =  new short[]{Short.MIN_VALUE,Short.MAX_VALUE};\n");
        } else {
            mList.add("    " + cParamInitBody.getParamType() + " " + cParamInitBody.getParamVariable()
                    + " =  Short.MAX_VALUE;\n");
            mList.add("    " + cParamInitBody.getParamType() + " " + cParamInitBody.getParamVariable()
                    + " =  Short.MIN_VALUE;\n");
            mList.add("    " + cParamInitBody.getParamType() + " " + cParamInitBody.getParamVariable() + " =  0;\n");

        }
        return mList;
    }
}
