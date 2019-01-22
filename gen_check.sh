#!/bin/sh
echo 'SVN CHECK OUT DIR'
echo $1
echo $2
cd $2
java -classpath $CLASSPATH:$HOME/aitest/aitest-all_1.3.jar org.aitest.checks.runtime.RunCheckAll $2
