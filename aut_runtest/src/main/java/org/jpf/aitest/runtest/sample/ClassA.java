/**
 * copyrigth by wupf@asiainfo.com
 * 2018年6月11日
 */
package org.jpf.aitest.runtest.sample;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

import org.junit.Test;

/**
 * @author wupf@asiainfo.com
 *
 */
public class ClassA {

	/**
	 * 
	 */
	public ClassA() {
		// TODO Auto-generated constructor stub
	}

	@Test
    public void a() {
        assertThat(3, is(1));
    }

    @Test
    public void b() {
        assertThat(3, not(1));
    }
}
