#!/bin/bash
#定义清空日志文件路径
LOG_PATH=/icore/writeLEDRFID/newfile4
#清空tomcat日志信息
cd $LOG_PATH
for n in `ls *.out -1`;do
    echo "$LOG_PATH/$n"
	cat /dev/null > $LOG_PATH/$n
done	
