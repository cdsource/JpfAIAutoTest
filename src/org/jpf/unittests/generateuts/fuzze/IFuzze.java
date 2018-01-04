/** 
* @author 吴平福 
* E-mail:wupf@asiainfo.com 
* @version 创建时间：2017年11月12日 下午12:53:30 
* 类说明 
*/ 

package org.jpf.unittests.generateuts.fuzze;

import java.util.ArrayList;

import org.jpf.unittests.generateuts.ParamInitBody;


/**
 * 
 */
public interface IFuzze {
    ArrayList<String> getFuzzeForNull(ParamInitBody cParamInitBody);
}
