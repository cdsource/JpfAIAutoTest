/**
 * copyrigth by wupf@ 2019年1月15日
 */
package org.jpf.aut.gts.gtm.plugins;

import org.jpf.aut.gts.gtm.api.MethodCallerable;

/**
 * @author wupf@asiainfo.com
 *
 */
public class SystemSetProperty implements MethodCallerable {

  /**
   * 
   */
  public SystemSetProperty() {
    // TODO Auto-generated constructor stub
  }

  @Override
  public boolean isCalled(String strMethodCode) {
    // TODO Auto-generated method stub
    // System.getProperty(ENV_KEY_AI_CENTER_ID);
    return false;
  }

  @Override
  public void doInitCode() {
    // TODO Auto-generated method stub
    // add System.setProperty(key,value);
    // value: 0,null,"",-1,100,1a2b;
  }

}
