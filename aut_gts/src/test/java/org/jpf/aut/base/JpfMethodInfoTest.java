package org.jpf.aut.base;

import java.util.LinkedList;
import java.util.List;
import org.junit.*;
import static org.junit.Assert.*;

/**
 * The class <code>JpfMethodInfoTest</code> contains tests for the class <code>{@link JpfMethodInfo}</code>.
 *
 * @generatedBy wupf@ at 18-7-8 上午12:27
 * @author wupfl
 * @version $Revision: 1.0 $
 */
public class JpfMethodInfoTest {
	/**
	 * Run the JpfMethodInfo() constructor test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy wupf@ at 18-7-8 上午12:27
	 */
	@Test
	public void testJpfMethodInfo_1()
		throws Exception {

		JpfMethodInfo result = new JpfMethodInfo();

		// add additional test code here
		assertNotNull(result);
		assertEquals(0, result.getModifiers());
		assertEquals(null, result.getClassName());
		assertEquals(null, result.getMethodName());
		assertEquals(null, result.getMethodParams());
		assertEquals(null, result.getMethodParam());
		assertEquals(null, result.getStrReturn());
		assertEquals(null, result.getStrJavaDoc());
		assertEquals(null, result.getMethodExceptions());
	}

	/**
	 * Run the String getClassName() method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy wupf@ at 18-7-8 上午12:27
	 */
	@Test
	public void testGetClassName_1()
		throws Exception {
		JpfMethodInfo fixture = new JpfMethodInfo();
		fixture.setMethodExceptions(new LinkedList());
		fixture.setModifiers(1);
		fixture.setMethodParams(new LinkedList());
		fixture.setStrReturn("");
		fixture.setStrJavaDoc("");
		fixture.setClassName("");
		fixture.setMethodName("");

		String result = fixture.getClassName();

		// add additional test code here
		assertEquals("", result);
	}

	/**
	 * Run the List getMethodExceptions() method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy wupf@ at 18-7-8 上午12:27
	 */
	@Test
	public void testGetMethodExceptions_1()
		throws Exception {
		JpfMethodInfo fixture = new JpfMethodInfo();
		fixture.setMethodExceptions(new LinkedList());
		fixture.setModifiers(1);
		fixture.setMethodParams(new LinkedList());
		fixture.setStrReturn("");
		fixture.setStrJavaDoc("");
		fixture.setClassName("");
		fixture.setMethodName("");

		List result = fixture.getMethodExceptions();

		// add additional test code here
		assertNotNull(result);
		assertEquals(0, result.size());
	}

	/**
	 * Run the String getMethodName() method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy wupf@ at 18-7-8 上午12:27
	 */
	@Test
	public void testGetMethodName_1()
		throws Exception {
		JpfMethodInfo fixture = new JpfMethodInfo();
		fixture.setMethodExceptions(new LinkedList());
		fixture.setModifiers(1);
		fixture.setMethodParams(new LinkedList());
		fixture.setStrReturn("");
		fixture.setStrJavaDoc("");
		fixture.setClassName("");
		fixture.setMethodName("");

		String result = fixture.getMethodName();

		// add additional test code here
		assertEquals("", result);
	}

	/**
	 * Run the List getMethodParam() method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy wupf@ at 18-7-8 上午12:27
	 */
	@Test
	public void testGetMethodParam_1()
		throws Exception {
		JpfMethodInfo fixture = new JpfMethodInfo();
		fixture.setMethodExceptions(new LinkedList());
		fixture.setModifiers(1);
		fixture.setMethodParams(new LinkedList());
		fixture.setStrReturn("");
		fixture.setStrJavaDoc("");
		fixture.setClassName("");
		fixture.setMethodName("");

		List result = fixture.getMethodParam();

		// add additional test code here
		assertNotNull(result);
		assertEquals(0, result.size());
	}

