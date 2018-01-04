/** 
* @author 吴平福 
* E-mail:wupf@asiainfo.com 
* @version 创建时间：2017年12月25日 下午10:05:57 
* 类说明 
*/ 

package org.jpf.unittests.utreport;

/**
 * 
 */
public class UtErrInfo {

    /**
     * 
     */
    public UtErrInfo() {
        // TODO Auto-generated constructor stub
    }

    String UtFileName;
    String UtClassName;
    String UtMethodName;
    String UtTime;
    String ErrMsg;
    String ErrType;
    String ErrCause;
    
    public String toString()
    {
        return UtFileName+UtClassName+UtMethodName+UtTime+ErrMsg+ErrType+ErrCause;
    }
}
