/** 
* @author 吴平福 
* E-mail:wupf@asiainfo.com 
* @version 创建时间：2017年11月12日 下午9:53:22 
* 类说明 
*/ 

package org.jpf.unittests.generateuts;

/**
 * 
 */
public class GenerateConst {

    public static final String GENERATE_AUTHOR="wupf@asiainfo.com";
    
    public static final int Max_CaseCount_PerMethod=5;
    
    public static int iAbstractFileCount = 0;
    public static int iInterfaceFileCount = 0;

    public static int iGenFileCount = 0;
    public static int iExistUtFileCount = 0;
    public static int iErrorFileCount = 0;
    public static int iEnumFileCount = 0;

    
    //抽象类，不能生成单元测试
    public final static int CLASS_TYPE_ABSTRACT =1025;
}
