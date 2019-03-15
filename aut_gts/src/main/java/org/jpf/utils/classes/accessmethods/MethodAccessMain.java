/**
 * copyrigth by wupf@asiainfo.com 2018年8月24日
 */
package org.jpf.utils.classes.accessmethods;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jdt.core.dom.AbstractTypeDeclaration;
import org.eclipse.jdt.core.dom.AnnotationTypeDeclaration;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.EnumDeclaration;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.jpf.aut.base.GenerateInputParam;
import org.jpf.aut.common.consts.AutConst;
import org.jpf.utils.classes.MethodAccessEnum;
import org.jpf.utils.classes.ParseJavaByJdt;
import org.jpf.utils.classes.formats.FormatCode;
import org.jpf.utils.ios.JpfFileUtil;
import org.jpf.utils.ios.JpfFileUtil;
import org.jpf.utils.mavens.JpfMvnUtil;



/**
 * @author wupf@asiainfo.com
 *
 */
public class MethodAccessMain {
  private static final Logger logger = LogManager.getLogger();

  // 0: 不修改，1:修改
  private int ChangeAccess = 0;

  /**
   * 
   * @param strPomFilePath
   * @param strChangeAccess
   */
  public MethodAccessMain(String strPomFilePath, String strChangeAccess) {
    if (strChangeAccess != null && strChangeAccess.trim().length() > 0) {
      ChangeAccess = Integer.parseInt(strChangeAccess);
    }
    doWork(strPomFilePath);
  }

  /**
   * 
   * @param strPomFilePath
   */
  public MethodAccessMain(String strPomFilePath) {
    doWork(strPomFilePath);
  }


