/**
 * copyrigth by wupf@ 2019年2月8日
 */
package org.jpf.utils.classes.fields;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jdt.core.dom.ArrayType;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.ParameterizedType;
import org.eclipse.jdt.core.dom.PrimitiveType;
import org.eclipse.jdt.core.dom.SimpleType;
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.jpf.aut.base.GenerateInputParam;
import org.jpf.utils.classes.ParseJavaByJdt;

/**
 * @author wupf@asiainfo.com
 *
 */
public class ParseJavaField {
  private static final Logger logger = LogManager.getLogger();


  // 已经自行实例化
  private static final ParseJavaField Instance = new ParseJavaField();

  // 静态工厂方法
  public static ParseJavaField getInstance() {
    return Instance;
  }

  /**
   * 
   */
  private ParseJavaField() {
    // TODO Auto-generated constructor stub
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
   * @Title: showFieldDeclarations
   * @author:wupf@asiainfo.com
   * @date:2019年2月25日
   * @param cu
   */
  public void showFieldDeclarations(CompilationUnit cu) {
    List types = cu.types();
    if (types.size() == 0) {
      return;
    }
    TypeDeclaration typeDec = (TypeDeclaration) types.get(0);
    logger.info("classname=" + typeDec.getName());

    showFieldDeclarations(typeDec);
  }

  /**
   * 
   * @category:
   * @Title: checkFieldAutowired
   * @author:wupf@asiainfo.com
   * @date:2019年2月25日
   * @param cu
   * @return
   */
  public boolean checkFieldAutowired(CompilationUnit cu) {
    List types = cu.types();
    if (types.size() == 0) {
      return false;
    }
    TypeDeclaration typeDec = (TypeDeclaration) types.get(0);
    if (logger.isDebugEnabled()) {
      logger.debug("classname=" + typeDec.getName());
    }
    FieldDeclaration[] fields = typeDec.getFields();
    for (FieldDeclaration fieldDeclaration : fields) {

      List modifier = fieldDeclaration.modifiers();
      for (int i = 0; i < modifier.size(); i++) {
        String field = modifier.get(i).toString();
        if (field.equalsIgnoreCase("@Autowired")) {
          logger.info(fieldDeclaration.toString());
          return true;
        }
      }
    }
    return false;
  }

  public static void main(String[] args) {
    try {
      CompilationUnit cu = ParseJavaByJdt.getInstance().parseJavaSourceFile18(
          "F:\\prj_code\\zjcmc\\adcloud-common\\common\\src\\main\\java\\com\\PrintIp.java",
          GenerateInputParam.JAVA_ENCODE);
      ParseJavaField.getInstance().checkFieldAutowired(cu);
    } catch (Exception e) {
      // TODO: handle exception
      e.printStackTrace();
    }

  }
}
