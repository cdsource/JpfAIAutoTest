/**
 * copyrigth by wupf@ 2019年1月17日
 */
package org.jpf.aut.optimal.api;

/**
 * @author wupf@asiainfo.com
 *
 */
public interface CodeOptimalable {

  // 是否是优选代码
  boolean checkOptimalCode(String strFileName, String strMethodName, String strCoverageReport);
}
