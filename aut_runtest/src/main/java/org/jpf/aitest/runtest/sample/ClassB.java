/**
 * copyrigth by wupf@asiainfo.com
 * 2018年6月11日
 */
package org.jpf.aitest.runtest.sample;

import static org.junit.Assert.assertThat;

import org.junit.Test;

/**
 * @author wupf@asiainfo.com
 *
 */
public class ClassB {

	/**
	 * 
	 */
	public ClassB() {
		// TODO Auto-generated constructor stub
	}

	@Test
    public void c() {
        org.junit.Assert.assertEquals(3, 1);
    }

    @Test
    public void d() {
        org.junit.Assert.assertEquals(3, 3);
    }
}
