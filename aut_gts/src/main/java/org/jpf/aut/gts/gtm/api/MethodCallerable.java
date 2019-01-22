/**
 * copyrigth by wupf@ 2019年1月15日
 */
package org.jpf.aut.gts.gtm.api;

/**
 * @author wupf@asiainfo.com
 *
 */
public interface MethodCallerable {
  boolean isCalled(String strMethodCode);

  void doInitCode();
}
