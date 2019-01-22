#!/bin/sh
echo 'SVN CHECK OUT DIR'
echo $1
echo $2
cd $2
mvn dependency:copy-dependencies -DoutputDirectory=lib -DincludeScope=compile
mvn dependency:copy-dependencies -DoutputDirectory=lib -DincludeScope=test
#java -classpath %CLASSPATH%:$HOME/aitest/aitest-all_1.3.jar org.aitest.GenerateMainAll $1 $2
#java -classpath %CLASSPATH%:$HOME/aitest/aitest-all_1.3.jar org.aitest.gts.genbytool.JpfUnitTestSourceCheck $2
java -classpath %CLASSPATH%:$HOME/aitest/aitest-all_1.3.jar org.aitest.checks.UtCompileCheck $2
