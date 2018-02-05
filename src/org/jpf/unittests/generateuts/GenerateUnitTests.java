/**
 * @author 吴平福 E-mail:wupf@asiainfo.com
 * @version 创建时间：2017年9月28日 下午11:40:54 类说明
 */

package org.jpf.unittests.generateuts;

/*
 * 待完善 1. 抽象类里面的非抽象方法 2类里面定义的类
 * 
 * 已经支持 1 public 2 private 3 public static
 * 
 */
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.ImportDeclaration;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import com.asiainfo.utils.ios.AiFileUtil;

/**
 * 
 */
public class GenerateUnitTests {
    private static final Logger logger = LogManager.getLogger();

    /**
     * 
     */
    public GenerateUnitTests() {
        ;
    }

    /**
     * 
     * @category 生成单个单元测试文件
     * @author 吴平福
     * @param strFileName update 2017年9月30日
     */
    public boolean doGenerateFile(String strFileName) {

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
            GenerateConst.iExistUtFileCount++;
        } else {
            JpfUtInfo cJpfUtInfo = new JpfUtInfo();
            GenerateRunResult.iMethodCount = 1;
            if (!generateUTFromJavaFile(strFileName, cJpfUtInfo)) {
                logger.warn("waring: " + strFileName);
                GenerateConst.iErrorFileCount++;
                return false;
            }
            AiFileUtil.saveFile(strSaveUtFileName, cJpfUtInfo.toString());

            return true;
        }


        return false;
    }

    private void test0468(CompilationUnit compilationUnit) throws JavaModelException {
        logger.info(compilationUnit.TYPES_PROPERTY);
        logger.info(compilationUnit.getNodeType());
        logger.info(compilationUnit.toString());
        logger.info(compilationUnit.getJavaElement());
        ICompilationUnit unit = (ICompilationUnit) compilationUnit;
        IType[] allTypes = unit.getAllTypes();
        for (IType otherType : allTypes) {
            logger.info(otherType.isInterface());
            logger.info(otherType.isEnum());
            logger.info(otherType.isAnnotation());
            /*
             * if (!otherType.equals(iType)) { if (otherType.isInterface()) {
             * sb.append("interface "); } else if (otherType.isAnnotation()) { // probably don't
             * need this sb.append("@interface "); } else if (otherType.isEnum()) {
             * sb.append("enum "); } else { sb.append("class "); }
             * 
             * // use '$' so that inner classes can be remembered String qualifiedTypeName =
             * otherType.getFullyQualifiedName('$'); int dotIndex =
             * qualifiedTypeName.lastIndexOf('.')+1; String simpleName =
             * qualifiedTypeName.substring(dotIndex); sb.append(simpleName + "{ }\n"); }
             */
        }

        /*
         * ASTNode node = compilationUnit.getAST(). //assertEquals("No error", 0,
         * compilationUnit.getProblems().length); //$NON-NLS-1$ //assertNotNull("No node", node);
         * //assertTrue("not a return statement", node.getNodeType() == ASTNode.RETURN_STATEMENT);
         * //$NON-NLS-1$ ReturnStatement returnStatement = (ReturnStatement) node; Expression
         * expression = returnStatement.getExpression(); assertNotNull("No expression", expression);
         * assertTrue("not a field access", expression.getNodeType() == ASTNode.FIELD_ACCESS);
         * //$NON-NLS-1$ FieldAccess fieldAccess = (FieldAccess) expression; Name name =
         * fieldAccess.getName(); IBinding binding = name.resolveBinding();
         * assertNotNull("No binding", binding); assertEquals("Wrong type", IBinding.VARIABLE,
         * binding.getKind()); IVariableBinding variableBinding = (IVariableBinding) binding;
         * assertEquals("Wrong name", "i", variableBinding.getName()); assertEquals("Wrong type",
         * "int", variableBinding.getType().getName()); IVariableBinding variableBinding2 =
         * fieldAccess.resolveFieldBinding(); assertTrue("different binding", variableBinding ==
         * variableBinding2);
         * 
         * node = getASTNode(compilationUnit, 0, 0); assertNotNull("No node", node);
         * assertEquals("Wrong type", ASTNode.FIELD_DECLARATION, node.getNodeType());
         * FieldDeclaration fieldDeclaration = (FieldDeclaration) node; List fragments =
         * fieldDeclaration.fragments(); assertEquals("wrong size", 1, fragments.size());
         * VariableDeclarationFragment fragment = (VariableDeclarationFragment) fragments.get(0);
         * 
         * ASTNode foundNode = compilationUnit.findDeclaringNode(variableBinding);
         * assertNotNull("No found node", foundNode); assertEquals("wrong node", fragment,
         * foundNode);
         */
    }

    /**
     * 
     * @category @author 吴平福
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
                GenerateConst.iAbstractFileCount++;
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

                for (int i = 0; i < cMethodInfo.getMethodExceptions().size(); i++) {
                    logger.info(cMethodInfo.getMethodExceptions().get(i).toString());
                }

                if (returnType != null) {
                    // addMethodDesc(typeDec.getName().toString(), methodName.toString(), param,
                    cMethodInfo.setStrReturn(returnType.toString());
                    if (typeDec.isInterface()) {
                        logger.info("接口类单元测试：" + sourceFileName);
                        GenerateConst.iInterfaceFileCount++;
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

    /**
     * 
     * @category 增加IMPORT
     * @author 吴平福
     * @param importList
     * @param sb update 2017年9月29日
     */
    private void addImport(List importList, JpfUtInfo cJpfUtInfo) {

        for (Object obj : importList) {
            ImportDeclaration importDec = (ImportDeclaration) obj;
            logger.debug(importDec.toString());
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



}
