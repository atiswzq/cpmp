@echo off

start mvn install:install-file -Dfile=aliyun-java-sdk-core-3.2.3.jar -DgroupId=com.aliyun -DartifactId=aliyun-java-sdk-core -Dversion=3.2.3 -Dpackaging=jar

start mvn install:install-file -Dfile=aliyun-java-sdk-dysmsapi-1.0.0-SANPSHOT.jar -DgroupId=com.aliyun -DartifactId=aliyun-java-sdk-dysmsapi -Dversion=1.0.0-SANPSHOT -Dpackaging=jar

echo ok
pause