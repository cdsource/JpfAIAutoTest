/**
 * 
 */
package org.jpf.aut.logs.plugins;

import java.util.regex.Pattern;

/**
 * @author Administrator
 *
 */
public class HandleLogInputParam {

    //原始DEBUG日志
    public static String RUN_LOG_SOURCE_FILEPATH="D:\\abc\\debug_source";

    public final static String KEY_SQL = "DEBUG [java.sql.PreparedStatement] - {pstm-";
    public final static String KEY_SQL_STATEMENT = "Executing Statement:";
    public final static String KEY_SQL_PARAM = "Parameters:";
    public final static String KEY_PARAM_TYPES = "Types:";
    public final static Pattern p = Pattern.compile("\\s+");
    public final static String KEY_EXCLUDE_SQL=".NEXTVAL AS ID FROM DUAL";
    
    public final static String KEY_SelfTest_Method="JPFSELFTEST:";
    
    //根据日志查找参数
    public static String RUN_LOG_FILEPATH="D:\\abc\\debug_source";
    
    public static String SELF_LOG_FILEPATH="D:\\abc\\selftest";
}
