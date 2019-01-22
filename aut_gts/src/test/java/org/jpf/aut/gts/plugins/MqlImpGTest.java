package org.jpf.aut.gts.plugins;

import org.junit.*;
import static org.junit.Assert.*;

/**
 * The class <code>MqlImpGTest</code> contains tests for the class <code>{@link MqlImp}</code>.
 *
 * @generatedBy wupf@ at 18-12-13 下午7:33
 * @author wupf
 * @version $Revision: 1.0 $
 */
public class MqlImpGTest {
	/**
	 * Run the boolean isInclude(String) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy wupf@ at 18-12-13 下午7:33
	 */
	@Test
	public void testIsInclude_1()
		throws Exception {
		MqlImp fixture = new MqlImp();
		String strImport = null;

		boolean result = fixture.isInclude(strImport);

		assertEquals(false, result);
	}

	/**
	 * Run the boolean isInclude(String) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy wupf@ at 18-12-13 下午7:33
	 */
	@Test
	public void testIsInclude_2()
		throws Exception {
		MqlImp fixture = new MqlImp();
		String strImport = "";

		boolean result = fixture.isInclude(strImport);

		assertEquals(false, result);
	}

	/**
	 * Run the boolean isInclude(String) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy wupf@ at 18-12-13 下午7:33
	 */
	@Test
	public void testIsInclude_3()
		throws Exception {
		MqlImp fixture = new MqlImp();
		String strImport = "import org.n3r.eql.matrix.2Mql;";

		boolean result = fixture.isInclude(strImport);

		assertEquals(false, result);
	}

	/**
	 * Run the boolean isInclude(String) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy wupf@ at 18-12-13 下午7:33
	 */
	@Test
	public void testIsInclude_4()
		throws Exception {
		MqlImp fixture = new MqlImp();
		String strImport = "import org.n3r.eql.matrix.Mql;";

		boolean result = fixture.isInclude(strImport);

		assertEquals(true, result);
	}

	  @Test(timeout = 4000)
	  public void test0()  throws Throwable  {
	      MqlImp mqlImp0 = new MqlImp();
	      boolean boolean0 = mqlImp0.isInclude("org.n3r.eql.matrix.Mql");
	      assertTrue(boolean0);
	  }

	  @Test
	  public void test1()  throws Throwable  {
	      MqlImp mqlImp0 = new MqlImp();
	      // Undeclared exception!
	      try { 
	        mqlImp0.isInclude("import");
	        fail("Expecting exception: StringIndexOutOfBoundsException");
	      
	      } catch(StringIndexOutOfBoundsException e) {
	      }
	  }

	  @Test(timeout = 4000)
	  public void test2()  throws Throwable  {
	      MqlImp mqlImp0 = new MqlImp();
	      boolean boolean0 = mqlImp0.isInclude("o");
	      assertFalse(boolean0);
	  }

	  @Test(timeout = 4000)
	  public void test3()  throws Throwable  {
	      MqlImp mqlImp0 = new MqlImp();
	      boolean boolean0 = mqlImp0.isInclude("");
	      assertFalse(boolean0);
	  }

	  @Test(timeout = 4000)
	  public void test4()  throws Throwable  {
	      MqlImp mqlImp0 = new MqlImp();
	      boolean boolean0 = mqlImp0.isInclude((String) null);
	      assertFalse(boolean0);
	  }
	/**
	 * Perform pre-test initialization.
	 *
	 * @throws Exception
	 *         if the initialization fails for some reason
	 *
	 * @generatedBy wupf@ at 18-12-13 下午7:33
	 */
	@Before
	public void setUp()
		throws Exception {
	}

	/**
	 * Perform post-test clean-up.
	 *
	 * @throws Exception
	 *         if the clean-up fails for some reason
	 *
	 * @generatedBy wupf@ at 18-12-13 下午7:33
	 */
	@After
	public void tearDown()
		throws Exception {
	}


}