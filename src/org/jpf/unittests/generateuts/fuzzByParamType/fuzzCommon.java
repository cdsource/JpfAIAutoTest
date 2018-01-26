/**
 * @author 吴平福 E-mail:wupf@asiainfo.com
 * @version 创建时间：2017年11月20日 上午11:58:36 类说明
 */

package org.jpf.unittests.generateuts.fuzzByParamType;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.jpf.unittests.generateuts.JpfUtInfo;
import org.jpf.unittests.generateuts.ParamInitBody;
import org.jpf.unittests.generateuts.utils.AppParam;
import org.jpf.unittests.generateuts.utils.FormatUtil;
import org.jpf.unittests.generateuts.utils.GenerateUtil;

import com.asiainfo.utils.ios.AiFileUtil;

/**
 * 
 */
public class fuzzCommon implements IFuzze {
    private static final Logger logger = LogManager.getLogger();

    /**
     * 
     */
    public fuzzCommon() {
        // TODO Auto-generated constructor stub
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.jpf.unittests.generateuts.fuzze.IFuzze#getFuzze(org.jpf.unittests.generateuts.
     * ParamInitBody)
     */
    @Override
    public ArrayList<String> getFuzzeForNull(ParamInitBody cParamInitBody) {
        // TODO Auto-generated method stub
        ArrayList<String> mList = new ArrayList<String>();

        logger.debug("strParamName=" + cParamInitBody.getParamVariable());
        if (cParamInitBody.isArray()) {
            mList.add("    " + cParamInitBody.getParamType() + " " + cParamInitBody.getParamVariable() + " =  new "
                    + cParamInitBody.getParamType() + "[]{};\n");

        } else {
            mList.add("    " + cParamInitBody.getParamType() + " " + cParamInitBody.getParamVariable() + " =  null;\n");

        }
        return mList;
    }

    public ArrayList<String> getFuzzeForNew(ParamInitBody cParamInitBody, JpfUtInfo cJpfUtInfo) {
        // TODO Auto-generated method stub
        ArrayList<String> mList = new ArrayList<String>();

        logger.debug("strParamName=" + cParamInitBody.getParamVariable());
        if (cParamInitBody.isArray()) {
            mList.add("    " + cParamInitBody.getParamType() + " " + cParamInitBody.getParamVariable() + " =  new "
                    + cParamInitBody.getParamType() + "{};\n");

        } else {
            mList.add("    " + cParamInitBody.getParamType() + " " + cParamInitBody.getParamVariable() + " =  new "
                    + cParamInitBody.getParamType() + "() ;\n" + getClassSetMethodInit(cParamInitBody, cJpfUtInfo));

        }
        return mList;
    }

    /**
     * 
     * @category 获取参数类的SET方法赋值
     * @author 吴平福
     * @param cAppParam
     * @param listParam
     * @return update 2017年12月8日
     */
    public StringBuffer getClassSetMethodInit(ParamInitBody cParamInitBody, JpfUtInfo cJpfUtInfo) {
        StringBuffer sb = new StringBuffer();

        String strJavaFileName = "";
        for (int i = 0; i < AppParam.vFilesAll.size(); i++) {
            if (AppParam.vFilesAll.get(i).endsWith("\\"+cParamInitBody.getParamType() + ".java")) {
                strJavaFileName = AppParam.vFilesAll.get(i);
                break;
            }
        }

        logger.info(strJavaFileName);
        if (strJavaFileName == "") {
            return sb;
        }
        try {
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
                return sb;
            }
            TypeDeclaration typeDec = (TypeDeclaration) types.get(0);
            logger.info("classname=" + typeDec.getName());
            logger.debug("typeDec.getModifiers()=" + typeDec.getModifiers());

            // show methods
            MethodDeclaration methodDec[] = typeDec.getMethods();

            for (MethodDeclaration method : methodDec) {

                SimpleName methodName = method.getName();
                if (!methodName.toString().startsWith("set")) {
                    continue;
                }
                logger.debug(methodName.toString());
                // 1 public
                // 2 private
                // 9 public static
                // 10 private static


                ArrayList<String> cParamInitBody2 = GenerateUtil.addMethodParamInit2(method.parameters(), cJpfUtInfo, 1);
                
                String strInit=cParamInitBody2.get(0);
                strInit =strInit.substring(strInit.indexOf("=")+1, strInit.length()).trim();
                if (strInit.endsWith(";"))
                {
                    strInit=strInit.substring(0,strInit.length()-1);
                }
                sb.append("     ").append(cParamInitBody.getParamVariable()).append(".").append(methodName.toString())
                        .append("(").append(strInit).append(");\n");

            }
        } catch (Exception ex) {
            // TODO: handle exception
            ex.printStackTrace();
        }


        return sb;
    }
}
