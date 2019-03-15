package org.jpf.aut.base;

/**
 * copyrigth by 吴平福
 * 
 * @author 吴平福 E-mail:wupf@aliyun.com
 * @version 创建时间：2017年9月30日 下午3:49:28 类说明
 */


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jpf.aut.common.consts.AutConst;
import org.jpf.utils.JpfDateTimeUtil;
import org.jpf.utils.classes.FindClassInfoUtil;



/**
 * 
 */
public class JpfUtInfo {
  private static final Logger logger = LogManager.getLogger();

  public String strCurrentFileName = "";
  private int initCountPerMethod = 0;

  public int getInitCountPerMethod() {
    return initCountPerMethod;
  }

  public void setInitCountPerMethod(int initCountPerMethod) {
    this.initCountPerMethod = initCountPerMethod;
  }

  private String utPackage = "";
  private String SourcePackage = "";

  private Map<String, String> mapImport = new HashMap<String, String>();
  private String currentJavaFilePackage = "";

  private String utFileJavaDoc = "";
  private String utClassDeclare = "";

  private String utBasic = "";

  private String utClassEnd = "";
  private List<JpfUtMethodInfo> listUtMethodInfos = new ArrayList<JpfUtMethodInfo>();
  private String utMinConstructor = "";
  private String utPrivateConstructor = "";

  // 初始化参数的变量，避免重复
  private Map<String, String> mapParamVar = new HashMap<String, String>();

  private JpfUtMethodInfo MethodSetup = new JpfUtMethodInfo();
  private JpfUtMethodInfo MethodSetUpBeforeClass = new JpfUtMethodInfo();
  private JpfUtMethodInfo MethodTearDown = new JpfUtMethodInfo();
  private JpfUtMethodInfo MethodTearDownAfterClass = new JpfUtMethodInfo();
  private JpfUtMethodInfo MethodUtMain = new JpfUtMethodInfo();

  // R：随机，D： JAVADOC ,L：从日志 r: 运行
  private String genType = "R";

  /**
   * @return the genType
   */
  public String getGenType() {
    return genType;
  }

  /**
   * @param genType the genType to set
   */
  public void setGenType(String genType) {
    this.genType = genType;
  }

  /**
   * @return the mapParamVar
   */
  public Map<String, String> getMapParamVar() {
    return mapParamVar;
  }

  /**
   * @param mapParamVar the mapParamVar to set
   */
  public void setMapParamVar(Map<String, String> mapParamVar) {
    this.mapParamVar = mapParamVar;
  }

  /**
   * @return the mapImport
   */
  public Map<String, String> getMapImport() {
    return mapImport;
  }

  /**
   * @param mapImport the mapImport to set
   */
  public void setMapImport(Map<String, String> mapImport) {
    this.mapImport = mapImport;
  }

  /**
   * @return the currentJavaFile
   */
  public String getCurrentJavaFilePackage() {
    return currentJavaFilePackage;
  }

  /**
   * @param currentJavaFile the currentJavaFile to set
   */
  public void setCurrentJavaFilePackage(String currentJavaFile) {
    int iPos = currentJavaFile.lastIndexOf("\\");

    if (iPos > 0) {
      currentJavaFile = currentJavaFile.substring(0, iPos);
    }
    iPos = currentJavaFile.indexOf(AutConst.MAIN_SRC);
    if (iPos > 0) {
      currentJavaFile = currentJavaFile.substring(iPos + AutConst.MAIN_SRC.length() + 1,
          currentJavaFile.length());
    }
    currentJavaFile = currentJavaFile.replaceAll("\\\\", ".");
    logger.debug(currentJavaFile);
    this.currentJavaFilePackage = currentJavaFile;
  }

  /**
   * @return the sourcePackage
   */
  public String getSourcePackage() {
    return SourcePackage;
  }

  /**
   * @param sourcePackage the sourcePackage to set
   */
  public void setSourcePackage(String sourcePackage) {
    SourcePackage = sourcePackage;
  }

  /**
   * 
   */
  public JpfUtInfo() {
    // TODO Auto-generated constructor stub
    initMethodSetup();
    initMethodSetUpBeforeClass();
    initMethodTearDown();
    initMethodTearDownAfterClass();
    initMethodUtMain();
  }

