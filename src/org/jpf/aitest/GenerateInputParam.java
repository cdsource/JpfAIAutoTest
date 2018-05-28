/** 
* @author 吴平福 
* E-mail:wupf@asiainfo.com 
* @version 创建时间：2018年1月31日 上午10:05:04 
* 类说明 
*/ 

package org.jpf.aitest;


/**
 * 
 */
public class GenerateInputParam {

    /**
     * 
     */
    private GenerateInputParam() {
    }
    //单元测试函数超时设置
    public static boolean bNeedTimeOut=false;
       
    //JAVA文件编码
    public static String JAVA_ENCODE="GBK";
   
    
    //对哪些文件生成测试过滤规则
    public static String FileNameFilter = ".*Manager.java";

    //查找JAVA代码路径
    public static String FilePath_Find_Java_Source="D:\\svn\\ecommerce-branch-20170912\\app-dao\\src\\main\\java;D:\\svn\\ecommerce-branch-20170912\\poffice-util\\src\\main;D:\\svn\\ecommerce-branch-20170912\\app-util\\src\\main\\java";

    public static String SRC_PATH="";
}
