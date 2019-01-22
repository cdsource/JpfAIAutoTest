/**
 * copyrigth by wupf@ 2019年1月17日
 */
package org.jpf.aut.optimal.api;

import java.util.List;

/**
 * @author wupf@asiainfo.com
 *
 */
public interface OptimalCodeQueryable {
  // 查询优选代码的测试CASE
  List<String> queryOptimalCodeCase(String strMethodName, String strParams, String strMethodBody);
}
