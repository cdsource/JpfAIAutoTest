/** 
* copyrigth by 吴平福
* @author 吴平福 
* E-mail:wupf@aliyun.com 
* @version 创建时间：2017年9月30日 下午3:49:28 
* 类说明 
*/ 
package org.jpf.aut.common.consts;

public enum AccessModifierEnum {

    Public("public"), Protected("protected"), PackageLocal(""), Private("private");

    private String name;

    private AccessModifierEnum(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }

}
