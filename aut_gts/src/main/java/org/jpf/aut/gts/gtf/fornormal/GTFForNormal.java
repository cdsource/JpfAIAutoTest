/**
 * @author 吴平福 E-mail:wupf@asiainfo.com
 * @version 创建时间：2017年9月28日 下午11:40:54 类说明
 */

package org.jpf.aut.gts.gtf.fornormal;


/*
 * 待完善 1. 抽象类里面的非抽象方法 2类里面定义的类
 * 
 * 已经支持 1 public 2 private 3 public static
 * 
 */

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jpf.aut.base.JpfUtInfo;
import org.jpf.aut.gts.gtf.AbstractGenerateTests;


/**
 * 
 */
public class GTFForNormal extends AbstractGenerateTests {
  private static final Logger logger = LogManager.getLogger();

  /**
   * 
   */
  public GTFForNormal() {

  }

  /**
   * 
   * @category 增加IMPORT
   * @author 吴平福
   * @param importList
   * @param sb update 2017年9月29日
   */
  public void addExtraImport(JpfUtInfo cJpfUtInfo) {

    cJpfUtInfo.addImport("import org.junit.Test;");
    cJpfUtInfo.addImport("import static org.junit.Assert.assertTrue;");
    cJpfUtInfo.addImport("import static org.junit.Assert.fail;");
    cJpfUtInfo.addImport("import static org.junit.Assert.assertEquals;");
    cJpfUtInfo.addImport("import static org.junit.Assert.assertNotNull;");
    cJpfUtInfo
        .addImport("import static org.jpf.aitest.runtime.WriteException.WritePrivateException;");

    cJpfUtInfo.setUtClassEnd("}");
  }


  @Override
  public String getUTSavePath() {
    // TODO Auto-generated method stub
    return "";
  }

  @Override
  public void setNewPackage(JpfUtInfo cJpfUtInfo) {

  }

  @Override
  public String getTestFileTypeName() {
    // TODO Auto-generated method stub
    return null;
  }


}
