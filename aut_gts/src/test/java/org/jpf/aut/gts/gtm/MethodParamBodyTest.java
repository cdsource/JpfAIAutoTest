package org.jpf.aut.gts.gtm;


import org.junit.*;
import static org.junit.Assert.*;


public class MethodParamBodyTest {
	/**
	 * Run the MethodParamBody(String) constructor test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy  at 18-6-2 上午9:28
	 */
	@Test
	public void testMethodParamBody_1()
		throws Exception {
		String strParamName = "@@RequestParam Long packageId,@RequestParam(required=false) Integer region";

		MethodParamBody result = new MethodParamBody(strParamName);

		// add additional test code here
		// An unexpected exception was thrown in user code while executing this test:
		//    java.lang.StringIndexOutOfBoundsException: String index out of range: -1
		//       at java.lang.String.substring(String.java:1967)
		//       at org.jpf.aitest.gts.gtm.MethodParamBody.<init>(MethodParamBody.java:43)
		assertNotNull(result);
	}

	/**
	 * Run the MethodParamBody(String) constructor test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 18-6-2 上午9:28
	 */
	@Test
	public void testMethodParamBody_2()
		throws Exception {
		String strParamName = "@RequestParam(required=false) Integer region";

		MethodParamBody result = new MethodParamBody(strParamName);

		assertNotNull(result);
		System.out.println(result.getParamRealType());
		System.out.println(result.getParamType());
		System.out.println(result.getParamVariable());
		assertEquals(result.getParamRealType() , "Integer");
		assertEquals(result.getParamType() , "Integer");
		assertEquals(result.getParamVariable() , "region");
	}

	/**
	 * Run the MethodParamBody(String) constructor test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 18-6-2 上午9:28
	 */
	@Test
	public void testMethodParamBody_3()
		throws Exception {
		String strParamName = "";

		MethodParamBody result = new MethodParamBody(strParamName);

		// add additional test code here
		// An unexpected exception was thrown in user code while executing this test:
		//    java.lang.StringIndexOutOfBoundsException: String index out of range: -1
		//       at java.lang.String.substring(String.java:1967)
		//       at org.jpf.aitest.gts.gtm.MethodParamBody.<init>(MethodParamBody.java:43)
		assertNotNull(result);
	}

	/**
	 * Run the MethodParamBody(String) constructor test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 18-6-2 上午9:28
	 */
	@Test
	public void testMethodParamBody_4()
		throws Exception {
		String strParamName = "";

		MethodParamBody result = new MethodParamBody(strParamName);

		// add additional test code here
		// An unexpected exception was thrown in user code while executing this test:
		//    java.lang.StringIndexOutOfBoundsException: String index out of range: -1
		//       at java.lang.String.substring(String.java:1967)
		//       at org.jpf.aitest.gts.gtm.MethodParamBody.<init>(MethodParamBody.java:43)
		assertNotNull(result);
	}

	/**
	 * Run the MethodParamBody(String) constructor test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 18-6-2 上午9:28
	 */
	@Test
	public void testMethodParamBody_5()
		throws Exception {
		String strParamName = "";

		MethodParamBody result = new MethodParamBody(strParamName);

		// add additional test code here
		// An unexpected exception was thrown in user code while executing this test:
		//    java.lang.StringIndexOutOfBoundsException: String index out of range: -1
		//       at java.lang.String.substring(String.java:1967)
		//       at org.jpf.aitest.gts.gtm.MethodParamBody.<init>(MethodParamBody.java:43)
		assertNotNull(result);
	}

	/**
	 * Run the MethodParamBody(String) constructor test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 18-6-2 上午9:28
	 */
	@Test
	public void testMethodParamBody_6()
		throws Exception {
		String strParamName = "";

		MethodParamBody result = new MethodParamBody(strParamName);

		// add additional test code here
		// An unexpected exception was thrown in user code while executing this test:
		//    java.lang.StringIndexOutOfBoundsException: String index out of range: -1
		//       at java.lang.String.substring(String.java:1967)
		//       at org.jpf.aitest.gts.gtm.MethodParamBody.<init>(MethodParamBody.java:43)
		assertNotNull(result);
	}

