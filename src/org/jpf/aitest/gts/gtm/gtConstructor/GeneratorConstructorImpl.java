/**
 * @author 吴平福 E-mail:wupf@asiainfo.com
 * @version 创建时间：2017年10月18日 下午10:43:56 类说明
 */

package org.jpf.aitest.gts.gtm.gtConstructor;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.Type;
import org.jpf.aitest.JpfUtInfo;
import org.jpf.aitest.JpfUtMethodInfo;
import org.jpf.aitest.RunResult;
import org.jpf.aitest.utils.FormatUtil;
import org.jpf.aitest.utils.GenerateUtil2;

/**
 * 
 */
public class GeneratorConstructorImpl implements IGTMForConstructor {
    private static final Logger logger = LogManager.getLogger();

    /**
     * 
     */
    public GeneratorConstructorImpl() {
        // TODO Auto-generated constructor stub
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.jpf.unittests.generateuts.IConstructorGenerator#doGenerate(org.eclipse.jdt.core.dom.
     * MethodDeclaration[], java.lang.String, org.jpf.unittests.generateuts.UtFileText)
     */
    @Override
    public void doGenerate(MethodDeclaration[] methodDec, String strClass, JpfUtInfo cJpfUtInfo) {
        // TODO Auto-generated method stub
        /*
         * 先找构造函数 1 没有，默认使用空参数 2. 私有构造函数：不支持 3. 有构造函数，无参数 4. 有构造函数，有参数 5. 多个构造函数，以参数最少的为准。
         */

        for (MethodDeclaration method : methodDec) {

            SimpleName methodName = method.getName();
            // System.out.println(method.getModifiers());
            // 1 public 2 private 9 public static 10 private static
            logger.debug("methodName=" + methodName.toString());
            // get method parameters
            List param = method.parameters();
            logger.debug("method parameters:" + param.toString());

            Type returnType = method.getReturnType2();
            logger.debug("method return type:" + returnType);


            if (returnType == null) {
                // PUBLIC构造函数
                if (method.getModifiers() == 1 || method.getModifiers() == 0) {
                    addConstructorMethodBody(method.getModifiers(), strClass, methodName.toString(), param,
                            cJpfUtInfo);
                }else
                {
                    logger.warn(method.getModifiers());
                }
                
                /*
                 * if (method.getModifiers() == 2) { // private
                 * addPrivateConstructor(method,strClass,cUtFileText); }
                 */
            } else if (method.getModifiers() == 9 && returnType.toString().equalsIgnoreCase(strClass)) {
                logger.info("单例模式");
                getSingletonInstance(method, strClass, cJpfUtInfo);
            }

        }
        if (cJpfUtInfo.getUtMinConstructor().length() == 0 && cJpfUtInfo.getUtPrivateConstructor().length()==0) {
            cJpfUtInfo.setUtMinConstructor("  " + strClass + " fixture = new " + strClass + "();");
        }
    }


    /**
     * 
     * @category 单例模式获取INSTANCE
     * @author 吴平福
     * @param methodDec
     * @param strClass
     * @return update 2017年10月19日
     */
    private void getSingletonInstance(MethodDeclaration method, String strClass, JpfUtInfo cJpfUtInfo) {
        
        // PUBLIC返回本身类函数
        StringBuffer sbPrivateConstructor = new StringBuffer();
        List param = method.parameters();
        ArrayList<String> cParamInitBody  = GenerateUtil2.addMethodParamInit2(param, cJpfUtInfo,1);

            for (int i = 0; i < cParamInitBody.size(); i++) {
                sbPrivateConstructor.append("    ").append( cParamInitBody.get(i) ).append("\n");
            }

        //logger.info(sbPrivateConstructor.toString());
        sbPrivateConstructor.append("    ").append(strClass).append(" fixture=").append(strClass).append(".")
                .append(method.getName().toString());
        sbPrivateConstructor.append(GenerateUtil2.addMethodParam2Method(method.getModifiers(), param, cJpfUtInfo));

        logger.info(sbPrivateConstructor);
        cJpfUtInfo.setUtPrivateConstructor(sbPrivateConstructor.toString());
        
    }

