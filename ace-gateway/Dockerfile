# FROM 指定基础镜像
FROM unitfinance/jdk17-sbt-scala:latest

MAINTAINER Garlam Au

#VOLUME /tmp
ADD target/ace-gateway-1.0.jar ace-gateway.jar
EXPOSE 9500
# 暴露端口 与配置文件对应
ENTRYPOINT ["java","-jar","ace-gateway.jar","--spring.profiles.active=docker"]

