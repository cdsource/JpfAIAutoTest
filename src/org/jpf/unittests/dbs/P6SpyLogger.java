/** 
* @author 吴平福 
* E-mail:wupf@asiainfo.com 
* @version 创建时间：2018年1月24日 下午6:03:59 
* 类说明 
*/ 

package org.jpf.unittests.dbs;

import com.p6spy.engine.spy.appender.MessageFormattingStrategy;
import java.text.SimpleDateFormat;
import java.util.Date;

public class P6SpyLogger implements MessageFormattingStrategy {
    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");

    public P6SpyLogger() {
    }

    @Override
    public String formatMessage(int connectionId, String now, long elapsed, String category, String prepared, String sql) {
        return !"".equals(sql.trim())?this.format.format(new Date()) + " | took " + elapsed + "ms | " + category + " | connection " + connectionId + "\n " + sql + ";":"";
    }
}
