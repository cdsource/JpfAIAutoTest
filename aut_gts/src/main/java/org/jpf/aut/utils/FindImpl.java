/** 
* @author 吴平福 
* E-mail:wupf 
* @version 创建时间：2018年2月26日 下午8:44:59 
* 类说明 
*/ 

package org.jpf.aut.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 
 */
public class FindImpl {

    /**
     * 
     */
    private FindImpl() {
        // TODO Auto-generated constructor stub
    }
    private static final Logger logger = LogManager.getLogger();


    // 已经自行实例化
    private static final FindImpl Instance = new FindImpl();

    // 静态工厂方法
    public static FindImpl getInstance() {
        return Instance;
    }
    
    /**
     * 
     * @category 查找实现类 
     * @author 吴平福 
     * @param strInterfaceName
     * @return
     * update 2018年2月26日
     */
    public static String getImplClass(String strInterfaceName)
    {
        
        return "";
    }
}
