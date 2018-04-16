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
public class SampleNLPMain {

    /**
     * @category @author 吴平福
     * @param args update 2017年11月20日
     */

    public static void main(String[] args) {

        //根据NLP生成
        GenerateInputParam.bNeedTimeOut=true;
        GenerateInputParam.FileNameFilter="";
        
        String strFileName="D:\\jworkspaces\\JpfUnitTest\\src\\org\\jpf\\aitest\\sample\\SampleNLP.java";
        
        
        new GenerateMain(1, strFileName);
    }

}
