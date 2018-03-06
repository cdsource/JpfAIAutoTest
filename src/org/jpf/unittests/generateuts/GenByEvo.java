/** 
* @author 吴平福 
* E-mail:wupf@asiainfo.com 
* @version 创建时间：2018年2月8日 上午11:22:28 
* 类说明 
*/ 

package org.jpf.unittests.generateuts;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.evosuite.EvoSuite;
import org.evosuite.Properties;
import org.evosuite.utils.Randomness;

/**
 * 
 */
public class GenByEvo {
    private static final Logger logger = LogManager.getLogger();
    /**
     * 
     */
    public GenByEvo() {
        
        long start = System.currentTimeMillis();
        try {
            String[] args= {" class org.jpf.unittests.generateuts.GenerateBaseMethods"," projectCP D:\\svn\\ecommerce-branch-20170912\\app-web-common\\target\\classes"};
            EvoSuite evosuite = new EvoSuite();
            evosuite.parseCommandLine(args);
        } catch (Throwable t) {
            logger.error("Fatal crash on main EvoSuite process. Class "
                    + Properties.TARGET_CLASS + " using seed " + Randomness.getSeed()
                    + ". Configuration id : " + Properties.CONFIGURATION_ID, t);
        }
        logger.info("ExcuteTime " + (System.currentTimeMillis() - start) + "ms");
    }

    /**
     * @category 
     * @author 吴平福 
     * @param args
     * update 2018年2月8日
     */

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        long start = System.currentTimeMillis();
        try {
            String[] args2= {" -class"," org.jpf.unittests.generateuts.GenerateBaseMethods"," -projectCP"," D:\\svn\\ecommerce-branch-20170912\\app-web-common\\target\\classes"};
            EvoSuite evosuite = new EvoSuite();
            evosuite.parseCommandLine(args);
        } catch (Throwable t) {
            logger.error("Fatal crash on main EvoSuite process. Class "
                    + Properties.TARGET_CLASS + " using seed " + Randomness.getSeed()
                    + ". Configuration id : " + Properties.CONFIGURATION_ID, t);
        }
        logger.info("ExcuteTime " + (System.currentTimeMillis() - start) + "ms");
    }

}
