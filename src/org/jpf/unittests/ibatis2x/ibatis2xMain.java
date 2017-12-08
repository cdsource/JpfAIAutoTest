/**
 * @author 吴平福 E-mail:wupf@asiainfo.com
 * @version 创建时间：2017年12月7日 下午1:54:05 类说明
 */

package org.jpf.unittests.ibatis2x;

import java.util.Vector;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jpf.unittests.generateuts.GenerateConst;

import com.asiainfo.utils.ios.AiFileUtil;

/**
 * 
 */
public class ibatis2xMain {
    private static final Logger logger = LogManager.getLogger();

    /**
     * 
     */
    public ibatis2xMain() {
        // TODO Auto-generated constructor stub

        try {
            // ContextManager

            // find interface Manager from jar or src
            String strInputFile = "D:\\svn\\ecommerce-branch-20170912\\app-dao\\src\\main\\java\\com\\asiainfo\\ebiz";
            if (AiFileUtil.isDirectory(strInputFile)) {
                AppParam cAppParam=new AppParam();
                Vector<String> vFiles = new Vector<String>();
                AiFileUtil.getFiles(strInputFile, vFiles, ".java");
                
                cAppParam.vFilesAll  = (Vector<String>) vFiles.clone();
                
                logger.info(vFiles.size());
                GenerateConst.iTotalFileCount = vFiles.size();
                GenerateUtByInterface cGenerateUtByInterface = new GenerateUtByInterface();
                while (vFiles.size() > 0) {
                    if (vFiles.get(vFiles.size() - 1).endsWith("DAO.java")) {
                        logger.debug(vFiles.get(vFiles.size() - 1));
                        if (cGenerateUtByInterface.doGenerateFile(cAppParam,vFiles.get(vFiles.size() - 1))) {
                            GenerateConst.iGenFileCount++;
                        }
                    }
                    if (vFiles.get(vFiles.size() - 1).endsWith("Dao.java")) {
                      logger.warn("unstard file Name:"+vFiles.get(vFiles.size() - 1));
                    }
                    vFiles.remove(vFiles.size() - 1);
                }

            }

            // get method from interface

            // get method param from interface

            // add import from interface

            // create new test method

            // init param

            // 放在一个事务里面

            // insert

            // get

            // query list

            // update

            // query

            // delete

            // query
        } catch (Exception ex) {
            // TODO: handle exception
            ex.printStackTrace();
        }


    }

    /**
     * @category @author 吴平福
     * @param args update 2017年12月7日
     */

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        long start = System.currentTimeMillis();
        ibatis2xMain cibatis2xMain = new ibatis2xMain();
        logger.info("ExcuteTime " + (System.currentTimeMillis() - start) + "ms");
    }

}
