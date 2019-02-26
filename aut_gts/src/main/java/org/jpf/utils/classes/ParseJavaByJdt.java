/**
 * @author 吴平福 E-mail:wupf@asiainfo.com
 * @version 创建时间：2018年3月6日 上午11:38:48 类说明
 */

package org.jpf.utils.classes;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.Annotation;
import org.eclipse.jdt.core.dom.AnnotationTypeDeclaration;
import org.eclipse.jdt.core.dom.ArrayType;
import org.eclipse.jdt.core.dom.BodyDeclaration;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.IDocElement;
import org.eclipse.jdt.core.dom.ImportDeclaration;
import org.eclipse.jdt.core.dom.Javadoc;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.Modifier;
import org.eclipse.jdt.core.dom.ParameterizedType;
import org.eclipse.jdt.core.dom.PrimitiveType;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.SimpleType;
import org.eclipse.jdt.core.dom.TagElement;
import org.eclipse.jdt.core.dom.TextElement;
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.TypeParameter;
import org.jpf.utils.ios.AiFileUtil;

/**
 * 
 */
public class ParseJavaByJdt {

  /**
   * 
   */
  private ParseJavaByJdt() {}

  private static final Logger logger = LogManager.getLogger();


  // 已经自行实例化
  private static final ParseJavaByJdt Instance = new ParseJavaByJdt();

  // 静态工厂方法
  public static ParseJavaByJdt getInstance() {
    return Instance;
  }

