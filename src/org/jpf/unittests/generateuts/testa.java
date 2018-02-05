/**
 * @author 吴平福 E-mail:wupf@asiainfo.com
 * @version 创建时间：2018年1月30日 下午4:12:02 类说明
 */

package org.jpf.unittests.generateuts;

import java.lang.reflect.Method;

/**
 * 
 */
public class testa {

    /**
     * 
     */
    public testa() {
        // TODO Auto-generated constructor stub
    }

    /**
     * @category @author 吴平福
     * @param args update 2018年1月30日
     */

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        testa ctesta = new testa();
        ctesta.doSelfTests();
    }

    public void addTest_1() {
        System.out.println("add");

    }

    protected void doSelfTests() {
        // TODO Auto-generated method stub
        // 获取全部公共方法的方法(含父类)
        try {
            Method[] method = this.getClass().getDeclaredMethods();
            for (Method m : method) {
                System.out.println("获取全部的方法:" + m.toString());
                if (m.toString().indexOf("Test_") > 0) {
                    System.out.println("invoke method:" + m.toString());
                   
                   m.invoke(this);
                }
            }
        } catch (Exception ex) {
            // TODO: handle exception
            ex.printStackTrace();
        }

        // insertAccessLogTest_1();
    }
}