    /**
     * 
     * @category 增加构造函数测试代码
     * @author 吴平福
     * @param strClass
     * @param strMethod
     * @param param
     * @param sb update 2017年9月29日
     */
    private void addConstructorMethodBody(int Modifiers, String strClass, String strMethodName, List MethodParam,
            JpfUtInfo cJpfUtInfo) {
       
        JpfUtMethodInfo cJpfUtMethodInfo=new JpfUtMethodInfo();
        cJpfUtMethodInfo.setMethodJavaDoc(GenerateUtil2.addMethodJavaDoc(Modifiers, "", strMethodName, MethodParam, cJpfUtInfo));
        RunResult.iMethodCount++;
        cJpfUtMethodInfo.setMethodDeclare("  public void test" +strMethodName+"_"+RunResult.iMethodCount +" () throws Exception\n   {\n" );
        // instance
        cJpfUtMethodInfo.setClassConstructor(analyseConstructor(Modifiers, strClass, strMethodName, MethodParam, cJpfUtInfo));
        cJpfUtMethodInfo.setMethodAssert("    assertNotNull(result);\n");
        logger.trace(cJpfUtMethodInfo.toString());
        cJpfUtInfo.getListUtMethodInfos().add(cJpfUtMethodInfo);
        
    }


    /**
     * 
     * @category 分析构造函数
     * @author 吴平福
     * @param Modifiers
     * @param strClass
     * @param strMethod
     * @param MethodParam
     * @param cConstructorInfo update 2017年9月30日
     */
    private String analyseConstructor(int Modifiers, String strClass, String strMethod, List MethodParam,
            JpfUtInfo cJpfUtInfo) {

        StringBuffer sbConstructor = new StringBuffer();
        sbConstructor.append(addConstructorParamInit(MethodParam, cJpfUtInfo));
        sbConstructor.append("    ").append(strClass).append(" result = new ").append(strClass);
        sbConstructor.append(addConstructorParam2Constructor(Modifiers, MethodParam, cJpfUtInfo));

        if (cJpfUtInfo.getUtMinConstructor().length() == 0
                || cJpfUtInfo.getUtMinConstructor().length() > sbConstructor.length()) {
            cJpfUtInfo.setUtMinConstructor(sbConstructor.toString());
        }
        // cUtFileText.sbMinConstructor.append(sbConstructor);
        logger.debug(sbConstructor);
        return sbConstructor.toString();
    }
    /**
     * 
     * @category 初始化构造方法的参数
     * @author 吴平福
     * @param MethodParam
     * @param cUtFileText
     * @return update 2017年11月13日
     */
    public StringBuffer addConstructorParamInit(List MethodParam, JpfUtInfo cJpfUtInfo) {

        StringBuffer sb = new StringBuffer();
        ArrayList<String> cParamInitBody  = GenerateUtil2.addMethodParamInit2(MethodParam, cJpfUtInfo,1);

        for (int i = 0; i < cParamInitBody.size(); i++) {
            sb.append("    ").append( cParamInitBody.get(i) ).append("\n");
        }
        logger.debug(sb);
        return sb;
    }
    
    /**
     * 
     * @category 把构造方法的参数增加到构造方法中
     * @author 吴平福
     * @param MethodParam
     * @param sb update 2017年9月29日
     */
    public StringBuilder addConstructorParam2Constructor(int Modifiers, List MethodParam,
            JpfUtInfo cJpfUtInfo) {

        StringBuilder sb = new StringBuilder();
        if (Modifiers == 2) {
            return sb;
        }
        sb.append("(");
        for (int i = 0; i < MethodParam.size(); i++) {

            String strParamName = FormatUtil.formatParam(MethodParam.get(i).toString());
            strParamName = GenerateUtil2.RemoveFinal(strParamName);
            strParamName = strParamName.substring(strParamName.indexOf(" ")).trim();
            int iPos = strParamName.indexOf("[");
            if (iPos > 0) {
                strParamName = strParamName.substring(0, iPos);
            }
            sb.append("c").append(strParamName).append(",");
        }

        if (MethodParam.size() > 0) {
            sb.deleteCharAt(sb.length() - 1);
        }
        sb.append(");\n");
        return sb;
    }
}
