/**
 * copyrigth by wupf@asiainfo.com
 * 2018年8月10日
 */
package com.asiainfo.aitest.procgeneratecase.common;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * @author wupf@asiainfo.com
 *
 */
public class SPTestSuiteProperties {

    private SPTestSuiteProperties()
    {
    }

    public static String format(String key, Object args[])
    {
        return MessageFormat.format(getString(key), args);
    }

    public static String getString(String key)
    {
        try
        {
            return fgResourceBundle.getString(key);
        }
        catch(MissingResourceException e)
        {
            return "!" + key + "!";
        }
    }

    private static final String RESOURCE_BUNDLE = "com.ibm.testCaseGenerator.SPTestSuite";
    static Locale locale;
    private static ResourceBundle fgResourceBundle;

    static 
    {
        locale = Locale.getDefault();
        fgResourceBundle = ResourceBundle.getBundle("com.ibm.testCaseGenerator.SPTestSuite", locale);
    }


}
