/** 
* @author 吴平福 
* E-mail:wupf@asiainfo.com 
* @version 创建时间：2017年11月17日 下午12:00:55 
* 类说明 
*/ 

package org.jpf.unittests.generateuts.methodparam;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jdt.core.dom.MethodDeclaration;

/**
 * 
 */
public class ReadInputOutPutFromJavaDoc {
    private static final Logger logger = LogManager.getLogger();
    /**
     * 
     */
    public ReadInputOutPutFromJavaDoc() {
        // TODO Auto-generated constructor stub
    }

    public static void ReadParamValueFromJavaDoc(MethodDeclaration method) {
        if (method.getJavadoc() != null) {
            logger.info( method.getJavadoc().toString());
        }
    }
}
