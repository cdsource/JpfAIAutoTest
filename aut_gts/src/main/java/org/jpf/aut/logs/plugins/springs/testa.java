/**
 * copyrigth by wupf@asiainfo.com
 * 2018年4月18日
 */
package org.jpf.aut.logs.plugins.springs;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author wupf@asiainfo.com
 *
 */
public class testa {

	/**
	 * 
	 */
	public testa() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @author wupf@asiainfo.com
	 * @param args
	 * 2018年4月18日
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String abc="的话 bb AND     CMS_ARTICLE_STATUS = ?   and a.CMS_ARTICLE_STATUd = ? and a.CMS_ARTICLE_STATUS3 =4";
		Pattern p = Pattern.compile("\\s^[A-Za-z][A-Z|a-z|0-9|\\.|\\_|\\-]{1,} = /?");
		//Pattern p = Pattern.compile("\\s?(.*)? = /?");
		Matcher m = p.matcher(abc);
		while(m.find()){ 
            //System.out.println(m.group());
        }
		
		abc="的话 bb AND     CMS_ARTICLE_STATUS = ?   and a.CMS_ARTICLE_STATUd > ? and a.CMS_ARTICLE_STATUS3 < ? AND al.trans_date >= ?";
		p = Pattern.compile("[A-Za-z][A-Z|a-z|0-9|\\_|\\-]{1,}\\s?[>=<]{1,2}\\s?\\?");
	    m = p.matcher(abc);
	    List<String> mList=new ArrayList();
		while(m.find()){ 
			mList.add(m.group().replaceAll(">", "").replaceAll("<", "").replaceAll("=", "").replaceAll("\\?","").trim());
            System.out.println(m.group());
        }
		System.out.println(mList);
		aa();
	}
	
	private static void aa()
	{
		String input = "company_code = $csc223@cc and project_id = @pid ; update t set a = @aa,b=@cd,c=@cd,ttt=@ttt;update t2 set d=@bb";
        
        String regex = "@\\w+\\s?";
        //regex = "(\\s*=\\s*)|(\\s*,\\s*)|(\\s*;\\s*)|(\\s+)";
        Pattern pattern = Pattern.compile(regex); 
        Matcher matcher = pattern.matcher(input); 

         
        while(matcher.find()){ 
        	System.out.println(matcher.group());
        } 

	}

}
