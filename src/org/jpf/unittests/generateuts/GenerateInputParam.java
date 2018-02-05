/** 
* @author 吴平福 
* E-mail:wupf@asiainfo.com 
* @version 创建时间：2018年1月31日 上午10:05:04 
* 类说明 
*/ 

package org.jpf.unittests.generateuts;


/**
 * 
 */
public class GenerateInputParam {

    /**
     * 
     */
    public GenerateInputParam() {
        // TODO Auto-generated constructor stub
    }
    //单元测试函数超时设置
    public static boolean bNeedTimeOut=false;
    
    //生成的测试代码的基础类
    public static String UT_ExtendClassName="abstractJpfSelfTest";
    
    //生成的测试文件保存位置
    public static String Save_UT_Path="D:\\svn\\ecommerce-branch-20170912\\app-web-wtld\\src\\main\\java\\com\\asiainfo\\ebiz\\selftesta";
    
    //生成的测试文件的包名，如果没有，则参考源文件
    public static String New_Package_Name="package com.asiainfo.ebiz.selftesta;";
    
    public static String FilePath_Find_Java_Source="D:\\svn\\ecommerce-branch-20170912";
    //对哪些文件生成测试过滤规则
    public static String FileNameFilter = ".*Manager.java";
    
    public static StringBuffer AddException()
    {
        StringBuffer sb = new StringBuffer();
        sb.append("        } catch (EbizException ex) { ").append("\n")
                .append("          ex.printStackTrace();").append("\n")
                .append("          logger.error(ex);").append("\n")
                .append("          cSelfJpfTestInfo.addCountEbizException(1);").append("\n");
        /*
                .append("        } catch (SQLException ex) {").append("\n")
                .append("          ex.printStackTrace();")                .append("\n")
                .append("          logger.error(ex);").append("\n")
                .append("          cSelfJpfTestInfo.addCountSQLException(1);").append("\n");
        */
        return sb;
    }
    
    //根据类名查找BEAN ID
    public static String SPRING_XML_FILEPATH="D:\\svn\\ecommerce-branch-20170912\\app-web-common\\src\\main\\java\\config";
    
    //根据日志查找参数
    public static String RUN_LOG_FILEPATH="D:\\abc\\debugrun";
    
    public static String SELF_LOG_FILEPATH="D:\\abc\\selftest";
    
    
}
