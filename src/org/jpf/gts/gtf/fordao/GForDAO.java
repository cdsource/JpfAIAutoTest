/** 
* @author 吴平福 
* E-mail:wupf@asiainfo.com 
* @version 创建时间：2018年3月6日 上午10:17:43 
* 类说明 
*/ 

package org.jpf.gts.gtf.fordao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jpf.gts.gtf.AbstractGenerateTests;
import org.jpf.unittests.generateuts.JpfUtInfo;

/**
 * 
 */
public class GForDAO   extends AbstractGenerateTests{
    private static final Logger logger = LogManager.getLogger();
    /**
     * 
     */
    public GForDAO() {
        // TODO Auto-generated constructor stub
    }
    /* (non-Javadoc)
     * @see org.jpf.unittests.generateuts.GenerateTests#addExtraImport(org.jpf.unittests.generateuts.JpfUtInfo)
     */
    @Override
    protected void addExtraImport(JpfUtInfo cJpfUtInfo) {
        // TODO Auto-generated method stub
        
    }
    /* (non-Javadoc)
     * @see org.jpf.unittests.generateuts.GenerateTests#addExtraBasic(org.jpf.unittests.generateuts.JpfUtInfo)
     */
    @Override
    protected void addExtraBasic(String strClassName,JpfUtInfo cJpfUtInfo) {
        // TODO Auto-generated method stub
        
        StringBuilder sb = new StringBuilder();
        sb.append("  private static final Logger logger = Logger.getLogger(").append(strClassName)
                .append("Test.class);").append("\n").append("  @Override").append("\n")
                .append("  public Logger getLogger() {").append("\n").append("       return logger;").append("\n")
                .append("   }").append("\n").append("  public ").append(strClassName)
                .append("Test(JpfTestInfo cJpfTestInfo) {").append("\n").append("   super(cJpfTestInfo);").append("\n")
                .append("  }").append("\n");
        sb.append("        ").append(strClassName).append(" c").append(strClassName).append("= (").append(strClassName)
                .append(") ContextManager.getContext().getBean(\"")
                .append(Service2Bean.getInstance().findBeanIdFromXML(strClassName, cJpfUtInfo.getUtPackage())).append("\");\n").append("  }");

        cJpfUtInfo.setUtBasic( sb.toString());
        
        cJpfUtInfo.addImport(cJpfUtInfo.getUtPackage().replaceAll("package", "import").replaceAll(";",
                "." + strClassName + ";"));
    }
    
    @Override
    protected  String addClassDeclare(String strClass) {
        StringBuffer sb = new StringBuffer();
        sb.append("public class ").append(strClass).append("Test ");
        
        if (null != GForDAOParam.UT_ExtendClassName && GForDAOParam.UT_ExtendClassName.trim().length() > 0) {
            sb.append(" extends ").append(GForDAOParam.UT_ExtendClassName);
        }
        sb.append(" {\n");
        return sb.toString();
    }
}
