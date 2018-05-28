/** 
* @author 吴平福 
* E-mail:wupf@asiainfo.com 
* @version 创建时间：2018年3月6日 上午11:33:03 
* 类说明 
*/ 

package org.jpf.aitest.gts.gtf.forabstract;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jpf.aitest.JpfUtInfo;
import org.jpf.aitest.gts.gtf.AbstractGenerateTests;

/**
 * 
 */
public class GTFForAbstract extends AbstractGenerateTests {
    private static final Logger logger = LogManager.getLogger();

    /**
     * 
     */
    public GTFForAbstract() {
        // TODO Auto-generated constructor stub
    }

    /* (non-Javadoc)
     * @see org.jpf.unittests.generateuts.GenerateTests#addExtraImport(org.jpf.unittests.generateuts.JpfUtInfo)
     */
    @Override
    public void addExtraImport(JpfUtInfo cJpfUtInfo) {
        // TODO Auto-generated method stub
        
    }

    /* (non-Javadoc)
     * @see org.jpf.unittests.generateuts.GenerateTests#addExtraBasic(java.lang.String, org.jpf.unittests.generateuts.JpfUtInfo)
     */
    @Override
    protected void addExtraBasic(String strClassName, JpfUtInfo cJpfUtInfo) {
        // TODO Auto-generated method stub
        
    }

	@Override
	public String getUTSavePath() {
		// TODO Auto-generated method stub
		return "";
	}

	@Override
	public void setNewPackage(JpfUtInfo cJpfUtInfo) {
		// TODO Auto-generated method stub

	}


}
