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
import org.eclipse.jdt.core.dom.CompilationUnit;


/**
 * 
 */
public class GenerateUnitTests extends GenerateTests {
    private static final Logger logger = LogManager.getLogger();

    /**
     * 
     */
    public GenerateUnitTests() {

    }

    /**
     * 
     * @category 增加IMPORT
     * @author 吴平福
     * @param importList
     * @param sb update 2017年9月29日
     */
    protected void addExtraImport( JpfUtInfo cJpfUtInfo) {

        cJpfUtInfo.addImport("import org.junit.*;");
        cJpfUtInfo.addImport("import static org.junit.Assert.*;");


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

    /* (non-Javadoc)
     * @see org.jpf.unittests.generateuts.GenerateTests#addExtraBasic(java.lang.String, org.jpf.unittests.generateuts.JpfUtInfo)
     */
    @Override
    protected void addExtraBasic(String strClassName, JpfUtInfo cJpfUtInfo) {
        // TODO Auto-generated method stub
        cJpfUtInfo.setUtBasic(GenerateBaseMethods.addTestEnd(strClassName));
    }



}
