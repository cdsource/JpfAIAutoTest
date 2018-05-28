/**
 * @author 吴平福 E-mail:wupf@asiainfo.com
 * @version 创建时间：2018年2月4日 下午4:27:00 类说明
 */

package org.jpf.aitest.gts.gtm.genbylog;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.jpf.aitest.JpfMethodInfo;
import org.jpf.aitest.JpfUtInfo;
import org.jpf.aitest.ParamInitBody;
import org.jpf.aitest.plugins.logs.LogCaseInfo;
import org.jpf.aitest.plugins.logs.ParamValueFromSelfLog;
import org.jpf.aitest.plugins.springs.logs.ParamValueFromDebugLog;
import org.jpf.aitest.utils.FindClassInfoUtil;

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
    /**
     * 
     * @param strFullClassName
     * @param cMethodInfo
     * @return
     */
    public String genParamValue(String strFullClassName, JpfMethodInfo cMethodInfo) {
    	return "";
    }
    
    /**
     * 
     * @param cJpfUtInfo
     * @param strFullClassName
     * @param cMethodInfo
     * @return
     */
    public String genParamValue(JpfUtInfo cJpfUtInfo,String strFullClassName, JpfMethodInfo cMethodInfo) {
        logger.info(strFullClassName);
        // find method from self test
        String strSql = ParamValueFromSelfLog.getInstance().findValueFromLog(strFullClassName);
        if (null == strSql || strSql.trim().length() == 0) {
            return "";
        }

        // find from log
        LogCaseInfo cLogCaseInfo = ParamValueFromDebugLog.getInstance().findValueFromLog(strSql);
        if (null == cLogCaseInfo) {
            return "";
        }
        StringBuffer sBuffer = new StringBuffer();
        try {

            for (int i = 0; i < cMethodInfo.getMethodParam().size(); i++) {
            	logger.debug(cMethodInfo.getMethodParam().get(i).toString());
                ParamInitBody cParamInitBody = new ParamInitBody(cMethodInfo.getMethodParam().get(i).toString());
                logger.debug(cParamInitBody.getParamType());
                // 在基本类型里面找不到
                boolean isFindType = true;
                String strValue = cLogCaseInfo.getParams().get(cParamInitBody.getParamVariable().toUpperCase());
                switch (cParamInitBody.getParamType()) {
                    case "int":
                        
                        if (null!=strValue)
                        {
                            sBuffer.append("  ").append(cParamInitBody.getParamVariable()).append("=")
                            .append(strValue).append(";\n");
                        }else {
                            sBuffer.append("  ").append(cParamInitBody.getParamVariable()).append("=")
                            .append(1).append(";\n");
                        }
                        break;
                    case "long":
                        if (null!=strValue)
                        {
                            sBuffer.append("  ").append(cParamInitBody.getParamVariable()).append("=")
                            .append(strValue).append("L;\n");
                        }else {
                            sBuffer.append("  ").append(cParamInitBody.getParamVariable()).append("=")
                            .append(1L).append(";\n");
                        }
                        break;
                    case "String":
                        if (null!=strValue)
                        {
                            sBuffer.append("  ").append(cParamInitBody.getParamVariable()).append("=\"")
                            .append(strValue).append("\";\n");
                        }else {
                            sBuffer.append("  ").append(cParamInitBody.getParamVariable()).append("=\"")
                            .append("abc").append("\";\n");
                        }
                        break;
                        
                    default:
                        isFindType = false;
                        break;
                }
                if (!isFindType) {
                    String strClassName=cJpfUtInfo.findImport(cParamInitBody.getParamType());
                    logger.info(strClassName);
                    
                    String strJavaFileName =
                            FindClassInfoUtil.getInstance().findJavaFile(strClassName);
                    logger.info(strJavaFileName);
                    sBuffer.append("  ").append(cParamInitBody.getParamType()).append(" ")
                            .append(cParamInitBody.getParamVariable()).append(" = new ")
                            .append(cParamInitBody.getParamType()).append("();\n");
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
                        String strShortMethodName = strMethodName.substring(3, strMethodName.length()).toUpperCase();

                        strValue = cLogCaseInfo.getParams().get(strShortMethodName);
                        if (null != strValue) {
                            logger.debug(strMethodName + ":" + strValue);
                            ParamInitBody cParamInitBody2 = new ParamInitBody(method.parameters().get(0).toString());
                            logger.debug(cParamInitBody2.getParamType());
                            sBuffer.append("  ").append(cParamInitBody.getParamVariable()).append(".")
                                    .append(strMethodName).append("(");
                            if (cParamInitBody2.getParamType().equalsIgnoreCase("String")) {
                                sBuffer.append("\"").append(strValue).append("\"");
                            } else if (cParamInitBody2.getParamType().equalsIgnoreCase("Long")) {
                                sBuffer.append(strValue).append("L");
                            } else {
                                sBuffer.append(strValue);
                            }
                            sBuffer.append(");\n");
                            
                            //SAVE TO DB,MAYBE USE AGAIN
                            
                        }
                        // 1 public
                        // 2 private
                        // 9 public static
                        // 10 private static

                    }

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
