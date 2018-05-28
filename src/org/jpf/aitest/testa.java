/**
 * @author 吴平福 E-mail:wupf@asiainfo.com
 * @version 创建时间：2018年1月30日 下午4:12:02 类说明
 */

package org.jpf.aitest;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.AbstractTypeDeclaration;
import org.eclipse.jdt.core.dom.BodyDeclaration;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.EnumDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;

import com.asiainfo.utils.ios.AiFileUtil;
import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;

import jdepend.framework.JDepend;
import jdepend.framework.JavaPackage;

/**
 * 
 */
public class testa {
    private static final Logger logger = LogManager.getLogger();

    /**
     * 
     */
    public testa() {
        // TODO Auto-generated constructor stub
    }

    /**
     * @category @author 吴平福
     * @param args update 2018年1月30日
     */

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        testa ctesta = new testa();
        ctesta.dodepend();
        
        formatParam("/*in*/ /**/final /**/ String \n strParam");
        logger.info("game over");
    }

    public void dodepend()
    {
        try {
            JDepend  jdepend = new JDepend();
            
            jdepend.addDirectory("D:\\svn\\ecommerce-branch-20170912\\app-dao\\target\\classes\\com\\asiainfo\\ebiz\\product\\service\\InstanceServicePackageManager.class");
            Collection packages =jdepend.analyze();
           logger.info( jdepend.countClasses());
           logger.info(jdepend.countPackages());
           java.util.Iterator itor = packages.iterator();  
           JavaPackage jPackage = null; 
           String analyzedPackageName = null;
         //Iterator就是统一的用来遍历collection里元素的方法。可以理解成指针/游标  
           while (itor.hasNext()) { 
               jPackage = (JavaPackage) itor.next(); 
               analyzedPackageName = jPackage.getName(); 
               logger.info(analyzedPackageName);
               Iterator afferentItor = jPackage.getAfferents().iterator(); 
               String afferentPackageName = null; 
               while (afferentItor.hasNext()) { 
                 JavaPackage afferentPackage = (JavaPackage) afferentItor.next();
                 afferentPackageName = afferentPackage.getName(); 
                 logger.info(afferentPackageName);
               } 

             }
        } catch (Exception ex) {
            // TODO: handle exception
            ex.printStackTrace();
        }

        
    }
    public void addTest_1() {
        // System.out.println("add");
        Map<String, String> map = new IdentityHashMap<>();
        map.put("1", "11");
        map.put("2", "22");
        logger.info(map.get("1"));
        Iterator<Map.Entry<String, String>> it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, String> entry = it.next();
            System.out.println("key= " + entry.getKey() + " and value= " + entry.getValue());
        }
    }

    protected void doSelfTests() {
        // TODO Auto-generated method stub
        try {

            String strJavaFileName =
                    "D:\\svn\\ecommerce-branch-20170912\\app-dao\\src\\main\\java\\com\\asiainfo\\ebiz\\cmod\\enums\\CmodOperatorID.java";
            // String
            // strJavaFileName="D:\\svn\\ecommerce-branch-20170912\\app-dao\\src\\main\\java\\com\\asiainfo\\ebiz\\accessLog\\service\\MobileChannelAccessLogManager.java";
            logger.info(strJavaFileName);
            String sourceString = AiFileUtil.getFileTxt(strJavaFileName, "GBK");
            logger.trace(sourceString);
            ASTParser astParser = ASTParser.newParser(AST.JLS8);
            astParser.setKind(ASTParser.K_COMPILATION_UNIT);
            astParser.setResolveBindings(true);

            astParser.setSource(sourceString.toCharArray());
            Map options = JavaCore.getOptions();
            options.put(JavaCore.COMPILER_SOURCE, JavaCore.VERSION_1_7); // or newer version
            astParser.setCompilerOptions(options);

            CompilationUnit cCompilationUnit = (CompilationUnit) astParser.createAST(null);


            List types = cCompilationUnit.types();
            logger.info(types.size());
            if (types.size() == 0) {
                logger.warn("type=null:" + strJavaFileName);
                return;
            }

            BodyDeclaration typeDec = (BodyDeclaration) types.get(0);
            // TypeDeclaration typeDec = (TypeDeclaration) types.get(0);
            logger.info("classname=" + typeDec.getNodeType());
            if (typeDec.getNodeType() == 71) {
                // enum
                EnumDeclaration typeDec2 = (EnumDeclaration) types.get(0);
                logger.info("classname=" + typeDec2.getName());
                for (int i = 0; i < typeDec2.enumConstants().size(); i++) {
                    logger.info(typeDec2.enumConstants().get(i));
                }

            }
            if (typeDec.getNodeType() == 55) {
                // class
                TypeDeclaration typeDec2 = (TypeDeclaration) types.get(0);
                logger.info("classname=" + typeDec2.getName());
            }
            logger.debug("typeDec.getModifiers()=" + cCompilationUnit.getPackage().getName().toString());
        } catch (Exception ex) {
            // TODO: handle exception
            ex.printStackTrace();
        }
        // insertAccessLogTest_1();
    }
    
    public static void formatParam(/*in*/ /**/ String /* */strParam)
    {
        System.out.println(strParam);
        //  /* a*/ final aa
        String saa=strParam.replaceAll("/\\*(.*?)\\*/", "");
        saa=saa.replaceAll("\n", "");
        System.out.println(saa);
        String REGEX="/\\*(.*)?\\*/";
        Pattern p = Pattern.compile(REGEX);
        Matcher m = p.matcher(strParam); // 获取 matcher 对象
        int count = 0;
  
        while(m.find()) {
          count++;
          System.out.println("Match number "+count);
          System.out.println("start(): "+m.start());
          System.out.println("end(): "+m.end());
       }
        
        strParam= "string keywod = \"abc\"; string value = \"test\";";
        REGEX="string (?<x>[^=]*?) *= *(?<y>[^;]*?);";
         p = Pattern.compile(REGEX);
         m = p.matcher(strParam); // 获取 matcher 对象
         count = 0;
  
        while(m.find()) {
          count++;
          System.out.println("Match number "+count);
          System.out.println("start(): "+m.start());
          System.out.println("end(): "+m.end());
          System.out.println(strParam.substring(m.start(),m.end()));
       }
    }
    
    /**
     * 
     * @category  去掉注释
     * @author 吴平福 
     * @param strParam
     * update 2017年11月8日
     */
    public static String removeDesc(String strParam)
    {
        return strParam.replaceAll("/\\*(.*?)\\*/", "").replaceAll("\n", "");
    }
}