	/**
	 * Run the MethodParamBody(String) constructor test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 18-6-2 上午9:28
	 */
	@Test
	public void testMethodParamBody_7()
		throws Exception {
		String strParamName = "";

		MethodParamBody result = new MethodParamBody(strParamName);

		// add additional test code here
		// An unexpected exception was thrown in user code while executing this test:
		//    java.lang.StringIndexOutOfBoundsException: String index out of range: -1
		//       at java.lang.String.substring(String.java:1967)
		//       at org.jpf.aitest.gts.gtm.MethodParamBody.<init>(MethodParamBody.java:43)
		assertNotNull(result);
	}

	/**
	 * Run the MethodParamBody(String) constructor test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 18-6-2 上午9:28
	 */
	@Test
	public void testMethodParamBody_8()
		throws Exception {
		String strParamName = "";

		MethodParamBody result = new MethodParamBody(strParamName);

		// add additional test code here
		// An unexpected exception was thrown in user code while executing this test:
		//    java.lang.StringIndexOutOfBoundsException: String index out of range: -1
		//       at java.lang.String.substring(String.java:1967)
		//       at org.jpf.aitest.gts.gtm.MethodParamBody.<init>(MethodParamBody.java:43)
		assertNotNull(result);
	}

	/**
	 * Run the String getCurrentPackage() method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 18-6-2 上午9:28
	 */
	@Test
	public void testGetCurrentPackage_1()
		throws Exception {
		MethodParamBody fixture = new MethodParamBody("");
		fixture.setParamMoidfy("");
		fixture.setCurrentPackage("");
		fixture.setParamRealType("");
		fixture.setParamPreInit("");
		fixture.setParamHideType("");
		fixture.setArray(true);

		String result = fixture.getCurrentPackage();

		// add additional test code here
		// An unexpected exception was thrown in user code while executing this test:
		//    java.lang.StringIndexOutOfBoundsException: String index out of range: -1
		//       at java.lang.String.substring(String.java:1967)
		//       at org.jpf.aitest.gts.gtm.MethodParamBody.<init>(MethodParamBody.java:43)
		assertNotNull(result);
	}

	/**
	 * Run the String getParamHideType() method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 18-6-2 上午9:28
	 */
	@Test
	public void testGetParamHideType_1()
		throws Exception {
		MethodParamBody fixture = new MethodParamBody("");
		fixture.setParamMoidfy("");
		fixture.setCurrentPackage("");
		fixture.setParamRealType("");
		fixture.setParamPreInit("");
		fixture.setParamHideType("");
		fixture.setArray(true);

		String result = fixture.getParamHideType();

		// add additional test code here
		// An unexpected exception was thrown in user code while executing this test:
		//    java.lang.StringIndexOutOfBoundsException: String index out of range: -1
		//       at java.lang.String.substring(String.java:1967)
		//       at org.jpf.aitest.gts.gtm.MethodParamBody.<init>(MethodParamBody.java:43)
		assertNotNull(result);
	}

	/**
	 * Run the String getParamMoidfy() method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 18-6-2 上午9:28
	 */
	@Test
	public void testGetParamMoidfy_1()
		throws Exception {
		MethodParamBody fixture = new MethodParamBody("");
		fixture.setParamMoidfy("");
		fixture.setCurrentPackage("");
		fixture.setParamRealType("");
		fixture.setParamPreInit("");
		fixture.setParamHideType("");
		fixture.setArray(true);

		String result = fixture.getParamMoidfy();

		// add additional test code here
		// An unexpected exception was thrown in user code while executing this test:
		//    java.lang.StringIndexOutOfBoundsException: String index out of range: -1
		//       at java.lang.String.substring(String.java:1967)
		//       at org.jpf.aitest.gts.gtm.MethodParamBody.<init>(MethodParamBody.java:43)
		assertNotNull(result);
	}

