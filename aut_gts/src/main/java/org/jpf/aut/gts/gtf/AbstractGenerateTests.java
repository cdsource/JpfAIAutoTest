/**
 * @author 吴平福 E-mail:wupf@asiainfo.com
 * @version 创建时间：2018年3月6日 上午10:16:51 类说明
 */

package org.jpf.aut.gts.gtf;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jdt.core.dom.AbstractTypeDeclaration;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.EnumDeclaration;
import org.eclipse.jdt.core.dom.ImportDeclaration;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.jpf.aut.base.GenerateInputParam;
import org.jpf.aut.base.JpfMethodInfo;
import org.jpf.aut.base.JpfUtInfo;
import org.jpf.aut.base.RunResult;
import org.jpf.aut.common.consts.AiTestConst;
import org.jpf.aut.gts.gtm.AbstractGenerateMethod;
import org.jpf.aut.gts.gtm.GenerateMethodPrivate;
import org.jpf.aut.gts.gtm.GenerateMethodPrivateStatic;
import org.jpf.aut.gts.gtm.GenerateMethodPublic;
import org.jpf.aut.gts.gtm.GenerateMethodPublicStatic;
import org.jpf.aut.gts.gtm.gtConstructor.GeneratorConstructorImpl;
import org.jpf.aut.gts.plugins.springs.gtm.GTMForDAOInterface;
import org.jpf.aut.utils.AddImport;
import org.jpf.utils.AiDateTimeUtil;
import org.jpf.utils.classes.ParseJavaSourceFile;
import org.jpf.utils.ios.AiFileUtil;

/**
 * 
 */
public abstract class AbstractGenerateTests {
  private static final Logger logger = LogManager.getLogger();

  /**
   * 
   */
  public AbstractGenerateTests() {
    // TODO Auto-generated constructor stub

  }

  public abstract String getTestFileTypeName();

  /**
   * 
   * @category 增加IMPORT
   * @author 吴平福
   * @param importList
   * @param sb update 2017年9月29日
   */
  public abstract void addExtraImport(JpfUtInfo cJpfUtInfo);

  /**
   * 
   * @return
   */
  public abstract String getUTSavePath();

  /**
   * 
   * @return
   */
  public abstract void setNewPackage(JpfUtInfo cJpfUtInfo);

  protected void addExtraBasic(String strClassName, JpfUtInfo cJpfUtInfo) {

  }

  private boolean a1(String sFileName) {
    long start = System.currentTimeMillis();
    File fpath = new File(sFileName);
    long timeStamp = fpath.lastModified();

    logger.info("timeStamp=" + timeStamp);
    logger.info("start=" + start);
    if (start > 1548272000000L) {
      // logger.warn("start=" + start);
      return false;
    }

    return true;
  }

  /**
   * 
   * @category 生成单个单元测试文件
   * @author 吴平福
   * @param strFileName update 2017年9月30日
   */
  public boolean doGenerateFile(String strFileName) throws Exception {

    logger.info("source java File :" + strFileName);
    if (!a1(strFileName)) {

      return false;
    }
    String strSaveUtFileName = "";
    String strPomFileName = strFileName.substring(0, strFileName.indexOf("src")) + "pom.xml";
    if (AiFileUtil.FileExist(strPomFileName)) {
      // maven
      strSaveUtFileName = strFileName.replace(".java", "_WRTest.java").replaceAll("main", "test");
      GenerateInputParam.setPOM_PATH(strPomFileName);
    } else {
      // ant
      strSaveUtFileName = strFileName.replace(".java", "_WRTest.java").replaceAll("src", "test");
    }
    if (getUTSavePath() != null && getUTSavePath().length() > 0) {
      strSaveUtFileName = getUTSavePath() + java.io.File.separator
          + AiFileUtil.getFileName(strFileName).replaceAll(".java", "_WRTest.java");
    }
    logger.info("save ut File :" + strSaveUtFileName);
    /*
     * if (AiFileUtil.FileExist(strSaveUtFileName)) { logger.warn("File Exist:" +
     * strSaveUtFileName); RunResult.iExistUtFileCount++; return false; }
     */
    JpfUtInfo cJpfUtInfo = new JpfUtInfo();
    cJpfUtInfo.setCurrentJavaFilePackage(strFileName);
    cJpfUtInfo.strCurrentFileName = strFileName;
    RunResult.iMethodCount = 1;
    if (!generateTestFromJavaFile(strFileName, cJpfUtInfo)) {
      logger.warn("waring: " + strFileName);
      RunResult.iErrorFileCount++;
      return false;
    }
    // RunResult.iGenFileCount++;
    saveFile(strSaveUtFileName, cJpfUtInfo.toString(), GenerateInputParam.JAVA_ENCODE);
    RunResult.GenFileCount++;
    return true;

  }

