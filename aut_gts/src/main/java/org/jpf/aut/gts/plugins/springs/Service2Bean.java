/**
 * @author 吴平福 E-mail:wupf@asiainfo.com
 * @version 创建时间：2018年1月31日 下午6:53:35 
 * @类说明 北京移动电商SPRING
 */
package org.jpf.aut.gts.plugins.springs;


import java.util.HashMap;
import java.util.Vector;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import org.jpf.utils.ios.AiFileUtil;
import org.jpf.utils.xmls.JpfXmlUtil;

/**
 * 
 */
public class Service2Bean {
    private static final Logger logger = LogManager.getLogger();


    // 已经自行实例化
    private static final Service2Bean Instance = new Service2Bean();

    // 静态工厂方法
    public static Service2Bean getInstance() {
        return Instance;
    }

    private HashMap<String, String> map;

    /**
     * 
     */
    private Service2Bean() {

    }

    private void init() throws Exception {
        map = new HashMap<>();
        Vector<String> vXmlFiles = new Vector<>();
        AiFileUtil.getFiles(SpringsInputParam.SPRING_XML_FILEPATH, vXmlFiles, ".xml");
        for (int j = 0; j < vXmlFiles.size(); j++) {
            String strXmlFileName = vXmlFiles.get(j);
            NodeList cNodeList = JpfXmlUtil.getNodeList("bean", strXmlFileName);
            for (int i = 0; i < cNodeList.getLength(); i++) {
                Element el = (Element) cNodeList.item(i);
                map.put(el.getAttribute("class"), el.getAttribute("id"));
            }
        }
        logger.info("service to bean size:" + map.size());
    }

    /**
     * 
     * @category 
     * @author 吴平福 
     * @param strClassName
     * @param strPackageName
     * @return
     * update 2018年3月6日
     */
    public String findBeanIdFromXML(String strClassName, String strPackageName) {
        String strReturn = "";
        try {

            if (map == null) {
                init();
            }
            String strImplClassName =
                    strPackageName.replaceAll("package", "").replaceAll("import", "").replaceAll(";", "").trim()
                            + ".impl." + strClassName + "Impl";
            logger.info("strImplClassName:" + strImplClassName);

            strReturn = map.get(strImplClassName);

            if (null == strReturn || strReturn.trim().length() == 0) {
                throw new Exception("not find beanId from xml:" + strImplClassName);
            }
        } catch (Exception ex) {
            // TODO: handle exception
            ex.printStackTrace();
        }
        return strReturn;
    }
}