	/**
	 * Run the String getParamPreInit() method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 18-6-2 上午9:28
	 */
	@Test
	public void testGetParamPreInit_1()
		throws Exception {
		MethodParamBody fixture = new MethodParamBody("");
		fixture.setParamMoidfy("");
		fixture.setCurrentPackage("");
		fixture.setParamRealType("");
		fixture.setParamPreInit("");
		fixture.setParamHideType("");
		fixture.setArray(true);

		String result = fixture.getParamPreInit();

		// add additional test code here
		// An unexpected exception was thrown in user code while executing this test:
		//    java.lang.StringIndexOutOfBoundsException: String index out of range: -1
		//       at java.lang.String.substring(String.java:1967)
		//       at org.jpf.aitest.gts.gtm.MethodParamBody.<init>(MethodParamBody.java:43)
		assertNotNull(result);
	}

	/**
	 * Run the String getParamRealType() method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 18-6-2 上午9:28
	 */
	@Test
	public void testGetParamRealType_1()
		throws Exception {
		MethodParamBody fixture = new MethodParamBody("");
		fixture.setParamMoidfy("");
		fixture.setCurrentPackage("");
		fixture.setParamRealType("");
		fixture.setParamPreInit("");
		fixture.setParamHideType("");
		fixture.setArray(true);

		String result = fixture.getParamRealType();

		// add additional test code here
		// An unexpected exception was thrown in user code while executing this test:
		//    java.lang.StringIndexOutOfBoundsException: String index out of range: -1
		//       at java.lang.String.substring(String.java:1967)
		//       at org.jpf.aitest.gts.gtm.MethodParamBody.<init>(MethodParamBody.java:43)
		assertNotNull(result);
	}

	/**
	 * Run the String getParamType() method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 18-6-2 上午9:28
	 */
	@Test
	public void testGetParamType_1()
		throws Exception {
		MethodParamBody fixture = new MethodParamBody("");
		fixture.setParamMoidfy("");
		fixture.setCurrentPackage("");
		fixture.setParamRealType("");
		fixture.setParamPreInit("");
		fixture.setParamHideType("");
		fixture.setArray(true);

		String result = fixture.getParamType();

		// add additional test code here
		// An unexpected exception was thrown in user code while executing this test:
		//    java.lang.StringIndexOutOfBoundsException: String index out of range: -1
		//       at java.lang.String.substring(String.java:1967)
		//       at org.jpf.aitest.gts.gtm.MethodParamBody.<init>(MethodParamBody.java:43)
		assertNotNull(result);
	}

	/**
	 * Run the String getParamValue() method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 18-6-2 上午9:28
	 */
	@Test
	public void testGetParamValue_1()
		throws Exception {
		MethodParamBody fixture = new MethodParamBody("");
		fixture.setParamMoidfy("");
		fixture.setCurrentPackage("");
		fixture.setParamRealType("");
		fixture.setParamPreInit("");
		fixture.setParamHideType("");
		fixture.setArray(true);

		String result = fixture.getParamValue();

		// add additional test code here
		// An unexpected exception was thrown in user code while executing this test:
		//    java.lang.StringIndexOutOfBoundsException: String index out of range: -1
		//       at java.lang.String.substring(String.java:1967)
		//       at org.jpf.aitest.gts.gtm.MethodParamBody.<init>(MethodParamBody.java:43)
		assertNotNull(result);
	}

	/**
	 * Run the String getParamVariable() method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 18-6-2 上午9:28
	 */
	@Test
	public void testGetParamVariable_1()
		throws Exception {
		MethodParamBody fixture = new MethodParamBody("");
		fixture.setParamMoidfy("");
		fixture.setCurrentPackage("");
		fixture.setParamRealType("");
		fixture.setParamPreInit("");
		fixture.setParamHideType("");
		fixture.setArray(true);

		String result = fixture.getParamVariable();

		// add additional test code here
		// An unexpected exception was thrown in user code while executing this test:
		//    java.lang.StringIndexOutOfBoundsException: String index out of range: -1
		//       at java.lang.String.substring(String.java:1967)
		//       at org.jpf.aitest.gts.gtm.MethodParamBody.<init>(MethodParamBody.java:43)
		assertNotNull(result);
	}