  /**
   * @category 保存文件
   * @param strFileFullName
   * @param sb update 2012-9-5
   */
  public void saveFile(String strFileFullName, String str, String strEncoding) {
    FileOutputStream fout = null;
    try {
      AiFileUtil.mkdir(AiFileUtil.getFilePath(strFileFullName));
      logger.debug("saving filename:" + strFileFullName);

      fout = new FileOutputStream(strFileFullName);
      OutputStreamWriter osw = new OutputStreamWriter(fout, strEncoding);
      osw.write(str);
      osw.flush();

      logger.debug("saved file ok:" + strFileFullName);

    } catch (Exception ex) {
      // TODO: handle exception
      ex.printStackTrace();
      logger.error(ex.getMessage());
    } finally {
      if (fout != null) {
        try {
          fout.close();
        } catch (Exception ex) {
          // TODO: handle exception
        }

      }
    }
  }

  /**
   * 
   * @category 增加IMPORT
   * @author 吴平福
   * @param importList
   * @param sb update 2017年9月29日
   */
  public void addImport(JpfUtInfo cJpfUtInfo, List importList) {

    for (Object obj : importList) {
      ImportDeclaration importDec = (ImportDeclaration) obj;
      // logger.debug(importDec.toString());
      cJpfUtInfo.addImport(importDec.toString().trim());
    }
  }

