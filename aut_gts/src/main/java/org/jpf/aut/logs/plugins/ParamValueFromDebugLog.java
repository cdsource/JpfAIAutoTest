/**
 * @author 吴平福 E-mail:wupf@asiainfo.com
 * @version 创建时间：2018年2月1日 下午1:35:57 类说明
 */

package org.jpf.aut.logs.plugins;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jpf.utils.ios.AiFileUtil;

/**
 * 
 */
public class ParamValueFromDebugLog {

    private static final Logger logger = LogManager.getLogger();


    // 已经自行实例化
    private static final ParamValueFromDebugLog Instance = new ParamValueFromDebugLog();

    // 静态工厂方法
    public static ParamValueFromDebugLog getInstance() {
        return Instance;
    }

    private HashMap<String, LogCaseInfo> map;

    /**
     * 
     */
    private ParamValueFromDebugLog() {

    }

    public static void main(String[] args) {
        try {
            ParamValueFromDebugLog.getInstance().init();
            ParamValueFromDebugLog.getInstance().toString();
            LogCaseInfo cLogCaseInfo=ParamValueFromDebugLog.getInstance().findValueFromLog("INSERT INTO ACCOUNT_LOG ( ACCOUNT_LOG_ID, ALIPAY_ACCOUNT_ID, BALANCE, INCOME, OUTCOME, TRANS_DATE, SUB_TRANS_CODE_MSG, TRANS_CODE_MSG, MER_CHANT_OUT_ORDER_NO, TRANS_OUT_ORDER_NO, BANK_NAME, BANK_ACCOUNT_NAME, BANK_ACCOUNT_NO, MEMO, BUYER_ACCOUNT, SELLER_ACCOUNT, SELLER_FULLNAME, CURRENCY, DEPOSIT_BANK_NO, GOODS_TITLE, IW_ACCOUNT_LOG_ID, TRANS_ACCOUNT, OTHER_ACCOUNT_EMAIL, OTHER_ACCOUNT_FULLNAME, OTHER_USER_ID, PARTNER_ID, SERVICE_FEE, SERVICE_FEE_RATIO, TOTAL_FEE, TRADE_NO, TRADE_REFUND_AMOUNT, SIGN_PRODUCT_NAME, RATE ) VALUES ( ?, ?, ?, ?, ?, to_date(?,'yyyy-MM-dd hh24:mi:ss'), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )");
            logger.debug(cLogCaseInfo.getCaseTime());
            logger.debug(cLogCaseInfo.getParameters());
        } catch (Exception ex) {
            // TODO: handle exception
            ex.printStackTrace();
        }
    }
    public HashMap <String, LogCaseInfo> getAllData()throws Exception
    {

            if (map == null) {
                init();
            }
            return map;
    }
    public String toString() {
        try {
            if (map == null) {
                init();
            }
            Set<String> key = map.keySet();
            for (Iterator it = key.iterator(); it.hasNext();) {
                String s = (String) it.next();
                System.out.println(s);
                System.out.println(map.get(s));
            }
 
        } catch (Exception ex) {
            // TODO: handle exception
            logger.error(ex);
        }
        return "";
    }

    private void init() throws Exception {
        map = new HashMap<>();
        Vector<String> vXmlFiles = new Vector<>();
        AiFileUtil.getFiles(HandleLogInputParam.RUN_LOG_FILEPATH, vXmlFiles, ".log");
        for (int j = 0; j < vXmlFiles.size(); j++) {
            String strXmlFileName = vXmlFiles.get(j);
            logger.info("debug run log:"+strXmlFileName);
            BufferedReader reader = null;
            try {
                File f = new File(strXmlFileName);
                reader = new BufferedReader(new InputStreamReader(new FileInputStream(f), "UTF-8"));
                String line;
                String strSql = "";
                String strDateTime = "";
                String strValue = "";

                while ((line = reader.readLine()) != null) {
                    // datetime
                    strDateTime = line.trim();
                    if ((line = reader.readLine()) != null) {
                        strSql = line.trim();
                    }
                    if ((line = reader.readLine()) != null) {
                        // value
                        strValue = line.trim();
                    }
                    LogCaseInfo cLogCaseInfo = map.get(strSql);
                    boolean isExist = true;
                    if (cLogCaseInfo == null) {
                        cLogCaseInfo = new LogCaseInfo();
                        isExist = false;
                    }
                    cLogCaseInfo.setCaseTime(strDateTime);
                    if (!isExist) {
                        map.put(strSql, cLogCaseInfo);
                    }
                }
            } catch (Exception ex) {
                // TODO: handle exception
                ex.printStackTrace();
            } finally {
                if (null != reader) {
                    reader.close();
                }
            }
        }
        logger.info("service to bean size:" + map.size());
    }

    public LogCaseInfo findValueFromLog(String strSql) {
        LogCaseInfo cLogCaseInfo=null;
        try {

            if (map == null) {
                init();
            }
            cLogCaseInfo=map.get(strSql);
            
            if (null == cLogCaseInfo) {
                throw new Exception("not find value from log:" + strSql);
            }
        } catch (Exception ex) {
            // TODO: handle exception
            ex.printStackTrace();
        }
        return cLogCaseInfo;
    }

}
