/** 
* @author 吴平福 
* E-mail:wupf@asiainfo.com 
* @version 创建时间：2017年11月12日 下午12:53:30 
* 类说明 
*/ 

package org.jpf.aitest.gts.gtm.fuzzByParamType;

import java.util.ArrayList;

import org.jpf.aitest.gts.gtm.MethodParamBody;


/**
 * 
 */
public interface IFuzz {
     ArrayList<String> getFuzzeForNull(MethodParamBody cParamInitBody);
}
