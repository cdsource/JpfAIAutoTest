/**
 * @author 吴平福 E-mail:wupf@asiainfo.com
 * @version 创建时间：2017年9月29日 上午11:27:34 类说明
 */

package org.jpf.unittests.generateuts;

import java.util.List;


/**
 * 
 */
public class GenerateMethodPublic extends GenerateMethod {

    /**
     * 
     */
    public GenerateMethodPublic() {
        // TODO Auto-generated constructor stub
    }

    @Override
    public StringBuffer addClassInstance(String strClass, List MethodParam,  UtFileText cUtFileText) {
        StringBuffer sb = new StringBuffer();
        sb.append(cUtFileText.getMinConstructor());
        return sb;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.jpf.unittests.generateuts.GenerateMethod#addMethodCaller(java.lang.String,
     * java.lang.StringBuffer)
     */
    @Override
    public StringBuffer addMethodCaller(String strClass, String strMethod, List MethodParam, UtFileText cUtFileText) {
        // TODO Auto-generated method stub
        StringBuffer sb = new StringBuffer();
        sb.append("  fixture.").append(strMethod);
        return sb;
    }
}
