/**
 * @author 吴平福 E-mail:wupf@asiainfo.com
 * @version 创建时间：2017年12月7日 下午3:15:07 类说明
 */

package org.jpf.unittests.ibatis2x;

import java.util.List;
import java.util.Vector;

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
import org.jpf.unittests.generateuts.ClassInfoConst;
import org.jpf.unittests.generateuts.ConstructorGeneratorImpl;
import org.jpf.unittests.generateuts.GenerateBaseMethods;
import org.jpf.unittests.generateuts.GenerateConst;
import org.jpf.unittests.generateuts.GenerateMethodPrivate;
import org.jpf.unittests.generateuts.GenerateMethodPublic;
import org.jpf.unittests.generateuts.GenerateMethodPublicStatic;
import org.jpf.unittests.generateuts.JpfMethodInfo;
import org.jpf.unittests.generateuts.UtFileText;

import com.asiainfo.utils.AiDateTimeUtil;
import com.asiainfo.utils.ios.AiFileUtil;

/**
 * 
 */
public class GenerateUtByInterface {
    private static final Logger logger = LogManager.getLogger();

    /**
     * 
     */
    public GenerateUtByInterface() {
        // TODO Auto-generated constructor stub

    }
    /**
     * 
     * @category 生成单个单元测试文件
     * @author 吴平福
     * @param strFileName update 2017年9月30日
     */
    public boolean doGenerateFile(AppParam cAppParam,String strFileName) {

        String strSaveUtFileName = strFileName.substring(0, strFileName.indexOf("src")) + "pom.xml";
        if (AiFileUtil.FileExist(strSaveUtFileName)) {
            // maven
            strSaveUtFileName = strFileName.replace(".java", "Test.java").replaceAll("main", "test");
        } else {
            // ant
            strSaveUtFileName = strFileName.replace(".java", "Test.java").replaceAll("src", "test");
        }
        if (AiFileUtil.FileExist(strSaveUtFileName)) {
            logger.warn("File Exist:" + strSaveUtFileName);
            GenerateConst.iExistUtFileCount++;
        } else {
            UtFileText cUtFileText = new UtFileText();
            GenerateConst.iMethodCount=1;
            if (!ReadJavaFile(strFileName, cUtFileText,cAppParam)) {
                logger.warn("waring: " + strFileName);
                GenerateConst.iErrorFileCount++;
                cUtFileText.clean();
                return false;
            }
            AiFileUtil.saveFile(strSaveUtFileName, cUtFileText.toString());
            cUtFileText.clean();

            return true;
        }


        return false;
    }

    public boolean ReadJavaFile(final String sourceFileName,UtFileText cUtFileText,AppParam cAppParam) {
        // TODO Auto-generated constructor stub
        try {
            logger.info(sourceFileName);
            String sourceString = AiFileUtil.getFileTxt(sourceFileName, "GBK");
            logger.trace(sourceString);
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
            if (typeDec.getModifiers() == ClassInfoConst.CLASS_TYPE_ABSTRACT) {
                // abstract class
                logger.info("抽象类不能生产单元测试：" + sourceFileName);
                GenerateConst.iAbstractFileCount++;
                return false;
            }
            if (!typeDec.isInterface()) {
                logger.info("不处理非接口类单元测试：" + sourceFileName);
                return false;
            }
            
            
            addPackage(cCompilationUnit.getPackage().getName().toString(), cUtFileText.sbPackage);
            addImport(cCompilationUnit.imports(), cUtFileText);
            addClassDesc(typeDec.getName().toString(), cUtFileText.sbClassDesc);

            // show methods
            MethodDeclaration methodDec[] = typeDec.getMethods();

            JpfMethodInfo cMethodInfo = new JpfMethodInfo();
            cMethodInfo.setClassName(typeDec.getName().toString());

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
                if (returnType != null) {
                    // addMethodDesc(typeDec.getName().toString(), methodName.toString(), param,
                    cMethodInfo.setStrReturn(returnType.toString());
                    if (0 == method.getModifiers() || 1 == method.getModifiers() ) {
                        // public:1 protected:3
                        GenerateMethodForInterface cGenerateMethodForInterface = new GenerateMethodForInterface();
                        cGenerateMethodForInterface.doGenerateMethod(cMethodInfo, cUtFileText,cAppParam);
                    }else
                    {
                        logger.warn("method.getModifiers() ="+method.getModifiers() );
                    }
                }

            }

            GenerateBaseMethods.addTestEnd(typeDec.getName().toString(), cUtFileText.sbBasic);
            
            return true;
        } catch (Exception ex) {
            // TODO: handle exception
            ex.printStackTrace();
        }
        return false;
    }

    /**
     * 
     * @category 增加PACKAGE
     * @author 吴平福
     * @param strPackage
     * @param sb update 2017年9月28日
     */
    private void addPackage(String strPackage, StringBuffer sb) {
        sb.setLength(0);
        sb.append("package ").append(strPackage).append(";\n");
    }

    /**
     * 
     * @category 增加IMPORT
     * @author 吴平福
     * @param importList
     * @param sb update 2017年9月29日
     */
    private void addImport(List importList, UtFileText cUtFileText) {

        for (Object obj : importList) {
            ImportDeclaration importDec = (ImportDeclaration) obj;
            logger.debug(importDec.toString());
            cUtFileText.addImport(importDec.toString().trim());
        }

        // sb.append("import org.easymock.EasyMock;").append("\n");
        cUtFileText.addImport("import org.junit.*;");
        cUtFileText.addImport("import static org.junit.Assert.*;");
        /*
         * sb.append("import java.lang.reflect.Method;").append("\n");
         * sb.append("import java.util.ArrayList;").append("\n");
         * sb.append("import java.util.HashMap;").append("\n");
         */

    }

    private void addClassDesc(String strClass, StringBuffer sb) {

        sb.append("/**").append("\n");
        sb.append("* The class <code>").append(strClass)
                .append("Test</code> contains tests for the class <code>{@link ").append(strClass).append("}</code>.")
                .append("\n");
        sb.append("* <p>").append("\n");
        sb.append("* Copyright (c) 2017").append("\n");
        sb.append("* ").append("\n");
        sb.append("* @generatedBy wupf@asiainfo.com at ").append(AiDateTimeUtil.getCurrDateTime()).append("\n");
        sb.append("* @author Administrator").append("\n");
        sb.append("* @version $Revision: 1.0 $").append("\n");
        sb.append("*/").append("\n");
        sb.append("public class ").append(strClass).append("Test {\n");
    }
}
