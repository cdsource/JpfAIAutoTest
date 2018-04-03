package org.jpf.gts.serializations;

import java.io.Serializable;

/**
 * 
 * @author wupf@asiainfo.com
 *　　显式地定义serialVersionUID有两种用途：
　　　　1、 在某些场合，希望类的不同版本对序列化兼容，因此需要确保类的不同版本具有相同的serialVersionUID；
　　　　2、 在某些场合，不希望类的不同版本对序列化兼容，因此需要确保类的不同版本具有不同的serialVersionUID。
 */
public class Customer implements Serializable {
    /**
     * Customer类中定义的serialVersionUID(序列化版本号)
     */
    private static final long serialVersionUID = -5182532647273106745L;
    private String name;
    private int age;

    //新添加的sex属性
    private String sex;
    
    public Customer(String name, int age) {
        this.name = name;
        this.age = age;
    }
    
    public Customer(String name, int age,String sex) {
        this.name = name;
        this.age = age;
        this.sex = sex;
    }

    /*
     * @MethodName toString
     * @Description 重写Object类的toString()方法
     * @author xudp
     * @return string
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "name=" + name + ", age=" + age;
    }
}
