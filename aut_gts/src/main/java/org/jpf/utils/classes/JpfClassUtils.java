/**
 * copyrigth by wupf@aliyun.com
 * 2018年8月22日
 */
package org.jpf.utils.classes;

import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jpf.utils.ios.AiOsUtil;
import org.jpf.utils.ios.WuFileUtil;


/**
 * @author wupf@aliyun.com
 *
 */
public class JpfClassUtils {
    private static final Logger logger = LogManager.getLogger();
	/**
	 * 
	 */
	public JpfClassUtils() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 * @author wupf@aliyun.com
	 * @param pkgName 2018年7月10日
	 */
	public static void getMethodInfo(String pkgName) {
		StringBuffer sb = new StringBuffer();
		try {
			Class clazz = Class.forName(pkgName);
			Method[] methods = clazz.getMethods();
			for (Method method : methods) {
				String methodName = method.getName();
				System.out.println("方法名称:" + methodName);

				Class<?>[] parameterTypes = method.getParameterTypes();
				for (Class<?> clas : parameterTypes) {
					String parameterName = clas.getName();
					System.out.println("参数名称:" + parameterName);
				}
				System.out.println("*****************************");
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 
	 * @author wupf@aliyun.com
	 * @param strFullClassName
	 * @return
	 * 2018年8月22日
	 */
	public static String ClassNameToPath(String strFullClassName)
	{
		if (AiOsUtil.getInstance().isWindows())
		{	
			return strFullClassName.replaceAll("\\.", "\\\\");
		}else
		{
			return strFullClassName.replaceAll("\\.", "/");
		}
	}
	
	public void doWork()
	{
		logger.info( getClass().getClassLoader().toString());
		logger.info(System.getProperty("java.class.path"));
		logger.info(System.getProperty("java.ext.dirs"));
		try {
			// MyWebAppLoader a = new
			// MyWebAppLoader("E:\\prj_code\\zjcmc\\adcloud-common\\util\\target\\classes");
			// logger.info(a.findClass("com.zmcc.adcloud.data.dto.UserPermission"));
			String strAddPath="F:\\prj_code\\zjcmc\\adcloud-common\\util\\target\\classes";
			if (!WuFileUtil.FileExist(strAddPath))
			{
				logger.warn("File not exist: "+strAddPath);
				return ;
			}
			File programRootDir = new File(strAddPath);
			URLClassLoader classLoader = (URLClassLoader) ClassLoader.getSystemClassLoader();
			Method add = URLClassLoader.class.getDeclaredMethod("addURL", new Class[] { URL.class });
			add.setAccessible(true);
			add.invoke(classLoader, programRootDir.toURI().toURL());
			
	        //InputStream is = classLoader.getResourceAsStream("com.zmcc.adcloud.data.dto.UserPermission" + ".class");

			Class c = Class.forName("com.zmcc.adcloud.data.dto.UserPermission");
			 InputStream is =c.getResourceAsStream("");
		        try {
		        	if(is == null)
		        		throw new NullPointerException("Class not found "+"com.zmcc.adcloud.data.dto.UserPermission");
		        	else
		        		logger.info(is);
		        } finally {
		        	if(is != null)
		        		is.close();
		        }
					 logger.info(c.getName());
			Method[] cms = c.getDeclaredMethods();
			for (Method cm : cms) {
				logger.info(cm.getName());
			}
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//logger.info(System.getProperty("java.class.path"));
		JpfClassUtils cJpfClassUtils=new JpfClassUtils();
		cJpfClassUtils.doWork();
	}
}