	/**
	 * Run the boolean isArray() method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 18-6-2 上午9:28
	 */
	@Test
	public void testIsArray_1()
		throws Exception {
		MethodParamBody fixture = new MethodParamBody("");
		fixture.setParamMoidfy("");
		fixture.setCurrentPackage("");
		fixture.setParamRealType("");
		fixture.setParamPreInit("");
		fixture.setParamHideType("");
		fixture.setArray(true);

		boolean result = fixture.isArray();

		// add additional test code here
		// An unexpected exception was thrown in user code while executing this test:
		//    java.lang.StringIndexOutOfBoundsException: String index out of range: -1
		//       at java.lang.String.substring(String.java:1967)
		//       at org.jpf.aitest.gts.gtm.MethodParamBody.<init>(MethodParamBody.java:43)
		assertTrue(result);
	}

	/**
	 * Run the boolean isArray() method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 18-6-2 上午9:28
	 */
	@Test
	public void testIsArray_2()
		throws Exception {
		MethodParamBody fixture = new MethodParamBody("");
		fixture.setParamMoidfy("");
		fixture.setCurrentPackage("");
		fixture.setParamRealType("");
		fixture.setParamPreInit("");
		fixture.setParamHideType("");
		fixture.setArray(false);

		boolean result = fixture.isArray();

		// add additional test code here
		// An unexpected exception was thrown in user code while executing this test:
		//    java.lang.StringIndexOutOfBoundsException: String index out of range: -1
		//       at java.lang.String.substring(String.java:1967)
		//       at org.jpf.aitest.gts.gtm.MethodParamBody.<init>(MethodParamBody.java:43)
		assertTrue(result);
	}

	/**
	 * Run the String removeDesc(String) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 18-6-2 上午9:28
	 */
	@Test
	public void testRemoveDesc_1()
		throws Exception {
		MethodParamBody fixture = new MethodParamBody("");
		fixture.setParamMoidfy("");
		fixture.setCurrentPackage("");
		fixture.setParamRealType("");
		fixture.setParamPreInit("");
		fixture.setParamHideType("");
		fixture.setArray(true);
		String strParam = "";

		String result = fixture.removeDesc(strParam);

		// add additional test code here
		// An unexpected exception was thrown in user code while executing this test:
		//    java.lang.StringIndexOutOfBoundsException: String index out of range: -1
		//       at java.lang.String.substring(String.java:1967)
		//       at org.jpf.aitest.gts.gtm.MethodParamBody.<init>(MethodParamBody.java:43)
		assertNotNull(result);
	}

	/**
	 * Run the void setArray(boolean) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 18-6-2 上午9:28
	 */
	@Test
	public void testSetArray_1()
		throws Exception {
		MethodParamBody fixture = new MethodParamBody("");
		fixture.setParamMoidfy("");
		fixture.setCurrentPackage("");
		fixture.setParamRealType("");
		fixture.setParamPreInit("");
		fixture.setParamHideType("");
		fixture.setArray(true);
		boolean isArray = true;

		fixture.setArray(isArray);

		// add additional test code here
		// An unexpected exception was thrown in user code while executing this test:
		//    java.lang.StringIndexOutOfBoundsException: String index out of range: -1
		//       at java.lang.String.substring(String.java:1967)
		//       at org.jpf.aitest.gts.gtm.MethodParamBody.<init>(MethodParamBody.java:43)
	}

	/**
	 * Run the void setArray(boolean) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 18-6-2 上午9:28
	 */
	@Test
	public void testSetArray_2()
		throws Exception {
		MethodParamBody fixture = new MethodParamBody("");
		fixture.setParamMoidfy("");
		fixture.setCurrentPackage("");
		fixture.setParamRealType("");
		fixture.setParamPreInit("");
		fixture.setParamHideType("");
		fixture.setArray(true);
		boolean isArray = true;

		fixture.setArray(isArray);

		// add additional test code here
		// An unexpected exception was thrown in user code while executing this test:
		//    java.lang.StringIndexOutOfBoundsException: String index out of range: -1
		//       at java.lang.String.substring(String.java:1967)
		//       at org.jpf.aitest.gts.gtm.MethodParamBody.<init>(MethodParamBody.java:43)
	}

