/**
 * @author 吴平福 E-mail:wupf@aliyun.com
 * @version 创建时间：2017年12月8日 上午11:55:24 类说明
 */

package org.jpf.utils.classes;

import java.util.HashMap;
import java.util.Vector;

import org.jpf.aut.common.consts.AutConst;
import org.jpf.aut.base.GenerateInputParam;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jpf.utils.ios.JpfFileUtil;


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

    public static void main(String[] args)
    {
        FindClassInfoUtil.getInstance().findJavaFile("aa");
    }

    private final String FILE_TYPE_JAVA=".java";
    private void init() throws Exception {
        Vector<String> vJavaFilesAll = new Vector<>();
        String[] strFindJavaPaths=GenerateInputParam.FilePath_Find_Java_Source.split(";");
        for(String strFindJavaPath: strFindJavaPaths)
        {
            JpfFileUtil.getFiles(strFindJavaPath, vJavaFilesAll, FILE_TYPE_JAVA);
        }
        logger.info("can find java file size:" + vJavaFilesAll.size());
        map = new HashMap<>();
        for (int i = 0; i < vJavaFilesAll.size(); i++) {
            if (vJavaFilesAll.get(i).indexOf("target") == -1 || vJavaFilesAll.get(i).indexOf("classes") == -1) {
                int iPos=vJavaFilesAll.get(i).indexOf(AutConst.MAIN_SRC);
                String strFullClassName=vJavaFilesAll.get(i).substring(iPos+AutConst.MAIN_SRC.length()+1, vJavaFilesAll.get(i).length()-FILE_TYPE_JAVA.length());
                strFullClassName=strFullClassName.replaceAll("\\\\", ".");
                //strFullClassName=strFullClassName.replaceAll(AiTestConst.FileSep , ".");
                //logger.debug(strFullClassName);
                map.put(strFullClassName, vJavaFilesAll.get(i));
            }
        }
        vJavaFilesAll.clear();
    }

    public String findJavaFile(String strClassName) {
        String strReturn = "";
        try {
            if (map == null) {
                init();
            }
            strReturn = map.get(strClassName);

            if (null == strReturn || strReturn.trim().length() == 0) {
                logger.warn("not find java source from key:" + strClassName);
            }
        } catch (Exception ex) {
            // TODO: handle exception
            ex.printStackTrace();
        }
        return strReturn;
    }
}
