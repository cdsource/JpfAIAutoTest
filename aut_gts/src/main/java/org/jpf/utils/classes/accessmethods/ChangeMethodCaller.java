/**
 * copyrigth by wupf@ 2019年2月8日
 */
package org.jpf.utils.classes.accessmethods;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.jpf.aut.base.GenerateInputParam;
import org.jpf.utils.classes.ClassUtil;
import org.jpf.utils.classes.ParseJavaByJdt;
import org.jpf.utils.classes.formats.FormatCode;
import org.jpf.utils.ios.JpfFileUtil;

/**
 * @author wupf@asiainfo.com
 *
 */
public class ChangeMethodCaller {
  private static final Logger logger = LogManager.getLogger();


  // 已经自行实例化
  private static final ChangeMethodCaller Instance = new ChangeMethodCaller();

  // 静态工厂方法
  public static ChangeMethodCaller getInstance() {
    return Instance;
  }


  public void doWork(final String strTestFileName, final String strJavaFileName,
      Map<String, String> mapMethods) {

    try {
      String strJavaFileText =
          JpfFileUtil.getFileTxt(strTestFileName, GenerateInputParam.JAVA_ENCODE);

      strJavaFileText = FormatCode.format(strJavaFileText);
      JpfFileUtil.saveFile(strTestFileName, strJavaFileText, GenerateInputParam.JAVA_ENCODE);

      String[] JavaFileTexts = strJavaFileText.split(ClassUtil.LineSeparator);
      // logger.info("Line count:" + JavaFileTexts.length);

      // get method pos
      CompilationUnit cu = ParseJavaByJdt.getInstance().parseJavaSourceFile18(strTestFileName,
          GenerateInputParam.JAVA_ENCODE);

      List types = cu.types();
      if (types.size() == 0) {
        logger.warn("type=null:" + strTestFileName);
        MethodAccessResult.getInstance().addUnknownJavaFileCount();
        return;
      }

      TypeDeclaration typeDec = (TypeDeclaration) types.get(0);
      if (typeDec == null) {
        return;
      }
      // show methods
      MethodDeclaration methodDec[] = typeDec.getMethods();
      MethodAccessResult.getInstance().addAllMethodCount(methodDec.length);
      Map<String, String> mapMethodBody = new HashMap<String, String>();
      for (MethodDeclaration method : methodDec) {
        SimpleName methodName = method.getName();
        if (methodName.toString().startsWith("test")) {

        }
        /*
         * fixture.add(i, j); Method method = fixture.getClass().getDeclaredMethod("add", int.class,
         * int.class); method.setAccessible(true);
         * 
         * method.invoke(fixture, i, j);
         */
        /*
         * Method method = fixture.getClass().getDeclaredMethod("add2", int.class, int.class);
         * method.setAccessible(true);
         * 
         * int result = (int) method.invoke(fixture, i, j);
         * 
         * int result = fixture.add2(i, j);
         */
      }
    } catch (Exception e) {
      // TODO: handle exception
      e.printStackTrace();
    }
  }

  /**
   * 
   */
  private ChangeMethodCaller() {
    // TODO Auto-generated constructor stub
  }

}
