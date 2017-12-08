/** 
* @author 吴平福 
* E-mail:wupf@asiainfo.com 
* @version 创建时间：2017年12月9日 上午6:34:42 
* 类说明 
*/ 

package org.jpf.unittests.generateuts;

import java.util.List;

/**
 * 
 */
public class JpfUtInfo {

    /**
     * 
     */
    public JpfUtInfo() {
        // TODO Auto-generated constructor stub
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

    private String utFileDesc;
    private String utPackage;
    private String utImport;
    private String utClassDeclare;
    private String utClassEnd;
    private List<JpfUtMethodInfo> listUtMethodInfos;
    private String utMinConstructor;
    private String utPrivateConstructor;
    
}