	/**
	 * Run the void setArray(boolean) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 18-6-2 上午9:28
	 */
	@Test
	public void testSetArray_3()
		throws Exception {
		MethodParamBody fixture = new MethodParamBody("");
		fixture.setParamMoidfy("");
		fixture.setCurrentPackage("");
		fixture.setParamRealType("");
		fixture.setParamPreInit("");
		fixture.setParamHideType("");
		fixture.setArray(true);
		boolean isArray = false;

		fixture.setArray(isArray);

		// add additional test code here
		// An unexpected exception was thrown in user code while executing this test:
		//    java.lang.StringIndexOutOfBoundsException: String index out of range: -1
		//       at java.lang.String.substring(String.java:1967)
		//       at org.jpf.aitest.gts.gtm.MethodParamBody.<init>(MethodParamBody.java:43)
	}

	/**
	 * Run the void setCurrentPackage(String) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 18-6-2 上午9:28
	 */
	@Test
	public void testSetCurrentPackage_1()
		throws Exception {
		MethodParamBody fixture = new MethodParamBody("");
		fixture.setParamMoidfy("");
		fixture.setCurrentPackage("");
		fixture.setParamRealType("");
		fixture.setParamPreInit("");
		fixture.setParamHideType("");
		fixture.setArray(true);
		String currentPackage = "";

		fixture.setCurrentPackage(currentPackage);

		// add additional test code here
		// An unexpected exception was thrown in user code while executing this test:
		//    java.lang.StringIndexOutOfBoundsException: String index out of range: -1
		//       at java.lang.String.substring(String.java:1967)
		//       at org.jpf.aitest.gts.gtm.MethodParamBody.<init>(MethodParamBody.java:43)
	}

	/**
	 * Run the void setParamHideType(String) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 18-6-2 上午9:28
	 */
	@Test
	public void testSetParamHideType_1()
		throws Exception {
		MethodParamBody fixture = new MethodParamBody("");
		fixture.setParamMoidfy("");
		fixture.setCurrentPackage("");
		fixture.setParamRealType("");
		fixture.setParamPreInit("");
		fixture.setParamHideType("");
		fixture.setArray(true);
		String paramHideType = "";

		fixture.setParamHideType(paramHideType);

		// add additional test code here
		// An unexpected exception was thrown in user code while executing this test:
		//    java.lang.StringIndexOutOfBoundsException: String index out of range: -1
		//       at java.lang.String.substring(String.java:1967)
		//       at org.jpf.aitest.gts.gtm.MethodParamBody.<init>(MethodParamBody.java:43)
	}

	/**
	 * Run the void setParamMoidfy(String) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 18-6-2 上午9:28
	 */
	@Test
	public void testSetParamMoidfy_1()
		throws Exception {
		MethodParamBody fixture = new MethodParamBody("");
		fixture.setParamMoidfy("");
		fixture.setCurrentPackage("");
		fixture.setParamRealType("");
		fixture.setParamPreInit("");
		fixture.setParamHideType("");
		fixture.setArray(true);
		String paramMoidfy = "";

		fixture.setParamMoidfy(paramMoidfy);

		// add additional test code here
		// An unexpected exception was thrown in user code while executing this test:
		//    java.lang.StringIndexOutOfBoundsException: String index out of range: -1
		//       at java.lang.String.substring(String.java:1967)
		//       at org.jpf.aitest.gts.gtm.MethodParamBody.<init>(MethodParamBody.java:43)
	}

	/**
	 * Run the void setParamPreInit(String) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 18-6-2 上午9:28
	 */
	@Test
	public void testSetParamPreInit_1()
		throws Exception {
		MethodParamBody fixture = new MethodParamBody("");
		fixture.setParamMoidfy("");
		fixture.setCurrentPackage("");
		fixture.setParamRealType("");
		fixture.setParamPreInit("");
		fixture.setParamHideType("");
		fixture.setArray(true);
		String paramPreInit = "";

		fixture.setParamPreInit(paramPreInit);

		// add additional test code here
		// An unexpected exception was thrown in user code while executing this test:
		//    java.lang.StringIndexOutOfBoundsException: String index out of range: -1
		//       at java.lang.String.substring(String.java:1967)
		//       at org.jpf.aitest.gts.gtm.MethodParamBody.<init>(MethodParamBody.java:43)
	}

