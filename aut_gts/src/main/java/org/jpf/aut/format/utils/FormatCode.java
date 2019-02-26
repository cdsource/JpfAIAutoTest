/**
 * copyrigth by wupf@asiainfo.com 2018年7月31日
 */
package org.jpf.aut.format.utils;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.jdt.core.formatter.CodeFormatter;
import org.eclipse.jdt.core.formatter.DefaultCodeFormatterConstants;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;
import org.eclipse.jdt.internal.formatter.DefaultCodeFormatter;
import org.eclipse.jdt.internal.formatter.DefaultCodeFormatterOptions;
import org.eclipse.jface.text.Document;
import org.eclipse.text.edits.TextEdit;
import org.jpf.aut.base.GenerateInputParam;
import org.jpf.utils.ios.AiFileUtil;

/**
 * @author wupf@asiainfo.com
 *
 */
public class FormatCode {

  /**
   * 
   */
  private FormatCode() {
    // TODO Auto-generated constructor stub
  }

  /**
   * 
   * @author wupf@asiainfo.com
   * @param fileName
   * @throws Exception 2018年8月17日
   */
  public static void formatJavaCode(String fileName) throws Exception {

    String fileContent = AiFileUtil.getFileTxt(fileName, GenerateInputParam.JAVA_ENCODE);
    fileContent = format(fileContent);
    AiFileUtil.saveFile(fileName, fileContent, GenerateInputParam.JAVA_ENCODE);
  }

  /**
   * format java source by default rule
   * 
   * @param fileContent
   * @exception Exception
   * @return sourceCode
   */
  public static String format(String fileContent) throws Exception {
    String sourceCode = fileContent;
    // get default format for java
    Map options = DefaultCodeFormatterConstants.getEclipseDefaultSettings();
    DefaultCodeFormatterOptions preferences = new DefaultCodeFormatterOptions(options);
    Document doc = new Document(sourceCode);

    try {
      Map compilerOptions = new HashMap();
      // confirm java source base on java 1.5
      compilerOptions.put(CompilerOptions.OPTION_Compliance, CompilerOptions.VERSION_1_8);
      compilerOptions.put(CompilerOptions.OPTION_TargetPlatform, CompilerOptions.VERSION_1_8);
      compilerOptions.put(CompilerOptions.OPTION_Source, CompilerOptions.VERSION_1_8);
      DefaultCodeFormatter codeFormatter = new DefaultCodeFormatter(preferences, compilerOptions);
      // format


      TextEdit edits = codeFormatter.format(CodeFormatter.K_COMPILATION_UNIT, sourceCode, 0,
          sourceCode.length(), 0, null);
      edits.apply(doc);
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
    sourceCode = doc.get();
    return sourceCode;
  }

  public static void main(String[] arg) {
    String javaCodeBefore =
        "import org.aa;import org.*;public class aa {public void aa(){System.out.println(\"aa\");}}";
    String javaCodeAfter = "";
    System.out.println("format before:");
    System.out.println(javaCodeBefore);
    try {
      javaCodeAfter = format(javaCodeBefore);
      System.out.println("format after:");
      System.out.println(javaCodeAfter);
    } catch (Exception e) {
      e.printStackTrace();
      System.out.println("format error");
    }
  }
}
