/**
 * copyrigth by wupf@asiainfo.com
 * 2018年8月6日
 */
package org.jpf.aut.gts.gtm.fuzzByParamType;

import java.util.ArrayList;

import org.jpf.aut.gts.gtm.MethodParamBody;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author wupf@asiainfo.com
 *
 */
public class fuzzLocalDateTime implements IFuzz {
	private static final Logger logger = LogManager.getLogger();

	public ArrayList<String> getFuzzeForNull(MethodParamBody cParamInitBody) {
		ArrayList<String> mList = new ArrayList<String>();
		// logger.debug("strParamName="+cParamInitBody.getParamVariable());

		if (cParamInitBody.isArray()) {
			mList.add("    " + cParamInitBody.getParamType() + " " + cParamInitBody.getParamVariable()
					+ " = { LocalDateTime.now(), null };\n");
			mList.add("    " + cParamInitBody.getParamType() + " " + cParamInitBody.getParamVariable()
			+ " = { null, null };\n");
			mList.add("    " + cParamInitBody.getParamType() + " " + cParamInitBody.getParamVariable()
			+ " = { LocalDateTime.now(), LocalDateTime.now() };\n");
		} else {
			mList.add("    " + cParamInitBody.getParamType() + " " + cParamInitBody.getParamVariable()
					+ " =  LocalDateTime.now();\n");
			mList.add("    " + cParamInitBody.getParamType() + " " + cParamInitBody.getParamVariable()
					+ " = null;\n");
		}
		return mList;
	}

}
