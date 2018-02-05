/** 
* @author 吴平福 
* E-mail:wupf@asiainfo.com 
* @version 创建时间：2018年2月4日 下午5:30:52 
* 类说明 
*/ 

package org.jpf.unittests.generateuts.genbytool;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 
 */
public class GenByRandoop {

    private static final Logger logger = LogManager.getLogger();


    // 已经自行实例化
    private static final GenByRandoop Instance = new GenByRandoop();

    // 静态工厂方法
    public static GenByRandoop getInstance() {
        return Instance;
    }

}
