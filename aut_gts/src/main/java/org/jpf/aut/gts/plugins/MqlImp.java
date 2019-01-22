/**
 * copyrigth by wupf@
 * 2018年12月13日
 */
package org.jpf.aut.gts.plugins;

/**
 * @author wupf@asiainfo.com
 *
 */
public class MqlImp  implements Iwupf {

	/**
	 * 
	 */
	public MqlImp() {
		// TODO Auto-generated constructor stub
	}
	
	private final String KEY_IMPORT="org.n3r.eql.matrix.Mql";

	@Override
	public boolean isInclude(String strImport) {
		// TODO Auto-generated method stub
		if (strImport==null || strImport.length()==0)
		{
			return false;
		}
		strImport=strImport.trim();
		if (strImport.startsWith("import"))
		{
			strImport=strImport.substring(7).trim();
		}
		if (strImport.startsWith(KEY_IMPORT))
		{
			return true;
		}
		return false;
	}

	@Override
	public String getWarnMsg() {
		// TODO Auto-generated method stub
		
		return "";
	}
	
}
