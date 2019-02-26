/**
 * copyrigth by wupf@
 * 2018年12月20日
 */
package org.jpf.utils.classes;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * @author wupf@asiainfo.com
 *
 */
public class TestMain {

    public static void main(String[] args) {  
        try {  
                  
                //第二种  
                URL url1 = new URL("file:F:\\prj_code\\zjcmc\\adcloud-common\\permission\\target\\permission-1.0-SNAPSHOT.jar");  
                URLClassLoader myClassLoader1 = new URLClassLoader(new URL[] { url1 }, Thread.currentThread()  
                        .getContextClassLoader());  
                Class<?> myClass1 = myClassLoader1.loadClass("com.zmcc.adcloud.permission.AdPermissionEvaluator");  
                myClass1.getResourceAsStream(""); 
   			 InputStream is =myClass1.getResourceAsStream("");
		        try {
		        	if(is == null)
		        		throw new NullPointerException("Class not found "+"com.zmcc.adcloud.data.dto.UserPermission");
		        	else
		        		System.out.println(is);
		        } finally {
		        	if(is != null)
		        		is.close();
		        }

        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }

}
