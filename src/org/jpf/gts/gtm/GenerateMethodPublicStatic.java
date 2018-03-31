/** 
* @author 吴平福 
* E-mail:wupf@asiainfo.com 
* @version 创建时间：2017年9月29日 上午11:28:20 
* 类说明 
*/ 

package org.jpf.gts.gtm;

import java.util.List;

import org.jpf.unittests.generateuts.JpfUtInfo;

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
    public String addClassInstance( String strClass, List MethodParam,
            JpfUtInfo cJpfUtInfo) {
        // TODO Auto-generated method stub
        return "";
    }

    /* (non-Javadoc)
     * @see org.jpf.unittests.generateuts.GenerateMethod#addMethodCaller(java.lang.String, java.lang.String, java.util.List, org.jpf.unittests.generateuts.UtFileText)
     */
    @Override
    public String addMethodCaller(String strClass, String strMethod, List MethodParam, JpfUtInfo cJpfUtInfo) {
        // TODO Auto-generated method stub
        StringBuffer sb=new StringBuffer();
        sb .append("    ").append(strClass) .append(".").append(strMethod);
        return sb.toString();
    }

    
    /* (non-Javadoc)
     * @see org.jpf.unittests.generateuts.GenerateMethod#addExtraMethod(org.jpf.unittests.generateuts.JpfUtInfo)
     */
    @Override
    public String addExtraMethod(String strClassName, String strPackageName) {
        // TODO Auto-generated method stub
        return "";
    }
}
