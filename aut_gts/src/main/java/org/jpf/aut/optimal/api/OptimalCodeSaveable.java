/**
 * copyrigth by wupf@ 2019年1月17日
 */
package org.jpf.aut.optimal.api;

/**
 * @author wupf@asiainfo.com
 *
 */
public interface OptimalCodeSaveable {
  // 保存优选代码
  String saveOptimalCode(String strMethodName, String strParams, String strMethodBody,
      String strTestCase, String strCoverageInfo);
}
