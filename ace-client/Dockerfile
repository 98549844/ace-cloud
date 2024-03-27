# FROM 指定基础镜像
FROM unitfinance/jdk17-sbt-scala:latest

MAINTAINER Garlam Au

#VOLUME /tmp
ADD target/ace-client-1.0.jar ace-client.jar
EXPOSE 8090
# 暴露端口 与配置文件对应
ENTRYPOINT ["java","-jar","ace-client.jar","--spring.profiles.active=docker"]