	/**
	 * Run the void setParamRealType(String) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 18-6-2 上午9:28
	 */
	@Test
	public void testSetParamRealType_1()
		throws Exception {
		MethodParamBody fixture = new MethodParamBody("");
		fixture.setParamMoidfy("");
		fixture.setCurrentPackage("");
		fixture.setParamRealType("");
		fixture.setParamPreInit("");
		fixture.setParamHideType("");
		fixture.setArray(true);
		String paramRealType = "";

		fixture.setParamRealType(paramRealType);

		// add additional test code here
		// An unexpected exception was thrown in user code while executing this test:
		//    java.lang.StringIndexOutOfBoundsException: String index out of range: -1
		//       at java.lang.String.substring(String.java:1967)
		//       at org.jpf.aitest.gts.gtm.MethodParamBody.<init>(MethodParamBody.java:43)
	}

	/**
	 * Run the void setParamType(String) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 18-6-2 上午9:28
	 */
	@Test
	public void testSetParamType_1()
		throws Exception {
		MethodParamBody fixture = new MethodParamBody("");
		fixture.setParamMoidfy("");
		fixture.setCurrentPackage("");
		fixture.setParamRealType("");
		fixture.setParamPreInit("");
		fixture.setParamHideType("");
		fixture.setArray(true);
		String paramType = "";

		fixture.setParamType(paramType);

		// add additional test code here
		// An unexpected exception was thrown in user code while executing this test:
		//    java.lang.StringIndexOutOfBoundsException: String index out of range: -1
		//       at java.lang.String.substring(String.java:1967)
		//       at org.jpf.aitest.gts.gtm.MethodParamBody.<init>(MethodParamBody.java:43)
	}

	/**
	 * Run the void setParamValue(String) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 18-6-2 上午9:28
	 */
	@Test
	public void testSetParamValue_1()
		throws Exception {
		MethodParamBody fixture = new MethodParamBody("");
		fixture.setParamMoidfy("");
		fixture.setCurrentPackage("");
		fixture.setParamRealType("");
		fixture.setParamPreInit("");
		fixture.setParamHideType("");
		fixture.setArray(true);
		String paramValue = "";

		fixture.setParamValue(paramValue);

		// add additional test code here
		// An unexpected exception was thrown in user code while executing this test:
		//    java.lang.StringIndexOutOfBoundsException: String index out of range: -1
		//       at java.lang.String.substring(String.java:1967)
		//       at org.jpf.aitest.gts.gtm.MethodParamBody.<init>(MethodParamBody.java:43)
	}

	/**
	 * Run the void setParamVariable(String) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 18-6-2 上午9:28
	 */
	@Test
	public void testSetParamVariable_1()
		throws Exception {
		MethodParamBody fixture = new MethodParamBody("");
		fixture.setParamMoidfy("");
		fixture.setCurrentPackage("");
		fixture.setParamRealType("");
		fixture.setParamPreInit("");
		fixture.setParamHideType("");
		fixture.setArray(true);
		String paramVariable = "";

		fixture.setParamVariable(paramVariable);

		// add additional test code here
		// An unexpected exception was thrown in user code while executing this test:
		//    java.lang.StringIndexOutOfBoundsException: String index out of range: -1
		//       at java.lang.String.substring(String.java:1967)
		//       at org.jpf.aitest.gts.gtm.MethodParamBody.<init>(MethodParamBody.java:43)
	}

	/**
	 * Perform pre-test initialization.
	 *
	 * @throws Exception
	 *         if the initialization fails for some reason
	 *
	 * @generatedBy CodePro at 18-6-2 上午9:28
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
	 * @generatedBy CodePro at 18-6-2 上午9:28
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
	 * @generatedBy CodePro at 18-6-2 上午9:28
	 */
	public static void main(String[] args) {
		new org.junit.runner.JUnitCore().run(MethodParamBodyTest.class);
	}
}