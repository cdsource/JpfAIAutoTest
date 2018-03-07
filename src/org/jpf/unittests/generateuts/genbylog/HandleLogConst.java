/**
 * @author 吴平福 E-mail:wupf@asiainfo.com
 * @version 创建时间：2018年2月1日 下午3:00:53 类说明
 */

package org.jpf.unittests.generateuts.genbylog;

import java.util.regex.Pattern;

/**
 * 
 */
public class HandleLogConst {

    /**
     * 
     */
    public HandleLogConst() {
        // TODO Auto-generated constructor stub
    }

    public final static String KEY_SQL = "DEBUG [java.sql.PreparedStatement] - {pstm-";
    public final static String KEY_SQL_STATEMENT = "Executing Statement:";
    public final static String KEY_SQL_PARAM = "Parameters:";
    public final static Pattern p = Pattern.compile("\\s+");
    public final static String KEY_EXCLUDE_SQL=".NEXTVAL AS ID FROM DUAL";
    
    public final static String KEY_SelfTest_Method="JPFSELFTEST:";
}
