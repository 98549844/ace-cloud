# FROM 指定基础镜像
FROM unitfinance/jdk17-sbt-scala:latest

MAINTAINER Garlam Au

#VOLUME /tmp
ADD target/ace-entities-module-1.0.jar ace-entities-module.jar
EXPOSE 8000
# 暴露端口 与配置文件对应
ENTRYPOINT ["java","-jar","ace-entities-module.jar","--spring.profiles.active=docker"]

