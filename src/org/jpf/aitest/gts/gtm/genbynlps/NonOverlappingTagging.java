/**
 * 
 */
package org.jpf.aitest.gts.gtm.genbynlps;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class NonOverlappingTagging {
	private static final Logger logger = LogManager.getLogger();

	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		String sentence = "SELLER1abvFULL3rqadfasNAMEfar54";
		HashMap<String, Integer> dict = new HashMap();
		HashMap<String, Integer> mapResult = new HashMap();
		dict.put("ACCOUNT", 1);
		dict.put("FEE", 1);
		dict.put("FULL", 1);
		dict.put("LOG", 1);
		dict.put("ID", 1);
		dict.put("NAME", 1);
		dict.put("SELLER", 1);
		dict.put("TYPE", 1);
		dict.put("USER", 1);

		dict.put("PAGE", 1);
		dict.put("QUERY", 1);
		dict.put("ADDRESS", 1);
		dict.put("PHONE", 1);
		dict.put("ALIPAY", 1);
		dict.put("RECORD", 1);
		dict.put("REFUND", 1);
		
		int iCount = 0;
		String tmpStr = sentence;
		while (tmpStr.length() > 0) {
			boolean bFind = false;
			Iterator iter = dict.entrySet().iterator();
			while (iter.hasNext()) {
				Map.Entry entry = (Map.Entry) iter.next();
				String key = (String) entry.getKey();
				Object val = entry.getValue();
				// System.out.println(key);
				if (tmpStr.startsWith(key)) {
					System.out.println("key=" + key);
					mapResult.put(key,1);
					tmpStr = tmpStr.substring(key.length(), tmpStr.length());
					bFind = true;
					iCount++;
				}
			}
			if (bFind == false) {
				tmpStr = tmpStr.substring(1, tmpStr.length());
				System.out.println(tmpStr);
			}
			iCount++;
		}
		System.out.println(iCount);
		logger.info("ExcuteTime " + (System.currentTimeMillis() - start) + "ms");
		
		System.out.println(sentence);
		Iterator iter = mapResult.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			String key = (String) entry.getKey();
			System.out.println(key);
		}
	}

}
