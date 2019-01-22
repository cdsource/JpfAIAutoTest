/** 
* @author 吴平福 
* E-mail:wupf@aliyun.com 
* @version 创建时间：2018年1月31日 下午10:48:36 
* 类说明 
*/ 

package org.jpf.aut.base;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 
 */
public class RunResult {
	private static final Logger logger = LogManager.getLogger();
    /**
     * 
     */
    private RunResult() {

    }
    public static int TotalJavaSrcFileCount = 0;
    
    public static int iMethodCount = 1;
    
    //抽象类文件数量
    public static int AbstractFileCount = 0;
    //接口类文件数量
    public static int InterfaceFileCount = 0;
    //枚举类，暂时不支持
    public static int EnumFileCount=0;
    //产生的单元测试数量    
    public static int GenFileCount = 0;
    //可以产生单元测试的源文件数量
    public static int CanGenFileCount = 0;

	public static int iExistUtFileCount = 0;
    public static int iErrorFileCount = 0;
    
    public static int iPrivateMethodCount=0;
    public static int iPublicMethodCount=0;
    
    public static int iNoParamMethodCount=0;
    
    public static int iGenericTypeCount=0;
    
    public static int iFail_TestFile=0;
    public static int iFail_TargetFile=0; 		
    
    //生成的空单元测试文件数量
    public static int GenEmptyUtFileCount=0;
    
	/**
	 * 
	 * @author wupf@aliyun.com 2018年7月5日
	 */
	public static void printResult() {
		logger.info("TotalJavaSrcFileCount:" + RunResult.TotalJavaSrcFileCount);
		logger.info("generate files:" + RunResult.GenFileCount);
		logger.info("abstract files:" + RunResult.AbstractFileCount);
		logger.info("interface files:" + RunResult.InterfaceFileCount);
		logger.info("iEnumFileCount:" + RunResult.EnumFileCount);
		logger.info("exist ut files:" + RunResult.iExistUtFileCount);
		logger.info("Exception files:" + RunResult.iErrorFileCount);
		logger.info("iFail_TestFile:" + RunResult.iFail_TestFile);
		logger.info("iFail_TargetFile:" + RunResult.iFail_TargetFile);
		logger.info("GenericTypeCount:" + RunResult.iGenericTypeCount);
		logger.info("CommentRowCount:" + RunResult.iCommentCountRow);
		logger.info("CommentFileCount:" + RunResult.iCommentCountFile);
		logger.info("GenEmptyUtFileCount:" + RunResult.GenEmptyUtFileCount);
	}
	
	private static int iCommentCountRow=0;
	
	private static int iCommentCountFile=0;
	
	public static int getiCommentCountRow() {
		return iCommentCountRow;
	}

	public static void addiCommentCountRow() {
		RunResult.iCommentCountRow++; 
	}

	public static int getiCommentCountFile() {
		return iCommentCountFile;
	}

	public static void addiCommentCountFile() {
		RunResult.iCommentCountFile++;
	}
	
	
}
