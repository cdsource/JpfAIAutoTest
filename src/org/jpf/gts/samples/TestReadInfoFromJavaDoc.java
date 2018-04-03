/**
 * 
 */
package org.jpf.gts.samples;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.jpf.gts.gtm.gtConstructor.GeneratorConstructorImpl;
import org.jpf.unittests.generateuts.GenerateConst;
import org.jpf.unittests.generateuts.JpfMethodInfo;
import org.jpf.unittests.generateuts.JpfUtMethodInfo;
import org.jpf.unittests.generateuts.RunResult;
import org.jpf.unittests.generateuts.genbyjavadoc.ReadInfoFromJavaDoc;
import org.jpf.unittests.generateuts.utils.ParseJavaSourceFile;

/**
 * @author Administrator
 *
 */
public class TestReadInfoFromJavaDoc {
    private static final Logger logger = LogManager.getLogger();
	/**
	 * 
	 */
	public TestReadInfoFromJavaDoc() {
		// TODO Auto-generated constructor stub
	}
    public static void main(String[] args)
    {
    	try
    	{
    		String strJavaFileName="D:\\jworkspaces\\JpfUnitTest\\src\\org\\jpf\\gts\\samples\\SampleJavaDoc.java";
    		
            GeneratorConstructorImpl cGeneratorConstructorImpl = new GeneratorConstructorImpl();

            CompilationUnit cCompilationUnit = ParseJavaSourceFile.getInstance().parseJavaSourceFile17(strJavaFileName);

            List types = cCompilationUnit.types();
            if (types.size() == 0) {
                logger.warn("type=null:" + strJavaFileName);
                return ;
            }
            TypeDeclaration typeDec = (TypeDeclaration) types.get(0);
            logger.info("classname=" + typeDec.getName());
            logger.debug("typeDec.getModifiers()=" + typeDec.getModifiers());

            if (typeDec.getModifiers() == GenerateConst.CLASS_TYPE_ABSTRACT) {
                // abstract class
                logger.info("抽象类不能生产单元测试：" + strJavaFileName);
                RunResult.iAbstractFileCount++;
                return ;
            }


            // show methods
            MethodDeclaration methodDec[] = typeDec.getMethods();

            JpfMethodInfo cMethodInfo = new JpfMethodInfo();
            cMethodInfo.setClassName(typeDec.getName().toString());

            JpfUtMethodInfo cJpfUtMethodInfo=new JpfUtMethodInfo();
            for (MethodDeclaration method : methodDec) {
            	logger.info(method.toString() );
            	cMethodInfo.setStrJavaDoc(method.getJavadoc().toString());
            	cMethodInfo.setMethodName(method.toString());
                cMethodInfo.setMethodParam(method.parameters());
            	logger.info(cMethodInfo.getStrJavaDoc() );
            	ReadInfoFromJavaDoc.getInstance().initParamByJavaDoc(cMethodInfo, cJpfUtMethodInfo);	
            	logger.info(cJpfUtMethodInfo.getMethodParam());
            	logger.info(cJpfUtMethodInfo.getMethodAssert());
            }	
            	
    		//ReadInfoFromJavaDoc.getInstance().initParamByJavaDoc(cMethodInfo, null);	
    	}catch(Exception ex)
    	{
    		ex.printStackTrace();
    	}
    	
    }
}
