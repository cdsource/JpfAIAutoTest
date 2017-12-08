/** 
* @author 吴平福 
* E-mail:wupf@asiainfo.com 
* @version 创建时间：2017年11月20日 上午11:58:36 
* 类说明 
*/ 

package org.jpf.unittests.generateuts.fuzze;

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
import org.jpf.unittests.generateuts.ClassInfoConst;
import org.jpf.unittests.generateuts.ParamInitBody;
import org.jpf.unittests.generateuts.utils.AppParam;

import com.asiainfo.utils.ios.AiFileUtil;

/**
 * 
 */
public class fuzzeCommon implements IFuzze {
    private static final Logger logger = LogManager.getLogger();
    /**
     * 
     */
    public fuzzeCommon() {
        // TODO Auto-generated constructor stub
    }

    /* (non-Javadoc)
     * @see org.jpf.unittests.generateuts.fuzze.IFuzze#getFuzze(org.jpf.unittests.generateuts.ParamInitBody)
     */
    @Override
    public ArrayList<String> getFuzzeForNull(ParamInitBody cParamInitBody) {
        // TODO Auto-generated method stub
       ArrayList<String> mList=new ArrayList<String>();
        
        logger.debug("strParamName="+cParamInitBody.getParamVariable());
        if (cParamInitBody.isArray())
        {
            mList.add("    "+cParamInitBody.getParamType()+" " +cParamInitBody.getParamVariable()+" =  new "+cParamInitBody.getParamType()+"{};\n");
            
        }else
        {
            mList.add("    "+cParamInitBody.getParamType()+" "+cParamInitBody.getParamVariable()+" =  null;\n");

        }
        return mList;
    }
    
    public ArrayList<String> getFuzzeForNew(ParamInitBody cParamInitBody) {
        // TODO Auto-generated method stub
       ArrayList<String> mList=new ArrayList<String>();
        
        logger.debug("strParamName="+cParamInitBody.getParamVariable());
        if (cParamInitBody.isArray())
        {
            mList.add("    "+cParamInitBody.getParamType()+" " +cParamInitBody.getParamVariable()+" =  new "+cParamInitBody.getParamType()+"{};\n");
            
        }else
        {
            mList.add("    "+cParamInitBody.getParamType()+" "+cParamInitBody.getParamVariable()+" =  new "+cParamInitBody.getParamType()+"() ;\n");
            
        }
        return mList;
    }
    
    /**
     * 
     * @category 获取参数类的SET方法赋值
     * @author 吴平福 
     * @param cAppParam
     * @param listParam
     * @return
     * update 2017年12月8日
     */
    public StringBuffer getClassSetMethodInit(AppParam cAppParam, ParamInitBody cParamInitBody) {
        StringBuffer sb = new StringBuffer();

        String strJavaFileName = "";
        for (int i = 0; i < cAppParam.vFilesAll.size(); i++) {
            if (cAppParam.vFilesAll.get(i).endsWith(cParamInitBody.getParamType() + ".java")) {
                strJavaFileName = cAppParam.vFilesAll.get(i);
                break;
            }
        }

        logger.info(strJavaFileName);

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
            if (typeDec.getModifiers() == ClassInfoConst.CLASS_TYPE_ABSTRACT) {
                // abstract class
                logger.info("抽象类不能生产单元测试：" + strJavaFileName);
                return sb;
            }
            if (typeDec.isInterface()) {
                logger.info("不处理非接口类单元测试：" + strJavaFileName);
                return sb;
            }

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
                sb.append("     ").append(cParamInitBody.getParamVariable()).append(".").append(methodName.toString()).append("()")
                        .append(";\n");

                //List param = method.parameters();

            }
        } catch (Exception ex) {
            // TODO: handle exception
            ex.printStackTrace();
        }


        return sb;
    }
}
