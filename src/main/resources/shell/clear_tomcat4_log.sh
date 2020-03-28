#!/bin/bash
#定义清空日志文件路径
LOG_PATH=/icore/tomcat/instances/4/logs
#清空tomcat日志信息
cd $LOG_PATH
for n in `ls catalina.out -1`;do
    echo "$LOG_PATH/$n"
	cat /dev/null > $LOG_PATH/$n
done	
