/** 
* @author 吴平福 
* E-mail:wupf@asiainfo.com 
* @version 创建时间：2018年3月6日 上午10:16:51 
* 类说明 
*/ 

package org.jpf.unittests.generateuts;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.ImportDeclaration;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.jpf.unittests.generateuts.utils.AddImport;

import com.asiainfo.utils.ios.AiFileUtil;

/**
 * 
 */
public class GenerateTests {
    private static final Logger logger = LogManager.getLogger();
    /**
     * 
     */
    public GenerateTests() {
        // TODO Auto-generated constructor stub
    }
    /**
     * 
     * @category 增加IMPORT
     * @author 吴平福
     * @param importList
     * @param sb update 2017年9月29日
     */
    protected void addImport(List importList, JpfUtInfo cJpfUtInfo) {

        for (Object obj : importList) {
            ImportDeclaration importDec = (ImportDeclaration) obj;
            //logger.debug(importDec.toString());
            cJpfUtInfo.addImport(importDec.toString().trim());
        }

        // sb.append("import org.easymock.EasyMock;").append("\n");
        cJpfUtInfo.addImport("import org.junit.*;");
        cJpfUtInfo.addImport("import static org.junit.Assert.*;");
        /*
         * sb.append("import java.lang.reflect.Method;").append("\n");
         * sb.append("import java.util.ArrayList;").append("\n");
         * sb.append("import java.util.HashMap;").append("\n");
         */

    }
    
    /**
     * 
     * @category 生成单个单元测试文件
     * @author 吴平福
     * @param strFileName update 2017年9月30日
     */
    public boolean doGenerateFile(String strFileName) {

        logger.info("source java File :" + strFileName);
        String strSaveUtFileName = strFileName.substring(0, strFileName.indexOf("src")) + "pom.xml";
        if (AiFileUtil.FileExist(strSaveUtFileName)) {
            // maven
            strSaveUtFileName = strFileName.replace(".java", "Test.java").replaceAll("main", "test");
        } else {
            // ant
            strSaveUtFileName = strFileName.replace(".java", "Test.java").replaceAll("src", "test");
        }
        if (GenerateInputParam.Save_UT_Path.length() > 0) {
            strSaveUtFileName = GenerateInputParam.Save_UT_Path + "\\"
                    + AiFileUtil.getFileName(strFileName).replaceAll(".java", "Test.java");
        }
        logger.info("save ut File :" + strSaveUtFileName);
        if (AiFileUtil.FileExist(strSaveUtFileName)) {
            logger.warn("File Exist:" + strSaveUtFileName);
            RunResult.iExistUtFileCount++;
        } else {
            JpfUtInfo cJpfUtInfo = new JpfUtInfo();
            cJpfUtInfo.setCurrentJavaFile(strFileName);
            RunResult.iMethodCount = 1;
            if (!generateUTFromJavaFile(strFileName, cJpfUtInfo)) {
                logger.warn("waring: " + strFileName);
                RunResult.iErrorFileCount++;
                return false;
            }
            AiFileUtil.saveFile(strSaveUtFileName, cJpfUtInfo.toString());

            return true;
        }


        return false;
    }
    