  /**
   * 
   * @category @author 吴平福
   * @param sourceFileName
   * @return
   * @throws Exception update 2018年3月6日
   */
  public CompilationUnit parseJavaSourceFile17(String sourceFileName, String strEnCode)
      throws Exception {
    if (logger.isDebugEnabled())
      logger.debug(sourceFileName);
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
   * @category @author 吴平福
   * @param sourceFileName
   * @return
   * @throws Exception update 2018年3月6日
   */
  public CompilationUnit parseJavaSourceFile18(String sourceFileName, String strEnCode)
      throws Exception {
    if (logger.isDebugEnabled())
      logger.debug(sourceFileName);
    String sourceString = AiFileUtil.getFileTxt(sourceFileName, strEnCode);
    ASTParser astParser = ASTParser.newParser(AST.JLS10);
    astParser.setKind(ASTParser.K_COMPILATION_UNIT);
    astParser.setResolveBindings(true);
    astParser.setSource(sourceString.toCharArray());

    Map options = JavaCore.getOptions();
    options.put(JavaCore.COMPILER_SOURCE, JavaCore.VERSION_1_8); // or newer version
    astParser.setCompilerOptions(options);

    return (CompilationUnit) astParser.createAST(null);
  }

  /*
   * 
   * @category: 显示方法，属性，内部类
   * 
   * @Title: showBodyDeclarations
   * 
   * @author:wupf@asiainfo.com
   * 
   * @date:2019年1月22日
   * 
   * @param typeDec
   */
  public void showBodyDeclarations(TypeDeclaration typeDec) {
    List<?> bodyDecliarations = typeDec.bodyDeclarations();
    for (Object object : bodyDecliarations) {

      if (object instanceof TypeDeclaration) {
        // inner class
        TypeDeclaration innerClass = (TypeDeclaration) object;
        logger.info(innerClass.toString());
      } else if (object instanceof FieldDeclaration) {
        // field
      } else if (object instanceof MethodDeclaration) {
        // method
      } else if (object instanceof AnnotationTypeDeclaration) {
        // method
        AnnotationTypeDeclaration cAnnotationTypeDeclaration = (AnnotationTypeDeclaration) object;
        logger.info(cAnnotationTypeDeclaration.toString());
      } else if (object instanceof Annotation) {
        logger.info(((Annotation) object).toString());
      }
    }
  }

  /**
   * 
   * @category:
   * @Title: showFieldDeclarations
   * @author:wupf@asiainfo.com
   * @date:2019年1月22日
   * @param typeDec
   */
  public void showFieldDeclarations(TypeDeclaration typeDec) {
    FieldDeclaration[] fields = typeDec.getFields();
    for (FieldDeclaration fieldDeclaration : fields) {
      String field = "";

      List modifier = fieldDeclaration.modifiers();
      for (int i = 0; i < modifier.size(); i++) {
        field += modifier.get(i) + " ";
      }

      // 字段类型
      Type fieldType = fieldDeclaration.getType();
      if (fieldType.isPrimitiveType()) {
        PrimitiveType p = (PrimitiveType) fieldType;
        field += p.getPrimitiveTypeCode() + " ";
      } else if (fieldType.isArrayType()) {
        // 数组
        ArrayType arrayType = (ArrayType) fieldType;
        field += arrayType.getComponentType() + "[] ";
      } else if (fieldType.isSimpleType()) {
        // 对象
        SimpleType s = (SimpleType) fieldType;
        field += s.getName().getFullyQualifiedName() + " ";
      } else if (fieldType.isParameterizedType()) {
        // 泛型
        ParameterizedType p = (ParameterizedType) fieldType;
        List tas = p.typeArguments();
        field += "<" + tas.get(0).toString() + ">";
      }
      // 得到变量名
      List fragments = fieldDeclaration.fragments();
      for (int i = 0; i < fragments.size(); i++) {
        field += fragments.get(i) + " ";
      }

    }
  }

  /**
   * 
   * @category:
   * @Title: showClassModifier
   * @author:wupf@asiainfo.com
   * @date:2019年1月22日
   * @param typeDec
   */
  public void showClassModifier(TypeDeclaration typeDec) {
    for (Object object : typeDec.modifiers()) {
      Modifier modifier = (Modifier) object;
      logger.info(modifier.getKeyword());
    }
  }

  /**
   * 
   * @category:
   * @Title: showClassType
   * @author:wupf@asiainfo.com
   * @date:2019年1月22日
   * @param typeDec
   */
  public void showClassType(TypeDeclaration typeDec) {
    // 得到类型信息 public classs MyAdapter<T extends Entity> 中的<T extends Entity>
    for (Object object : typeDec.typeParameters()) {
      TypeParameter typeParameter = (TypeParameter) object;
      SimpleName genericName = typeParameter.getName(); // T
      logger.info(genericName.getFullyQualifiedName());
      for (Object object2 : typeParameter.typeBounds()) { // Entity
        SimpleType superGrnericType = (SimpleType) object2;
        String superGrnericName = superGrnericType.getName().getFullyQualifiedName();
        logger.info(superGrnericName);
      }
    }

    // extends Adapter<Entity>
    Type superType = typeDec.getSuperclassType();
    if (null != superType) {
      logger.info(superType.toString());
    }


    // implements Adapter<Entity>,BaseBean{}

    for (Object object : typeDec.superInterfaceTypes()) {
      ParameterizedType parameterizedType = (ParameterizedType) object;
      for (Object object2 : parameterizedType.typeArguments()) {
        SimpleType simpleType = (SimpleType) object2;
        logger.info(simpleType.getName().getFullyQualifiedName());
      }
    }

  }

  /**
   * 
   * @category:
   * @Title: showJavaDoc
   * @author:wupf@asiainfo.com
   * @date:2019年1月22日
   * @param typeDec
   */
  public void showJavaDoc(TypeDeclaration typeDec) {
    Javadoc classdoc = typeDec.getJavadoc();
    if (null != classdoc) {
      logger.info(classdoc.isBlockComment());
      logger.info(classdoc.isDocComment());
      logger.info(classdoc.isLineComment());
      // logger.info(classdoc.getComment());

    }
  }

  public void showJavaDoc2(TypeDeclaration typeDec) {
    Javadoc classdoc = typeDec.getJavadoc();
    if (null != classdoc) {
      List targs = classdoc.tags();
      for (Object object : targs) {
        TagElement tagElement = (TagElement) object;
        String optionalTagName = tagElement.getTagName();
        logger.info(optionalTagName);
        List textElements = tagElement.fragments();
        for (Object object2 : textElements) {

          IDocElement cIDocElement = (IDocElement) object2;
          if (cIDocElement instanceof TextElement) {
            logger.info(((TextElement) cIDocElement).getText());
          } else if (cIDocElement instanceof TagElement) {
            logger.info(((TagElement) cIDocElement).getTagName());
          }

          /*
           * TextElement cTextElement = (TextElement) object2; logger.info(cTextElement.getText());
           */
        }
      }
    }
  }

  /**
   * 
   * @category:
   * @Title: showImport
   * @author:wupf@asiainfo.com
   * @date:2019年1月22日
   * @param cu
   */
  public void showImport(CompilationUnit cu) {
    List importDeclarations = cu.imports();
    for (Object object : importDeclarations) {
      ImportDeclaration importDec = (ImportDeclaration) object;
      // logger.info(importDec.getName());
      logger.info(importDec);
      // logger.info(importDec.isOnDemand());
      // logger.info(importDec.isStatic());
    }
  }

  /**
   * 
   * @category:
   * @Title: showMethod
   * @author:wupf@asiainfo.com
   * @date:2019年1月22日
   * @param typeDec
   */
  public void showMethod(TypeDeclaration typeDec) {

    MethodDeclaration[] methods = typeDec.getMethods();
    for (MethodDeclaration method : methods) {

      // retrieveAnnotations(method);
      // 得到修饰符
      List modifiers = method.modifiers();

      // Java doc

      // Java annotation
      for (Object modifier : modifiers) {
        if (modifier instanceof Annotation) {
          logger.info(((Annotation) modifier).toString());
        }
      }
    }

  }

  /**
   * 
   * @category:
   * @Title: getStartandEnd
   * @author:wupf@asiainfo.com
   * @date:2019年2月6日
   * @param cu
   * @param cASTNode
   * @return
   */
  public int[] getStartandEnd(CompilationUnit cu, ASTNode cASTNode) {
    int[] pos = new int[] {0, 0};
    pos[0] = cu.getLineNumber(cASTNode.getStartPosition());
    pos[1] = cu.getLineNumber(cASTNode.getStartPosition() + cASTNode.getLength());
    logger.info("startLine:" + pos[0] + " endLine:" + pos[1]);
    return pos;
  }

  /**
   * 
   * @category:
   * @Title: getStartLine
   * @author:wupf@asiainfo.com
   * @date:2019年2月6日
   * @param cu
   * @param cASTNode
   * @return
   */
  public int getStartLine(CompilationUnit cu, ASTNode cASTNode) {
    return cu.getLineNumber(cASTNode.getStartPosition());
  }

  /**
   * 
   * @category:
   * @Title: getEndLine
   * @author:wupf@asiainfo.com
   * @date:2019年2月6日
   * @param cu
   * @param cASTNode
   * @return
   */
  public int getEndLine(CompilationUnit cu, ASTNode cASTNode) {
    return cu.getLineNumber(cASTNode.getStartPosition() + cASTNode.getLength());
  }

  /**
   * 
   * @category:
   * @Title: retrieveAnnotations
   * @author:wupf@asiainfo.com
   * @date:2019年2月5日
   * @param node
   * @return
   */
  public List<Annotation> retrieveAnnotations(BodyDeclaration node) {
    List<?> modifiers = node.modifiers();
    List<Annotation> annotations = new ArrayList<Annotation>();
    for (Object modifier : modifiers) {
      if (modifier instanceof Annotation) {
        annotations.add((Annotation) modifier);
      }
    }
    return annotations;
  }


}
