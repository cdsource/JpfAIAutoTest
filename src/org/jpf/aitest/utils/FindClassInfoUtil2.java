/** 
* @author 吴平福 
* E-mail:wupf@asiainfo.com 
* @version 创建时间：2018年3月1日 下午8:02:52 
* 类说明 
*/ 

package org.jpf.aitest.utils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jpf.aitest.GenerateInputParam;

import com.asiainfo.utils.ios.AiFileUtil;

/**
 * 
 */
public class FindClassInfoUtil2 {

    private static final Logger logger = LogManager.getLogger();


    // 已经自行实例化
    private static final FindClassInfoUtil2 Instance = new FindClassInfoUtil2();

    // 静态工厂方法
    public static FindClassInfoUtil2 getInstance() {
        return Instance;
    }


    /**
     * 
     */
    private FindClassInfoUtil2() {

    }


    private HashMap<String, String> map;

    private void init() throws Exception {
        Vector<String> vJavaFilesAll = new Vector<>();
        String[] strFindJavaPaths=GenerateInputParam.FilePath_Find_Java_Source.split(";");
        for(String strFindJavaPath: strFindJavaPaths)
        {
            AiFileUtil.getFiles(strFindJavaPath, vJavaFilesAll, ".java");
        }
        logger.info("can find java file size:" + vJavaFilesAll.size());
        map = new HashMap<>();
        for (int i = 0; i < vJavaFilesAll.size(); i++) {
            if (vJavaFilesAll.get(i).indexOf("target") == -1 || vJavaFilesAll.get(i).indexOf("classes") == -1) {
                map.put(  vJavaFilesAll.get(i),AiFileUtil.getFileName(vJavaFilesAll.get(i)));
            }
        }
        vJavaFilesAll.clear();
    }

    public String findJavaFile(String strClassName,String strMaxPath) {
        String strReturn = "";
        try {
            if (map == null) {
                init();
            }
            strClassName = strClassName + ".java";
            
            Iterator<Map.Entry<String, String>> it = map.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<String, String> entry = it.next();
                //System.out.println("key= " + entry.getKey() + " and value= " + entry.getValue());
                if (entry.getValue().trim().equalsIgnoreCase(strClassName))
                {
                    strReturn+=entry.getKey()+";";
                }
            }
            
            
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

