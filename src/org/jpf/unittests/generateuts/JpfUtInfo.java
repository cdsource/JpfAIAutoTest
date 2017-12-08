/** 
* @author 吴平福 
* E-mail:wupf@asiainfo.com 
* @version 创建时间：2017年12月9日 上午6:34:42 
* 类说明 
*/ 

package org.jpf.unittests.generateuts;

import java.util.ArrayList;
import java.util.List;

import com.asiainfo.utils.AiDateTimeUtil;

/**
 * 
 */
public class JpfUtInfo {

    
    private String utFileDesc="";
    private String utPackage="";
    private String utImport="";
    private String utClassDeclare="";
    private String utBasic="";

    private String utClassEnd="";
    private List<JpfUtMethodInfo> listUtMethodInfos =new ArrayList<JpfUtMethodInfo>() ;
    private String utMinConstructor="";
    private String utPrivateConstructor="";
    
    private JpfUtMethodInfo MethodSetup =new JpfUtMethodInfo();
    private JpfUtMethodInfo MethodSetUpBeforeClass=new JpfUtMethodInfo();
    private JpfUtMethodInfo MethodTearDown=new JpfUtMethodInfo();
    private JpfUtMethodInfo MethodTearDownAfterClass=new JpfUtMethodInfo();
    private JpfUtMethodInfo MethodUtMain=new JpfUtMethodInfo();
    
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
   
    private void initMethodSetup()
    {
        StringBuffer sb=new StringBuffer();
        sb.append("\n").append("  /**").append("\n");
        sb.append("  * 测试方法初始化.").append("\n");
        sb.append("  * ").append("\n");
        sb.append("  * @throws Exception ").append("\n");
        sb.append("  *         if the initialization fails for some reason ").append("\n");
        sb.append("  *  ").append("\n");
        sb.append("  * @generatedBy wupf@asiainfo.com at ").append(AiDateTimeUtil.getCurrDateTime()).append("\n");
        sb.append("  */  ").append("\n");
        sb.append(" @Before ").append("\n");
        MethodSetup.setMethodJavaDoc(sb.toString());
    }
    private void initMethodSetUpBeforeClass()
    {
        
    }
    private void initMethodTearDown()
    {
        
    }
    private void initMethodTearDownAfterClass()
    {
        
    }
    private void initMethodUtMain()
    {
        
    }
    /**
     * @return the utFileDesc
     */
    public String getUtFileDesc() {
        return utFileDesc;
    }
    /**
     * @param utFileDesc the utFileDesc to set
     */
    public void setUtFileDesc(String utFileDesc) {
        this.utFileDesc = utFileDesc;
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
        this.utMinConstructor = utMinConstructor.replaceAll("result", "fixture");
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
     * @return the utImport
     */
    public String getUtImport() {
        return utImport;
    }

    /**
     * @param utImport the utImport to set
     */
    public void setUtImport(String utImport) {
        this.utImport = utImport;
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
        if (utImport.indexOf(strImport) < 0) {
            utImport += strImport+"\n";
        }
    }
    
    public String toString() {
        StringBuffer  sbUtText=new StringBuffer();   
        
        sbUtText.append(utPackage).append("\n").append(utImport).append("\n").append(utFileDesc).append("\n").append(utClassDeclare).append("\n");
        
        for(int i=0;i<listUtMethodInfos.size();i++)
        {
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