  private void initMethodSetup() {
    StringBuffer sb = new StringBuffer();
    sb.append("\n").append("  /**").append("\n");
    sb.append("  * 测试方法初始化.").append("\n");
    sb.append("  * ").append("\n");
    sb.append("  * @throws Exception ").append("\n");
    sb.append("  *         if the initialization fails for some reason ").append("\n");
    sb.append("  *  ").append("\n");
    sb.append("  * @generatedBy wupf@aliyun.com at ").append(JpfDateTimeUtil.getCurrDateTime())
        .append("\n");
    sb.append("  */  ").append("\n");
    sb.append(" @Before ").append("\n");
    MethodSetup.setMethodJavaDoc(sb.toString());
  }

  private void initMethodSetUpBeforeClass() {

  }

  private void initMethodTearDown() {

  }

  private void initMethodTearDownAfterClass() {

  }

  private void initMethodUtMain() {

  }

  /**
   * @return the utFileDesc
   */
  public String getUtFileDesc() {
    return utFileJavaDoc;
  }

  /**
   * @param utFileDesc the utFileDesc to set
   */
  public void setUtFileDesc(String utFileDesc) {
    this.utFileJavaDoc = utFileDesc;
  }

  /**
   * @return the utClassDeclare
   */
  public String getUtClassDeclare() {
    return utClassDeclare;
  }

  /**
   * @param utClassDeclare the utClassDeclare to set
   */
  public void setUtClassDeclare(String utClassDeclare) {
    this.utClassDeclare = utClassDeclare;
  }

  /**
   * @return the utClassEnd
   */
  public String getUtClassEnd() {
    return utClassEnd;
  }

  /**
   * @param utClassEnd the utClassEnd to set
   */
  public void setUtClassEnd(String utClassEnd) {
    this.utClassEnd = utClassEnd;
  }

  /**
   * @return the listUtMethodInfos
   */
  public List<JpfUtMethodInfo> getListUtMethodInfos() {
    return listUtMethodInfos;
  }

  /**
   * @param listUtMethodInfos the listUtMethodInfos to set
   */
  public void setListUtMethodInfos(List<JpfUtMethodInfo> listUtMethodInfos) {
    this.listUtMethodInfos = listUtMethodInfos;
  }

  /**
   * @return the utMinConstructor
   */
  public String getUtMinConstructor() {
    if (utMinConstructor.length() > 0) {
      return utMinConstructor;
    } else if (utPrivateConstructor.length() > 0) {
      return utPrivateConstructor;
    }
    return "";
  }

  /**
   * @param utMinConstructor the utMinConstructor to set
   */
  public void setUtMinConstructor(String utMinConstructor) {
    if (utMinConstructor.indexOf("wupf_result") > 0) {
      this.utMinConstructor = utMinConstructor.replaceAll("wupf_result", "wupf_fixture");

    } else {
      this.utMinConstructor = utMinConstructor.replaceAll("result", "wupf_fixture");
    }
    logger.debug("utMinConstructor=" + this.utMinConstructor);
  }

  /**
   * @return the utPrivateConstructor
   */
  public String getUtPrivateConstructor() {
    return utPrivateConstructor;
  }

  /**
   * @param utPrivateConstructor the utPrivateConstructor to set
   */
  public void setUtPrivateConstructor(String utPrivateConstructor) {
    this.utPrivateConstructor = utPrivateConstructor;
  }

  /**
   * @return the utPackage
   */
  public String getUtPackage() {
    return utPackage;
  }

  /**
   * @param utPackage the utPackage to set
   */
  public void setUtPackage(String utPackage) {
    this.utPackage = utPackage;
  }

  /**
   * 
   * @category 根据方法参数类型增加IMPORT
   * @author 吴平福
   * @param strImport update 2017年11月8日
   */
  public void addImport(String strImport) {

    if (strImport == null || strImport.trim().length() == 0) {
      return;
    }
    if (strImport.startsWith(".")) {
      strImport = strImport.substring(1, strImport.length());
    }
    strImport = strImport.replaceAll("import ", "").replaceAll(";", "").trim();

    // logger.debug("add import:"+strImport);
    if (strImport.equalsIgnoreCase("org.codehaus.jackson.annotate.JsonProperty")) {
      logger.debug("add import:" + strImport);

    }
    mapImport.put(strImport, strImport);
    /*
     * Iterator<Map.Entry<String, String>> it = mapImport.entrySet().iterator(); while
     * (it.hasNext()) { Map.Entry<String, String> entry = it.next(); logger.debug("key= " +
     * entry.getKey() + " and value= " + entry.getValue()); }
     */
  }

