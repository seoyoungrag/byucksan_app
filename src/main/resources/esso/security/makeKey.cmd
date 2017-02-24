@echo off

set CLASSPATH1=D:\project\SSOAgent\classes
@REM set JAR1=D:\project\ep\ep_lib\lib\SSOAgent.jar
set JAR2=D:\project\ep\ep_lib\lib\bcprov-jdk14-133.jar
set JAR3=D:\project\ep\ep_lib\lib\jce-jdk13-130.jar

set SECURITY_CLASSPATH=.;%CLASSPATH1%;%JAR2%;%JAR3%

java -classpath %SECURITY_CLASSPATH% com.sds.acube.esso.security.MakeSecurityKeys
