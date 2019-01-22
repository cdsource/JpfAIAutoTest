/**
 * copyrigth by wupf@aliyun.com
 * 2018年6月27日
 */
package org.jpf.utils.mavens;

/**
 * @author wupf@aliyun.com
 *
 */

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CreateMavenRepoApp {
	private static final Logger logger = LogManager.getLogger();
	private static final String OCB_PLUGIN_FOLDER = "D:\\jworkspaces\\jpf_aitest";

	public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
		File directory = new File(OCB_PLUGIN_FOLDER);
		// get all the files from a directory
		PrintWriter writer = new PrintWriter("update_repo_maven.bat", "UTF-8");
		writer.println("rem " + new Date());
		File[] fList = directory.listFiles();
		for (File file : fList) {
			if (file.isFile()) {

				String absolutePath = file.getAbsolutePath();
				logger.info(absolutePath);
				if (absolutePath.endsWith(".jar")) {
					Manifest m = new JarFile(absolutePath).getManifest();
					Attributes attributes = m.getMainAttributes();
					logger.info(attributes);
					String symbolicName = attributes.getValue("Bundle-SymbolicName");

					
					logger.info(symbolicName);
					if (symbolicName != null && symbolicName.contains("com.yourCompany.yourProject")) {
						String[] parts = symbolicName.split("\\.");
						String artifactId = parts[parts.length - 1];
						String groupId = symbolicName.substring(0, symbolicName.length() - artifactId.length() - 1);
						String version = attributes.getValue("Bundle-Version");
						String mavenLine = "call mvn org.apache.maven.plugins:maven-install-plugin:2.5.1:install-file -Dfile="
								+ absolutePath + " -DgroupId=" + groupId + " -DartifactId=" + artifactId + " -Dversion="
								+ version + " -Dpackaging=jar ";
						writer.println(mavenLine);
					}
				}

			}
		}
		writer.close();
        logger.info("ExcuteTime " + (System.currentTimeMillis() - start) + "ms");
	}

}