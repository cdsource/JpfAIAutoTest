/**
 * copyrigth by wupf@
 * 2018年12月20日
 */
package org.jpf.utils.classes;

/**
 * @author wupf@asiainfo.com
 *
 */
public class JpfClassLoader  extends ClassLoader{

	/**
	 * 
	 */
	public JpfClassLoader() {
		// TODO Auto-generated constructor stub
	}

	private String newClassPath="";
	public void addNewClassPath(String newClassPath)
	{
		this.newClassPath=newClassPath;
	}
	/**
	
	 * @Title: main
	
	 * @Description: TODO
	
	 * @author:wupf@
	
	 * @param args
	
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			/*ClassLoader loader = ClassLoaderTest.class.getClassLoader();	//获得ClassLoaderTest这个类的类加载器
			while(loader != null) {
				System.out.println(loader);
				loader = loader.getParent();	//获得父加载器的引用
			}
			System.out.println(loader);*/
			
 
			String rootUrl = "F:\\prj_code\\zjcmc\\adcloud-common\\util\\target\\classes";
			JpfClassLoader cJpfClassLoader = new JpfClassLoader();
			cJpfClassLoader.addNewClassPath(rootUrl);
			String classname = getFileName("com.zmcc.adcloud.data.dto.UserPermission");
			Class clazz = cJpfClassLoader.loadClass(classname);
			System.out.println(clazz.getClassLoader());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/*
	@Override
	protected Class<?> findClass(String name) throws ClassNotFoundException {
		Class clazz = null;//this.findLoadedClass(name); // 父类已加载	
		//if (clazz == null) {	//检查该类是否已被加载过
			byte[] classData = getClassLoader(name);	//根据类的二进制名称,获得该class文件的字节码数组
			if (classData == null) {
				throw new ClassNotFoundException();
			}
			clazz = defineClass(name, classData, 0, classData.length);	//将class的字节码数组转换成Class类的实例
		//} 
		return clazz;
	}
	*/
	//获取要加载 的class文件名
    private static String getFileName(String name) {
        // TODO Auto-generated method stub
        int index = name.lastIndexOf('.');
        if(index == -1){ 
            return name+".class";
        }else{
            return name.substring(index+1)+".class";
        }
    }

}
