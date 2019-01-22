/**
 * copyrigth by wupf@asiainfo.com
 * 2018年8月10日
 */
package com.asiainfo.aitest.procgeneratecase.tcgenerate;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.eclipse.core.runtime.Path;

import com.asiainfo.aitest.procgeneratecase.common.DBInfoObject;
import com.asiainfo.aitest.procgeneratecase.common.ParmInfo;
import com.asiainfo.aitest.procgeneratecase.common.SPProcessSample;
import com.asiainfo.aitest.procgeneratecase.common.SPTestSuiteProperties;
import com.asiainfo.aitest.procgeneratecase.common.StoredProcedureInfo;

/**
 * @author wupf@asiainfo.com
 *
 */
public class CodeGenerator {

    public CodeGenerator(String pkg, String file, String spNameStr, DBInfoObject dbConfig)
    {
        templateFileName = "sampleTestCaseTemplate.txt";
        spList = getSpNameList(spNameStr);
        this.dbConfig = dbConfig;
        fileName = file.substring(0, file.indexOf("."));
        process = new SPProcessSample(this.dbConfig);
        PACKAGE = getPackageName(pkg);
        templateFileName = (new Path(System.getProperty("java.class.path"))).removeLastSegments(1).toString() + System.getProperty("file.separator") + "plugins" + System.getProperty("file.separator") +  templateFileName;
    }

    private String getPackageName(String pkgName)
    {
        pkgName = pkgName.replace('/', '.');
        pkgName = pkgName.replace('\\', '.');
        if(pkgName.indexOf("src") != -1)
        {
            pkgName = pkgName.substring(pkgName.indexOf("src") + 3);
            if(pkgName.length() > 1)
                pkgName = pkgName.substring(1);
        } else
        if(pkgName.indexOf("JavaSource") != -1)
        {
            pkgName = pkgName.substring(pkgName.indexOf("JavaSource") + 10);
            if(pkgName.length() > 1)
                pkgName = pkgName.substring(1);
        }
        if(pkgName.trim().length() == 0)
            pkgName = " ";
        else
            pkgName = "package " + pkgName + ";";
        return pkgName;
    }

    public boolean isSPNameValid(String spName)
    {
        if(spName == null)
            return false;
        spName = spName.trim();
        if(spName.trim().length() == 0)
            return false;
        return spName.indexOf("") <= 0 && spName.indexOf("\"") <= 0 && spName.indexOf("\\") <= 0 && spName.indexOf("'") <= 0 && spName.indexOf("~") <= 0 && spName.indexOf("!") <= 0 && spName.indexOf("@") <= 0 && spName.indexOf("#") <= 0 && spName.indexOf("$") <= 0 && spName.indexOf("%") <= 0 && spName.indexOf("-") <= 0 && spName.indexOf("<") <= 0 && spName.indexOf(">") <= 0 && spName.indexOf("+") <= 0 && spName.indexOf("*") <= 0 && spName.indexOf("/") <= 0 && spName.indexOf(" ") <= 0 && spName.indexOf("|") <= 0 && spName.indexOf("(") <= 0 && spName.indexOf(")") <= 0 && spName.indexOf("[") <= 0 && spName.indexOf("]") <= 0 && spName.indexOf("{") <= 0 && spName.indexOf("}") <= 0 && spName.indexOf("?") <= 0 && spName.indexOf(";") <= 0 && spName.indexOf("=") <= 0;
    }

