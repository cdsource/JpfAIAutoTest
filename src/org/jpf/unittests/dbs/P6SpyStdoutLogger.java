/** 
* @author 吴平福 
* E-mail:wupf@asiainfo.com 
* @version 创建时间：2018年1月24日 下午6:01:51 
* 类说明 
*/ 

package org.jpf.unittests.dbs;

/**
 * 
 */
import com.p6spy.engine.spy.appender.StdoutLogger;

public class P6SpyStdoutLogger extends StdoutLogger {
    public void logText(String text) {
        StringBuilder sb = new StringBuilder();
        //匹配到最后一个|作为分隔符
        String[] arrString = text.split("\\|(?![^\\|]*\\|)");
        if(arrString.length > 1) {
            sb.append(arrString[0]);
            //去最后一段语句做替换进行格式化
            arrString[1] = arrString[1].replaceAll(", ", ",\r\n\t");
            arrString[1] = arrString[1].replaceAll(" values ", ",\r\nvalues\r\n\t");
            arrString[1] = arrString[1].replaceAll(" from ", "\r\nfrom\r\n\t");
            arrString[1] = arrString[1].replaceAll(" where ", "\r\nwhere\r\n\t");
            arrString[1] = arrString[1].replaceAll(" order by ", "\r\norder by\r\n\t");
            arrString[1] = arrString[1].replaceAll(" group by ", "\r\ngroup by\r\n\t");
            sb.append("\r\n");
            sb.append(arrString[1]);
            System.out.println(sb.toString());
        }
        else {
            System.out.println(text);
        }
        arrString = null;
    }
}