	/**
	 * Run the List getMethodParams() method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy wupf@ at 18-7-8 上午12:27
	 */
	@Test
	public void testGetMethodParams_1()
		throws Exception {
		JpfMethodInfo fixture = new JpfMethodInfo();
		fixture.setMethodExceptions(new LinkedList());
		fixture.setModifiers(1);
		fixture.setMethodParams(new LinkedList());
		fixture.setStrReturn("");
		fixture.setStrJavaDoc("");
		fixture.setClassName("");
		fixture.setMethodName("");

		List result = fixture.getMethodParams();

		// add additional test code here
		assertNotNull(result);
		assertEquals(0, result.size());
	}

	/**
	 * Run the int getModifiers() method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy wupf@ at 18-7-8 上午12:27
	 */
	@Test
	public void testGetModifiers_1()
		throws Exception {
		JpfMethodInfo fixture = new JpfMethodInfo();
		fixture.setMethodExceptions(new LinkedList());
		fixture.setModifiers(1);
		fixture.setMethodParams(new LinkedList());
		fixture.setStrReturn("");
		fixture.setStrJavaDoc("");
		fixture.setClassName("");
		fixture.setMethodName("");

		int result = fixture.getModifiers();

		// add additional test code here
		assertEquals(1, result);
	}

	/**
	 * Run the String getStrJavaDoc() method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy wupf@ at 18-7-8 上午12:27
	 */
	@Test
	public void testGetStrJavaDoc_1()
		throws Exception {
		JpfMethodInfo fixture = new JpfMethodInfo();
		fixture.setMethodExceptions(new LinkedList());
		fixture.setModifiers(1);
		fixture.setMethodParams(new LinkedList());
		fixture.setStrReturn("");
		fixture.setStrJavaDoc("");
		fixture.setClassName("");
		fixture.setMethodName("");

		String result = fixture.getStrJavaDoc();

		// add additional test code here
		assertEquals("", result);
	}

	/**
	 * Run the String getStrReturn() method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy wupf@ at 18-7-8 上午12:27
	 */
	@Test
	public void testGetStrReturn_1()
		throws Exception {
		JpfMethodInfo fixture = new JpfMethodInfo();
		fixture.setMethodExceptions(new LinkedList());
		fixture.setModifiers(1);
		fixture.setMethodParams(new LinkedList());
		fixture.setStrReturn("");
		fixture.setStrJavaDoc("");
		fixture.setClassName("");
		fixture.setMethodName("");

		String result = fixture.getStrReturn();

		// add additional test code here
		assertEquals("", result);
	}

	/**
	 * Run the void setClassName(String) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy wupf@ at 18-7-8 上午12:27
	 */
	@Test
	public void testSetClassName_1()
		throws Exception {
		JpfMethodInfo fixture = new JpfMethodInfo();
		fixture.setMethodExceptions(new LinkedList());
		fixture.setModifiers(1);
		fixture.setMethodParams(new LinkedList());
		fixture.setStrReturn("");
		fixture.setStrJavaDoc("");
		fixture.setClassName("");
		fixture.setMethodName("");
		String className = "";

		fixture.setClassName(className);

		// add additional test code here
	}

	/**
	 * Run the void setMethodExceptions(List) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy wupf@ at 18-7-8 上午12:27
	 */
	@Test
	public void testSetMethodExceptions_1()
		throws Exception {
		JpfMethodInfo fixture = new JpfMethodInfo();
		fixture.setMethodExceptions(new LinkedList());
		fixture.setModifiers(1);
		fixture.setMethodParams(new LinkedList());
		fixture.setStrReturn("");
		fixture.setStrJavaDoc("");
		fixture.setClassName("");
		fixture.setMethodName("");
		List methodExceptions = new LinkedList();

		fixture.setMethodExceptions(methodExceptions);

		// add additional test code here
	}

	/**
	 * Run the void setMethodName(String) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy wupf@ at 18-7-8 上午12:27
	 */
	@Test
	public void testSetMethodName_1()
		throws Exception {
		JpfMethodInfo fixture = new JpfMethodInfo();
		fixture.setMethodExceptions(new LinkedList());
		fixture.setModifiers(1);
		fixture.setMethodParams(new LinkedList());
		fixture.setStrReturn("");
		fixture.setStrJavaDoc("");
		fixture.setClassName("");
		fixture.setMethodName("");
		String methodName = "";

		fixture.setMethodName(methodName);

		// add additional test code here
	}