    public String getCode()
    {
        StringBuffer code = new StringBuffer();
        String errException = "";
        StringBuffer allTestCasesFunc = new StringBuffer();
        StringBuffer allTestCasesRef = new StringBuffer();
        //monitor.setTaskName(SPTestSuiteProperties.getString("Monitor_GetTemplate"));
        code = getTempleteCode();
        if(code.length() == 0)
        {
            code.append("/*Fail to open the file: sampleTestCaseTemplate.txt at \"" + templateFileName + "\", please ensure that the file exist!*/\r\n");
            code.append("/*Fail to generate test code because the templete file can not be found!*/");
            //monitor.setTaskName(SPTestSuiteProperties.getString("Monitor_FailGetTemplate") + templateFileName + "\"");
            return code.toString();
        }
        //monitor.worked(1);
        //monitor.setTaskName(SPTestSuiteProperties.getString("Monitor_GenerateBody"));
        //monitor.setTaskName(SPTestSuiteProperties.getString("Monitor_Replace") + "*PACKAGE*");
        code = replace(code, "*PACKAGE*", PACKAGE);
        //monitor.setTaskName(SPTestSuiteProperties.getString("Monitor_Replace") + "*AUTHOR*");
        code = replace(code, "*AUTHOR*", "wupf@asiainfo.com");
        //monitor.setTaskName(SPTestSuiteProperties.getString("Monitor_Replace") + "*CREATE_DATE*");
        code = replace(code, "*CREATE_DATE*", getSysDate());
        //monitor.setTaskName(SPTestSuiteProperties.getString("Monitor_ReplaceParms"));
        code = replace(code, "*DB_DRIVER*", dbConfig.getDriverName());
        code = replace(code, "*DB_URL*", dbConfig.getUrl());
        code = replace(code, "*DB_USER*", dbConfig.getUserName());
        code = replace(code, "*DB_PWD*", dbConfig.getPassword());
        //monitor.setTaskName(SPTestSuiteProperties.getString("Monitor_Replace") + "*RESULT_FILE_ADDIN*");
        //monitor.setTaskName(SPTestSuiteProperties.getString("Monitor_Replace") + "*CLASS_NAME*");
        code = replace(code, "*CLASS_NAME*", fileName.substring(0, 1).toUpperCase() + fileName.substring(1, fileName.length()));
        StringBuffer firstTestCase = new StringBuffer(getFirstTestCase(code));
        for(int i = 0; i < spList.size(); i++)
            try
            {
                if(spList.get(i) != null && isSPNameValid(spList.get(i).toString()))
                {
                    String spName = new String((String)spList.get(i));
                    String spRoutineSchema = spName.substring(0, spName.indexOf(".") != -1 ? spName.indexOf(".") : 0).toUpperCase();
                    String spRoutineName = spName.substring(spName.indexOf(".") != -1 ? spName.indexOf(".") + 1 : 0, spName.length()).toUpperCase();
                    StringBuffer testCase_i = new StringBuffer(firstTestCase.toString());
                    StoredProcedureInfo spInfo = new StoredProcedureInfo();
                    //monitor.setTaskName(SPTestSuiteProperties.getString("Monitor_GenerateTC") + spName + "... " + (new Integer(i)).toString() + "/" + (new Integer(spList.size())).toString() + "... ");
                    spInfo = process.getSPParmList(dbConfig.getDbType(), spRoutineSchema, spRoutineName);
                    testCase_i = replace(testCase_i, "*ROUTINETYPE*", spInfo.getRoutineType());
                    testCase_i = replace(testCase_i, "*ROUTINESCHEMA*", spRoutineSchema);
                    testCase_i = replace(testCase_i, "*ROUTINENAME*", spRoutineName);
                    testCase_i = replace(testCase_i, "*CLASS_NAME*", fileName.substring(0, 1).toUpperCase() + fileName.substring(1, fileName.length()));
                    ArrayList parmList = new ArrayList();
                    StringBuffer parmStr_i = new StringBuffer();
                    String errMsg = null;
                    if(spInfo != null && spInfo.getParmList() != null && spInfo.getParmList().size() > 0)
                    {
                        parmList = spInfo.getParmList();
                        for(int j = 0; j < parmList.size(); j++)
                        {
                            ParmInfo parmInfo = (ParmInfo)parmList.get(j);
                            if("O".equals(parmInfo.getRowType()))
                                parmStr_i.append("\r\n\t\tparms.add(new ParmInfo(\"" + parmInfo.getParmName() + "\",\"" + parmInfo.getRowType() + "\" ,\"" + parmInfo.getTypeName() + "\"));");
                            else
                            if("P".equals(parmInfo.getRowType()))
                                parmStr_i.append("\r\n\t\tparms.add(new ParmInfo(\"" + parmInfo.getParmName() + "\",\"" + parmInfo.getRowType() + "\" ,\"" + parmInfo.getTypeName() + "\"," + parmInfo.getDefaultValue() + "));");
                        }

                    } else
                    {
                        errMsg = new String("\r\n\t\t/***********Attention: Failed to get SP parms(reason: Database connection failed or SP does not exist), you have to set SP parms manully according to the following example:");
                        errMsg = errMsg + "\r\n\t\t/******** Example for adding SP parameters: ***************************";
                        errMsg = errMsg + "\r\n\t\tparms.add(new ParmInfo(\"piEmailAddr\"    ,\"P\" ,\"VARCHAR\",\"jennfs@sohu.com\"));  // This is an input parameter";
                        errMsg = errMsg + "\r\n\t\tparms.add(new ParmInfo(\"piRnwlStartDate\",\"P\" ,\"DATE\"   ,\"1999-01-01\"));";
                        errMsg = errMsg + "\r\n\t\tparms.add(new ParmInfo(\"piIndexStart\"   ,\"P\" ,\"INTEGER\",\"1\"));";
                        errMsg = errMsg + "\r\n\t\tparms.add(new ParmInfo(\"poRowsSum\"      ,\"O\" ,\"INTEGER\",\"\"));  // This is an output parameter";
                        errMsg = errMsg + "\r\n\t\t**********************************************************************/";
                    }
                    allTestCasesRef.append("do" + spRoutineSchema + "_" + spRoutineName + "();\r\n\t\t");
                    if(parmStr_i.length() > 0)
                        testCase_i = replace(testCase_i, "*PARAMETERS_LIST*", parmStr_i.toString());
                    else
                        testCase_i = replace(testCase_i, "*PARAMETERS_LIST*", errMsg);
                    allTestCasesFunc.append(testCase_i);
                    allTestCasesFunc.append("\r\n");
                    //monitor.worked(1);
                }
            }
            catch(Exception e)
            {
                errException = e.getMessage();
                e.printStackTrace();
            }

        if(allTestCasesFunc.length() == 0)
            allTestCasesFunc.append("/* Attention: No proper stored procedures names are provided, the requested format is :ROUTINESCHEMA.ROUTINENAME.*/");
        //monitor.setTaskName(SPTestSuiteProperties.getString("Monitor_Replace") + "TEST_CASE_BODY...");
        code = replace(code, "*TEST_CASE_BODY*", allTestCasesFunc.toString());
        //monitor.setTaskName(SPTestSuiteProperties.getString("Monitor_Replace") + "TEST_CASE_BODY...done");
        //monitor.worked(1);
        //monitor.setTaskName(SPTestSuiteProperties.getString("Monitor_Replace") + "REF_ALL_TEST_CASE...");
        code = replace(code, "*REF_ALL_TEST_CASE*", allTestCasesRef.toString());
        //monitor.setTaskName(SPTestSuiteProperties.getString("Monitor_Replace") + "REF_ALL_TEST_CASE...done");
        code = replace(code, "*CREATE_ERROR*", errException);
        //monitor.worked(1);
        return code.toString();
    }

