/** 
* @author 吴平福 
* E-mail:wupf@asiainfo.com 
* @version 创建时间：2017年10月18日 下午10:43:41 
* 类说明 
*/ 

package org.jpf.gts.gtConstructor;

import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.jpf.unittests.generateuts.JpfUtInfo;

/**
 * 
 */
public interface IGTMForConstructor {
    public void doGenerate(MethodDeclaration methodDec[], String strClass, JpfUtInfo cJpfUtInfo);
}