	/**
	 * Run the void setMethodParam(List) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy wupf@ at 18-7-8 上午12:27
	 */
	@Test
	public void testSetMethodParam_1()
		throws Exception {
		JpfMethodInfo fixture = new JpfMethodInfo();
		fixture.setMethodExceptions(new LinkedList());
		fixture.setModifiers(1);
		fixture.setMethodParams(new LinkedList());
		fixture.setStrReturn("");
		fixture.setStrJavaDoc("");
		fixture.setClassName("");
		fixture.setMethodName("");
		List methodParam = new LinkedList();

		fixture.setMethodParam(methodParam);

		// add additional test code here
	}

	/**
	 * Run the void setMethodParams(List) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy wupf@ at 18-7-8 上午12:27
	 */
	@Test
	public void testSetMethodParams_1()
		throws Exception {
		JpfMethodInfo fixture = new JpfMethodInfo();
		fixture.setMethodExceptions(new LinkedList());
		fixture.setModifiers(1);
		fixture.setMethodParams(new LinkedList());
		fixture.setStrReturn("");
		fixture.setStrJavaDoc("");
		fixture.setClassName("");
		fixture.setMethodName("");
		List methodParams = new LinkedList();

		fixture.setMethodParams(methodParams);

		// add additional test code here
	}

	/**
	 * Run the void setModifiers(int) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy wupf@ at 18-7-8 上午12:27
	 */
	@Test
	public void testSetModifiers_1()
		throws Exception {
		JpfMethodInfo fixture = new JpfMethodInfo();
		fixture.setMethodExceptions(new LinkedList());
		fixture.setModifiers(1);
		fixture.setMethodParams(new LinkedList());
		fixture.setStrReturn("");
		fixture.setStrJavaDoc("");
		fixture.setClassName("");
		fixture.setMethodName("");
		int modifiers = 1;

		fixture.setModifiers(modifiers);

		// add additional test code here
	}

	/**
	 * Run the void setStrJavaDoc(String) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy wupf@ at 18-7-8 上午12:27
	 */
	@Test
	public void testSetStrJavaDoc_1()
		throws Exception {
		JpfMethodInfo fixture = new JpfMethodInfo();
		fixture.setMethodExceptions(new LinkedList());
		fixture.setModifiers(1);
		fixture.setMethodParams(new LinkedList());
		fixture.setStrReturn("");
		fixture.setStrJavaDoc("");
		fixture.setClassName("");
		fixture.setMethodName("");
		String strJavaDoc = "";

		fixture.setStrJavaDoc(strJavaDoc);

		// add additional test code here
	}

	/**
	 * Run the void setStrReturn(String) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy wupf@ at 18-7-8 上午12:27
	 */
	@Test
	public void testSetStrReturn_1()
		throws Exception {
		JpfMethodInfo fixture = new JpfMethodInfo();
		fixture.setMethodExceptions(new LinkedList());
		fixture.setModifiers(1);
		fixture.setMethodParams(new LinkedList());
		fixture.setStrReturn("");
		fixture.setStrJavaDoc("");
		fixture.setClassName("");
		fixture.setMethodName("");
		String strReturn = "";

		fixture.setStrReturn(strReturn);

		// add additional test code here
	}

	/**
	 * Perform pre-test initialization.
	 *
	 * @throws Exception
	 *         if the initialization fails for some reason
	 *
	 * @generatedBy wupf@ at 18-7-8 上午12:27
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
	 * @generatedBy wupf@ at 18-7-8 上午12:27
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
	 * @generatedBy wupf@ at 18-7-8 上午12:27
	 */
	public static void main(String[] args) {
		new org.junit.runner.JUnitCore().run(JpfMethodInfoTest.class);
	}
}