  /**
   * 
   * @category:
   * @Title: doWork
   * @author:wupf@asiainfo.com
   * @date:2019年3月3日
   * @param strPomFilePath
   */
  public void doWork(String strPomFilePath) {

    try {
      // copy src/main/java
      if (!JpfFileUtil.isDirectory(strPomFilePath)) {
        logger.error("Not exist Dir:" + strPomFilePath);
      }
      String oldJavaPath = JpfMvnUtil.getSrcPath(strPomFilePath);
      String newJavaPath = JpfMvnUtil.getSrcTmpPath(strPomFilePath);

      Vector<String> vFile = new Vector<>();

      JpfFileUtil.getFiles(oldJavaPath, vFile, ".java");
      MethodAccessResult.getInstance().setJavaFileCount(vFile.size());
      if (ChangeAccess == 0) {
        for (int i = 0; i < vFile.size(); i++) {
          // logger.info("Checking file:" + vFile.get(i));
          getPrivateMethodCount(vFile.get(i));
          // ChangeMethodAccess("D:\\jworkspaces\\JpfUnitTest2\\src\\org\\aitest\\sample\\SampleAcces.java");
        }
      }
      if (ChangeAccess == 1) {
        for (int i = 0; i < vFile.size(); i++) {
          logger.info("Checking file:" + vFile.get(i));
          ChangeMethodAccess(vFile.get(i));
          // ChangeMethodAccess("D:\\jworkspaces\\JpfUnitTest2\\src\\org\\aitest\\sample\\SampleAcces.java");
        }
      }
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    MethodAccessResult.getInstance().printResult();
  }

  String LineSeparator = System.getProperty("line.separator");

  /**
   * 
   * @category:
   * @Title: getPrivateMethodCount
   * @author:wupf@asiainfo.com
   * @date:2019年3月3日
   * @param strFileName
   */
  public void getPrivateMethodCount(String strFileName) {
    try {
      // format code

      String strJavaFileText = JpfFileUtil.getFileTxt(strFileName, GenerateInputParam.JAVA_ENCODE);

      // get method pos
      CompilationUnit cu = ParseJavaByJdt.getInstance().parseJavaSourceFile18(strFileName,
          GenerateInputParam.JAVA_ENCODE);

      List types = cu.types();
      if (types.size() == 0) {
        logger.warn("type=null:" + strFileName);
        MethodAccessResult.getInstance().addUnknownJavaFileCount();
        return;
      }

      AbstractTypeDeclaration cAbstractTypeDeclaration = (AbstractTypeDeclaration) types.get(0);
      TypeDeclaration typeDec = null;
      if (cAbstractTypeDeclaration instanceof EnumDeclaration) {
        // EnumDeclaration cEnumDeclaration = (EnumDeclaration) cAbstractTypeDeclaration;
        logger.info("枚举类:" + cAbstractTypeDeclaration.getName().toString());
        MethodAccessResult.getInstance().addEnumFileCount();
      } else if (cAbstractTypeDeclaration instanceof AnnotationTypeDeclaration) {
        logger.info("AnnotationType类:" + cAbstractTypeDeclaration.getName().toString());
        MethodAccessResult.getInstance().addAnnotationTypeFileCount();
      } else {

        if (!(cAbstractTypeDeclaration instanceof TypeDeclaration)) {
          logger.info("不支持的类生产单元测试：" + strFileName);
          MethodAccessResult.getInstance().addUnknownJavaFileCount();
          return;
        }

        typeDec = (TypeDeclaration) cAbstractTypeDeclaration;

        if (typeDec.getModifiers() == AutConst.CLASS_TYPE_ABSTRACT) {
          logger.info("抽象类:" + cAbstractTypeDeclaration.getName());
          MethodAccessResult.getInstance().addAbstractFileCount();
        }

        if (typeDec.isInterface()) {
          logger.info("不处理接口类:" + cAbstractTypeDeclaration.getName());
          MethodAccessResult.getInstance().addInterfaceFileCount();
        }
      }
      if (typeDec == null) {
        return;
      }
      // show methods
      MethodDeclaration methodDec[] = typeDec.getMethods();
      boolean bHasPrivate = false;
      MethodAccessResult.getInstance().addAllMethodCount(methodDec.length);
      for (MethodDeclaration method : methodDec) {
        SimpleName methodName = method.getName();
        //

        if (method.getModifiers() == MethodAccessEnum.AccessPrivate.getValue()) {
          MethodAccessResult.getInstance().addPrivateMethodCount();
          // logger.info("methodName:" + methodName);
          bHasPrivate = true;
        }
        if (method.getModifiers() == MethodAccessEnum.AccessPrivateStatic.getValue()) {
          MethodAccessResult.getInstance().addStaticPrivateMethodCount();
          // logger.info("methodName:" + methodName);
          bHasPrivate = true;
        }

      }
      if (bHasPrivate) {
        MethodAccessResult.getInstance().addHasPrivateFileCount();
      }
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  /**
   * 
   * @author wupf@asiainfo.com
   * @param strFileName
   * @param listMethodName 2018年8月21日
   */
  public void ChangeMethodAccess(final String strFileName) {
    try {
      // format code

      String strJavaFileText = JpfFileUtil.getFileTxt(strFileName, GenerateInputParam.JAVA_ENCODE);

      strJavaFileText = FormatCode.format(strJavaFileText);
      JpfFileUtil.saveFile(strFileName, strJavaFileText, GenerateInputParam.JAVA_ENCODE);

      String[] JavaFileTexts = strJavaFileText.split(LineSeparator);
      // logger.info("Line count:" + JavaFileTexts.length);

      // get method pos
      CompilationUnit cu = ParseJavaByJdt.getInstance().parseJavaSourceFile18(strFileName,
          GenerateInputParam.JAVA_ENCODE);

      List types = cu.types();
      if (types.size() == 0) {
        logger.warn("type=null:" + strFileName);
        MethodAccessResult.getInstance().addUnknownJavaFileCount();
        return;
      }

      AbstractTypeDeclaration cAbstractTypeDeclaration = (AbstractTypeDeclaration) types.get(0);
      TypeDeclaration typeDec = null;
      if (cAbstractTypeDeclaration instanceof EnumDeclaration) {
        // EnumDeclaration cEnumDeclaration = (EnumDeclaration) cAbstractTypeDeclaration;
        logger.info("枚举类:" + cAbstractTypeDeclaration.getName().toString());
        MethodAccessResult.getInstance().addEnumFileCount();
      } else if (cAbstractTypeDeclaration instanceof AnnotationTypeDeclaration) {
        logger.info("AnnotationType类:" + cAbstractTypeDeclaration.getName().toString());
        MethodAccessResult.getInstance().addAnnotationTypeFileCount();
      } else {

        if (!(cAbstractTypeDeclaration instanceof TypeDeclaration)) {
          logger.info("不支持的类生产单元测试：" + strFileName);
          MethodAccessResult.getInstance().addUnknownJavaFileCount();
          return;
        }

        typeDec = (TypeDeclaration) cAbstractTypeDeclaration;

        if (typeDec.getModifiers() == AutConst.CLASS_TYPE_ABSTRACT) {
          logger.info("抽象类:" + cAbstractTypeDeclaration.getName());
          MethodAccessResult.getInstance().addAbstractFileCount();
        }

        if (typeDec.isInterface()) {
          logger.info("不处理接口类:" + cAbstractTypeDeclaration.getName());
          MethodAccessResult.getInstance().addInterfaceFileCount();
        }
      }
      if (typeDec == null) {
        return;
      }

      // show methods
      MethodDeclaration methodDec[] = typeDec.getMethods();
      MethodAccessResult.getInstance().addAllMethodCount(methodDec.length);
      Map<String, String> mapMethodBody = new HashMap<String, String>();
      boolean bHasPrivate = false;
      for (MethodDeclaration method : methodDec) {
        SimpleName methodName = method.getName();
        // logger.info("methodName:" + methodName);
        if (method.getModifiers() == MethodAccessEnum.AccessPrivate.getValue()
            || method.getModifiers() == MethodAccessEnum.AccessPrivateStatic.getValue()) {
          logger.info("methodName:" + methodName);
          if (method.isConstructor()) {
            continue;
          }
          bHasPrivate = true;
          int iMethodStartRow = cu.getLineNumber(method.getReturnType2().getStartPosition());
          // int iMethodStartRow = cu.getLineNumber(method.getStartPosition());
          // logger.info("start line:" + iMethodStartRow2);
          // logger.info("start line:" + JavaFileTexts[iMethodStartRow2 - 1]);

          logger.info("start line:" + iMethodStartRow);
          logger.info("start line:" + JavaFileTexts[iMethodStartRow - 1]);
          JavaFileTexts[iMethodStartRow - 1] =
              JavaFileTexts[iMethodStartRow - 1].replaceFirst("private", "protected");
          logger.info("start line:" + JavaFileTexts[iMethodStartRow - 1]);
          if (method.getModifiers() == MethodAccessEnum.AccessPrivate.getValue()) {
            MethodAccessResult.getInstance().addPrivateMethodCount();
          } else {
            MethodAccessResult.getInstance().addStaticPrivateMethodCount();
          }
        }
      }
      if (bHasPrivate) {
        MethodAccessResult.getInstance().addHasPrivateFileCount();

        // rewrite java file
        strJavaFileText = "";
        for (int i = 0; i < JavaFileTexts.length; i++) {
          strJavaFileText += JavaFileTexts[i] + LineSeparator;
        }
        // strJavaFileText = FormatCode.format(strJavaFileText);

        JpfFileUtil.copyFile(strFileName.replaceAll("\\\\java", "\\\\java_wupf"), strFileName);
        JpfFileUtil.saveFile(strFileName, strJavaFileText, GenerateInputParam.JAVA_ENCODE);
      }
    } catch (Exception ex) {
      logger.error(strFileName);
      ex.printStackTrace();
    }
  }

  /**
   * @author wupf@asiainfo.com
   * @param args 2018年8月24日
   */
  public static void main(String[] args) {
    // TODO Auto-generated method stub
    if (1 == args.length) {
      MethodAccessMain cJpfClassAccess = new MethodAccessMain(args[0]);
    } else if (2 == args.length) {
      MethodAccessMain cJpfClassAccess = new MethodAccessMain(args[0], args[1]);
    } else {
      logger.warn("error input param");
    }
  }

}
