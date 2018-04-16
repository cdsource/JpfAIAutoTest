/**
 * 
 */
package org.jpf.aitest.sample;

import org.jpf.aitest.GenerateInputParam;
import org.jpf.aitest.GenerateMain;

/**
 * @author Administrator
 *
 */
public class SampleLogMain {

    /**
     * @category @author 吴平福
     * @param args update 2017年11月20日
     */

    public static void main(String[] args) {
         
        GenerateInputParam.bNeedTimeOut=true;
        GenerateInputParam.FileNameFilter="";
        
        //根据日志生成
    	String strFileName = "D:\\jworkspaces\\JpfUnitTest\\src\\org\\jpf\\aitest\\sample\\SampleLog.java";
        
        new GenerateMain(1, strFileName);
    }
}
