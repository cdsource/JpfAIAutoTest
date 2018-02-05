/** 
* @author 吴平福 
* E-mail:wupf@asiainfo.com 
* @version 创建时间：2017年12月8日 上午11:55:24 
* 类说明 
*/ 

package org.jpf.unittests.generateuts.utils;

import java.util.HashMap;
import java.util.Vector;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jpf.unittests.generateuts.GenerateInputParam;
import com.asiainfo.utils.ios.AiFileUtil;


/**
 * 
 */
public class FindClassInfoUtil {

    private static final Logger logger = LogManager.getLogger();


    // 已经自行实例化
    private static final FindClassInfoUtil Instance = new FindClassInfoUtil();

    // 静态工厂方法
    public static FindClassInfoUtil getInstance() {
        return Instance;
    }


    /**
     * 
     */
    private FindClassInfoUtil() {

    }


    private HashMap<String, String> map;

    private void init() throws Exception {
        Vector<String>  vJavaFilesAll = new Vector<>();
        AiFileUtil.getFiles(GenerateInputParam.FilePath_Find_Java_Source , vJavaFilesAll, ".java");
        logger.info("can find java file size:" + vJavaFilesAll.size());
        map = new HashMap<>();
        for(int i=0;i<vJavaFilesAll.size();i++)
        {
            map.put("\\"+AiFileUtil.getFileName(vJavaFilesAll.get(i)), vJavaFilesAll.get(i));
        }
        vJavaFilesAll.clear();
    }

    public String findJavaFile(String strClassName) {
        String strReturn = "";
        try {
            if (map == null) {
                init();
            }
            strClassName="\\"+strClassName+".java";
            strReturn = map.get(strClassName);

            if (null == strReturn || strReturn.trim().length() == 0) {
                logger.warn("not find java source :" + strClassName);
            }
        } catch (Exception ex) {
            // TODO: handle exception
            ex.printStackTrace();
        }
        return strReturn;
    }
}
