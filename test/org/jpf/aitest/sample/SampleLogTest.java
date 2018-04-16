package org.jpf.aitest.sample;

import static org.junit.Assert.fail;
import org.junit.Test;
import static org.junit.Assert.assertTrue;
import static org.jpf.aitest.runtime.WriteException.WritePrivateException;

/**
* The class <code>SampleLogTest</code> contains tests for the class <code>{@link SampleLog}</code>.
* <p>
* Copyright (c) 2017
* 
* @generatedBy wupf@asiainfo.com at 2018-04-17 07:10:06
* @author Administrator
* @version $Revision: 1.0 $
*/

public class SampleLogTest  {


  /**
  * Run the add method test.
  *
  * @throws Exception
  * 
  * @generated By wupf@asiainfo.com 
  * @generated at 2018-04-17 07:10:13
  *@see  Modifier=1  void add(String className)
  */
  @Test(timeout=1000)

  public void test_add_R2() throws Exception
  {

    try{

    String className =  "abc123";

  SampleLog fixture = new SampleLog();
  fixture.add(className);

   //请在这里增加检查点:比如 assertEquals(true, result);
    assertTrue(true);

    }catch(Exception ex){
      WritePrivateException(ex,Thread.currentThread().getStackTrace(),true);
      ex.printStackTrace();
      fail();
    }

  }



  /**
  * Run the add method test.
  *
  * @throws Exception
  * 
  * @generated By wupf@asiainfo.com 
  * @generated at 2018-04-17 07:10:13
  *@see  Modifier=1  void add(String className)
  */
  @Test(timeout=1000)

  public void test_add_R3() throws Exception
  {

    try{

    String className =  "123";

  SampleLog fixture = new SampleLog();
  fixture.add(className);

   //请在这里增加检查点:比如 assertEquals(true, result);
    assertTrue(true);

    }catch(Exception ex){
      WritePrivateException(ex,Thread.currentThread().getStackTrace(),true);
      ex.printStackTrace();
      fail();
    }

  }



  /**
  * Run the add method test.
  *
  * @throws Exception
  * 
  * @generated By wupf@asiainfo.com 
  * @generated at 2018-04-17 07:10:13
  *@see  Modifier=1  void add(String className)
  */
  @Test(timeout=1000)

  public void test_add_R4() throws Exception
  {

    try{

    String className =  "中文";

  SampleLog fixture = new SampleLog();
  fixture.add(className);

   //请在这里增加检查点:比如 assertEquals(true, result);
    assertTrue(true);

    }catch(Exception ex){
      WritePrivateException(ex,Thread.currentThread().getStackTrace(),true);
      ex.printStackTrace();
      fail();
    }

  }



  /**
  * Run the add method test.
  *
  * @throws Exception
  * 
  * @generated By wupf@asiainfo.com 
  * @generated at 2018-04-17 07:10:13
  *@see  Modifier=1  void add(String className)
  */
  @Test(timeout=1000)

  public void test_add_R5() throws Exception
  {

    try{

    String className =  "中文123abc";

  SampleLog fixture = new SampleLog();
  fixture.add(className);

   //请在这里增加检查点:比如 assertEquals(true, result);
    assertTrue(true);

    }catch(Exception ex){
      WritePrivateException(ex,Thread.currentThread().getStackTrace(),true);
      ex.printStackTrace();
      fail();
    }

  }



  /**
  * Run the add method test.
  *
  * @throws Exception
  * 
  * @generated By wupf@asiainfo.com 
  * @generated at 2018-04-17 07:10:13
  *@see  Modifier=1  void add(String className)
  */
  @Test(timeout=1000)

  public void test_add_R6() throws Exception
  {

    try{

    String className =  "!@#$%^&*";

  SampleLog fixture = new SampleLog();
  fixture.add(className);

   //请在这里增加检查点:比如 assertEquals(true, result);
    assertTrue(true);

    }catch(Exception ex){
      WritePrivateException(ex,Thread.currentThread().getStackTrace(),true);
      ex.printStackTrace();
      fail();
    }

  }



  /**
  * Run the add method test.
  *
  * @throws Exception
  * 
  * @generated By wupf@asiainfo.com 
  * @generated at 2018-04-17 07:10:14
  *@see  Modifier=1  void add(String className)
  */
  @Test(timeout=1000)

  public void test_add_NLP7() throws Exception
  {

    try{

    String className =  zjmcc;

  SampleLog fixture = new SampleLog();
  fixture.add(className);

   //请在这里增加检查点:比如 assertEquals(true, result);
    assertTrue(true);

    }catch(Exception ex){
      WritePrivateException(ex,Thread.currentThread().getStackTrace(),true);
      ex.printStackTrace();
      fail();
    }

  }



}