  /**
   * 
   * @category @author 吴平福
   * @param sourceFileName
   * @param cJpfUtInfo
   * @return update 2018年1月30日
   */
  public boolean generateTestFromJavaFile(final String sourceFileName, JpfUtInfo cJpfUtInfo) {
    try {
      GeneratorConstructorImpl cGeneratorConstructorImpl = new GeneratorConstructorImpl();

      CompilationUnit cCompilationUnit = ParseJavaSourceFile.getInstance()
          .parseJavaSourceFile17(sourceFileName, GenerateInputParam.JAVA_ENCODE);

      List types = cCompilationUnit.types();
      if (types.size() == 0) {
        logger.warn("type=null:" + sourceFileName);
        return false;
      }
      // logger.warn("types:" + (AbstractTypeDeclaration)types.get(0));

      AbstractTypeDeclaration cAbstractTypeDeclaration = (AbstractTypeDeclaration) types.get(0);
      if (cAbstractTypeDeclaration instanceof EnumDeclaration) {
        logger.info("不支持枚举类生产单元测试：" + sourceFileName);
        RunResult.EnumFileCount++;
        return false;
      }
      if (!(cAbstractTypeDeclaration instanceof TypeDeclaration)) {
        return false;
      }

      TypeDeclaration typeDec = (TypeDeclaration) types.get(0);
      logger.info("classname=" + typeDec.getName());
      logger.debug("typeDec.getModifiers()=" + typeDec.getModifiers());

      if (typeDec.getModifiers() == AiTestConst.CLASS_TYPE_ABSTRACT) {
        // abstract class
        logger.info("抽象类不能生产单元测试：" + sourceFileName);
        RunResult.AbstractFileCount++;
        return false;
      }

      if (typeDec.isInterface() && GenerateInputParam.GenerateType != 2) {
        logger.info("当前设置不支持接口类生产单元测试：" + sourceFileName);
        RunResult.InterfaceFileCount++;
        return false;
      }
      // addPackage
      cJpfUtInfo
          .setUtPackage("package " + cCompilationUnit.getPackage().getName().toString() + ";\n");

      cJpfUtInfo.setSourcePackage(cCompilationUnit.getPackage().getName().toString());
      // add import
      addImport(cJpfUtInfo, cCompilationUnit.imports());
      addExtraImport(cJpfUtInfo);

      // add class javadoc
      cJpfUtInfo.setUtFileDesc(addClassDesc(typeDec.getName().toString()));
      // add class declare
      cJpfUtInfo.setUtClassDeclare(addClassDeclare(typeDec.getName().toString()));

      // show methods
      MethodDeclaration methodDec[] = typeDec.getMethods();

      // Constructor
      cGeneratorConstructorImpl.doGenerate(methodDec, typeDec.getName().toString(), cJpfUtInfo);


      JpfMethodInfo cMethodInfo = new JpfMethodInfo();
      cMethodInfo.setClassName(typeDec.getName().toString());

      // addExtraMethod(typeDec.getName().toString(), cJpfUtInfo);

      for (MethodDeclaration method : methodDec) {
        cJpfUtInfo.setInitCountPerMethod(0);
        SimpleName methodName = method.getName();
        if (methodName.toString().equalsIgnoreCase("main")) {
          continue;
        }
        // 1 public
        // 2 private
        // 9 public static
        // 10 private static

        Type returnType = method.getReturnType2();
        // logger.debug("method return type:" + returnType);

        cMethodInfo.setModifiers(method.getModifiers());
        cMethodInfo.setMethodName(methodName.toString());
        cMethodInfo.setMethodParam(method.parameters());
        if (method.getJavadoc() != null) {
          cMethodInfo.setStrJavaDoc(method.getJavadoc().toString());
        }
        cMethodInfo.setMethodExceptions(method.thrownExceptionTypes());

        /*
         * for (int i = 0; i < cMethodInfo.getMethodExceptions().size(); i++) {
         * logger.info(cMethodInfo.getMethodExceptions().get(i).toString()); }
         */
        if (returnType != null) {

          cMethodInfo.setStrReturn(returnType.toString());
          AddImport.getInstance().addImportForList(cMethodInfo.getStrReturn(), cJpfUtInfo);
          AbstractGenerateMethod cGenerateMethod = null;
          if (typeDec.isInterface()) {
            logger.info("接口类单元测试：" + sourceFileName);
            RunResult.InterfaceFileCount++;
            cGenerateMethod = new GTMForDAOInterface();
            cGenerateMethod.doGenerateMethod(cMethodInfo, cJpfUtInfo);

          } else if (0 == method.getModifiers() || 1 == method.getModifiers()
              || 3 == method.getModifiers() || 4 == method.getModifiers()) {
            // public:1 protected:3
            cGenerateMethod = new GenerateMethodPublic();
            cGenerateMethod.doGenerateMethod(cMethodInfo, cJpfUtInfo);

          } else if (2 == method.getModifiers()) {
            // private
            cGenerateMethod = new GenerateMethodPrivate();
            cGenerateMethod.doGenerateMethod(cMethodInfo, cJpfUtInfo);
            cJpfUtInfo.addImport("import java.lang.reflect.Method;");

          } else if (9 == method.getModifiers()) {
            // public static
            cGenerateMethod = new GenerateMethodPublicStatic();
            cGenerateMethod.doGenerateMethod(cMethodInfo, cJpfUtInfo);
          } else if (10 == method.getModifiers()) {
            // private static
            cJpfUtInfo.addImport("import java.lang.reflect.Method;");
            cGenerateMethod = new GenerateMethodPrivateStatic();
            cGenerateMethod.doGenerateMethod(cMethodInfo, cJpfUtInfo);
          } else {
            logger.warn("not support method");
            logger.warn("method.getModifiers():" + method.getModifiers());
          }

        }

      }

      addExtraBasic(typeDec.getName().toString(), cJpfUtInfo);
      setNewPackage(cJpfUtInfo);
      /*
       * if (typeDec.isInterface()) { cJpfUtInfo.setUtBasic(
       * GenerateBaseMethods.addExtraMethod(typeDec.getName().toString(),
       * cJpfUtInfo.getUtPackage()));
       * cJpfUtInfo.addImport(cJpfUtInfo.getUtPackage().replaceAll("package",
       * "import").replaceAll(";", "." + typeDec.getName().toString() + ";")); } else {
       * cJpfUtInfo.setUtBasic(GenerateBaseMethods.addTestEnd(typeDec.getName(). toString())); }
       */
      return true;
    } catch (Exception ex) {
      // TODO: handle exception
      ex.printStackTrace();
      logger.error("sourceFileName:" + sourceFileName);
    }
    return false;
  }

  /**
   * 
   * @category 增加类说明
   * @author 吴平福
   * @param strClass
   * @param sb update 2017年9月29日
   */
  protected String addClassDeclare(String strClass) {
    StringBuffer sb = new StringBuffer();
    sb.append("public class ").append(strClass).append("WTest ");

    sb.append(" {\n");
    return sb.toString();
  }