    public String getFirstTestCase(StringBuffer code)
    {
        String str = new String();
        if(code == null || code.length() == 0)
        {
            return str;
        } else
        {
            int begin = code.toString().indexOf("*FIRST_TEST_CASE_BEGIN*");
            int end = code.toString().indexOf("*FIRST_TEST_CASE_END*");
            str = code.substring(begin + "*FIRST_TEST_CASE_BEGIN*".length() + 1, end);
            code = code.delete(begin, end + "*FIRST_TEST_CASE_END*".length());
            return str;
        }
    }

    public String getSysDate()
    {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date currentTime_1 = new Date();
        String dateString = formatter.format(currentTime_1);
        return dateString;
    }

    public static StringBuffer replace(StringBuffer code, String src, String des)
    {
        for(int pos = 0; (pos = code.toString().indexOf(src)) >= 0;)
        {
            code = code.delete(pos, pos + src.length());
            code = code.insert(pos, des);
        }

        return code;
    }

    public StringBuffer getTempleteCode()
    {
        StringBuffer content = new StringBuffer();
        try
        {
            File file = new File(templateFileName);
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String currentLine;
            while((currentLine = bufferedReader.readLine()) != null) 
                content.append(currentLine + "\r\n");
        }
        catch(FileNotFoundException e)
        {
            System.err.println(e.getMessage());
        }
        catch(IOException e)
        {
            System.err.println(e.getMessage());
        }
        catch(Exception e)
        {
            System.err.println(e.getMessage());
        }
        return content;
    }

    public ArrayList getSpNameList(String nameStr)
    {
        ArrayList spList = new ArrayList();
        String temp = new String();
        for(int pos = 0; (pos = nameStr.indexOf("\r\n")) > 0;)
        {
            temp = nameStr.substring(0, pos);
            if(temp.length() > 0)
                spList.add(temp);
            if(pos + 2 < nameStr.length() - pos)
                nameStr = nameStr.substring(pos + 2, nameStr.length());
            else
                nameStr = nameStr.substring(0, nameStr.indexOf("\r\n"));
        }

        return spList;
    }

    private ArrayList spList;
    private StoredProcedureInfo spInfo;
    private ParmInfo parmInfo;
    private String templateFileName;
    private String PACKAGE;
    private SPProcessSample process;
    private DBInfoObject dbConfig;
    private String fileName;

    public static void main(String[] args)
    {
    	//CodeGenerator cCodeGenerator=new CodeGenerator();
    }

}
