package org.jpf.utils.classes.accessmethods;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Method;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * The class <code>SampleAccessMethod_WGTest</code> contains tests for the class
 * <code>{@link SampleAccessMethod}</code>.
 *
 * @generatedBy CodePro at 19-3-4 下午2:20
 * @author wupf
 * @version $Revision: 1.0 $
 */
public class SampleAccessMethod_WGTest {


  @Test
  public void testAdd_3() throws Exception {
    SampleAccessMethod fixture = new SampleAccessMethod();
    int i = 1;
    int j = 1;

    Method method = fixture.getClass().getDeclaredMethod("add", int.class, int.class);
    method.setAccessible(true);

    method.invoke(fixture, i, j);

  }

  /**
   * Run the int add(int,int,int) method test.
   *
   * @throws Exception
   *
   * @generatedBy CodePro at 19-3-4 下午2:20
   */
  @Test
  public void testAdd_2() throws Exception {
    SampleAccessMethod fixture = new SampleAccessMethod();
    int i = 1;
    int j = 1;
    int m = 1;

    int result = fixture.add(i, j, m);

    assertEquals(3, result);
  }


  @Test
  public void testAdd2_2() throws Exception {
    SampleAccessMethod fixture = new SampleAccessMethod();
    int i = 1;
    int j = 1;

    Method method = fixture.getClass().getDeclaredMethod("add2", int.class, int.class);
    method.setAccessible(true);

    int result = (int) method.invoke(fixture, i, j);

    // int result = fixture.add2(i, j);

    assertEquals(2, result);
  }

  /**
   * Perform pre-test initialization.
   *
   * @throws Exception if the initialization fails for some reason
   *
   * @generatedBy CodePro at 19-3-4 下午2:20
   */
  @Before
  public void setUp() throws Exception {}

  /**
   * Perform post-test clean-up.
   *
   * @throws Exception if the clean-up fails for some reason
   *
   * @generatedBy CodePro at 19-3-4 下午2:20
   */
  @After
  public void tearDown() throws Exception {}

  /**
   * Launch the test.
   *
   * @param args the command line arguments
   *
   * @generatedBy CodePro at 19-3-4 下午2:20
   */
  public static void main(String[] args) {
    new org.junit.runner.JUnitCore().run(SampleAccessMethod_WGTest.class);
  }
}
