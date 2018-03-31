/**
 * 
 */
package org.jpf.unittests.generateuts.genbytool;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import com.asiainfo.utils.ios.AiFileUtil;

/**
 * @author wupf@asiainfo.com
 *
 */
public class GenByEvoForMaven {
    private static final Logger logger = LogManager.getLogger();
    
    private void AddPluginToPom(String strPomFile) throws Exception
    {
    	logger.debug(strPomFile);
        String strNewPomFile=strPomFile.replace(".xml", "2.xml");
        AiFileUtil.copyFile(strNewPomFile, strPomFile);
    	Document doc= new SAXReader().read(new File(strNewPomFile));
    	
		/*
	      <!--add by wupf 20180326 begin-->
	      <plugin>
	        <groupId>org.evosuite.plugins</groupId>
	        <artifactId>evosuite-maven-plugin</artifactId>
	        <version>1.0.5</version>
	      </plugin>
	      <!--add by wupf 20180326 end -->
	      */
        Element name=doc.getRootElement().element("build").element("plugins");
        
        Element contactelem= name.addElement("plugin");
        contactelem.addElement("groupId").addText("org.evosuite.plugins"); 
        contactelem.addElement("artifactId").addText("evosuite-maven-plugin"); 
        contactelem.addElement("version").addText("1.0.5"); 
        
        /*
           <groupId>org.evosuite</groupId>
  <artifactId>evosuite-standalone-runtime</artifactId>
  <version>1.0.5</version>
  <scope>test</scope>
         
        name=doc.getRootElement().element("dependencies");
        
        contactelem= name.addElement("dependency");
        contactelem.addElement("groupId").addText("org.evosuite"); 
        contactelem.addElement("artifactId").addText("evosuite-standalone-runtime"); 
        contactelem.addElement("version").addText("1.0.5"); 
        contactelem.addElement("scope").addText("test"); 
        */

        FileOutputStream out = new FileOutputStream(strPomFile);
        OutputFormat format = OutputFormat.createPrettyPrint();
        format.setEncoding("utf-8");
        XMLWriter writer = new XMLWriter(out,format);
        writer.write(doc);
        writer.close();
    }
	/**
	 * 
	 */
	public GenByEvoForMaven(String strPrjPath) {
        long start = System.currentTimeMillis();
		doWork(strPrjPath);
        logger.info("ExcuteTime " + (System.currentTimeMillis() - start) + "ms");
	}
	
	private void doWork(String strPrjPath) {

		//modify pom.xml
		//run generate
		//export
		//test
		try {
			if (!AiFileUtil.FileExist(strPrjPath+"\\pom.xml"))
			{
				logger.warn("not find pom.xml in "+strPrjPath);
				return;
			}
			AddPluginToPom(strPrjPath+"\\pom.xml");
			
			//runexec("cmd /c "+strStartPath.substring(0,2) +" && cd "+strStartPath+"  && mvn clean install -Dmaven.test.failure.ignore=true");
    		
			String strCmd="cmd /c "+strPrjPath.substring(0,2) +" && cd "+strPrjPath+"  &&  mvn -DmemoryInMB=2000 -Dcores=2 evosuite:generate evosuite:export && mvn evosuite:info";
			runexec(strCmd);
			
			//new GenByEvoClean(strPrjPath);
			//new UtRunCheck(strPrjPath);
		}catch(Exception ex)
		{
			logger.error(ex);
			ex.printStackTrace();
		}

	}
	
    private void runexec(String strCmd)
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
			new GenByEvoForMaven(args[0]);
		}else
		{
			 logger.info("error input!");
		}
	}

}
