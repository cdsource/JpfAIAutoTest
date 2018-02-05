/**
 * @author 吴平福 E-mail:wupf@asiainfo.com
 * @version 创建时间：2018年2月4日 下午4:27:00 类说明
 */

package org.jpf.unittests.generateuts.genbylog;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.jpf.unittests.generateuts.JpfMethodInfo;
import org.jpf.unittests.generateuts.ParamInitBody;
import org.jpf.unittests.generateuts.utils.FindClassInfoUtil;
import org.jpf.unittests.generateuts.utils.FormatUtil;

import com.asiainfo.utils.ios.AiFileUtil;

/**
 * 
 */
public class GenParamValueFromLog {

    private static final Logger logger = LogManager.getLogger();


    // 已经自行实例化
    private static final GenParamValueFromLog Instance = new GenParamValueFromLog();

    // 静态工厂方法
    public static GenParamValueFromLog getInstance() {
        return Instance;
    }

    public String genParamValue(String strFullClassName, JpfMethodInfo cMethodInfo) {
        logger.info(strFullClassName);
        //find method from self test
        String strSql=ParamValueFromSelfLog.getInstance().findValueFromLog(strFullClassName);
        if (null==strSql || strSql.trim().length()==0)
        {
            return "";
        }
        
        //find from log
        LogCaseInfo cLogCaseInfo=ParamValueFromDebugLog.getInstance().findValueFromLog(strSql);
        if (null==cLogCaseInfo)
        {
            return "";
        }
        StringBuffer sBuffer = new StringBuffer();
        try {

            for (int i = 0; i < cMethodInfo.getMethodParam().size(); i++) {

                ParamInitBody cParamInitBody = new ParamInitBody();
                FormatUtil.formatToParamBody(cParamInitBody, cMethodInfo.getMethodParam().get(i).toString());
                
                //在基本类型里面找不到
                String strJavaFileName = FindClassInfoUtil.getInstance().findJavaFile(cParamInitBody.getParamType());
                sBuffer.append("  ").append(cParamInitBody.getParamType()).append(" ").append(cParamInitBody.getParamVariable()).append(" = new ").append(cParamInitBody.getParamType()).append("();\n");
                logger.info(strJavaFileName);
                if (strJavaFileName == "") {
                    logger.warn("not find java file:" + strJavaFileName);
                    return "";
                }

                String sourceString = AiFileUtil.getFileTxt(strJavaFileName, "GBK");
                logger.trace(sourceString);
                ASTParser astParser = ASTParser.newParser(AST.JLS8);
                astParser.setKind(ASTParser.K_COMPILATION_UNIT);
                astParser.setResolveBindings(true);

                astParser.setSource(sourceString.toCharArray());
                CompilationUnit cCompilationUnit = (CompilationUnit) astParser.createAST(null);


                List types = cCompilationUnit.types();
                if (types.size() == 0) {
                    logger.warn("type=null:" + strJavaFileName);
                    return "";
                }
                TypeDeclaration typeDec = (TypeDeclaration) types.get(0);
                logger.info("classname=" + typeDec.getName());
                logger.debug("typeDec.getModifiers()=" + typeDec.getModifiers());

                // show methods
                MethodDeclaration methodDec[] = typeDec.getMethods();
 
                
                for (MethodDeclaration method : methodDec) {

                    String strMethodName = method.getName().toString();
                    if (!strMethodName.startsWith("set")) {
                        continue;
                    }
                    logger.debug(strMethodName);
                    String strShortMethodName=strMethodName.substring(3,strMethodName.length()).toUpperCase();
                    
                    
                    String strValue=cLogCaseInfo.getParams().get(strShortMethodName);
                    if (null!=strValue)
                    {
                        logger.debug(strMethodName+":"+strValue);
                        ParamInitBody cParamInitBody2 = new ParamInitBody();
                        FormatUtil.formatToParamBody(cParamInitBody2, method.parameters().get(0).toString());
                        logger.debug(cParamInitBody2.getParamType());
                        sBuffer.append("  ").append(cParamInitBody.getParamVariable()).append(".") .append(strMethodName).append("(");
                        if (cParamInitBody2.getParamType().equalsIgnoreCase("String"))
                        {
                            sBuffer.append("\"").append(strValue).append("\"");
                        }else if (cParamInitBody2.getParamType().equalsIgnoreCase("Long"))
                        {
                            sBuffer.append(strValue).append("L");
                        }else {
                            sBuffer.append(strValue);
                        }
                        sBuffer.append(");\n");
                    }
                    // 1 public
                    // 2 private
                    // 9 public static
                    // 10 private static



                }
            }
            logger.debug(sBuffer);


        } catch (Exception ex) {
            // TODO: handle exception
            ex.printStackTrace();
        }
        return sBuffer.toString();
    }
}
