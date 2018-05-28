/**
 * @author 吴平福 E-mail:wupf@asiainfo.com
 * @version 创建时间：2017年12月25日 下午4:13:46 类说明
 */

package org.jpf.unittests.utreport;

import java.util.Vector;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.asiainfo.utils.ios.AiFileUtil;
import com.asiainfo.utils.xmls.AiXmlUtil;

/**
 * 
 */
public class SurefireXmlReport {

    private static final Logger logger = LogManager.getLogger();


    /**
     * 
     */
    public SurefireXmlReport(String strUtReportPath) {
        // TODO Auto-generated constructor stub
        try {
            Vector<String> vFiles = new Vector<String>();
            StringBuilder sb = new StringBuilder();
            AiFileUtil.getFiles(strUtReportPath, vFiles, ".xml");
            for (int i = 0; i < vFiles.size(); i++) {
                handleUtReport(vFiles.get(i), sb);
            }
           AiFileUtil.writeToCsv("ut_result.csv", sb);
           
        } catch (Exception ex) {
            // TODO: handle exception
            ex.printStackTrace();
        }
    }

    public void handleUtReport(String strUtFileName, StringBuilder sb) {
        try {

            NodeList nl = AiXmlUtil.getNodeList("testcase", strUtFileName);
            logger.debug(strUtFileName);
            logger.debug(nl.getLength());
            for (int i = 0; i < nl.getLength(); i++) {
                Element ss = (Element) nl.item(i);
                logger.debug(ss.getAttribute("classname"));
                sb.append(ss.getAttribute("classname")).append("\t");

                logger.debug(ss.getAttribute("name"));
                sb.append(ss.getAttribute("name")).append("\t");

                logger.debug(ss.getAttribute("time"));
                sb.append(ss.getAttribute("time")).append("\t");

                NodeList names = ss.getElementsByTagName("error");
                if (names.getLength() > 0) {
                    Element e = (Element) names.item(0);
                    logger.debug(e.getAttribute("message"));
                    sb.append(e.getAttribute("message")).append("\t");
                    
                    logger.debug(e.getAttribute("type"));
                    sb.append(e.getAttribute("type")).append("\t");
                    
                    Node t = e.getFirstChild();
                    // logger.debug(t.getNodeValue());
                    String[] strErrMsgs = t.getNodeValue().split("\n");
                    for (int j = 0; j < strErrMsgs.length; j++) {
                        // logger.debug(strErrMsgs[j]);
                        strErrMsgs[j] = strErrMsgs[j].trim();
                        if (strErrMsgs[j].startsWith("at com.asiainfo.ebiz")) {
                            logger.debug(strErrMsgs[j]);
                            sb.append(strErrMsgs[j]).append("\t");
                            break;
                        }
                    }
                }
                sb.append("\n");
            }
        } catch (Exception ex) {
            // TODO: handle exception
            ex.printStackTrace();
        }
    }

    /**
     * @category @author 吴平福
     * @param args update 2017年12月25日
     */

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        long start = System.currentTimeMillis();
        if (args.length == 1) {
            SurefireXmlReport cSurefireXmlReport = new SurefireXmlReport(args[0]);
        } else {
            logger.warn("error input ");
        }
        logger.info ("ExcuteTime " + (System.currentTimeMillis() - start) + "ms");
    }

}
