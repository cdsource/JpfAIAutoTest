/** 
* @author 吴平福 
* E-mail:wupf@asiainfo.com 
* @version 创建时间：2017年9月29日 上午11:28:20 
* 类说明 
*/ 

package org.jpf.unittests.generateuts;

import java.util.List;

/**
 * 
 */
public class GenerateMethodPublicStatic extends GenerateMethod {

    /**
     * 
     */
    public GenerateMethodPublicStatic() {
        // TODO Auto-generated constructor stub
    }

    /* (non-Javadoc)
     * @see org.jpf.unittests.generateuts.GenerateMethod#addClassInstance(java.lang.String, java.lang.String, java.util.List, org.jpf.unittests.generateuts.UtFileText)
     */
    @Override
    public StringBuffer addClassInstance( String strClass, List MethodParam,
            UtFileText cUtFileText) {
        // TODO Auto-generated method stub
        StringBuffer sb=new StringBuffer();
        return sb;
    }

    /* (non-Javadoc)
     * @see org.jpf.unittests.generateuts.GenerateMethod#addMethodCaller(java.lang.String, java.lang.String, java.util.List, org.jpf.unittests.generateuts.UtFileText)
     */
    @Override
    public StringBuffer addMethodCaller(String strClass, String strMethod, List MethodParam, UtFileText cUtFileText) {
        // TODO Auto-generated method stub
        StringBuffer sb=new StringBuffer();
        sb .append("    ").append(strClass) .append(".").append(strMethod);
        return sb;
    }
}
