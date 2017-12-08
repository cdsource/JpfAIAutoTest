/** 
* @author 吴平福 
* E-mail:wupf@asiainfo.com 
* @version 创建时间：2017年12月9日 上午10:51:48 
* 类说明 
*/ 

package org.jpf.unittests.generateuts;

import java.util.List;

/**
 * 
 */
public class GenerateMethodForDAOInterface  extends GenerateMethod{

    /**
     * 
     */
    public GenerateMethodForDAOInterface() {
        // TODO Auto-generated constructor stub
    }

    /* (non-Javadoc)
     * @see org.jpf.unittests.generateuts.GenerateMethod#addMethodCaller(java.lang.String, java.lang.String, java.util.List, org.jpf.unittests.generateuts.JpfUtInfo)
     */
    @Override
    public String addMethodCaller(String strClassName, String strMethod, List MethodParam, JpfUtInfo cJpfUtInfo) {
        // TODO Auto-generated method stub
        return "    c"+strClassName+"."+strMethod;
        
    }

    /* (non-Javadoc)
     * @see org.jpf.unittests.generateuts.GenerateMethod#addClassInstance(java.lang.String, java.util.List, org.jpf.unittests.generateuts.JpfUtInfo)
     */
    @Override
    public String addClassInstance(String strClassName, List MethodParam, JpfUtInfo cJpfUtInfo) {
        // TODO Auto-generated method stub
        cJpfUtInfo.addImport("import org.springframework.dao.DataAccessException;\n");
        
        return "";
    }
    
    @Override
    public String addCatch(String strClassName) {
        StringBuffer sb = new StringBuffer();
        sb.append("    }catch(DataAccessException ex){\n").append("        ex.printStackTrace();\n")
        .append("        fail(\"").append(strClassName).append(" error! ").append("\");\n")
        .append("      }\n");
        return sb.toString();
    }

    /* (non-Javadoc)
     * @see org.jpf.unittests.generateuts.GenerateMethod#addExtraMethod(org.jpf.unittests.generateuts.JpfUtInfo)
     */
    @Override
    public void addExtraMethod(String strClassName, JpfUtInfo cJpfUtInfo) {
        // TODO Auto-generated method stub
        JpfUtMethodInfo cJpfUtMethodInfo=new JpfUtMethodInfo();
        cJpfUtMethodInfo.setMethodJavaDoc("    //共用\n");
        cJpfUtMethodInfo.setMethodEnd("    "+strClassName+" c"+strClassName+";");
        cJpfUtInfo.getListUtMethodInfos().add(cJpfUtMethodInfo);
    }
}
