/**
 * @author 吴平福 E-mail:wupf@asiainfo.com
 * @version 创建时间：2018年1月23日 下午1:41:58 类说明
 */

package org.jpf.unittests.nlps;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.util.InvalidFormatException;

/**
 * 
 */
public class ParticipleUtil {

    /**
     * 
     */
    public ParticipleUtil() {
        // TODO Auto-generated constructor stub
    }

    /**
     * @category @author 吴平福
     * @param args update 2018年1月23日
     */

    public static void main(String[] args) {

        try {
            String str = "The quick, red fox jumped over ? the lazy  brown dogs.";
            ENSplit(str);
            str="a d d m o n t h";
            SentenceDetector(str);
        } catch (Exception ex) {

            ex.printStackTrace();
        }

    }

    /**
     * 
     * @category 英文切词：空格或者换行符
     * @author 吴平福
     * @param str update 2018年1月23日
     */
    private static void ENSplit(String str) {
        String[] result = str.split("\\s+");
        for (String s : result) {
            System.out.println(s + " ");
        }
        System.out.println();
    }

    public static void SentenceDetector(String str) throws InvalidFormatException, IOException {
        // always start with a model, a model is learned from training data
        InputStream is = new FileInputStream("./nlpbin/en-sent.bin");
        SentenceModel model = new SentenceModel(is);
        SentenceDetectorME sdetector = new SentenceDetectorME(model);

        String sentences[] = sdetector.sentDetect(str);
        for (int i = 0; i < sentences.length; i++) {
            System.out.println(sentences[i]);
        }
        is.close();
        System.out.println("---------------1------------");
    }
}
