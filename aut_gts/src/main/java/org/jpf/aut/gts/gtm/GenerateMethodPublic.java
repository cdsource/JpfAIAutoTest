/**
 * @author 吴平福 E-mail:wupf@asiainfo.com
 * @version 创建时间：2017年9月29日 上午11:27:34 类说明
 */

package org.jpf.aut.gts.gtm;

import java.util.List;

import org.jpf.aut.base.JpfUtInfo;
import org.jpf.aut.gts.gtm.MethodParamBody;
import org.jpf.aut.utils.GenerateUtil2;


/**
 * 
 */
public class GenerateMethodPublic extends AbstractGenerateMethod {

    /**
     * 
     */
    public GenerateMethodPublic() {
        // TODO Auto-generated constructor stub
    }

    @Override
    public String addClassInstance(String strClass, List MethodParam,  JpfUtInfo cJpfUtInfo) {
        return cJpfUtInfo.getUtMinConstructor();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.jpf.unittests.generateuts.GenerateMethod#addMethodCaller(java.lang.String,
     * java.lang.StringBuffer)
     */
    @Override
    public String addMethodCaller(String strClass, String strMethod, List MethodParam, JpfUtInfo cJpfUtInfo) {
        // TODO Auto-generated method stub
        return "  wupf_fixture."+strMethod;
    }

    /* (non-Javadoc)
     * @see org.jpf.unittests.generateuts.GenerateMethod#addExtraMethod(org.jpf.unittests.generateuts.JpfUtInfo)
     */
    @Override
    public String addExtraMethod(String strClassName, String strPackageName) {
        // TODO Auto-generated method stub
        return "";
    }

	@Override
	public void addExtraImport(JpfUtInfo cJpfUtInfo) {
		// TODO Auto-generated method stub
		
	}
}
