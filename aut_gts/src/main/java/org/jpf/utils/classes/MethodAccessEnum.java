/**
 * copyrigth by wupf@ 2019年3月3日
 */
package org.jpf.utils.classes;

/**
 * @author wupf@asiainfo.com
 *
 */
public enum MethodAccessEnum {
  AccessPrivateStatic(10), AccessPrivate(2), AccessPublic(1), AccessPublicStatic(
      9), AccessProtected(3);
  /**
   * 枚举值
   */
  private int value;

  MethodAccessEnum(int value) {
    this.value = value;
  }

  /**
   * GET方法
   *
   * @return 值
   */
  public int getValue() {
    return value;
  }
}
