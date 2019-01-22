/**
 * copyrigth by wupf@asiainfo.com
 * 2018年6月11日
 */
package org.jpf.aitest.runtest;

import org.jpf.aitest.runtest.sample.ClassA;
import org.jpf.aitest.runtest.sample.ClassB;
import org.junit.experimental.ParallelComputer;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;

/**
 * @author wupf@asiainfo.com
 *
 */
public class ParallelTest {

	/**
	 * 
	 */
	public ParallelTest() {
		// TODO Auto-generated constructor stub
	}
	
    public static void main(String[] args) {
        Class[] cls = { ClassA.class, ClassB.class };

        Result rt;

        // 并发以类为维度
        // rt = JUnitCore.runClasses(ParallelComputer.classes(), cls);

        // 并发以方法为维度
        // rt = JUnitCore.runClasses(ParallelComputer.methods(), cls);

        // 并发以类和方法为维度
        rt = JUnitCore.runClasses(new ParallelComputer(true, true), cls);

        System.out.println(rt.getRunCount() + " " + rt.getFailures()  + " " + rt.getRunTime());
    }

}
