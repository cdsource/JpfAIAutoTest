/** 
* @author 吴平福 
* E-mail:wupf@asiainfo.com 
* @version 创建时间：2018年3月6日 上午11:38:48 
* 类说明 
*/ 

package org.jpf.utils.classes;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.jpf.utils.ios.AiFileUtil;

/**
 * 
 */
public class ParseJavaSourceFile {

    /**
     * 
     */
    private ParseJavaSourceFile() {
    }
    private static final Logger logger = LogManager.getLogger();


    // 已经自行实例化
    private static final ParseJavaSourceFile Instance = new ParseJavaSourceFile();

    // 静态工厂方法
    public static ParseJavaSourceFile getInstance() {
        return Instance;
    }
    
    /**
     * 
     * @category 
     * @author 吴平福 
     * @param sourceFileName
     * @return
     * @throws Exception
     * update 2018年3月6日
     */
    public CompilationUnit parseJavaSourceFile17(String sourceFileName,String strEnCode )throws Exception
    {
        logger.trace(sourceFileName);
        String sourceString = AiFileUtil.getFileTxt(sourceFileName, strEnCode);
        ASTParser astParser = ASTParser.newParser(AST.JLS8);
        astParser.setKind(ASTParser.K_COMPILATION_UNIT);
        astParser.setResolveBindings(true);
        astParser.setSource(sourceString.toCharArray());
        
        Map options = JavaCore.getOptions();
        options.put(JavaCore.COMPILER_SOURCE, JavaCore.VERSION_1_7); // or newer version
        astParser.setCompilerOptions(options);
        
        return (CompilationUnit) astParser.createAST(null);
    }

    /**
     * 
     * @category 
     * @author 吴平福 
     * @param sourceFileName
     * @return
     * @throws Exception
     * update 2018年3月6日
     */
    public CompilationUnit parseJavaSourceFile18(String sourceFileName,String strEnCode )throws Exception
    {
        logger.trace(sourceFileName);
        String sourceString = AiFileUtil.getFileTxt(sourceFileName, strEnCode);
        ASTParser astParser = ASTParser.newParser(AST.JLS8);
        astParser.setKind(ASTParser.K_COMPILATION_UNIT);
        astParser.setResolveBindings(true);
        astParser.setSource(sourceString.toCharArray());
        
        Map options = JavaCore.getOptions();
        options.put(JavaCore.COMPILER_SOURCE, JavaCore.VERSION_1_8); // or newer version
        astParser.setCompilerOptions(options);
        
        return (CompilationUnit) astParser.createAST(null);
    }
}
