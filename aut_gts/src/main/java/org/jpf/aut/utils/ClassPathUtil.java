/**
 * copyrigth by wupf@aliyun.com
 * 2018年8月25日
 */
package org.jpf.aut.utils;

/**
 * @author wupf@aliyun.com
 *
 */
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.StringTokenizer;
import java.util.Vector;

public class ClassPathUtil {

	Vector _elements = new Vector();

	public ClassPathUtil() {
	}

	public ClassPathUtil(String initial) {
		addClasspath(initial);
	}

	public boolean addComponent(String component) {
		if ((component != null) && (component.length() > 0)) {
			try {
				File f = new File(component);
				if (f.exists()) {
					File key = f.getCanonicalFile();
					if (!_elements.contains(key)) {
						_elements.add(key);
						return true;
					}
				}
			} catch (IOException e) {
			}
		}
		return false;
	}

	public boolean addComponent(File component) {
		if (component != null) {
			try {
				if (component.exists()) {
					File key = component.getCanonicalFile();
					if (!_elements.contains(key)) {
						_elements.add(key);
						return true;
					}
				}
			} catch (IOException e) {
			}
		}
		return false;
	}

	public boolean addClasspath(String s) {
		boolean added = false;
		if (s != null) {
			StringTokenizer t = new StringTokenizer(s, File.pathSeparator);
			while (t.hasMoreTokens()) {
				added |= addComponent(t.nextToken());
			}
		}
		return added;
	}

	public String toString() {
		StringBuffer cp = new StringBuffer(1024);
		int cnt = _elements.size();
		if (cnt >= 1) {
			cp.append(((File) (_elements.elementAt(0))).getPath());
		}
		for (int i = 1; i < cnt; i++) {
			cp.append(File.pathSeparatorChar);
			cp.append(((File) (_elements.elementAt(i))).getPath());
		}
		return cp.toString();
	}

	public URL[] getUrls() {
		int cnt = _elements.size();
		URL[] urls = new URL[cnt];
		for (int i = 0; i < cnt; i++) {
			try {
				urls[i] = ((File) (_elements.elementAt(i))).toURL();
			} catch (MalformedURLException e) {
			}
		}
		return urls;
	}

	public ClassLoader getClassLoader() {
		URL[] urls = getUrls();

		ClassLoader parent = Thread.currentThread().getContextClassLoader();
		if (parent == null) {
			parent = ClassPathUtil.class.getClassLoader();
		}
		if (parent == null) {
			parent = ClassLoader.getSystemClassLoader();
		}
		return new URLClassLoader(urls, parent);
	}

	public static void add_class_path(String additional_class_path) {
		ClassPathUtil classPath = new ClassPathUtil(System.getProperty("java.class.path"));
		System.out.print(classPath.toString());
		classPath.addClasspath(additional_class_path);
		System.setProperty("java.class.path", classPath.toString());
		ClassLoader classloader = classPath.getClassLoader();
		Thread.currentThread().setContextClassLoader(classloader);

	}
}