    /**
     * 
     * @category 
     * @author 吴平福
     * @param sourceFileName
     * @param cJpfUtInfo
     * @return update 2018年1月30日
     */
    public boolean generateUTFromJavaFile(final String sourceFileName, JpfUtInfo cJpfUtInfo) {
        // TODO Auto-generated constructor stub

        try {
            GeneratorConstructorImpl cConstructorInfo = new GeneratorConstructorImpl();
            String sourceString = AiFileUtil.getFileTxt(sourceFileName, "GBK");
            ASTParser astParser = ASTParser.newParser(AST.JLS8);
            astParser.setKind(ASTParser.K_COMPILATION_UNIT);
            astParser.setResolveBindings(true);

            astParser.setSource(sourceString.toCharArray());
            CompilationUnit cCompilationUnit = (CompilationUnit) astParser.createAST(null);
            // test0468(cCompilationUnit);
            // show class name

            List types = cCompilationUnit.types();
            if (types.size() == 0) {
                logger.warn("type=null:" + sourceFileName);
                return false;
            }
            TypeDeclaration typeDec = (TypeDeclaration) types.get(0);
            logger.info("classname=" + typeDec.getName());
            logger.debug("typeDec.getModifiers()=" + typeDec.getModifiers());
            if (typeDec.getModifiers() == GenerateConst.CLASS_TYPE_ABSTRACT) {
                // abstract class
                logger.info("抽象类不能生产单元测试：" + sourceFileName);
                RunResult.iAbstractFileCount++;
                return false;
            }

            /*
             * if (!typeDec.isInterface()) { return null; }
             */
            // addPackage
            cJpfUtInfo.setUtPackage("package " + cCompilationUnit.getPackage().getName().toString() + ";\n");
            cJpfUtInfo.setSourcePackage(cCompilationUnit.getPackage().getName().toString());
            // add import
            addImport(cCompilationUnit.imports(), cJpfUtInfo);
            // add class javadoc
            cJpfUtInfo.setUtFileDesc(GenerateBaseMethods.addClassDesc(typeDec.getName().toString()));
            // add class declare
            cJpfUtInfo.setUtClassDeclare(GenerateBaseMethods.addClassDeclare(typeDec.getName().toString()));


            // show methods
            MethodDeclaration methodDec[] = typeDec.getMethods();

            cConstructorInfo.doGenerate(methodDec, typeDec.getName().toString(), cJpfUtInfo);

            JpfMethodInfo cMethodInfo = new JpfMethodInfo();
            cMethodInfo.setClassName(typeDec.getName().toString());

            // addExtraMethod(typeDec.getName().toString(), cJpfUtInfo);

            for (MethodDeclaration method : methodDec) {

                SimpleName methodName = method.getName();
                if (methodName.toString().equalsIgnoreCase("main")) {
                    continue;
                }
                // 1 public
                // 2 private
                // 9 public static
                // 10 private static

                List param = method.parameters();

                Type returnType = method.getReturnType2();
                logger.debug("method return type:" + returnType);

                cMethodInfo.setModifiers(method.getModifiers());
                cMethodInfo.setMethodName(methodName.toString());
                cMethodInfo.setMethodParam(param);
                if (method.getJavadoc() != null) {
                    cMethodInfo.setStrJavaDoc(method.getJavadoc().toString());
                }
                cMethodInfo.setMethodExceptions(method.thrownExceptionTypes());

                /*
                for (int i = 0; i < cMethodInfo.getMethodExceptions().size(); i++) {
                    logger.info(cMethodInfo.getMethodExceptions().get(i).toString());
                }
                */
                if (returnType != null) {
                    // addMethodDesc(typeDec.getName().toString(), methodName.toString(), param,
                    cMethodInfo.setStrReturn(returnType.toString());
                   AddImport.getInstance().addImportForList(cMethodInfo.getStrReturn(), cJpfUtInfo);
                    if (typeDec.isInterface()) {
                        logger.info("接口类单元测试：" + sourceFileName);
                        RunResult.iInterfaceFileCount++;
                        GenerateMethodForDAOInterface cGenerateMethodForInterface = new GenerateMethodForDAOInterface();

                        cGenerateMethodForInterface.doGenerateMethod(cMethodInfo, cJpfUtInfo);

                    } else if (0 == method.getModifiers() || 1 == method.getModifiers() || 3 == method.getModifiers()
                            || 4 == method.getModifiers()) {
                        // public:1 protected:3
                        GenerateMethodPublic cGenerateMethodPublic = new GenerateMethodPublic();
                        cGenerateMethodPublic.doGenerateMethod(cMethodInfo, cJpfUtInfo);
                    } else if (2 == method.getModifiers()) {
                        // private
                        GenerateMethodPrivate cGenerateMethodPrivate = new GenerateMethodPrivate();
                        cGenerateMethodPrivate.doGenerateMethod(cMethodInfo, cJpfUtInfo);
                        cJpfUtInfo.addImport("import java.lang.reflect.Method;");
                    } else if (9 == method.getModifiers()) {
                        // public static
                        GenerateMethodPublicStatic cGenerateMethodPublic = new GenerateMethodPublicStatic();
                        cGenerateMethodPublic.doGenerateMethod(cMethodInfo, cJpfUtInfo);
                    }

                }

            }
            if (typeDec.isInterface()) {
                cJpfUtInfo.setUtBasic(
                        GenerateBaseMethods.addExtraMethod(typeDec.getName().toString(), cJpfUtInfo.getUtPackage()));
                cJpfUtInfo.addImport(cJpfUtInfo.getUtPackage().replaceAll("package", "import").replaceAll(";",
                        "." + typeDec.getName().toString() + ";"));
            } else {
                cJpfUtInfo.setUtBasic(GenerateBaseMethods.addTestEnd(typeDec.getName().toString()));
            }
            return true;
        } catch (Exception ex) {
            // TODO: handle exception
            ex.printStackTrace();
        }
        return false;
    }
}
