/**
 * 
 */
package org.jpf.utils.mavens;

import java.util.Vector;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jpf.utils.ios.AiFileUtil;

/**
 * @author wupf@aliyun.com
 *
 */
public class AntToMaven {
	private static final Logger logger = LogManager.getLogger();

    /**
     * 
     * @author wupf@aliyun.com
     * @param args
     * 2018年8月14日
     */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		if (args.length==2)
		{
			AntToMaven cAntToMaven=new AntToMaven(args[0],args[1]);
		}else
		{
			logger.warn("error input param");
			logger.warn(" please input 2 param;");
			logger.warn(" 1. libpath");
			logger.warn(" 2. pompath");
		}
	}
	
	/**
	 * 
	 * @author wupf@aliyun.com
	 * @param strFileName
	 * @return
	 * 2018年8月14日
	 */
	public String getArtifactId(final String strFileName)
	{
		String strArtifactId=AiFileUtil.getFileName(strFileName);
		strArtifactId=strArtifactId.substring(0,strArtifactId.length()-4);
		int i=strArtifactId.indexOf("-");
		if (i>0)
		{
			strArtifactId=strArtifactId.substring(0,i);
		}
		return strArtifactId;
	}
	
	/**
	 * 
	 * @author wupf@aliyun.com
	 * @param strFileName
	 * @return
	 * 2018年8月14日
	 */
	public String getVersion(final String strFileName)
	{
		String strVersion=AiFileUtil.getFileName(strFileName);
		strVersion=strVersion.substring(0,strVersion.length()-4);
		int i=strVersion.lastIndexOf("-");
		if (i>0)
		{
			strVersion=strVersion.substring(i+1,strVersion.length());
		}else
		{
			strVersion="wupf4.0";
		}
		return strVersion;
	}
	
	/**
	 * 
	 * @author wupf@aliyun.com
	 * @param strFilePath
	 * @param strPomPath
	 * @return
	 * 2018年8月14日
	 */
	public String getRelativePath(String strFilePath,String strPomPath)
	{
		return strFilePath.substring(strPomPath.length(), strFilePath.length()) ;
		
	}
	
	/**
	 * 
	 * @param strFilePath
	 * @param strPomPath
	 */
	public AntToMaven(String strFilePath,String strPomPath)
	{
		
		/*
		 <dependency>
        <groupId>com.cmbchina.cc</groupId>
                <artifactId>CreditsFront7</artifactId>
                <version>4.0.0</version>
        <scope>system</scope>
        <systemPath>${basedir}/src/main/webapp/WEB-INF/lib/commons-codec-1.6.jar</systemPath>
                </dependency> 
		 */
		logger.info(strFilePath);
		try
		{
			StringBuffer sb=new StringBuffer();
			Vector<String> v=new Vector<String>();
			AiFileUtil.getFiles(strFilePath, v, ".jar");
			for(int i=0;i<v.size();i++)
			{
				String strFileName=v.get(i);
				sb.append("<dependency>\n");
				sb.append("<groupId>com.cmbchina.cc</groupId>\n");
				sb.append("<artifactId>").append(getArtifactId(strFileName)).append("</artifactId>\n");
				sb.append("<version>").append(getVersion(strFileName)).append("</version>\n");
				sb.append("<scope>system</scope>\n");
				sb.append("<systemPath>${basedir}").append(getRelativePath(strFileName,strPomPath)).append("</systemPath>\n");
				sb.append("</dependency>\n");
				 
			}
			System.out.println(sb);
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
}
