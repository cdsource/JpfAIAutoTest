package org.jpf.aut.common.consts;

import org.junit.*;
import static org.junit.Assert.*;

/**
 * The class <code>AiTestConstTest</code> contains tests for the class <code>{@link AutConst}</code>.
 *
 * @generatedBy wupf@ at 18-7-8 上午12:26
 * @author wupfl
 * @version $Revision: 1.0 $
 */
public class AiTestConstTest {
	/**
	 * Run the AiTestConst() constructor test.
	 *
	 * @generatedBy wupf@ at 18-7-8 上午12:26
	 */
	@Test
	public void testAiTestConst_1()
		throws Exception {
		AutConst result = new AutConst();
		assertNotNull(result);
		// add additional test code here
	}

	/**
	 * Run the String getJarConn() method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy wupf@ at 18-7-8 上午12:26
	 */
	@Test
	public void testGetJarConn_1()
		throws Exception {

		String result = AutConst.getJarConn();

		// add additional test code here
		assertEquals(";", result);
	}

	/**
	 * Run the String getJarConn() method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy wupf@ at 18-7-8 上午12:26
	 */
	@Test
	public void testGetJarConn_2()
		throws Exception {

		String result = AutConst.getJarConn();

		// add additional test code here
		assertEquals(";", result);
	}

	/**
	 * Perform pre-test initialization.
	 *
	 * @throws Exception
	 *         if the initialization fails for some reason
	 *
	 * @generatedBy wupf@ at 18-7-8 上午12:26
	 */
	@Before
	public void setUp()
		throws Exception {
		// add additional set up code here
	}

	/**
	 * Perform post-test clean-up.
	 *
	 * @throws Exception
	 *         if the clean-up fails for some reason
	 *
	 * @generatedBy wupf@ at 18-7-8 上午12:26
	 */
	@After
	public void tearDown()
		throws Exception {
		// Add additional tear down code here
	}

	/**
	 * Launch the test.
	 *
	 * @param args the command line arguments
	 *
	 * @generatedBy wupf@ at 18-7-8 上午12:26
	 */
	public static void main(String[] args) {
		new org.junit.runner.JUnitCore().run(AiTestConstTest.class);
	}
}
