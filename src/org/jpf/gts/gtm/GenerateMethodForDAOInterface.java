/**
 * @author 吴平福 E-mail:wupf@asiainfo.com
 * @version 创建时间：2017年12月9日 上午10:51:48 类说明
 */

package org.jpf.gts.gtm;

import java.util.List;

import org.jpf.unittests.generateuts.JpfUtInfo;
import org.jpf.unittests.plugins.spring.Service2Bean;


/**
 * 
 */
public class GenerateMethodForDAOInterface extends AbstractGenerateMethod {

    /**
     * 
     */
    public GenerateMethodForDAOInterface() {
        // TODO Auto-generated constructor stub
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.jpf.unittests.generateuts.GenerateMethod#addMethodCaller(java.lang.String,
     * java.lang.String, java.util.List, org.jpf.unittests.generateuts.JpfUtInfo)
     */
    @Override
    public String addMethodCaller(String strClassName, String strMethod, List MethodParam, JpfUtInfo cJpfUtInfo) {
        // TODO Auto-generated method stub
        return "    c" + strClassName + "." + strMethod;

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.jpf.unittests.generateuts.GenerateMethod#addClassInstance(java.lang.String,
     * java.util.List, org.jpf.unittests.generateuts.JpfUtInfo)
     */
    @Override
    public String addClassInstance(String strClassName, List MethodParam, JpfUtInfo cJpfUtInfo) {
        // TODO Auto-generated method stub
        // cJpfUtInfo.addImport("import org.springframework.dao.DataAccessException;\n");
        //cJpfUtInfo.removeImport("import org.junit.*;");
        //cJpfUtInfo.removeImport("import static org.junit.Assert.*;");
        cJpfUtInfo.addImport("import org.apache.log4j.Logger;");
        cJpfUtInfo.addImport("import com.asiainfo.ebiz.exception.EbizException;");
        cJpfUtInfo.addImport("import java.sql.SQLException;");
        cJpfUtInfo.addImport("import com.asiainfo.ebiz.util.ContextManager;");
        cJpfUtInfo.addImport(" import com.asiainfo.ebiz.selftest.JpfTestInfo;");
        cJpfUtInfo.addImport(" import com.asiainfo.ebiz.selftest.abstractJpfSelfTest;");
        return "";
    }

    @Override
    public String addCatchException(List MethodExceptions) {
        StringBuffer sb = new StringBuffer();
        if (null != MethodExceptions && MethodExceptions.size()>=1) {
            sb.append(AddException());
        }
        sb.append("        } catch (Exception ex) {").append("\n").append("          ex.printStackTrace();")
                .append("\n").append("          logger.error(ex);").append("\n")
                .append("          cSelfJpfTestInfo.addCountException(1);").append("\n").append("        }")
                .append("\n");
        return sb.toString();
    }

 

    /**
     * 
     * @category @author 吴平福
     * @param strReturn
     * @param sb update 2017年9月29日
     */
    @Override
    public String addMethodAssert(String strClassName, String strMethod, String strReturn, JpfUtInfo cJpfUtInfo) {
        StringBuffer sb = new StringBuffer();
        sb.append("            cSelfJpfTestInfo.addCountSuccess(1);").append("\n");

        return sb.toString();
    }

    /* (non-Javadoc)
     * @see org.jpf.unittests.generateuts.GenerateMethod#addExtraMethod(java.lang.String, org.jpf.unittests.generateuts.JpfUtInfo)
     */
    @Override
    public String addExtraMethod(String strClassName, String strPackageName) {
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
                .append(Service2Bean.getInstance().findBeanIdFromXML(strClassName, strPackageName)).append("\");\n").append("  }");

        return sb.toString();
    }
    
    public StringBuffer AddException()
    {
        StringBuffer sb = new StringBuffer();
        sb.append("        } catch (EbizException ex) { ").append("\n")
                .append("          ex.printStackTrace();").append("\n")
                .append("          logger.error(ex);").append("\n")
                .append("          cSelfJpfTestInfo.addCountEbizException(1);").append("\n");
        /*
                .append("        } catch (SQLException ex) {").append("\n")
                .append("          ex.printStackTrace();")                .append("\n")
                .append("          logger.error(ex);").append("\n")
                .append("          cSelfJpfTestInfo.addCountSQLException(1);").append("\n");
        */
        return sb;
    }
}
