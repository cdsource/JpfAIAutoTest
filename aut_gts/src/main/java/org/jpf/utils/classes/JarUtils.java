

package org.jpf.utils.classes;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarOutputStream;

import org.jpf.utils.ios.JpfFileUtil;

/**
 * 
 */
public class JarUtils {

    /**
     * 
     * @category @author 鍚村钩绂�
     * @param strJarName
     * @param strFileName
     * @return update 2016骞�4鏈�16鏃�
     */
    public String readFileFromJar(String strJarName, String strFileName) {
        String result = "";
        try {
            String currentJarPath =
                    JarUtils.class.getProtectionDomain().getCodeSource().getLocation().getFile();
            JarFile currentJar = new JarFile(strJarName);
            JarEntry dbEntry = currentJar.getJarEntry(strFileName);
            InputStream in = currentJar.getInputStream(dbEntry);

            // FileInputStream fis = new
            // FileInputStream(currentJarPath+"/text.properties");
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String readLine = null;
            while ((readLine = br.readLine()) != null) {
                result = result + readLine;
            }
        } catch (IOException e) {
            // TODO 鑷姩鐢熸垚 catch 鍧�
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 
     * @category 瑙ｅ帇鏌愪釜鏂囦欢 鎶奵onf.jar浠嶶WFE.ear閲岄潰瑙ｅ帇鍑烘潵
     * @author 鍚村钩绂�
     * @param path
     * @throws Exception update 2016骞�4鏈�15鏃�
     */
    public void getJarFromJar(String strEarFileName, String strJarFileName) throws Exception {
        JarFile currentJar = new JarFile(strEarFileName);
        JarEntry dbEntry = currentJar.getJarEntry(strJarFileName);
        InputStream in = currentJar.getInputStream(dbEntry);
        FileOutputStream fos =
                new FileOutputStream(JpfFileUtil.getFilePath(strEarFileName) + strJarFileName, true);
        int i = 0;
        while ((i = in.read()) != -1) {
            fos.write(i);
        }
        fos.flush();
        fos.close();
    }

    /**
     * 
     * @category 鎵撳寘 鎶奵onf.jar鎵撳寘鍒癠WFE.ear閲岄潰銆�
     * @author 鍚村钩绂�
     * @param filepath
     * @return update 2016骞�4鏈�15鏃�
     */
    public boolean EAR(String filepath) {
        try {

            File tempJar = new File(filepath + "/UWFE1.ear");
            tempJar.createNewFile();
            JarFile jar = new JarFile(filepath + "/UWFE.ear");
            FileOutputStream f = new FileOutputStream(tempJar);
            JarOutputStream newJar = new JarOutputStream(f);
            byte buffer[] = new byte[100];
            int bytesRead;

            // Add back the original files
            Enumeration entries = jar.entries();

            while (entries.hasMoreElements()) {
                // Prompt for copy
                JarEntry entry = (JarEntry) entries.nextElement();
                if (entry.toString().equals("conf.jar"))
                    continue;
                InputStream is = jar.getInputStream(entry);
                newJar.putNextEntry(entry);
                while ((bytesRead = is.read(buffer)) != -1) {
                    newJar.write(buffer, 0, bytesRead);
                }
                newJar.flush();
                is.close();
            } // Add new file last
            jar.close();
            JarEntry entry = new JarEntry("conf.jar");
            newJar.putNextEntry(entry);
            FileInputStream fis = new FileInputStream(filepath + "/conf.jar");
            while ((bytesRead = fis.read(buffer)) != -1) {
                newJar.write(buffer, 0, bytesRead);
            }
            newJar.flush();
            fis.close();
            newJar.close();
            f.close();
            return true;
        } catch (Exception ex) {
            ex.getMessage();
            return false;
        }
    }

}
