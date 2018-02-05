/** 
* @author 吴平福 
* E-mail:wupf@asiainfo.com 
* @version 创建时间：2018年2月4日 下午5:31:51 
* 类说明 
*/ 

package org.jpf.unittests.generateuts.genbytool;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 
 */
public class GenByEvoSuite {

    private static final Logger logger = LogManager.getLogger();


    // 已经自行实例化
    private static final GenByEvoSuite Instance = new GenByEvoSuite();

    // 静态工厂方法
    public static GenByEvoSuite getInstance() {
        return Instance;
    }
    
}
