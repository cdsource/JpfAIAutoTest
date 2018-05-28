/**
 * 
 */
package org.jpf.aitest.gts.genbytool;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.evosuite.EvoSuite;
import org.evosuite.Properties;
import org.evosuite.utils.Randomness;

/**
 * @author Administrator
 *
 */
public class GenByCodePro {
    private static final Logger logger = LogManager.getLogger();
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
        long start = System.currentTimeMillis();
        try {
        	String arg=" -class org.jpf.unittests.generateuts.GenerateBaseMethods -projectCP D:\\svn\\ecommerce-branch-20170912\\app-web-common\\target\\classes";
            //String[] args= arg.split(" ");
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