  /**
   * 
   * @category 增加启动结束主函数
   * @author 吴平福
   * @param strClass
   * @param sb update 2017年9月29日
   */
  public String addTestEnd(String strClass) {
    StringBuffer sb = new StringBuffer();
    sb.append("\n").append("  /**").append("\n");
    sb.append("  * 测试方法初始化.").append("\n");
    sb.append("  * ").append("\n");
    sb.append("  * @throws Exception ").append("\n");
    sb.append("  *         if the initialization fails for some reason ").append("\n");
    sb.append("  *  ").append("\n");
    sb.append("  * @generatedBy wupf@asiainfo.com at ").append(AiDateTimeUtil.getCurrDateTime())
        .append("\n");
    sb.append("  */  ").append("\n");
    sb.append(" @Before ").append("\n");
    sb.append("  public void setUp()  throws Exception ").append("\n");
    sb.append("  { ").append("\n");
    sb.append("     // TODO: add additional set up code here").append("\n");
    sb.append("  }").append("\n");

    sb.append("\n").append("  /**").append("\n");
    sb.append("  *  如果有必须，测试方法退出清理工作.").append("\n");
    sb.append("  * ").append("\n");
    sb.append("  * @throws Exception ").append("\n");
    sb.append("  * if the clean-up fails for some reason ").append("\n");
    sb.append("  *  ").append("\n");
    sb.append("  * @generatedBy wupf@asiainfo.com at ").append(AiDateTimeUtil.getCurrDateTime())
        .append("\n");
    sb.append("  */  ").append("\n");
    sb.append("  @After ").append("\n");
    sb.append("  public void tearDown()  throws Exception ").append("\n");
    sb.append("  { ").append("\n");
    sb.append("    // TODO: add additional clean-up code here").append("\n");
    sb.append("  }").append("\n");

    sb.append("\n").append("  /**").append("\n");
    sb.append("  * 测试类初始化.").append("\n");
    sb.append("  * ").append("\n");
    sb.append("  * @throws Exception ").append("\n");
    sb.append("  *         if the initialization fails for some reason ").append("\n");
    sb.append("  *  ").append("\n");
    sb.append("  * @generatedBy wupf@asiainfo.com at ").append(AiDateTimeUtil.getCurrDateTime())
        .append("\n");
    sb.append("  */  ").append("\n");
    sb.append(" @BeforeClass ").append("\n");
    sb.append("  public static void setUpBeforeClass()  throws Exception ").append("\n");
    sb.append("  { ").append("\n");
    sb.append("     // TODO: add additional set up code here").append("\n");
    sb.append("  }").append("\n");

    sb.append("\n").append("  /**").append("\n");
    sb.append("  *  如果有必须，测试类退出清理工作.").append("\n");
    sb.append("  * ").append("\n");
    sb.append("  * @throws Exception ").append("\n");
    sb.append("  * if the clean-up fails for some reason ").append("\n");
    sb.append("  *  ").append("\n");
    sb.append("  * @generatedBy wupf@asiainfo.com at ").append(AiDateTimeUtil.getCurrDateTime())
        .append("\n");
    sb.append("  */  ").append("\n");
    sb.append("  @AfterClass ").append("\n");
    sb.append("  public static void tearDownAfterClass()  throws Exception ").append("\n");
    sb.append("  { ").append("\n");
    sb.append("    // TODO: add additional clean-up code here").append("\n");
    sb.append("  }").append("\n");

    sb.append("\n").append("  /**").append("\n");
    sb.append("  * Launch the test").append("\n");
    sb.append("  * ").append("\n");
    sb.append("  * @param args the command line arguments ").append("\n");
    sb.append("  *  ").append("\n");
    sb.append("  * @generatedBy wupf@asiainfo.com at ").append(AiDateTimeUtil.getCurrDateTime())
        .append("\n");
    sb.append("  * ").append("\n");
    sb.append("  */").append("\n");
    sb.append("  public static void main(String[] args) ").append("\n");
    sb.append("  {").append("\n");
    sb.append("    new org.junit.runner.JUnitCore().run(").append(strClass)
        .append("Test.class);\n");
    sb.append("  } ").append("\n");
    sb.append("} ").append("\n");

    return sb.toString();
  }

  /**
   * 
   * @category 增加类说明
   * @author 吴平福
   * @param strClass
   * @param sb update 2017年9月29日
   */
  public String addClassDesc(String strClass) {
    StringBuffer sb = new StringBuffer();
    sb.append("/**").append("\n");
    sb.append("* The class <code>").append(strClass)
        .append("Test</code> contains tests for the class <code>{@link ").append(strClass)
        .append("}</code>.").append("\n");
    sb.append("* <p>").append("\n");
    sb.append("* Copyright (c) 2017").append("\n");
    sb.append("* ").append("\n");
    sb.append("* @generatedBy wupf@asiainfo.com at ").append(AiDateTimeUtil.getCurrDateTime())
        .append("\n");
    sb.append("* @author Administrator").append("\n");
    sb.append("* @version $Revision: ").append(GenerateInputParam.VERSION_INFO).append("\n");
    sb.append("*/").append("\n");

    return sb.toString();
  }
}
