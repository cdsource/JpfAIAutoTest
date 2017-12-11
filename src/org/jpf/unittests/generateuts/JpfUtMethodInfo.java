/** 
* @author 吴平福 
* E-mail:wupf@asiainfo.com 
* @version 创建时间：2017年12月9日 上午6:37:17 
* 类说明 
*/ 

package org.jpf.unittests.generateuts;

/**
 * 
 */
public class JpfUtMethodInfo {

    /**
     * 
     */
    public JpfUtMethodInfo() {
        // TODO Auto-generated constructor stub
    }
    private String methodJavaDoc="";
    private String methodDeclare="";
    private String methodTry="";
    private String methodCatch="";
    private String classConstructor="";
    private String methodEnd="  }\n";
    private String methodParam="";
    private String methodCaller="";
    private String methodAssert="";
    
    public String toString() {
        StringBuffer sBuffer=new StringBuffer();
        if (methodJavaDoc!=null && methodJavaDoc.length()>0)
        {
            sBuffer.append(methodJavaDoc).append("\n");
        }
        if (methodDeclare!=null && methodDeclare.length()>0)
        {
            sBuffer.append(methodDeclare).append("\n");
        }
        if (methodTry!=null && methodTry.length()>0)
        {
            sBuffer.append(methodTry).append("\n");
        }
        if (methodParam!=null && methodParam.length()>0)
        {
            sBuffer.append(methodParam).append("\n");
        }
        if (classConstructor!=null && classConstructor.length()>0)
        {
            sBuffer.append(classConstructor).append("\n");
        }
        if (methodCaller!=null && methodCaller.length()>0)
        {
            sBuffer.append(methodCaller).append("\n");
        }
        if (methodAssert!=null && methodAssert.length()>0)
        {
            sBuffer.append(methodAssert).append("\n");
        }
        if (methodCatch!=null && methodCatch.length()>0)
        {
            sBuffer.append(methodCatch).append("\n");
        }
        if (methodEnd!=null && methodEnd.length()>0)
        {
            sBuffer.append(methodEnd).append("\n");
        }

        
        return sBuffer.toString();
    }
    /**
     * @return the methodEnd
     */
    public String getMethodEnd() {
        return methodEnd;
    }
    /**
     * @param methodEnd the methodEnd to set
     */
    public void setMethodEnd(String methodEnd) {
        this.methodEnd = methodEnd;
    }
    /**
     * @return the classConstructor
     */
    public String getClassConstructor() {
        return classConstructor;
    }
    /**
     * @param classConstructor the classConstructor to set
     */
    public void setClassConstructor(String classConstructor) {
        this.classConstructor = classConstructor;
    }
    /**
     * @return the methodJavaDoc
     */
    public String getMethodJavaDoc() {
        return methodJavaDoc;
    }
    /**
     * @param methodJavaDoc the methodJavaDoc to set
     */
    public void setMethodJavaDoc(String methodJavaDoc) {
        this.methodJavaDoc = methodJavaDoc;
    }
    /**
     * @return the methodDeclare
     */
    public String getMethodDeclare() {
        return methodDeclare;
    }
    /**
     * @param methodDeclare the methodDeclare to set
     */
    public void setMethodDeclare(String methodDeclare) {
        this.methodDeclare = methodDeclare;
    }
    /**
     * @return the methodTry
     */
    public String getMethodTry() {
        return methodTry;
    }
    /**
     * @param methodTry the methodTry to set
     */
    public void setMethodTry(String methodTry) {
        this.methodTry = methodTry;
    }
    /**
     * @return the methodCatch
     */
    public String getMethodCatch() {
        return methodCatch;
    }
    /**
     * @param methodCatch the methodCatch to set
     */
    public void setMethodCatch(String methodCatch) {
        this.methodCatch = methodCatch;
    }
    /**
     * @return the methodAssert
     */
    public String getMethodAssert() {
        return methodAssert;
    }
    /**
     * @param methodAssert the methodAssert to set
     */
    public void setMethodAssert(String methodAssert) {
        this.methodAssert = methodAssert;
    }
    /**
     * @return the methodParam
     */
    public String getMethodParam() {
        return methodParam;
    }
    /**
     * @param methodParam the methodParam to set
     */
    public void setMethodParam(String methodParam) {
        this.methodParam = methodParam;
    }
    /**
     * @return the methodCaller
     */
    public String getMethodCaller() {
        return methodCaller;
    }
    /**
     * @param methodCaller the methodCaller to set
     */
    public void setMethodCaller(String methodCaller) {
        this.methodCaller = methodCaller;
    }

    
}
