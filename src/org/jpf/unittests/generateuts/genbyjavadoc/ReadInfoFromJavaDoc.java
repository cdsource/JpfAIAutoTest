/** 
* @author 吴平福 
* E-mail:wupf@asiainfo.com 
* @version 创建时间：2017年11月17日 下午12:00:55 
* 类说明 
*/ 

package org.jpf.unittests.generateuts.genbyjavadoc;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jpf.unittests.generateuts.JpfMethodInfo;
import org.jpf.unittests.generateuts.JpfUtMethodInfo;
import org.jpf.unittests.generateuts.ParamInitBody;
import org.jpf.unittests.generateuts.utils.FormatUtil;


/**
 * 
 */
public class ReadInfoFromJavaDoc {
    private static final Logger logger = LogManager.getLogger();
    
    /**
     * 
     */
    private ReadInfoFromJavaDoc() {
        
    }
    // 已经自行实例化
    private static final ReadInfoFromJavaDoc Instance = new ReadInfoFromJavaDoc();

    // 静态工厂方法
    public static ReadInfoFromJavaDoc getInstance() {
        return Instance;
    }
    /**
     * 
     * @category 根据声明产生初始化值
     * @author 吴平福
     * @param cMethodInfo
     * @param cJpfUtInfo
     * @return update 2018年1月23日
     */
    public void initParamByJavaDoc(JpfMethodInfo cMethodInfo, JpfUtMethodInfo cJpfUtMethodInfo) {
        StringBuffer sb = new StringBuffer();
        String[] strJavaDocs = cMethodInfo.getStrJavaDoc().split("\n");
        int iFindCount = 0;

        for (int i = 0; i < cMethodInfo.getMethodParam().size(); i++) {
            for (int j = 0; j < strJavaDocs.length; j++) {

                ParamInitBody cParamInitBody = new ParamInitBody(cMethodInfo.getMethodParam().get(i).toString());

                if (strJavaDocs[j].trim().startsWith("@ " + cParamInitBody.getParamVariable())) {
                    sb.append("    " + cParamInitBody.getParamType() + " " + cParamInitBody.getParamVariable()
                            + " =  new boolean[]{true,true};\n");
                    iFindCount++;
                }
            }
        }
        logger.debug(sb);
        if (iFindCount == cMethodInfo.getMethodParam().size()) {
            cJpfUtMethodInfo.setMethodParam(sb.toString());
        }

        for (int j = 0; j < strJavaDocs.length; j++) {

            if (strJavaDocs[j].trim().startsWith("@ return")) {
                cJpfUtMethodInfo.setMethodAssert("    assert()");
            }
        }
    }
}
