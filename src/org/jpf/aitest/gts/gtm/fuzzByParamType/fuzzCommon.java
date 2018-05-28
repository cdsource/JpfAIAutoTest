/**
 * @author 吴平福 E-mail:wupf@asiainfo.com
 * @version 创建时间：2017年11月20日 上午11:58:36 类说明
 */

package org.jpf.aitest.gts.gtm.fuzzByParamType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.BodyDeclaration;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.EnumDeclaration;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.jpf.aitest.JpfUtInfo;
import org.jpf.aitest.gts.gtm.MethodParamBody;
import org.jpf.aitest.utils.FindClassInfoUtil;
import org.jpf.aitest.utils.FormatUtil;
import org.jpf.aitest.utils.GenerateUtil2;
import org.jpf.aitest.utils.ParseJavaSourceFile;

import com.asiainfo.utils.ios.AiFileUtil;

/**
 * 
 */
public class fuzzCommon implements IFuzz {
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
    public ArrayList<String> getFuzzeForNull(MethodParamBody cParamInitBody) {
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

    public ArrayList<String> getFuzzeForNew(MethodParamBody cParamInitBody, JpfUtInfo cJpfUtInfo) {
        // TODO Auto-generated method stub
        ArrayList<String> mList = new ArrayList<String>();

        // logger.debug("strParamName=" + cParamInitBody.getParamVariable());
        if (cParamInitBody.isArray()) {
            mList.add("    " + cParamInitBody.getParamType() + " " + cParamInitBody.getParamVariable() + " =  new "
                    + cParamInitBody.getParamType() + "{};\n");

        } else {
            String strAdd = "    " + cParamInitBody.getParamType() + " " + cParamInitBody.getParamVariable()
            + " =  new " + cParamInitBody.getParamType() + "() ;\n";
            

            CompilationUnit cCompilationUnit = getClassCompilationUnit(cParamInitBody, cJpfUtInfo);
          
            if (cCompilationUnit != null) {
                String strPackageName=cCompilationUnit.getPackage().getName().toString();
                cJpfUtInfo.setCurrentJavaFilePackage(strPackageName);
                GenerateUtil2.addImport(cCompilationUnit.imports(), cJpfUtInfo);
                List types = cCompilationUnit.types();
                if (types.size() == 0) {
                    // can not analy
                    mList.add(strAdd);
                }
                BodyDeclaration cBodyDeclaration = (BodyDeclaration) types.get(0);
                // TypeDeclaration typeDec = (TypeDeclaration) types.get(0);
                //logger.debug("BodyDeclaration=" + cBodyDeclaration.getNodeType());

                if (cBodyDeclaration != null && cBodyDeclaration.getNodeType() == 71) {
                    // enum
                    strAdd = "    " + cParamInitBody.getParamType() + " " + cParamInitBody.getParamVariable()
                    + " =   " + getEnumInit((EnumDeclaration)cBodyDeclaration,strPackageName,cJpfUtInfo) + " ;\n";
                    mList.add(strAdd);
                    
                } 
                if (cBodyDeclaration != null && cBodyDeclaration.getNodeType() == 55) {
                    // class
                    TypeDeclaration typeDecClass = (TypeDeclaration) cBodyDeclaration;
                    logger.info("classname=" + typeDecClass.getName());
                    if (typeDecClass.isInterface())
                    {
                        strAdd =   "    " + cParamInitBody.getParamType() + " " + cParamInitBody.getParamVariable()
                        + " =  null ;\n";
                    }else
                    {
                    strAdd = strAdd                    + getClassSetMethodInit(typeDecClass, strPackageName,
                            cParamInitBody, cJpfUtInfo);
                    }
                    mList.add(strAdd);

                }

            }else
            {
                //not find source from given path
                mList.add(strAdd);
            }


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
    private CompilationUnit getClassCompilationUnit(MethodParamBody cParamInitBody, JpfUtInfo cJpfUtInfo) {

        try {
            logger.debug(cJpfUtInfo.getCurrentJavaFilePackage());
            logger.debug(cParamInitBody.getParamType());
            //logger.debug(cJpfUtInfo.getUtImport());
            String strClassName=cJpfUtInfo.findImport(cParamInitBody.getParamType());
            logger.info(strClassName);
            if (strClassName.equalsIgnoreCase(cParamInitBody.getParamType()))
            {
                strClassName=cJpfUtInfo.findImport(cJpfUtInfo.getCurrentJavaFilePackage()+"."+ cParamInitBody.getParamType());
            }
            String strJavaFileName = FindClassInfoUtil.getInstance().findJavaFile(strClassName);
            
            logger.debug(strJavaFileName);
            
            if (null == strJavaFileName || 0 == strJavaFileName.trim().length()) {
                return null;
            }

            return ParseJavaSourceFile.getInstance().parseJavaSourceFile17(strJavaFileName);

        } catch (Exception ex) {
            // TODO: handle exception
            ex.printStackTrace();
        }
        return null;
    }

   

    /**
     * 
     * @category @author 吴平福
     * @param sb
     * @param typeDec
     * @param PackageName
     * @param cParamInitBody
     * @param cJpfUtInfo update 2018年2月28日
     */
    private String getEnumInit( EnumDeclaration typeDec, String PackageName,
             JpfUtInfo cJpfUtInfo) {

        try {

            // logger.debug("classname=" + typeDec.getName());
            // logger.debug("typeDec.getModifiers()=" + typeDec.getModifiers());

            cJpfUtInfo.addImport(PackageName + "." + typeDec.getName().toString());
            String strInit = typeDec.enumConstants().get(0).toString();
            strInit = typeDec.getName().toString()+"."+strInit.substring(0, strInit.indexOf("(")).trim();

            return strInit;


        } catch (Exception ex) {
            // TODO: handle exception
            ex.printStackTrace();
        }
       return "";

    }

    /**
     * 
     * @category 获取参数类的SET方法赋值 
     * @author 吴平福
     * @param typeDec
     * @param PackageName
     * @param cParamInitBody
     * @param cJpfUtInfo
     * @return update 2018年2月28日
     */
    public StringBuffer getClassSetMethodInit( TypeDeclaration typeDec, String PackageName,
            MethodParamBody cParamInitBody, JpfUtInfo cJpfUtInfo) {
        StringBuffer sb =new StringBuffer();
        try {

            // logger.debug("classname=" + typeDec.getName());
            // logger.debug("typeDec.getModifiers()=" + typeDec.getModifiers());

            cJpfUtInfo.addImport(PackageName + "." + typeDec.getName().toString());

            if (typeDec.isInterface()) {
                sb.append("     ").append(cParamInitBody.getParamType()).append(" ")
                        .append(cParamInitBody.getParamVariable()).append(" = null ;\n");
                return sb;
            }

            // show methods
            MethodDeclaration methodDec[] = typeDec.getMethods();

            for (MethodDeclaration method : methodDec) {

                SimpleName methodName = method.getName();
                if (!methodName.toString().startsWith("set")) {
                    continue;
                }
               
                //MethodParam cMethodParam
                List<MethodParamBody> parameters =new ArrayList<MethodParamBody>();
                
                //copy
                /*
                for(int i=0;i<method.parameters().size();i++)
                {
                    ParamInitBody cParamInitBody2=new ParamInitBody(method.parameters().get(i).toString());
                    parameters.add(cParamInitBody2);
                    cParamInitBody2.setParamVariable(cParamInitBody2.getParamVariable()+i);
                    logger.debug(cParamInitBody2.getParamVariable());
                    if (cParamInitBody2.getParamVariable().startsWith("aiPricePlan"))
                    {
                        logger.debug(cParamInitBody2.getParamType());
                        logger.debug(cParamInitBody2.getParamVariable());
                    }
                }
                */
                ArrayList<String> cParamInitBody2 =
                        GenerateUtil2.addMethodParamInit2(method.parameters(), cJpfUtInfo, 1);


                
                String strInit = cParamInitBody2.get(0);
                // logger.debug(strInit);
                String[] inits = strInit.split(";\n");
                // logger.debug(inits.length);
                if (inits.length > 1) {
                    sb.append("     ").append(strInit).append("\n");
                    sb.append("     ").append(cParamInitBody.getParamVariable()).append(".")
                            .append(methodName.toString()).append("(")
                            .append(FormatUtil.getParamVar(method.parameters())).append(");\n");

                } else {
                    sb.append("     ").append(cParamInitBody.getParamVariable()).append(".")
                            .append(methodName.toString()).append("(").append(FormatUtil.getParamValue(strInit))
                            .append(");\n");
                }
                // logger.info(sb);

            }
        } catch (Exception ex) {
            // TODO: handle exception
            ex.printStackTrace();
        }
        // logger.debug(sb);
        return sb;
    }
}
