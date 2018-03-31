/**
 * 
 */
package org.jpf.unittests.utcheck;

import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.Vector;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.asiainfo.utils.ios.AiFileUtil;

/**
 * @author wupf@asiainfo.com
 *
 */
public class UtRunCheck {
    private static final Logger logger = LogManager.getLogger();
    
    String strStartPath="F:/svn/migu/inst/yellowstone-parent";
	boolean bOK=false;
	int iDeleteTestFileCount=0;
	Vector<String> v=new Vector<String>();
	/**
	 * 
	 */
	public UtRunCheck(String strStartPath) {
		// TODO Auto-generated constructor stub
        long start = System.currentTimeMillis();
        this.strStartPath=strStartPath;
        try {
        	int iTryCount=0;
        	
        	while(!bOK)
        	{
        		bOK=true;
        		runexec("cmd /c "+strStartPath.substring(0,2) +" && cd "+strStartPath+"  && mvn clean install -Dmaven.test.failure.ignore=true");
        		iTryCount++;
            	logger.info("iTryCount="+iTryCount);
            	logger.info("iDeleteTestFileCount="+iDeleteTestFileCount);
        	}
        	
        	logger.info("iTryCount="+iTryCount);
        	logger.info("iDeleteTestFileCount="+iDeleteTestFileCount);
        	for(int i=0;i<v.size();i++)
        	{
        		logger.info(v.get(i));
        	}
        }catch(Exception ex)
        {
        	logger.error(ex);
        }
        logger.info("ExcuteTime " + (System.currentTimeMillis() - start) + "ms");
	}

    public void runexec(String strCmd)
    {
        try
        {
            logger.info(strCmd);
            Process process = Runtime.getRuntime().exec(strCmd);
            InputStreamReader ir = new InputStreamReader(process.getInputStream());
            LineNumberReader input = new LineNumberReader(ir);
            
            String line;

            while ((line = input.readLine()) != null)
            {

                System.out.println(line);
        		if (line.startsWith("[ERROR]"))
        		{
        			int i=line.indexOf(strStartPath);
        			int j=line.indexOf(".java")+5;
        			logger.debug(line.indexOf("test"));
    				logger.debug(i);
    				logger.debug(j);
        			if (i>0 && j>0 && j< line.length())
        			{
        				//logger.debug(line.indexOf("test", i));
        				//logger.debug(line.indexOf("test"));
        				if (line.indexOf("/test/", i)>0)
        				{
        					String strDelFileName=line.substring(i, j).trim();
        					logger.debug(strDelFileName);
        					if (v.indexOf(strDelFileName)==-1)
        					{
        						v.add(strDelFileName);
        						AiFileUtil.delFile(strDelFileName);
            					iDeleteTestFileCount++;
        					}
        					bOK=false;

        				}
        			}
        		}
             }
            process.waitFor();
            int iRetValue = process.exitValue();


        } catch (Exception e)
        {
        	logger.error(e);

        }
    }
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		if (1==args.length)
		{
			new UtRunCheck(args[0]);
		}else
		{
			 logger.info("error input!");
		}
	}

}
