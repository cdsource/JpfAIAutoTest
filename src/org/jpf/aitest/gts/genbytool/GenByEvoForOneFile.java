/** 
* @author 吴平福 
* E-mail:wupf@asiainfo.com 
* @version 创建时间：2018年2月8日 上午11:22:28 
* 类说明 
*/ 

package org.jpf.aitest.gts.genbytool;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.evosuite.EvoSuite;
import org.evosuite.Properties;
import org.evosuite.utils.Randomness;

/**
 * 
 */
public class GenByEvoForOneFile {
    private static final Logger logger = LogManager.getLogger();
    /**
     * 
     */
    public GenByEvoForOneFile() {
        
        long start = System.currentTimeMillis();
        try {
        	String arg=" -class org.jpf.unittests.generateuts.GenerateBaseMethods -projectCP D:\\svn\\ecommerce-branch-20170912\\app-web-common\\target\\classes";
            String[] args= arg.split(" ");
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
        	for(int i=0;i<args.length;i++)
        	{
        		logger.info(args[i]);
        	}
        	logger.info(args.length);
        	String arg=" -class org.jpf.unittests.generateuts.GenerateBaseMethods -projectCP D:\\svn\\ecommerce-branch-20170912\\app-web-common\\target\\classes";
            String[] args2= new String[7];
            args2[0]="-class";
            args2[1]="com.asiainfo.ebiz.chongfa.WeiXinLlzcUpAndDownSender";
            args2[2]="-projectCP";
            args2[3]="D:\\svn\\ecommerce-branch-20170912\\app-util\\target\\classes;C:\\Users\\Administrator\\.m2\\repository\\org\\apache\\commons-httpclient\\3.1\\commons-httpclient-3.1.jar";
            //达到100%覆盖即退出
            args2[4]="-criterion";
            args2[5]="branch";
            //搜索预算时间
            args2[6]="-Dsearch_budget=20";
            //断言负载的长测试
            args2[7]="-Dassertion_strategy=all";
            
            // 已经存在TEST的情况下参数
            //$EVOSUITE -class tutorial.Stack -Djunit=tutorial.StackTest -projectCP target/classes:target/test-classes -criterion branch
            //多个文件 -target 取代 -class 可以是目录，可以是JAR文件
            //$EVOSUITE -target target/classes 
            
            for(int i=0;i<args2.length;i++)
        	{
        		logger.info(args2[i]);
        	}
            EvoSuite evosuite = new EvoSuite();
            evosuite.parseCommandLine(args2);
        } catch (Throwable t) {
            logger.error("Fatal crash on main EvoSuite process. Class "
                    + Properties.TARGET_CLASS + " using seed " + Randomness.getSeed()
                    + ". Configuration id : " + Properties.CONFIGURATION_ID, t);
        }
        logger.info("ExcuteTime " + (System.currentTimeMillis() - start) + "ms");

    }

}
