/** 
* @author 吴平福 
* E-mail:wupf@asiainfo.com 
* @version 创建时间：2018年3月6日 上午10:17:43 
* 类说明 
*/ 

package org.jpf.unittests.generateuts;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 
 */
public class GenerateInterfaceTests   extends GenerateTests{
    private static final Logger logger = LogManager.getLogger();
    /**
     * 
     */
    public GenerateInterfaceTests() {
        // TODO Auto-generated constructor stub
    }
    /* (non-Javadoc)
     * @see org.jpf.unittests.generateuts.GenerateTests#addExtraImport(org.jpf.unittests.generateuts.JpfUtInfo)
     */
    @Override
    protected void addExtraImport(JpfUtInfo cJpfUtInfo) {
        // TODO Auto-generated method stub
        
    }
    /* (non-Javadoc)
     * @see org.jpf.unittests.generateuts.GenerateTests#addExtraBasic(org.jpf.unittests.generateuts.JpfUtInfo)
     */
    @Override
    protected void addExtraBasic(String strClassName,JpfUtInfo cJpfUtInfo) {
        // TODO Auto-generated method stub
        cJpfUtInfo.setUtBasic(
                GenerateBaseMethods.addExtraMethod(strClassName, cJpfUtInfo.getUtPackage()));
        cJpfUtInfo.addImport(cJpfUtInfo.getUtPackage().replaceAll("package", "import").replaceAll(";",
                "." + strClassName + ";"));
    }

}
