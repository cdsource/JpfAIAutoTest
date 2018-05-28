/** 
* @author 吴平福 
* E-mail:wupf@asiainfo.com 
* @version 创建时间：2018年2月27日 上午9:53:58 
* 类说明 
*/ 

package org.jpf.aitest.utils;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.jpf.aitest.JpfUtInfo;

import com.asiainfo.utils.ios.AiFileUtil;

/**
 * 
 */
public class AddImport {

    /**
     * 
     */
    private AddImport() {
    }
    private static final Logger logger = LogManager.getLogger();


    // 已经自行实例化
    private static final AddImport Instance = new AddImport();

    // 静态工厂方法
    public static AddImport getInstance() {
        return Instance;
    }
    /**
     * 
     * @category 查找引用类 
     * @author 吴平福 
     * @param strInterfaceName
     * @return
     * update 2018年2月26日
     */
    public String addImportForList(final String strParamType, JpfUtInfo cJpfUtInfo)
    {
        try {
            int i=strParamType.indexOf("<");
            String strNewParamType=strParamType;
            if (i>0)
            {
            	strNewParamType=strNewParamType.substring(i+1,strParamType.length()).trim();
            }
            i=strParamType.lastIndexOf(">");
            if (i>0)
            {
            	strNewParamType=strNewParamType.substring(0,i).trim();
            }
            
            //String strJavaFileName = FindClassInfoUtil.getInstance().findJavaFile(strParamType);
            if (strNewParamType.indexOf(".")==-1)
            {
            	strNewParamType= cJpfUtInfo.getCurrentJavaFilePackage()+"."+strNewParamType;
            }
            logger.debug ("strParamType="+strNewParamType.trim());
            String strJavaFileName = FindClassInfoUtil.getInstance().findJavaFile(strNewParamType);
            logger.debug ("strParamType="+strNewParamType.trim());
            if (null !=strJavaFileName )
            {
                cJpfUtInfo.addImport(strNewParamType);
            }else
            {
            	strNewParamType=cJpfUtInfo.findImport(strParamType);
            	 logger.debug ("strParamType="+strNewParamType.trim());
            }
           
        }catch (Exception ex) {
            // TODO: handle exception
            logger.error(ex);
        }

        return "";
    }
    /**
     * 
     * @category 查找引用类 
     * @author 吴平福 
     * @param strInterfaceName
     * @return
     * update 2018年2月26日
     */
    public String addImportForList2(String strParamType, JpfUtInfo cJpfUtInfo)
    {
        try {
            int i=strParamType.indexOf("<");
            if (i>0)
            {
                strParamType=strParamType.substring(i+1,strParamType.length()).trim();
            }
            i=strParamType.lastIndexOf(">");
            if (i>0)
            {
                strParamType=strParamType.substring(0,i).trim();
            }
            logger.debug ("strParamType="+strParamType.trim());
            String strJavaFileName = FindClassInfoUtil.getInstance().findJavaFile(strParamType);
            if (null ==strJavaFileName )
            {
                strParamType= cJpfUtInfo.getCurrentJavaFilePackage()+"."+strParamType;
            }
            
            strJavaFileName = FindClassInfoUtil.getInstance().findJavaFile(strParamType);
            if (null !=strJavaFileName && strJavaFileName.trim().length()>0) {

                CompilationUnit cCompilationUnit = ParseJavaSourceFile.getInstance().parseJavaSourceFile17(strJavaFileName);


                List types = cCompilationUnit.types();
                if (types.size() == 0) {
                    logger.warn("type=null:" + strJavaFileName);
                    return "";
                }
                TypeDeclaration typeDec = (TypeDeclaration) types.get(0);
                logger.info("classname=" + typeDec.getName());
                logger.debug("typeDec.getModifiers()=" +  cCompilationUnit.getPackage().getName().toString());
                return  cCompilationUnit.getPackage().getName().toString()+"."+typeDec.getName().toString();
            }
        }catch (Exception ex) {
            // TODO: handle exception
            logger.error(ex);
        }

        return "";
    }
    
    public static void main(String[] args)
    {
        String strParamType="List< aa  > ";
        int i=strParamType.indexOf("<");
        if (i>0)
        {
            strParamType=strParamType.substring(i+1,strParamType.length()).trim();
        }
        i=strParamType.lastIndexOf(">");
        if (i>0)
        {
            strParamType=strParamType.substring(0,i);
        }
        System.out.println(strParamType.trim());
    }
}
