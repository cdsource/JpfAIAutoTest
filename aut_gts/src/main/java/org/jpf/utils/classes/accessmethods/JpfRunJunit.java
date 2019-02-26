/**
 * copyrigth by wupf@aliyun.com
 * 2018年8月25日
 */
package org.jpf.utils.classes.accessmethods;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * @author wupf@aliyun.com
 *
 */
public class JpfRunJunit {

	/**
	 * 
	 */
	public JpfRunJunit() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @author wupf@aliyun.com
	 * @param args 2018年8月25日
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	/**
	 * @category 加载其他CLASSPATH
	 * @author wupf@aliyun.com
	 * @throws Exception
	 * 2018年8月25日
	 */
	public void setUp() throws Exception {
		File programRootDir = new File("./");
		URLClassLoader classLoader = (URLClassLoader) ClassLoader.getSystemClassLoader();
		Method add = URLClassLoader.class.getDeclaredMethod("addURL", new Class[] { URL.class });
		add.setAccessible(true);
		add.invoke(classLoader, programRootDir.toURI().toURL());
	}
}