  /**
   * 
   * @category @author 吴平福
   * @param strClassName
   * @return update 2018年3月6日
   */
  public String findImport(final String strClassSimpleName) {
    Iterator<Map.Entry<String, String>> it = mapImport.entrySet().iterator();
    while (it.hasNext()) {
      Map.Entry<String, String> entry = it.next();
      // logger.debug("key= " + entry.getKey() + " and value= " + entry.getValue());
      if (entry.getKey().endsWith(strClassSimpleName)) {
        logger.debug("key= " + entry.getKey() + " and value= " + entry.getValue());
        return entry.getValue();
      }
      if (entry.getKey().endsWith(".*")) {
        String strClassName =
            entry.getKey().substring(0, entry.getKey().length() - 1) + strClassSimpleName;
        String strJavaFileName = FindClassInfoUtil.getInstance().findJavaFile(strClassName);
        if (strJavaFileName != null) {
          return strClassName;
        }
      }
    }
    return "";
  }

  /**
   * 
   * @category @author 吴平福
   * @param strImport update 2018年3月6日
   */
  public void removeImport(String strImport) {
    Iterator<Map.Entry<String, String>> it = mapImport.entrySet().iterator();
    while (it.hasNext()) {
      Map.Entry<String, String> entry = it.next();
      System.out.println("key= " + entry.getKey() + " and value= " + entry.getValue());
      if (entry.getValue().trim().equalsIgnoreCase(strImport)) {
        mapImport.remove(entry.getKey());
      }
    }

  }

  @Override
  public String toString() {
    StringBuffer sbUtText = new StringBuffer();

    /*
     * if (GenerateInputParam.New_Package_Name.length()>0) {
     * utPackage=GenerateInputParam.New_Package_Name+"\n//"+utPackage; }
     */

    sbUtText.append(utPackage).append("\n");

    Iterator<Map.Entry<String, String>> it = mapImport.entrySet().iterator();
    while (it.hasNext()) {
      Map.Entry<String, String> entry = it.next();
      sbUtText.append("import ").append(entry.getValue() + ";\n");
    }
    sbUtText.append("\n").append(utFileJavaDoc).append("\n").append(utClassDeclare).append("\n");

    for (int i = 0; i < listUtMethodInfos.size(); i++) {
      sbUtText.append(listUtMethodInfos.get(i).toString()).append("\n");
    }
    sbUtText.append(utBasic).append("\n").append(utClassEnd);
    return sbUtText.toString();
  }

  /**
   * @return the utBasic
   */
  public String getUtBasic() {
    return utBasic;
  }

  /**
   * @param utBasic the utBasic to set
   */
  public void setUtBasic(String utBasic) {
    this.utBasic = utBasic;
  }

  /**
   * @return the methodSetup
   */
  public JpfUtMethodInfo getMethodSetup() {
    return MethodSetup;
  }

  /**
   * @param methodSetup the methodSetup to set
   */
  public void setMethodSetup(JpfUtMethodInfo methodSetup) {
    MethodSetup = methodSetup;
  }

  /**
   * @return the methodSetUpBeforeClass
   */
  public JpfUtMethodInfo getMethodSetUpBeforeClass() {
    return MethodSetUpBeforeClass;
  }

  /**
   * @param methodSetUpBeforeClass the methodSetUpBeforeClass to set
   */
  public void setMethodSetUpBeforeClass(JpfUtMethodInfo methodSetUpBeforeClass) {
    MethodSetUpBeforeClass = methodSetUpBeforeClass;
  }

  /**
   * @return the methodTearDown
   */
  public JpfUtMethodInfo getMethodTearDown() {
    return MethodTearDown;
  }

  /**
   * @param methodTearDown the methodTearDown to set
   */
  public void setMethodTearDown(JpfUtMethodInfo methodTearDown) {
    MethodTearDown = methodTearDown;
  }

  /**
   * @return the methodTearDownAfterClass
   */
  public JpfUtMethodInfo getMethodTearDownAfterClass() {
    return MethodTearDownAfterClass;
  }

  /**
   * @param methodTearDownAfterClass the methodTearDownAfterClass to set
   */
  public void setMethodTearDownAfterClass(JpfUtMethodInfo methodTearDownAfterClass) {
    MethodTearDownAfterClass = methodTearDownAfterClass;
  }

  /**
   * @return the methodUtMain
   */
  public JpfUtMethodInfo getMethodUtMain() {
    return MethodUtMain;
  }

  /**
   * @param methodUtMain the methodUtMain to set
   */
  public void setMethodUtMain(JpfUtMethodInfo methodUtMain) {
    MethodUtMain = methodUtMain;
  }

}
